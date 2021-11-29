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

import com.nnk.springboot.poseidon.dto.RuleNameDto;
import com.nnk.springboot.poseidon.service.RuleNameService;

@Controller
public class RuleNameController {

	@Autowired
	private RuleNameService ruleNameService;

	@RequestMapping("/ruleName/list")
	public String home(Model model) {

		model.addAttribute("ruleNames", ruleNameService.getRuleNames());

		return "ruleName/list";
	}

	@GetMapping("/ruleName/add")
	public String addRuleForm(RuleNameDto ruleNameDto) {
		return "ruleName/add";
	}

	@PostMapping("/ruleName/validate")
	public String validate(@Valid RuleNameDto ruleNameDto, BindingResult result, Model model) {
		// TODO: check data valid and save to db, after saving return RuleName list

		if (!result.hasErrors()) {
			ruleNameService.saveRuleName(ruleNameDto);
			model.addAttribute("ruleNames", ruleNameService.getRuleNames());
			return "redirect:/ruleName/list";
		}

		return "ruleName/add";
	}

	@GetMapping("/ruleName/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

		model.addAttribute("ruleNameDto", ruleNameService.getRuleNameById(id).get());

		return "ruleName/update";
	}

	@PutMapping("/ruleName/update/{id}")
	public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleNameDto ruleNameDto, BindingResult result,
			Model model) {
		// TODO: check required fields, if valid call service to update RuleName and
		// return RuleName list

		if (!result.hasErrors()) {
			ruleNameDto.setId(id);
			ruleNameService.saveRuleName(ruleNameDto);
			model.addAttribute("ruleNames", ruleNameService.getRuleNames());
			return "redirect:/ruleName/list";
		}

		return "/ruleName/update/" + id;
	}

	@GetMapping("/ruleName/delete/{id}")
	public String deleteRuleName(@PathVariable("id") Integer id, Model model) {

		RuleNameDto ruleNamedtoToDelete = ruleNameService.getRuleNameById(id).get();
		ruleNameService.deleteRuleName(ruleNamedtoToDelete);

		return "redirect:/ruleName/list";
	}
}
