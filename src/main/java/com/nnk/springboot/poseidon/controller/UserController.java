package com.nnk.springboot.poseidon.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@RequestMapping("/user/list")
	public String homeUserList(Model model) {
		logger.info("User list requested");

		model.addAttribute("users", userService.getUsers());
		return "user/list";
	}

	@GetMapping("/user/add")
	public String addUserForm(UserDto userDto) {
		logger.info("User add form requested");

		return "user/add";
	}

	@PostMapping("/user/validate")
	public String validateUser(@Valid UserDto userDto, BindingResult result, Model model) {
		logger.info("Adding user requested");

		if (!result.hasErrors()) {
			logger.info("Saving user");

			String saveResult = userService.saveUser(userDto);
			if (saveResult == "User saved") {
				logger.info("User saved, redirecting to list");

				model.addAttribute("users", userService.getUsers());
				return "redirect:/user/list";
			} else {
				logger.debug("User not saved because of invalid password, return to add form with error message");

				ObjectError error = new ObjectError("globalError",
						"Password must be at least 8 characters and content at least one digit, one upper case letter and one special character");
				result.addError(error);
				return "user/add";
			}
		}
		logger.debug("Invalid data, return to add form");

		return "user/add";
	}

	@GetMapping("/user/update/{id}")
	public String showUpdateUserForm(@PathVariable("id") Integer id, Model model) {
		logger.info("User update form for id " + id + " requested");

		UserDto userDto = userService.getUserById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		userDto.setPassword("");
		model.addAttribute("userDto", userDto);
		return "user/update";
	}

	@PutMapping("/user/update/{id}")
	public String updateUser(@PathVariable("id") Integer id, @Valid UserDto userDto, BindingResult result,
			Model model) {
		logger.info("Updating user with id " + id + " requested");

		if (!result.hasErrors()) {
			logger.info("Updating user with password format control");

			userDto.setId(id);
			String saveResult = userService.saveUser(userDto);
			if (saveResult == "User saved") {
				logger.info("User updated, redirecting to list");

				model.addAttribute("users", userService.getUsers());
				return "redirect:/user/list";
			} else {
				logger.debug("User not updated because of invalid password, return to add form with error message");

				ObjectError error = new ObjectError("globalError",
						"Password must be at least 8 characters and content at least one digit, one upper case letter and one special character");
				result.addError(error);
				return "/user/update";
			}

		}
		logger.debug("Invalid data for user with id " + id + ", return to update form");

		return "/user/update";
	}

	@GetMapping("/user/delete/{id}")
	public String deleteUser(@PathVariable("id") Integer id, Model model) {
		logger.info("Deleting user with id " + id + " requested");

		UserDto userDtoToDelete = userService.getUserById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		userService.deleteUser(userDtoToDelete);
		model.addAttribute("users", userService.getUsers());
		return "redirect:/user/list";
	}
}
