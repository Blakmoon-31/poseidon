package com.nnk.springboot.poseidon.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.poseidon.domain.User;
import com.nnk.springboot.poseidon.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/user/list")
	public String home(Model model) {
		model.addAttribute("users", userService.getUsers());
		return "user/list";
	}

	@GetMapping("/user/add")
	public String addUser(User bid) {
		return "user/add";
	}

	@PostMapping("/user/validate")
	public String validate(@Valid User user, BindingResult result, Model model) {
		if (!result.hasErrors()) {
			String saveResult = userService.saveUser(user);
			if (saveResult == "User saved") {
				model.addAttribute("users", userService.getUsers());
				return "redirect:/user/list";
			} else {
				model.addAttribute("ErrPassword",
						"Password must be at least 8 characters and content at least one digit, one upper case letter and one special character");
				return "user/add";
			}
		}
		return "user/add";
	}

	@GetMapping("/user/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		User user = userService.getUserById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		user.setPassword("");
		model.addAttribute("user", user);
		return "user/update";
	}

	@PutMapping("/user/update/{id}")
	public String updateUser(@PathVariable("id") Integer id, @Valid User user, BindingResult result, Model model) {
		if (!result.hasErrors()) {
			user.setId(id);
			String saveResult = userService.saveUser(user);
			if (saveResult == "User saved") {
				model.addAttribute("users", userService.getUsers());
				return "redirect:/user/list";
			} else {
				model.addAttribute("ErrPassword",
						"Password must be at least 8 characters and content at least one digit, one upper case letter and one special character");
				return "user/add";
			}

		}

		return "user/update";
	}

	@GetMapping("/user/delete/{id}")
	public String deleteUser(@PathVariable("id") Integer id, Model model) {
		User userToDelete = userService.getUserById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		userService.deleteUser(userToDelete);
		model.addAttribute("users", userService.getUsers());
		return "redirect:/user/list";
	}
}
