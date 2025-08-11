package com.GameRecord.score;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.GameRecord.entitys.DeskEntity;
import com.GameRecord.entitys.PlayerEntity;
import com.GameRecord.entitys.ScoreEntity;
import com.GameRecord.repository.DeskRepository;
import com.GameRecord.repository.PlayersRepository;
import com.GameRecord.repository.ScoreRepository;
import com.GameRecord.utils.MyDateTimeUtil;

@Service
public class ScoreService {

	@Autowired
	private DeskRepository deskRepository;

	@Autowired
	private PlayersRepository playersRepository;

	@Autowired
	private ScoreRepository scoreRepository;

	// 組合記分表的資料
	public Model queryScoreInfo(String deskUuid, Model model) {
		// 取得deskId
		DeskEntity deskEntity = deskRepository.findByUuid(deskUuid);
		Integer deskId = deskEntity.getId();
		model.addAttribute("deskUuid", deskUuid);

		// 取得玩家資料
		List<PlayerEntity> playerEntityList = playersRepository.findByFkDeskOrderByIdAsc(deskId);
		model.addAttribute("playerEntityList", playerEntityList);
		
		// 取得每位玩家的總分
		List<Integer> totalScoresList = queryTotalScores(playerEntityList);
		model.addAttribute("totalScoresList", totalScoresList);

		// 取得最後局數
		Integer lastRound = queryLastRound(playerEntityList);
		model.addAttribute("lastRound", lastRound);
		
		// 取得分記錄資料
		List<List<Integer>> scoreList = queryHisScores(playerEntityList, lastRound);
		model.addAttribute("scoreList", scoreList);

		return model;
	}
	
	// 取得最後局數
	private Integer queryLastRound(List<PlayerEntity> playerEntityList) {
		// 取得第一位玩家的id
		Integer playerId = playerEntityList.get(0).getId();
		// 取得該玩家的最大局數
		Integer maxRound = scoreRepository.findMaxRoundByFkPlayers(playerId);
		if (maxRound == null) {
			// 如果沒有找到，則返回0
			return 0;
		}
		return maxRound;
	}

	// 取得每位玩家的總分
	private List<Integer> queryTotalScores(List<PlayerEntity> playerEntityList) {
		// 初始化返回物件
		List<Integer> totalScoresList = new ArrayList<>();
	    // 取得玩家id列表
		List<Integer> playIdList = playerEntityList.stream().map(PlayerEntity::getId).toList();
		// 取得每位玩家的總分
		List<List<Integer>> totalScoreList = scoreRepository.findTotalScore(playIdList);
		
		for (PlayerEntity playerEntity : playerEntityList) {
			Integer playerId = playerEntity.getId();
			for (List<Integer> row : totalScoreList) {
				if (row.get(0).equals(playerId)) {
					// 將總分數加入玩家物件中
					totalScoresList.add(row.get(1));
					break;
				}
			}
		}
		return totalScoresList;
	}

	// 取得分數記錄資料
	private List<List<Integer>> queryHisScores(List<PlayerEntity> playerEntityList, int lastRound) {
		// 取得最後局數
		// 取得該場次玩家的所有id和所有分數列表
		List<Integer> playIdList = playerEntityList.stream().map(PlayerEntity::getId).toList();
		List<List<Integer>> queryScoreList = scoreRepository.queryScoreByIdList(playIdList);

		// 組合記分表資料
		// 返回物件
		List<List<Integer>> scoreList = new ArrayList<>();

		// 防呆, 如果沒有局數或分數資料，直接返回空列表
		if ( lastRound == 0 || queryScoreList.size() == 0) {
			scoreList.add(new ArrayList());
			return scoreList;
		}

		// 排序player id (建立可變版本)
		playIdList = new ArrayList<>(playIdList);
		Collections.sort(playIdList);

		for (int roundIndex = 1; roundIndex <= lastRound; roundIndex++) {
			List<Integer> scoreRow = new ArrayList<>();
			for (PlayerEntity playerEntity : playerEntityList) {
				// 預設分數
				Integer score = 0;
				Integer playerId = playerEntity.getId();
				// 在查詢結果中尋找對應的分數
				for (List<Integer> row : queryScoreList) {
					// 如果該行的輪次和玩家id符合
					if (row.get(0).equals(roundIndex) && row.get(1).equals(playerId)) {
						// 取得分數
						score = row.get(2);
						// 找到對應的分數後跳出內層循環
						break;
					}
				}
				// 將分數加入該玩家的分數行
				scoreRow.add(score);
			}
			// 將該輪的分數行加入總分數列表
			scoreList.add(scoreRow);
		}
		return scoreList;
	}

