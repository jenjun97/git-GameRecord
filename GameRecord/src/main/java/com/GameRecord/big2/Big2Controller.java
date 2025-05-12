package com.GameRecord.big2;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/big2")
public class Big2Controller {

	// 導頁
	@PostMapping("/big2_create_game")
	public String add_game_site(@RequestParam("player_num") int gamePlayNum, Model model) {
		model.addAttribute("gamePlayNum", gamePlayNum);
		return "big2/big2_create_game";
	}

	// 新增遊戲場次
	@PostMapping("big2_add_game_info")
	public String addNewGame(@RequestParam("game_title") String gameTitle,
			@RequestParam("players") List<String> players, Model model) {
		return "redirect:/index";
	}

}
