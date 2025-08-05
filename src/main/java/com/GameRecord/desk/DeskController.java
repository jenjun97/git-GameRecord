package com.GameRecord.desk;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DeskController {

	@Autowired
	private DeskService deskService;

	// 歷史記錄頁
	@RequestMapping("/list_desk")
	public String list_desk() {
		return "list_desk";
	}

	// 新增場次
	@GetMapping("creat_desk")
	public String creatDesk(@RequestParam("playerCount") int playerCount, Model model) {
		model.addAttribute("playerCount", playerCount);
		return "creat_desk";
	}

	// 儲存新增場次資訊
	@PostMapping("save_desk")
	public String saveDesk(@RequestParam("deskName") String deskName,
			@RequestParam("playerName") List<String> playerNameList, Model model) {
		deskService.saveDesk(deskName, playerNameList, model);
		return "list_desk";
	}

}
