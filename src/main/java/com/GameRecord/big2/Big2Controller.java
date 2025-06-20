package com.GameRecord.big2;

import java.util.List;

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
import com.GameRecord.big2.dto.request.AddScoreRequestDto;
import com.GameRecord.big2.dto.response.HisDeskResponseDto;
import com.GameRecord.big2.dto.response.ScoreboardResponseDto;

@Controller
@RequestMapping("/big2")
public class Big2Controller {

	@Autowired
	Big2Service big2Service;

	// 老大二首頁，列出歷史場次
	@GetMapping("/big2_index")
	public String big2List(Model model) {
		List<HisDeskResponseDto> hisDeskResponseDto = big2Service.queryHisDesk();
		model.addAttribute("hisDeskResponseDto", hisDeskResponseDto);
		return "big2/big2_index";
	}

	// 新增玩家人數
	@PostMapping("/new_big2")
	public String add_game_site(@RequestParam(value = "playerNum", required = false, defaultValue = "0") int playerNum,
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
		return "big2/new_big2";
	}

	// 新增玩家姓名
	@PostMapping("/add_player")
	public String addNewBig2Game(@ModelAttribute AddPlayerRequestDto requestDto, Model model) {
		String deskUuid = null;
		try {
			deskUuid = big2Service.addBig2Game(requestDto);
		} catch (Exception e) {
			e.printStackTrace();
			return "big2/error";
		}
		return "redirect:/big2/score_list/" + deskUuid;
	}

	// 列出現在所有的比分
	@GetMapping("/score_list/{deskUuid}")
	public String showRecordList(@PathVariable String deskUuid, Model model) {
		ScoreboardResponseDto scoreboardResponseDto = big2Service.showScoreList(deskUuid);
		model.addAttribute("scoreboardResponseDto", scoreboardResponseDto);
		return "big2/score_list";
	}

	// 增加比分
	@PostMapping("/add_score")
	public String addScore(@ModelAttribute AddScoreRequestDto addScoreRequestDto, Model model) {
		big2Service.addScore(addScoreRequestDto);
		return "redirect:/big2/score_list/" + addScoreRequestDto.getDeskUuid();
	}
	
	// 修改比分
	@PostMapping("/fix_score")
	public String fixScore(@RequestParam("deskUuid") String deskUuid, @RequestParam("roundNum") int roundNum, Model model) {
		System.out.println(deskUuid);
		System.out.println(roundNum);
		return "redirect:/big2/score_list/" + deskUuid;
	}
	
	

}
