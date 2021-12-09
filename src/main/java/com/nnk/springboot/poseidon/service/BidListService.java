package com.nnk.springboot.poseidon.service;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.poseidon.domain.BidList;
import com.nnk.springboot.poseidon.dto.BidListDto;
import com.nnk.springboot.poseidon.mapper.MapStructMapper;
import com.nnk.springboot.poseidon.repository.BidListRepository;

@Service
public class BidListService {

	private static Logger logger = LoggerFactory.getLogger(BidListService.class);

	@Autowired
	private MapStructMapper mapStructMapper;

	@Autowired
	private BidListRepository bidListRepository;

	public Collection<BidListDto> getBidLists() {
		logger.info("Obtaining list of bidLists, mapping in bidListDtos");

		Collection<BidList> bidLists = bidListRepository.findAll();

		Collection<BidListDto> bidListDtos = mapStructMapper.bidListsToBidListDtos(bidLists);

		return bidListDtos;
	}

	public Optional<BidListDto> getBidListByBidListId(Integer id) {
		logger.info("Obtaining bidList with id " + id + ", mapping in bidListDto");

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
		logger.info("Deleting bidList");

		BidList bidListToDelete = mapStructMapper.bidListDtoToBidList(bidListDtoToDelete);

		bidListRepository.delete(bidListToDelete);

	}

	@Transactional
	public BidListDto saveBidList(BidListDto bidListDtoToSave) {
		logger.info("Saving bidList, return mapping bidListDto");

		BidList bidListToSave = mapStructMapper.bidListDtoToBidList(bidListDtoToSave);

		BidList bidListSaved = bidListRepository.save(bidListToSave);

		return mapStructMapper.bidListToBidListDto(bidListSaved);

	}

}
