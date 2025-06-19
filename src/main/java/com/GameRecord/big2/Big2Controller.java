package com.GameRecord.big2;

import com.GameRecord.index.IndexController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
	public String add_game_site(@RequestParam(value = "player_num", required = false, defaultValue = "0") int playerNum,
			Model model) {
		try {
			if (!big2Service.validPlayerNum(playerNum)) {
				return "big2/error";
			}
			model.addAttribute("playerNum", playerNum);
		} catch (Exception e) {
			e.printStackTrace();
			return "big2/error";
		}
		return "big2/input_player";
	}

	// 新增場次及玩家
	@PostMapping("/add_player")
	public String addNewBig2Game(@ModelAttribute AddPlayerRequestDto requestDto, Model model) {
		ScoreboardResponseDto scoreboardResponseDto = null;
		String deskUuid = null;
		try {
			deskUuid = big2Service.addBig2Game(requestDto);
			model.addAttribute("scoreboardResponseDto", scoreboardResponseDto);
		} catch (Exception e) {
			e.printStackTrace();
			return "big2/error";
		}
		return "redirect:/big2/record_list/" + deskUuid;
	}

	// 列出現在所有的比分
	@GetMapping("/record_list/{deskUuid}")
	public String showRecordList(@PathVariable String deskUuid, Model model) {
		ScoreboardResponseDto scoreboardResponseDto = big2Service.showRecordList(deskUuid);
		model.addAttribute("", scoreboardResponseDto);
		return "big2/record_list";
	}

	// 增加比分
	@PostMapping("/add_record")
	public String addRecord(@PathVariable String deskUuid, Model model) {

		return "redirect:/big2/record_list/" + deskUuid;
	}

//	return "redirect:/index";
}
