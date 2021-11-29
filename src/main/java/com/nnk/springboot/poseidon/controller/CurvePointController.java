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

import com.nnk.springboot.poseidon.dto.CurvePointDto;
import com.nnk.springboot.poseidon.service.CurvePointService;

@Controller
public class CurvePointController {

	@Autowired
	private CurvePointService curvePointService;

	@RequestMapping("/curvePoint/list")
	public String home(Model model) {

		model.addAttribute("curvePoints", curvePointService.getCurvePoints());

		return "curvePoint/list";
	}

	@GetMapping("/curvePoint/add")
	public String addCurvePointForm(CurvePointDto curvePointDto) {
		return "curvePoint/add";
	}

	@PostMapping("/curvePoint/validate")
	public String validate(@Valid CurvePointDto curvePointDto, BindingResult result, Model model) {

		if (!result.hasErrors()) {
			curvePointService.saveCurvePoint(curvePointDto);
			model.addAttribute("curvePoints", curvePointService.getCurvePoints());
			return "redirect:/curvePoint/list";
		}

		return "curvePoint/add";
	}

	@GetMapping("/curvePoint/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

		model.addAttribute("curvePointDto", curvePointService.getCurvePointById(id).get());

		return "curvePoint/update";
	}

	@PutMapping("/curvePoint/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid CurvePointDto curvePointDto, BindingResult result,
			Model model) {
		// TODO: check required fields, if valid call service to update Curve and return
		// Curve list

		if (!result.hasErrors()) {
			curvePointDto.setId(id);
			curvePointService.saveCurvePoint(curvePointDto);
			model.addAttribute("curvePoints", curvePointService.getCurvePoints());
			return "redirect:/curvePoint/list";
		}

		return "/curvePoint/update/" + id;
	}

	@GetMapping("/curvePoint/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {

		CurvePointDto curvePointDtoToDelete = curvePointService.getCurvePointById(id).get();
		curvePointService.deleteCurvePoint(curvePointDtoToDelete);

		return "redirect:/curvePoint/list";
	}
}
