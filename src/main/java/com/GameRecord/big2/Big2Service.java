package com.GameRecord.big2;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GameRecord.big2.dto.AddScorePojo;
import com.GameRecord.big2.dto.request.AddPlayerRequestDto;
import com.GameRecord.big2.dto.request.AddScoreRequestDto;
import com.GameRecord.big2.dto.response.HisDeskResponseDto;
import com.GameRecord.big2.dto.response.RoundScoreResponseDto;
import com.GameRecord.big2.dto.response.ScoreboardResponseDto;
import com.GameRecord.utils.MyDateTimeUtil;
import com.GameRecord.utils.MySourceProperties;

/**
 * 新增場次及玩家
 */
@Service
public class Big2Service {

	@Autowired
	Big2Dao big2Dao;

	public void addScore(AddScoreRequestDto addScoreRequestDto) {
		// 用場次代號取出玩家的id
		List<Integer> playerIdList = big2Dao.queryPlayerId(addScoreRequestDto.getDeskUuid());

		// 用玩家id取出新的roundNo
		int nextRoundNum = getNextRoundNum(playerIdList.get(0));

		// 組dto：新的roundNo, 玩家id, score
		List<AddScorePojo> addScorePojoList = getAddScorePojoList(nextRoundNum, playerIdList,
				addScoreRequestDto.getScore());
		// 將dto寫入
		addScore(addScorePojoList);
	}

	// insert分數
	private void addScore(List<AddScorePojo> addScorePojoList) {
		for (AddScorePojo scorePojo : addScorePojoList) {
			big2Dao.addScore(scorePojo.roundNo, scorePojo.fkPlayerId, scorePojo.score);
		}
	}

	// 新增場次及玩家
	public String addBig2Game(AddPlayerRequestDto requestDto) throws Exception {
		// 防呆
		validates(requestDto.getPlayerName());

		// 新增場次，取得id
		String deskUuid = UUID.randomUUID().toString();
		int deskId = addDesk(deskUuid.toString(), requestDto);

		// 新增玩家
		addPlayers(deskId, requestDto.getPlayerName());

		return deskUuid;

	}

	/**
	 * 列出現在所有的比分
	 * 
	 * @param deskUuid
	 * @return
	 */
	public ScoreboardResponseDto showScoreList(String deskUuid) {
		// 初始化返回物件
		ScoreboardResponseDto scoreboardResponseDto = new ScoreboardResponseDto();

		scoreboardResponseDto.deskUuid = deskUuid;

		// query玩家姓名
		scoreboardResponseDto.playerNameList = queryPlayerName(deskUuid);

		// query玩家每把分數
		scoreboardResponseDto.roundScoreList = queryScore(deskUuid);

		// query玩家總分
		int playerNum = scoreboardResponseDto.playerNameList.size();
		scoreboardResponseDto.sumScoreList = initialSumScore(playerNum);

		return scoreboardResponseDto;

	}

	/**
	 * 驗證輸入玩家人數
	 * 
	 * @param playerNum
	 * @return
	 */
	public boolean validPlayerNum(int playerNum) {
		int big2PlayerMax = Integer.parseInt(MySourceProperties.get("big2.player.max"));
		if (playerNum <= 0 || playerNum > big2PlayerMax) {
			return false;
		}
		return true;
	}

	/**
	 * query歷史場次
	 * 
	 * @return
	 */
	public List<HisDeskResponseDto> queryHisDesk() {
		String kindStr = MySourceProperties.get("kind.big2");
		List<HisDeskResponseDto> queryHisDesk = big2Dao.queryHisDesk(kindStr);
		return queryHisDesk;
	}

	/**
	 * 取得新增比分的pojo
	 * 
	 * @param nextRoundNum
	 * @param playerIdList
	 * @param screList
	 * @return
	 */
	private List<AddScorePojo> getAddScorePojoList(int nextRoundNum, List<Integer> playerIdList,
			List<Integer> screList) {
		// 初始化物件
		List<AddScorePojo> addScoreDtoList = new ArrayList();
		for (int i = 0; i < playerIdList.size(); i++) {
			Integer playerId = playerIdList.get(i);
			Integer score = screList.get(i);
			AddScorePojo addScorePojo = new AddScorePojo(nextRoundNum, playerId, score);
			addScoreDtoList.add(addScorePojo);
		}
		return addScoreDtoList;
	}

	/**
	 * 取得本次是幾第把的號次
	 * 
	 * @param playerId
	 * @return
	 */
	private int getNextRoundNum(int playerId) {
		Integer lastRoundNum = big2Dao.queryLastRoundNum(playerId);
		int nextRoundNum = 0;
		if (lastRoundNum == null) {
			nextRoundNum = 1;
		} else {
			nextRoundNum = lastRoundNum + 1;
		}
		return nextRoundNum;
	}

	/**
	 * 初始化總分列表
	 * 
	 * @param num
	 * @return
	 */
	private List<Integer> initialSumScore(int num) {
		List<Integer> initialList = new ArrayList();
		for (int i = 0; i < num; i++) {
			initialList.add(0);
		}
		return initialList;
	}

	/**
	 * query玩家總數
	 * 
	 * @param deskUuid
	 * @return
	 */
	private List<Integer> querySumScore(String deskUuid) {
		List<Integer> sumScoreList = big2Dao.querySumScore(deskUuid);
		return sumScoreList;
	}

	/**
	 * query玩家姓名
	 * 
	 * @param deskUuid
	 */
	private List<String> queryPlayerName(String deskUuid) {
		return big2Dao.queryPlayerName(deskUuid);
	}

	/**
	 * query歷史分數
	 * 
	 * @param deskUuid
	 * @return
	 */
	private List<List<Integer>> queryScore(String deskUuid) {
		// query場次裡所有的歷史分數
		List<RoundScoreResponseDto> queryDtoList = big2Dao.queryScore(deskUuid);

		// 二維list放入每把分數
		List<List<Integer>> roundScoreList = new ArrayList();

		// 把同樣的一把分數，建list放進去
		int roundNo = 0;
		for (RoundScoreResponseDto queryDto : queryDtoList) {
			if (queryDto.getRoundNo() != roundNo) {
				roundNo = queryDto.getRoundNo();
				roundScoreList.add(new ArrayList());
			}
			roundScoreList.get(roundScoreList.size() - 1).add(queryDto.getScore());
		}

		return roundScoreList;
	}

	/**
	 * 新增玩家
	 * 
	 * @param deskId
	 * @param playerName
	 */
	private void addPlayers(int deskId, List<String> playerName) {

		int index = 0;
		for (String name : playerName) {
			index++;
			big2Dao.addPlayers(deskId, index, name);
		}
	}

	/**
	 * 新增場次
	 * 
	 * @param requestDto
	 * @return
	 */
	private int addDesk(String deskUuid, AddPlayerRequestDto requestDto) {
		int deskId = big2Dao.addDesk(deskUuid, requestDto.getDeskName(), MySourceProperties.get("kind.big2"),
				MyDateTimeUtil.getNowTimestamp());
		return deskId;
	}

	// 防呆檢查
	private void validates(List<String> nameList) throws Exception {
		for (String name : nameList) {
			if (name == null || name.isBlank()) {
				throw new Exception("玩家名稱不得為空");
			}
		}
	}

}
