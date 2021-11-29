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

import com.nnk.springboot.poseidon.dto.BidListDto;
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
	public String addBidForm(BidListDto bidDto) {
		return "bidList/add";
	}

	@PostMapping("/bidList/validate")
	public String validate(@Valid BidListDto bidDto, BindingResult result, Model model) {

		if (!result.hasErrors()) {
			bidListService.saveBid(bidDto);
			model.addAttribute("bidLists", bidListService.getBidLists());
			return "redirect:/bidList/list";
		}

		return "bidList/add";
	}

	@GetMapping("/bidList/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

		model.addAttribute("bidListDto", bidListService.getBidListByBidListId(id).get());

		return "bidList/update";
	}

	@PutMapping("/bidList/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid BidListDto bidListDto, BindingResult result,
			Model model) {
		// TODO: check required fields, if valid call service to update Bid and return
		// list Bid

		if (!result.hasErrors()) {
			bidListDto.setBidListId(id);
			bidListService.saveBid(bidListDto);
			model.addAttribute("bidLists", bidListService.getBidLists());
			return "redirect:/bidList/list";
		}

		return "/bidList/update/" + id;
	}

	@GetMapping("/bidList/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {

		BidListDto bidListDtoToDelete = bidListService.getBidListByBidListId(id).get();
		bidListService.deleteBidList(bidListDtoToDelete);

		return "redirect:/bidList/list";
	}
}
