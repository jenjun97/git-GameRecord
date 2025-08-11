package com.GameRecord.score;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ScoreController {
	@Autowired
	ScoreService scoreService;

	// 查詢記分表
	@GetMapping("/score_info/{uuid}")
	public String viewDesk(@PathVariable("uuid") String deskUuid, Model model) {
		// 這裡你應該從資料庫或記憶體中查詢場次資訊
		model = scoreService.queryScoreInfo(deskUuid, model);

		// 傳回的 Thymeleaf 頁面名稱（例如 desk_detail.html）
		return "score_info";
	}

	// 新增分數
	@PostMapping("/add_score")
	public String addScore(@RequestParam("deskUuid") String deskUuid,
			@RequestParam("playerId") List<Integer> playerIdList, @RequestParam("score") List<Integer> scoreList,
			Model model) {

		// 新增比分
		model = scoreService.addScore(deskUuid, playerIdList, scoreList, model);

		// 查詢記分表
		model = scoreService.queryScoreInfo(deskUuid, model);
		// 傳回的 Thymeleaf 頁面名稱（例如 desk_detail.html）
		return "score_info";
	}

}
