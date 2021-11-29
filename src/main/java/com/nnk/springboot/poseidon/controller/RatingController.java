package com.nnk.springboot.poseidon.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.poseidon.dto.RatingDto;
import com.nnk.springboot.poseidon.service.RatingService;

@Controller
public class RatingController {

	@Autowired
	private RatingService ratingService;

	@RequestMapping("/rating/list")
	public String home(Model model) {

		model.addAttribute("ratings", ratingService.getRatings());

		return "rating/list";
	}

	@GetMapping("/rating/add")
	public String addRatingForm(RatingDto ratingDto) {
		return "rating/add";
	}

	@PostMapping("/rating/validate")
	public String validate(@Validated RatingDto ratingDto, BindingResult result, Model model) {
		// TODO: check data valid and save to db, after saving return Rating list

		if (!result.hasErrors()) {
			ratingService.saveRating(ratingDto);
			model.addAttribute("ratings", ratingService.getRatings());
			return "redirect:/rating/list";
		}

		return "rating/add";
	}

	@GetMapping("/rating/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

		model.addAttribute("ratingDto", ratingService.getRatingById(id).get());

		return "rating/update";
	}

	@PutMapping("/rating/update/{id}")
	public String updateRating(@PathVariable("id") Integer id, @Valid RatingDto ratingdto, BindingResult result,
			Model model) {
		// TODO: check required fields, if valid call service to update Rating and
		// return Rating list

		if (!result.hasErrors()) {
			ratingdto.setId(id);
			ratingService.saveRating(ratingdto);
			model.addAttribute("ratings", ratingService.getRatings());
			return "redirect:/rating/list";
		}

		return "/rating/update/" + id;
	}

	@GetMapping("/rating/delete/{id}")
	public String deleteRating(@PathVariable("id") Integer id, Model model) {

		RatingDto ratingDtoToDelete = ratingService.getRatingById(id).get();
		ratingService.deleteRating(ratingDtoToDelete);

		return "redirect:/rating/list";
	}
}
