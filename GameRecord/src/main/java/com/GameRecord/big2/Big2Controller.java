package com.GameRecord.big2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/big2")
public class Big2Controller {

	// 導頁
	@GetMapping("/add_game_site")
	public String add_game_site() {
		System.out.println("Big2Controller.add_game_site()");
		return "big2/add_game_site";
	}

	// 新增遊戲場次
	@PostMapping("add_new_game")
	public String addNewGame(@RequestParam("game_title") String gameTitle,
			@RequestParam("game_play_num") String gamePlayNum) {
		System.out.println(gameTitle);
		System.out.println(gamePlayNum);
		return "addPlayer";
	}
}