	// 新增分數
	public Model addScore(String deskUuid, List<Integer> playerIdList, List<Integer> scoreList, Model model) {
		// 驗證player id不為空
		validatePlayerIdNotNull(playerIdList);

		// 驗證玩家id是該uuid的場次
		validatePlayerIdInDesk(deskUuid, playerIdList);

		// 驗證score只能有1個null, 且一定要有1個null, 且其他全為正數
		boolean validateScoreCorrect = validateScoreListCorrect(scoreList);
		if (!validateScoreCorrect) {
			return model.addAttribute("msg", "輸入失分有錯，請重新輸入"); // 返回錯誤訊息
		}

		// 計算得分並替換null值
		scoreList = countWinScore(scoreList);

		// 驗證玩家數量與分數數量匹配
		if (playerIdList.size() != scoreList.size()) {
			throw new IllegalArgumentException("玩家數量與分數數量不匹配");
		}

		// 上面驗證都沒有問題，則新增分數到資料庫
		addScoreToDatabase(playerIdList, scoreList);

		return model;

	}

	// 新增分數到資料庫
	private void addScoreToDatabase(List<Integer> playerIdList, List<Integer> scoreList) {
		// 用玩家id取得下一輪的數字
		Integer nextRound = scoreRepository.findMaxRoundByFkPlayers(playerIdList.get(0));
		if (nextRound == null) {
			nextRound = 1; // 如果沒有找到，則從第一輪開始
		} else {
			nextRound++; // 否則加一
		}

		Timestamp nowTimestamp = MyDateTimeUtil.getNowTimestamp();
		for (int i = 0; i < playerIdList.size(); i++) {
			Integer playerId = playerIdList.get(i);
			Integer score = scoreList.get(i);
			// 新增分數到資料庫
			ScoreEntity entity = new ScoreEntity();
			entity.setRound(nextRound);
			entity.setFkPlayers(playerId);
			entity.setScore(score);
			entity.setDatime(nowTimestamp);
			scoreRepository.save(entity);
		}
	}

	// 計算得分並替換null值
	private List<Integer> countWinScore(List<Integer> scoreList) {
		int winScore = 0;
		// 計算總得分
		for (Integer score : scoreList) {
			if (score != null) {
				winScore += score;
			}
		}

		// 替換null值及原本的正數變負數
		for (int i = 0; i < scoreList.size(); i++) {
			if (scoreList.get(i) == null) {
				scoreList.set(i, winScore);
			} else {
				scoreList.set(i, 0 - scoreList.get(i));
			}
		}

		return scoreList;
	}

	// 驗證score只能有一個null, 且其他全為正數
	private boolean validateScoreListCorrect(List<Integer> scoreList) {
		if (Collections.frequency(scoreList, null) != 1) {
			return false;
		}
		for (Integer score : scoreList) {
			if (score != null && score < 1) {
				return false;
			}
		}
		return true;
	}

	// 驗證玩家id是該uuid的場次
	private void validatePlayerIdInDesk(String deskUuid, List<Integer> playerIdList) {
		int deskId = deskRepository.findByUuid(deskUuid).getId();
		List<Integer> queryPlayerIdList = playersRepository.findByFkDeskOrderByIdAsc(deskId).stream()
				.map(PlayerEntity::getId).toList();
		// 比較2個列表是否相同
		if (!queryPlayerIdList.containsAll(playerIdList)) {
			throw new IllegalArgumentException("玩家 id 不在該場次中");
		}
	}

	// 驗證player id不為空
	private void validatePlayerIdNotNull(List<Integer> playerIdList) {
		playerIdList.stream().forEach(id -> {
			if (id == null) {
				throw new IllegalArgumentException("玩家 id 為 null");
			}
		});
	}

}
