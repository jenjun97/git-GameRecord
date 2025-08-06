package com.GameRecord.desk;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DeskController {

	@Autowired
	private DeskService deskService;

	// 歷史記錄頁
	@RequestMapping("/desk_info")
	public String deskInfo() {
		return "desk_info";
	}

	// 新增場次
	@GetMapping("desk_creat")
	public String deskCreat(@RequestParam("playerCount") int playerCount, Model model) {
		model.addAttribute("playerCount", playerCount);
		return "desk_creat";
	}

	// 儲存新增場次資訊
	@PostMapping("desk_save")
	public String deskSave(@RequestParam("deskName") String deskName,
			@RequestParam("playerName") List<String> playerNameList, RedirectAttributes redirectAttributes) {
		// 傳遞場次uuid
		String deskUuid = deskService.saveDesk(deskName, playerNameList);
		redirectAttributes.addFlashAttribute("deskUuid", deskUuid);
		return "redirect:/score_new";
	}

}
