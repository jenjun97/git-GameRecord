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
		deskUuid = "8c3d7feae4d04f21a885d2d7ae32d8d5";
		// 取得deskId
		DeskEntity deskEntity = deskRepository.findByUuid(deskUuid);
		Integer deskId = deskEntity.getId();

		// 用deskId取得players資料
		List<PlayerEntity> playerEntityList = playersRepository.findByFkDeskOrderByIdAsc(deskId);

		List<Integer> playIdList = playerEntityList.stream().map(PlayerEntity::getId).toList();
		List<PlayerScoreDTO> PlayerScoreDTOList = scoreRepository.queryScoreByIdList(playIdList);
		List<List<Integer>> scoreList = getScoreList(PlayerScoreDTOList);

		model.addAttribute("deskUuid", deskUuid);
		model.addAttribute("playerEntityList", playerEntityList);
		model.addAttribute("scoreList", scoreList);

		return model;
	}

	private List<List<Integer>> getScoreList(List<PlayerScoreDTO> playerScoreDTOList) {

		List<List<Integer>> scoreList = new ArrayList();


		return scoreList;
	}

}
