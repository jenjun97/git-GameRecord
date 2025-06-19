package com.GameRecord.big2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.GameRecord.big2.dto.request.AddPlayerRequestDto;
import com.GameRecord.big2.dto.response.ScoreboardResponseDto;

@Controller
@RequestMapping("/big2")
public class Big2Controller {

	@Autowired
	Big2Service big2Service;

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
			ScoreboardResponseDto scoreboardResponseDto = big2Service.addBig2Game(requestDto);
			model.addAttribute("scoreboardResponseDto", scoreboardResponseDto);
		} catch (Exception e) {
			e.printStackTrace();
			return "big2/error";
		}
		return "big2/record_list";
	}

//	return "redirect:/index";
}
