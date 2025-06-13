package com.GameRecord.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	// 導頁
	@RequestMapping({ "/", "/index", "/index.html", "" })
	public String index() {
		return "index_list";
	}

	// 大老二導頁
	@GetMapping("/big2")
	public String big2() {
		return "big2/big2_list";
	}
}
