package com.GameRecord.big2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/big2")
public class Big2Controller {

	@Autowired
//	Big2Service big2Service;
	Big2AddPlayerService big2AddPlayerService;

	// 導頁
	@PostMapping("/new_players")
	public String add_game_site(@RequestParam("player_num") int playerNum, Model model) {
		model.addAttribute("playerNum", playerNum);
		return "big2/input_player";
	}

	// 新增場次及玩家
	@PostMapping("/add_player")
	public String addNewBig2Game(@RequestParam("playerName") List<String> nameList, Model model) {
		try {
			int deskId = big2AddPlayerService.addBig2Game(nameList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/index";
	}

}
