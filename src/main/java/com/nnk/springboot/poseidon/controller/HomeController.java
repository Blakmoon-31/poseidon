package com.nnk.springboot.poseidon.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	private static Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping("/")
	public String home(Model model) {
		logger.info("Redirecting to page /home");

		return "/home";
	}

	@RequestMapping("/admin/home")
	public String adminHome(Model model) {
		logger.info("Redirecting to page bidlist's list");

		return "redirect:/bidList/list";
	}

}
