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

import com.nnk.springboot.poseidon.domain.Trade;
import com.nnk.springboot.poseidon.service.TradeService;

@Controller
public class TradeController {

	@Autowired
	private TradeService tradeService;

	@RequestMapping("/trade/list")
	public String home(Model model) {

		model.addAttribute("trades", tradeService.getTrades());

		return "trade/list";
	}

	@GetMapping("/trade/add")
	public String addUser(Trade bid) {
		return "trade/add";
	}

	@PostMapping("/trade/validate")
	public String validate(@Valid Trade trade, BindingResult result, Model model) {
		// TODO: check data valid and save to db, after saving return Trade list

		if (!result.hasErrors()) {
			tradeService.saveTrade(trade);
			model.addAttribute("trades", tradeService.getTrades());
			return "redirect:/trade/list";
		}

		return "trade/add";
	}

	@GetMapping("/trade/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

		model.addAttribute("trade", tradeService.getTradeByTradeId(id));

		return "trade/update";
	}

	@PutMapping("/trade/update/{id}")
	public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade, BindingResult result, Model model) {
		// TODO: check required fields, if valid call service to update Trade and return
		// Trade list

		if (!result.hasErrors()) {
			trade.setTradeId(id);
			tradeService.saveTrade(trade);
			model.addAttribute("trades", tradeService.getTrades());
			return "redirect:/trade/list";
		}

		return "/trade/update/" + id;
	}

	@GetMapping("/trade/delete/{id}")
	public String deleteTrade(@PathVariable("id") Integer id, Model model) {

		Trade tradeToDelete = tradeService.getTradeByTradeId(id).get();
		tradeService.deleteTrade(tradeToDelete);

		return "redirect:/trade/list";
	}
}
