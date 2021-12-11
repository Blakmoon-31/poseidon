package com.nnk.springboot.poseidon.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.poseidon.dto.CurvePointDto;
import com.nnk.springboot.poseidon.service.CurvePointService;

@Controller
public class CurvePointController {

	private static Logger logger = LoggerFactory.getLogger(CurvePointController.class);

	@Autowired
	private CurvePointService curvePointService;

	@RequestMapping("/curvePoint/list")
	public String homeCurvePointList(Model model) {
		logger.info("CurvePoint list requested");

		model.addAttribute("curvePoints", curvePointService.getCurvePoints());

		return "curvePoint/list";
	}

	@GetMapping("/curvePoint/add")
	public String addCurvePointForm(CurvePointDto curvePointDto) {
		logger.info("CurvePoint add form requested");

		return "curvePoint/add";
	}

	@PostMapping("/curvePoint/validate")
	public String validateCurvePoint(@Valid CurvePointDto curvePointDto, BindingResult result, Model model) {
		logger.info("Adding curvePoint requested");

		if (!result.hasErrors()) {
			logger.info("Adding new curvePoint and redirect to list");

			curvePointService.saveCurvePoint(curvePointDto);
			model.addAttribute("curvePoints", curvePointService.getCurvePoints());
			return "redirect:/curvePoint/list";
		}
		logger.debug("Invalid data, return to add form");

		return "curvePoint/add";
	}

	@GetMapping("/curvePoint/update/{id}")
	public String showUpdateCurvePointForm(@PathVariable("id") Integer id, Model model) {
		logger.info("CurvePoint update form for id " + id + " requested");

		model.addAttribute("curvePointDto", curvePointService.getCurvePointById(id).get());

		return "curvePoint/update";
	}

	@PutMapping("/curvePoint/update/{id}")
	public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePointDto curvePointDto,
			BindingResult result, Model model) {
		logger.info("Updating curvePoint with id " + id + " requested");

		if (!result.hasErrors()) {
			logger.info("CurvePoint updated, redirecting to list");

			curvePointDto.setId(id);
			curvePointService.saveCurvePoint(curvePointDto);
			model.addAttribute("curvePoints", curvePointService.getCurvePoints());
			return "redirect:/curvePoint/list";
		}
		logger.debug("Invalid data for curvePoint with id " + id + ", return to update form");

		return "/curvePoint/update/" + id;
	}

	@GetMapping("/curvePoint/delete/{id}")
	public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) {
		logger.info("Deleting curvePoint with id " + id + " requested");

		CurvePointDto curvePointDtoToDelete = curvePointService.getCurvePointById(id).get();
		curvePointService.deleteCurvePoint(curvePointDtoToDelete);

		return "redirect:/curvePoint/list";
	}
}
