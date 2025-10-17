package com.GameRecord.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	// 導頁
	@RequestMapping({ "/", "/index", "/index.html" })
	public String index() {
		return "redirect:/desk_info";
	}
	// 導頁
	@RequestMapping( "/game_info")
	public String gameInfo() {
		return "redirect:/game_info";
	}
}
