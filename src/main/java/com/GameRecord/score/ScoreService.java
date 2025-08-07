package com.GameRecord.score;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.GameRecord.entitys.DeskEntity;
import com.GameRecord.entitys.PlayerEntity;
import com.GameRecord.repository.DeskRepository;
import com.GameRecord.repository.PlayersRepository;
import com.GameRecord.repository.ScoreRepository;

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

		// 用deskId取得players資料
		List<PlayerEntity> playerEntityList = playersRepository.findByFkDeskOrderByIdAsc(deskId);

		// 用id list取得分數資料
		List<Integer> playIdList = playerEntityList.stream().map(PlayerEntity::getId).toList();
		List<PlayerScoreDTO> PlayerScoreDTOList = scoreRepository.queryScoreByIdList(playIdList);
		List<List<Integer>> scoreList = getScoreList(PlayerScoreDTOList, playIdList);

		model.addAttribute("deskUuid", deskUuid);
		model.addAttribute("playerEntityList", playerEntityList);
		model.addAttribute("scoreList", scoreList);

		return model;
	}

	// 組分數列表資料
	private List<List<Integer>> getScoreList(List<PlayerScoreDTO> playerScoreDTOList, List<Integer> playIdList) {

		// 返回物件
		List<List<Integer>> scoreList = new ArrayList();
		// 從第一輪開始
		int round = 1;
		while (playerScoreDTOList.size() > 0) {
			List<Integer> scoreRow = new ArrayList<>();
			for (int playerId : playIdList) {
				for (PlayerScoreDTO playerScoreDTO : playerScoreDTOList) {
					// 如果找到對應的 playerId 和 round，則加入分數到 scoreRow
					if (playerScoreDTO.getPlayerId() == playerId && playerScoreDTO.getRound() == round) {
						scoreRow.add(playerScoreDTO.getScore());
						playerScoreDTOList.remove(playerScoreDTO);
						break; // 找到對應的分數後跳出內層循環
					}
				}
			}
			round++;
			scoreList.add(scoreRow);
		}
		return scoreList;
	}

}
