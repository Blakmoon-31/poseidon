package com.nnk.springboot.poseidon.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.nnk.springboot.poseidon.domain.Trade;
import com.nnk.springboot.poseidon.repository.TradeRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TradeTests {

	@Autowired
	private TradeRepository tradeRepository;

	@Test
	public void tradeTest() {
		Trade trade = new Trade("Trade Account", "Type");

		// Save
		trade = tradeRepository.save(trade);
		assertThat(trade.getTradeId()).isNotNull();
		assertThat(trade.getAccount().equals("Trade Account")).isTrue();

		// Update
		trade.setAccount("Trade Account Update");
		trade = tradeRepository.save(trade);
		assertThat(trade.getAccount().equals("Trade Account Update")).isTrue();

		// Find
		List<Trade> listResult = tradeRepository.findAll();
		assertThat(listResult.size() > 0).isTrue();

		// Delete
		Integer id = trade.getTradeId();
		tradeRepository.delete(trade);
		Optional<Trade> tradeList = tradeRepository.findById(id);
		assertThat(tradeList.isPresent()).isFalse();
	}
}
