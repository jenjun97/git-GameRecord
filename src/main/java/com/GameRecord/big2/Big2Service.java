package com.GameRecord.big2;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GameRecord.big2.dto.request.AddPlayerRequestDto;
import com.GameRecord.big2.dto.response.RoundScoreDto;
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
	public ScoreboardResponseDto showRecordList(String deskUuid) {
		// query玩家姓名
		List<String> playerNameList = queryPlayerName(deskUuid);

		// query玩家每把分數
		List<List<Integer>> roundScoreList = queryScore(deskUuid);

		// query玩家總數
		List<Integer> sumScoreList = querySumScore(deskUuid);

		// 初始化返回物件
		ScoreboardResponseDto scoreboardResponseDto = new ScoreboardResponseDto();
		scoreboardResponseDto.deskUuid = deskUuid;
		scoreboardResponseDto.playerNameList = playerNameList;
		scoreboardResponseDto.roundScoreList = roundScoreList;
		scoreboardResponseDto.sumScoreList = sumScoreList;
		return scoreboardResponseDto;

	}

	/**
	 * 驗證輸入玩家人數
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
		List<RoundScoreDto> queryDtoList = big2Dao.queryScore(deskUuid);

		// 二維list放入每把分數
		List<List<Integer>> roundScoreList = new ArrayList();

		// 把同樣的一把分數，建list放進去
		int roundNo = 0;
		for (RoundScoreDto queryDto : queryDtoList) {
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
		int deskId = big2Dao.addDesk(deskUuid, requestDto.getDesk_name(), MySourceProperties.get("kind.big2"),
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
