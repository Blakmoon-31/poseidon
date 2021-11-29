package com.nnk.springboot.poseidon.service;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.poseidon.domain.Trade;
import com.nnk.springboot.poseidon.dto.TradeDto;
import com.nnk.springboot.poseidon.mapper.MapStructMapper;
import com.nnk.springboot.poseidon.repository.TradeRepository;

@Service
public class TradeService {

	@Autowired
	private MapStructMapper mapStructMapper;

	@Autowired
	private TradeRepository tradeRepository;

	public Collection<TradeDto> getTrades() {

		Collection<Trade> trades = tradeRepository.findAll();

		Collection<TradeDto> tradeDtos = mapStructMapper.tradesToTradeDtos(trades);

		return tradeDtos;
	}

	@Transactional
	public TradeDto saveTrade(@Valid TradeDto tradeDtoToSave) {

		Trade tradeToSave = mapStructMapper.tradeDtoToTrade(tradeDtoToSave);

		Trade tradeSaved = tradeRepository.save(tradeToSave);

		return mapStructMapper.tradeToTradeDto(tradeSaved);

	}

	public Optional<TradeDto> getTradeByTradeId(Integer id) {

		Optional<Trade> trade = tradeRepository.findByTradeId(id);

		if (trade.isPresent()) {
			Optional<TradeDto> tradeDto = Optional.of(mapStructMapper.tradeToTradeDto(trade.get()));
			return tradeDto;
		} else {
			Optional<TradeDto> tradeDto = Optional.empty();
			return tradeDto;
		}

	}

	@Transactional
	public void deleteTrade(TradeDto tradeDtoToDelete) {

		Trade tradeToDelete = mapStructMapper.tradeDtoToTrade(tradeDtoToDelete);

		tradeRepository.delete(tradeToDelete);

	}

}
