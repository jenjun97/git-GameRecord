package com.GameRecord.score;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ScoreController {
	@Autowired
	ScoreService scoreService;

	// 新記分表
	@RequestMapping("score_new")
	public String queryScoreInfo(@ModelAttribute("deskUuid") String deskUuid, Model model) {
		model = scoreService.queryScoreInfo(deskUuid, model);
		return "score_info";
	}

}
