package com.nnk.springboot.poseidon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nnk.springboot.poseidon.service.UserService;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;

	@GetMapping("login")
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("trade/list");
		return mav;
	}

	@GetMapping("/app/secure/article-details")
	public ModelAndView getAllUserArticles() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("users", userService.getUsers());
		mav.setViewName("user/list");
		return mav;
	}

	@GetMapping("/error")
	public ModelAndView error() {
		ModelAndView mav = new ModelAndView();
		String errorMessage = "You are not authorized for the requested data.";
		mav.addObject("errorMsg", errorMessage);
		mav.setViewName("403");
		return mav;
	}
}
