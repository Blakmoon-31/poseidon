package com.nnk.springboot.poseidon.service;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.poseidon.domain.Trade;
import com.nnk.springboot.poseidon.dto.TradeDto;
import com.nnk.springboot.poseidon.mapper.MapStructMapper;
import com.nnk.springboot.poseidon.repository.TradeRepository;

@Service
public class TradeService {

	private static Logger logger = LoggerFactory.getLogger(TradeService.class);

	@Autowired
	private MapStructMapper mapStructMapper;

	@Autowired
	private TradeRepository tradeRepository;

	public Collection<TradeDto> getTrades() {
		logger.info("Obtaining trade of trades, mapping in tradeDtos");

		Collection<Trade> trades = tradeRepository.findAll();

		Collection<TradeDto> tradeDtos = mapStructMapper.tradesToTradeDtos(trades);

		return tradeDtos;
	}

	public Optional<TradeDto> getTradeByTradeId(Integer id) {
		logger.info("Obtaining trade with id " + id + ", mapping in tradeDto");

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
		logger.info("Deleting trade");

		Trade tradeToDelete = mapStructMapper.tradeDtoToTrade(tradeDtoToDelete);

		tradeRepository.delete(tradeToDelete);

	}

	@Transactional
	public TradeDto saveTrade(@Valid TradeDto tradeDtoToSave) {
		logger.info("Saving trade, return mapping tradeDto");

		Trade tradeToSave = mapStructMapper.tradeDtoToTrade(tradeDtoToSave);

		Trade tradeSaved = tradeRepository.save(tradeToSave);

		return mapStructMapper.tradeToTradeDto(tradeSaved);

	}

}
