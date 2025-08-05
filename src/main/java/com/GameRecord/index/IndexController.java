package com.GameRecord.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	// 導頁
	@RequestMapping({ "/", "/index", "/index.html" })
	public String index() {
		return "redirect:/list_desk";
	}
}
