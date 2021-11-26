package com.nnk.springboot.poseidon.service;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.poseidon.domain.BidList;
import com.nnk.springboot.poseidon.repository.BidListRepository;

@Service
public class BidListService {

	@Autowired
	private BidListRepository bidListRepository;

	public Collection<BidList> getBidLists() {

		Collection<BidList> bidLists = bidListRepository.findAll();

		return bidLists;
	}

	public Optional<BidList> getBidListByBidListId(Integer id) {

		Optional<BidList> bidlist = bidListRepository.findByBidListId(id);

		return bidlist;
	}

	@Transactional
	public void deleteBidList(BidList bidListToDelete) {

		bidListRepository.delete(bidListToDelete);

	}

	@Transactional
	public void saveBid(BidList bidList) {

		bidListRepository.save(bidList);

	}

}
