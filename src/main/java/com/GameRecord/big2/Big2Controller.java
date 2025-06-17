package com.GameRecord.big2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.GameRecord.big2.dto.request.AddPlayerRequestDto;

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
	public String addNewBig2Game(@ModelAttribute AddPlayerRequestDto requestDto, Model model) {
		try {
			int deskId = big2AddPlayerService.addBig2Game(requestDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/index";
	}

}
