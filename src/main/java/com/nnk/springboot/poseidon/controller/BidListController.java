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

import com.nnk.springboot.poseidon.dto.BidListDto;
import com.nnk.springboot.poseidon.service.BidListService;

@Controller
public class BidListController {

	private static Logger logger = LoggerFactory.getLogger(BidListController.class);

	@Autowired
	private BidListService bidListService;

	@GetMapping("/bidList/list")
	public String homeBidListList(Model model) {
		logger.info("BidList list requested");

		model.addAttribute("bidLists", bidListService.getBidLists());

		return "bidList/list";
	}

	@GetMapping("/bidList/add")
	public String addBidListForm(BidListDto bidDto) {
		logger.info("BidList add form requested");

		return "bidList/add";
	}

	@PostMapping("/bidList/validate")
	public String validateBidList(@Valid BidListDto bidDto, BindingResult result, Model model) {
		logger.info("Adding bidList requested");

		if (!result.hasErrors()) {
			logger.info("Adding new bidList and redirect to list");

			bidListService.saveBidList(bidDto);
			model.addAttribute("bidLists", bidListService.getBidLists());
			return "redirect:/bidList/list";
		}
		logger.debug("Invalid data, return to add form");

		return "bidList/add";
	}

	@GetMapping("/bidList/update/{id}")
	public String showUpdateBidListForm(@PathVariable("id") Integer id, Model model) {
		logger.info("BidList update form for id " + id + " requested");

		model.addAttribute("bidListDto", bidListService.getBidListByBidListId(id).get());

		return "bidList/update";
	}

	@PutMapping("/bidList/update/{id}")
	public String updateBidList(@PathVariable("id") Integer id, @Valid BidListDto bidListDto, BindingResult result,
			Model model) {
		logger.info("Updating bidList with id " + id + " requested");

		if (!result.hasErrors()) {
			logger.info("BidList updated, redirecting to list");

			bidListDto.setBidListId(id);
			bidListService.saveBidList(bidListDto);
			model.addAttribute("bidLists", bidListService.getBidLists());
			return "redirect:/bidList/list";
		}
		logger.debug("Invalid data for bidList with id " + id + ", return to update form");

		return "/bidList/update";
	}

	@GetMapping("/bidList/delete/{id}")
	public String deleteBidList(@PathVariable("id") Integer id, Model model) {
		logger.info("Deleting bidList with id " + id + " requested");

		BidListDto bidListDtoToDelete = bidListService.getBidListByBidListId(id).get();
		bidListService.deleteBidList(bidListDtoToDelete);

		return "redirect:/bidList/list";
	}
}
