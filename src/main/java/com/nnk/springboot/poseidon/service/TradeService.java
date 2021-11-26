package com.nnk.springboot.poseidon.service;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.poseidon.domain.Trade;
import com.nnk.springboot.poseidon.repository.TradeRepository;

@Service
public class TradeService {

	@Autowired
	private TradeRepository tradeRepository;

	public Collection<Trade> getTrades() {

		Collection<Trade> trades = tradeRepository.findAll();

		return trades;
	}

	@Transactional
	public void saveTrade(@Valid Trade trade) {

		tradeRepository.save(trade);

	}

	public Optional<Trade> getTradeByTradeId(Integer id) {

		Optional<Trade> trade = tradeRepository.findByTradeId(id);

		return trade;
	}

	@Transactional
	public void deleteTrade(Trade tradeToDelete) {

		tradeRepository.delete(tradeToDelete);

	}

}
