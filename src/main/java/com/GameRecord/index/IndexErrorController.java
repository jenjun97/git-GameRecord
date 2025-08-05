package com.GameRecord.index;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexErrorController implements ErrorController {
	// 重導錯誤頁面
	@RequestMapping("/error")
	public String direct_error() {
		return "redirect:/404";
	}

	// 導錯誤頁面
	@RequestMapping("/404")
	public String direct_404() {
		return "404";
	}
}
