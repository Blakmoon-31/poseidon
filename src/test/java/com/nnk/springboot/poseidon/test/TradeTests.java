package com.nnk.springboot.poseidon.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.nnk.springboot.poseidon.dto.TradeDto;
import com.nnk.springboot.poseidon.service.TradeService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class TradeTests {

	@Autowired
	private TradeService tradeService;

	@BeforeAll
	public void initTestData() {

		TradeDto tradeDto = new TradeDto();
		tradeDto.setAccount("Account TestInit one");
		tradeDto.setType("Type TestInit one");
		tradeDto.setBuyQuantity(10d);

		tradeService.saveTrade(tradeDto);

		tradeDto.setAccount("Account TestInit two");
		tradeDto.setType("Type TestInit two");
		tradeDto.setBuyQuantity(10d);

		tradeService.saveTrade(tradeDto);

	}

	@AfterAll
	public void resetTestData() {

		Collection<TradeDto> tradeDtos = tradeService.getTrades();

		for (TradeDto trade : tradeDtos) {
			if (trade.getAccount().equals("Account TestInit one") || trade.getAccount().equals("Trade Account Test")) {
				tradeService.deleteTrade(trade);
			}
		}

	}

	@Test
	public void testSaveTrade() {

		TradeDto tradeDto = new TradeDto();
		tradeDto.setAccount("Trade Account Test");
		tradeDto.setType("Type Test");
		tradeDto.setBuyQuantity(10d);

		tradeDto = tradeService.saveTrade(tradeDto);
		assertThat(tradeDto.getTradeId()).isNotNull();
		assertThat(tradeDto.getAccount()).isEqualTo("Trade Account Test");

	}

	@Test
	public void testUpdateTrade() {

		Collection<TradeDto> tradeDtos = tradeService.getTrades();
		TradeDto tradeDtoToUpdate = new TradeDto();

		for (TradeDto trade : tradeDtos) {
			if (trade.getAccount().equals("Account TestInit one")) {
				tradeDtoToUpdate = trade;
			}
		}

		tradeDtoToUpdate.setType("Type Updated");
		tradeDtoToUpdate = tradeService.saveTrade(tradeDtoToUpdate);
		assertThat(tradeDtoToUpdate.getType()).isEqualTo("Type Updated");
	}

	@Test
	public void testGetTrades() {

		Collection<TradeDto> tradeDtos = tradeService.getTrades();

		assertThat(tradeDtos.size() > 0).isTrue();

	}

	@Test
	public void testDeleteTrade() {

		Collection<TradeDto> tradeDtos = tradeService.getTrades();
		TradeDto tradeDtoToUpdate = new TradeDto();

		for (TradeDto trade : tradeDtos) {
			if (trade.getAccount().equals("Account TestInit two")) {
				tradeDtoToUpdate = trade;
			}
		}

		Integer id = tradeDtoToUpdate.getTradeId();
		tradeService.deleteTrade(tradeDtoToUpdate);
		Optional<TradeDto> tradeDtoDeleted = tradeService.getTradeByTradeId(id);
		assertThat(tradeDtoDeleted.isPresent()).isFalse();
	}
}
