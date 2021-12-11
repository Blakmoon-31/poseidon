package com.nnk.springboot.poseidon.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static Logger logger = LoggerFactory.getLogger(RatingController.class);

	@Autowired
	private RatingService ratingService;

	@RequestMapping("/rating/list")
	public String homeRatingList(Model model) {
		logger.info("Rating list requested");

		model.addAttribute("ratings", ratingService.getRatings());

		return "rating/list";
	}

	@GetMapping("/rating/add")
	public String addRatingForm(RatingDto ratingDto) {
		logger.info("Rating add form requested");

		return "rating/add";
	}

	@PostMapping("/rating/validate")
	public String validateRating(@Validated RatingDto ratingDto, BindingResult result, Model model) {
		logger.info("Adding rating requested");

		if (!result.hasErrors()) {
			logger.info("Adding new rating and redirect to list");

			ratingService.saveRating(ratingDto);
			model.addAttribute("ratings", ratingService.getRatings());
			return "redirect:/rating/list";
		}
		logger.debug("Invalid data, return to add form");

		return "rating/add";
	}

	@GetMapping("/rating/update/{id}")
	public String showUpdateRatingForm(@PathVariable("id") Integer id, Model model) {
		logger.info("Rating update form for id " + id + " requested");

		model.addAttribute("ratingDto", ratingService.getRatingById(id).get());

		return "rating/update";
	}

	@PutMapping("/rating/update/{id}")
	public String updateRating(@PathVariable("id") Integer id, @Valid RatingDto ratingDto, BindingResult result,
			Model model) {
		logger.info("Updating rating with id " + id + " requested");

		if (!result.hasErrors()) {
			logger.info("Rating updated, redirecting to list");

			ratingDto.setId(id);
			ratingService.saveRating(ratingDto);
			model.addAttribute("ratings", ratingService.getRatings());
			return "redirect:/rating/list";
		}
		logger.debug("Invalid data for rating with id " + id + ", return to update form");

		return "/rating/update/" + id;
	}

	@GetMapping("/rating/delete/{id}")
	public String deleteRating(@PathVariable("id") Integer id, Model model) {
		logger.info("Deleting rating with id " + id + " requested");

		RatingDto ratingDtoToDelete = ratingService.getRatingById(id).get();
		ratingService.deleteRating(ratingDtoToDelete);

		return "redirect:/rating/list";
	}
}
