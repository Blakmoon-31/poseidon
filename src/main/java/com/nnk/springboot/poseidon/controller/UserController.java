package com.nnk.springboot.poseidon.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.poseidon.dto.UserDto;
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
	public String addUser(UserDto userDto) {
		return "user/add";
	}

	@PostMapping("/user/validate")
	public String validate(@Valid UserDto userDto, BindingResult result, Model model) {
		if (!result.hasErrors()) {
			String saveResult = userService.saveUser(userDto);
			if (saveResult == "User saved") {
				model.addAttribute("users", userService.getUsers());
				return "redirect:/user/list";
			} else {

				ObjectError error = new ObjectError("globalError",
						"Password must be at least 8 characters and content at least one digit, one upper case letter and one special character");
				result.addError(error);
				return "user/add";
			}
		}
		return "user/add";
	}

	@GetMapping("/user/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		UserDto userDto = userService.getUserById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		userDto.setPassword("");
		model.addAttribute("userDto", userDto);
		return "user/update";
	}

	@PutMapping("/user/update/{id}")
	public String updateUser(@PathVariable("id") Integer id, @Valid UserDto userDto, BindingResult result,
			Model model) {
		if (!result.hasErrors()) {
			userDto.setId(id);
			String saveResult = userService.saveUser(userDto);
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
		UserDto userDtoToDelete = userService.getUserById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		userService.deleteUser(userDtoToDelete);
		model.addAttribute("users", userService.getUsers());
		return "redirect:/user/list";
	}
}
