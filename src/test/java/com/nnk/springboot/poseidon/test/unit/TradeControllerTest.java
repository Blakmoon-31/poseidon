package com.nnk.springboot.poseidon.test.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.nnk.springboot.poseidon.controller.TradeController;
import com.nnk.springboot.poseidon.domain.Trade;
import com.nnk.springboot.poseidon.dto.TradeDto;
import com.nnk.springboot.poseidon.repository.TradeRepository;
import com.nnk.springboot.poseidon.service.TradeService;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
public class TradeControllerTest {

	@Autowired
	private TradeController tradeController;

	@Autowired
	private TradeRepository tradeRepository;

	@Mock
	private TradeService tradeService;

	@BeforeAll
	public void initTestData() {

		Trade tradeOne = new Trade();
		tradeOne.setAccount("Account test one");
		tradeOne.setType("Type test one");
		tradeOne.setBuyQuantity(1d);

		tradeRepository.save(tradeOne);

		Trade tradeTwo = new Trade();
		tradeTwo.setAccount("Account test two");
		tradeTwo.setType("Type test two");
		tradeTwo.setBuyQuantity(2d);

		tradeRepository.save(tradeTwo);
	}

	@AfterAll
	public void resetTestData() {

		Collection<Trade> trades = tradeRepository.findAll();
		for (Trade trade : trades) {
			if (trade.getAccount().equals("Account test") || trade.getAccount().equals("Account test two")) {
				tradeRepository.deleteById(trade.getTradeId());
			}
		}
	}

	@Test
	public void testHomeTradeList() throws Exception {

		Model model = mock(Model.class);
		String response = tradeController.homeTradeList(model);

		assertThat(response).isEqualTo("trade/list");

	}

	@Test
	public void testAddTradeForm() {

		TradeDto tradeDto = new TradeDto();

		String response = tradeController.addTradeForm(tradeDto);

		assertThat(response).isEqualTo("trade/add");

	}

	@Test
	public void testValidateTradeOk() {

		TradeDto tradeDto = new TradeDto();
		tradeDto.setAccount("Account test");
		tradeDto.setType("Type test");
		tradeDto.setBuyQuantity(3d);

		BindingResult result = mock(BindingResult.class);
		Model model = mock(Model.class);

		String response = tradeController.validateTrade(tradeDto, result, model);

		assertThat(result.hasErrors()).isFalse();
		assertThat(response).isEqualTo("redirect:/trade/list");

	}

	@Test
	public void testValidateTradeKo() {

		BindingResult result = mock(BindingResult.class);
		Model model = mock(Model.class);
		TradeDto tradeDto = new TradeDto();

		when(result.hasErrors()).thenReturn(true);

		String response = tradeController.validateTrade(tradeDto, result, model);

		assertThat(result.hasErrors()).isTrue();
		assertThat(response).isEqualTo("trade/add");

	}

	@Test
	public void testUpdateTradeOk() {

		BindingResult result = mock(BindingResult.class);
		Model model = mock(Model.class);
		TradeDto tradeDtoToUpdate = new TradeDto();

		Collection<Trade> tradesBeforeUpdate = tradeRepository.findAll();
		for (Trade trade : tradesBeforeUpdate) {
			if (trade.getAccount().equals("Account test two")) {
				tradeDtoToUpdate.setTradeId(trade.getTradeId());
				tradeDtoToUpdate.setAccount(trade.getAccount());
				tradeDtoToUpdate.setType(trade.getType());
				tradeDtoToUpdate.setBuyQuantity(4d);

				tradeController.updateTrade(trade.getTradeId(), tradeDtoToUpdate, result, model);
			}
		}

		Optional<Trade> tradeUpdated = tradeRepository.findByTradeId(tradeDtoToUpdate.getTradeId());

		assertThat(tradeUpdated.get().getBuyQuantity() == 4d).isTrue();
	}

	@Test
	public void testUpdateTradeKo() {

		BindingResult result = mock(BindingResult.class);
		Model model = mock(Model.class);
		TradeDto tradeDtoToUpdate = new TradeDto();
		String response = "";

		when(result.hasErrors()).thenReturn(true);

		Collection<Trade> tradesBeforeUpdate = tradeRepository.findAll();
		for (Trade trade : tradesBeforeUpdate) {
			if (trade.getAccount().equals("Account test two")) {
				tradeDtoToUpdate.setTradeId(trade.getTradeId());
				tradeDtoToUpdate.setAccount(trade.getAccount());
				tradeDtoToUpdate.setType(null);
				tradeDtoToUpdate.setBuyQuantity(trade.getBuyQuantity());

				response = tradeController.updateTrade(trade.getTradeId(), tradeDtoToUpdate, result, model);
			}
		}

		Optional<Trade> tradeUpdated = tradeRepository.findByTradeId(tradeDtoToUpdate.getTradeId());

		assertThat(result.hasErrors()).isTrue();
		assertThat(response).isEqualTo("/trade/update");
		assertThat(tradeUpdated.get().getType()).isEqualTo("Type test two");
	}

	@Test
	public void testDeleteTrade() {
		Model model = mock(Model.class);

		Collection<Trade> tradesBeforeDelete = tradeRepository.findAll();
		int listSize = tradesBeforeDelete.size();
		for (Trade trade : tradesBeforeDelete) {
			if (trade.getAccount().equals("Account test one")) {
				tradeController.deleteTrade(trade.getTradeId(), model);
			}
		}
		Collection<Trade> tradesAfterDelete = tradeRepository.findAll();
		assertThat(tradesAfterDelete.size() < listSize).isTrue();
	}

}
