package com.nnk.springboot.poseidon.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nnk.springboot.poseidon.service.UserService;

@Controller
public class LoginController {

	private static Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserService userService;

	@GetMapping("login")
	public ModelAndView login() {
		logger.info("Mapping to page /trade/list");

		ModelAndView mav = new ModelAndView();
		mav.setViewName("trade/list");
		return mav;
	}

	@GetMapping("/app/secure/article-details")
	public ModelAndView getAllUserArticles() {
		logger.info("Mapping to page /user/list");

		ModelAndView mav = new ModelAndView();
		mav.addObject("users", userService.getUsers());
		mav.setViewName("user/list");
		return mav;
	}

	@GetMapping({ "/error", "/403" })
	public ModelAndView error() {
		logger.info("Mapping to page /403");

		ModelAndView mav = new ModelAndView();
		String errorMessage = "You are not authorized for the requested data.";
		mav.addObject("errorMsg", errorMessage);
		mav.setViewName("403");
		return mav;
	}

}
