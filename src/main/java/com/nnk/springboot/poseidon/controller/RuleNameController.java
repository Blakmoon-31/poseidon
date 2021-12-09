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

import com.nnk.springboot.poseidon.dto.RuleNameDto;
import com.nnk.springboot.poseidon.service.RuleNameService;

@Controller
public class RuleNameController {

	private static Logger logger = LoggerFactory.getLogger(RuleNameController.class);

	@Autowired
	private RuleNameService ruleNameService;

	@RequestMapping("/ruleName/list")
	public String homeRuleNameList(Model model) {
		logger.info("RuleName list requested");

		model.addAttribute("ruleNames", ruleNameService.getRuleNames());

		return "ruleName/list";
	}

	@GetMapping("/ruleName/add")
	public String addRuleNameForm(RuleNameDto ruleNameDto) {
		logger.info("RuleName add form requested");

		return "ruleName/add";
	}

	@PostMapping("/ruleName/validate")
	public String validateRuleName(@Valid RuleNameDto ruleNameDto, BindingResult result, Model model) {
		logger.info("Adding ruleName requested");

		if (!result.hasErrors()) {
			logger.info("Adding new ruleName and redirect to list");

			ruleNameService.saveRuleName(ruleNameDto);
			model.addAttribute("ruleNames", ruleNameService.getRuleNames());
			return "redirect:/ruleName/list";
		}
		logger.debug("Invalid data, return to add form");

		return "ruleName/add";
	}

	@GetMapping("/ruleName/update/{id}")
	public String showUpdateRuleNameForm(@PathVariable("id") Integer id, Model model) {
		logger.info("RuleName update form for id" + id + " requested");

		model.addAttribute("ruleNameDto", ruleNameService.getRuleNameById(id).get());

		return "ruleName/update";
	}

	@PutMapping("/ruleName/update/{id}")
	public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleNameDto ruleNameDto, BindingResult result,
			Model model) {
		logger.info("Updating ruleName with id " + id + " requested");

		if (!result.hasErrors()) {
			logger.info("RuleName updated, redirecting to list");

			ruleNameDto.setId(id);
			ruleNameService.saveRuleName(ruleNameDto);
			model.addAttribute("ruleNames", ruleNameService.getRuleNames());
			return "redirect:/ruleName/list";
		}
		logger.debug("Invalid data for ruleName with id " + id + ", return to update form");

		return "/ruleName/update/" + id;
	}

	@GetMapping("/ruleName/delete/{id}")
	public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
		logger.info("Deleting ruleName with id " + id + " and redirect to list");

		RuleNameDto ruleNamedtoToDelete = ruleNameService.getRuleNameById(id).get();
		ruleNameService.deleteRuleName(ruleNamedtoToDelete);

		return "redirect:/ruleName/list";
	}
}
