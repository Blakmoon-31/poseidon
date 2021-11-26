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

import com.nnk.springboot.poseidon.domain.BidList;
import com.nnk.springboot.poseidon.service.BidListService;

@Controller
public class BidListController {

	@Autowired
	private BidListService bidListService;

	@RequestMapping("/bidList/list")
	public String home(Model model) {

		model.addAttribute("bidLists", bidListService.getBidLists());

		return "bidList/list";
	}

	@GetMapping("/bidList/add")
	public String addBidForm(BidList bid) {
		return "bidList/add";
	}

	@PostMapping("/bidList/validate")
	public String validate(@Valid BidList bid, BindingResult result, Model model) {

		if (!result.hasErrors()) {
			bidListService.saveBid(bid);
			model.addAttribute("bidLists", bidListService.getBidLists());
			return "redirect:/bidList/list";
		}

		return "bidList/add";
	}

	@GetMapping("/bidList/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

		model.addAttribute("bidList", bidListService.getBidListByBidListId(id).get());

		return "bidList/update";
	}

	@PutMapping("/bidList/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList, BindingResult result, Model model) {
		// TODO: check required fields, if valid call service to update Bid and return
		// list Bid

		if (!result.hasErrors()) {
			bidList.setBidListId(id);
			bidListService.saveBid(bidList);
			model.addAttribute("bidLists", bidListService.getBidLists());
			return "redirect:/bidList/list";
		}

		return "/bidList/update/" + id;
	}

	@GetMapping("/bidList/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {

		BidList bidListToDelete = bidListService.getBidListByBidListId(id).get();
		bidListService.deleteBidList(bidListToDelete);

		return "redirect:/bidList/list";
	}
}
