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

import com.nnk.springboot.poseidon.dto.TradeDto;
import com.nnk.springboot.poseidon.service.TradeService;

@Controller
public class TradeController {

	private static Logger logger = LoggerFactory.getLogger(TradeController.class);

	@Autowired
	private TradeService tradeService;

	@RequestMapping("/trade/list")
	public String home(Model model) {
		logger.info("Trade list requested");

		model.addAttribute("trades", tradeService.getTrades());

		return "trade/list";
	}

	@GetMapping("/trade/add")
	public String addTradeForm(TradeDto tradeDto) {
		logger.info("Trade add form requested");

		return "trade/add";
	}

	@PostMapping("/trade/validate")
	public String validate(@Valid TradeDto tradeDto, BindingResult result, Model model) {
		logger.info("Adding trade requested");

		if (!result.hasErrors()) {
			logger.info("Adding new trade and redirect to list");

			tradeService.saveTrade(tradeDto);
			model.addAttribute("trades", tradeService.getTrades());
			return "redirect:/trade/list";
		}
		logger.debug("Invalid data, return to add form");

		return "trade/add";
	}

	@GetMapping("/trade/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		logger.info("Trade update form for id" + id + " requested");

		model.addAttribute("tradeDto", tradeService.getTradeByTradeId(id).get());

		return "trade/update";
	}

	@PutMapping("/trade/update/{id}")
	public String updateTrade(@PathVariable("id") Integer id, @Valid TradeDto tradeDto, BindingResult result,
			Model model) {
		logger.info("Updating trade with id " + id + " requested");

		if (!result.hasErrors()) {
			logger.info("Trade updated, redirecting to list");

			tradeDto.setTradeId(id);
			tradeService.saveTrade(tradeDto);
			model.addAttribute("trades", tradeService.getTrades());
			return "redirect:/trade/list";
		}
		logger.debug("Invalid data for trade with id " + id + ", return to update form");

		return "/trade/update/" + id;
	}

	@GetMapping("/trade/delete/{id}")
	public String deleteTrade(@PathVariable("id") Integer id, Model model) {
		logger.info("Deleting trade with id " + id + " requested");

		TradeDto tradeDtoToDelete = tradeService.getTradeByTradeId(id).get();
		tradeService.deleteTrade(tradeDtoToDelete);

		return "redirect:/trade/list";
	}
}
