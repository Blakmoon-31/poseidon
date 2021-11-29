package com.nnk.springboot.poseidon.service;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.poseidon.domain.BidList;
import com.nnk.springboot.poseidon.dto.BidListDto;
import com.nnk.springboot.poseidon.mapper.MapStructMapper;
import com.nnk.springboot.poseidon.repository.BidListRepository;

@Service
public class BidListService {

	@Autowired
	private MapStructMapper mapStructMapper;

	@Autowired
	private BidListRepository bidListRepository;

	public Collection<BidListDto> getBidLists() {

		Collection<BidList> bidLists = bidListRepository.findAll();

		Collection<BidListDto> bidListDtos = mapStructMapper.bidListsToBidListDtos(bidLists);

		return bidListDtos;
	}

	public Optional<BidListDto> getBidListByBidListId(Integer id) {

		Optional<BidList> bidList = bidListRepository.findByBidListId(id);

		if (bidList.isPresent()) {
			Optional<BidListDto> bidListDto = Optional.of(mapStructMapper.bidListToBidListDto(bidList.get()));
			return bidListDto;
		} else {
			Optional<BidListDto> bidListDto = Optional.empty();
			return bidListDto;
		}

	}

	@Transactional
	public void deleteBidList(BidListDto bidListDtoToDelete) {

		BidList bidListToDelete = mapStructMapper.bidListDtoToBidList(bidListDtoToDelete);

		bidListRepository.delete(bidListToDelete);

	}

	@Transactional
	public BidListDto saveBid(BidListDto bidListDtoToSave) {

		BidList bidListToSave = mapStructMapper.bidListDtoToBidList(bidListDtoToSave);

		BidList bidListSaved = bidListRepository.save(bidListToSave);

		return mapStructMapper.bidListToBidListDto(bidListSaved);

	}

}
