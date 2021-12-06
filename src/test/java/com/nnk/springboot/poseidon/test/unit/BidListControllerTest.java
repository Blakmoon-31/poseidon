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

import com.nnk.springboot.poseidon.controller.BidListController;
import com.nnk.springboot.poseidon.domain.BidList;
import com.nnk.springboot.poseidon.dto.BidListDto;
import com.nnk.springboot.poseidon.repository.BidListRepository;
import com.nnk.springboot.poseidon.service.BidListService;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
public class BidListControllerTest {

	@Autowired
	private BidListController bidListController;

	@Autowired
	private BidListRepository bidListRepository;

	@Mock
	private BidListService bidListService;

	@BeforeAll
	public void initTestsData() {

		BidList bidOne = new BidList();
		bidOne.setAccount("Account test one");
		bidOne.setType("Type test one");
		bidOne.setBidQuantity(99d);

		bidListRepository.save(bidOne);

		BidList bidTwo = new BidList();
		bidTwo.setAccount("Account test two");
		bidTwo.setType("Type test two");
		bidTwo.setBidQuantity(88d);

		bidListRepository.save(bidTwo);
	}

	@AfterAll
	public void resetTestsData() {

		Collection<BidList> bidLists = bidListRepository.findAll();
		for (BidList bid : bidLists) {
			if (bid.getAccount().equals("Account test") || bid.getAccount().equals("Account test two")) {
				bidListRepository.deleteById(bid.getBidListId());
			}
		}
	}

	@Test
	public void testRequestBidListPage() throws Exception {

		Model model = mock(Model.class);
		String response = bidListController.home(model);

		assertThat(response).isEqualTo("bidList/list");

	}

	@Test
	public void testAddBidForm() {

		BidListDto bidListDto = new BidListDto();

		String response = bidListController.addBidListForm(bidListDto);

		assertThat(response).isEqualTo("bidList/add");

	}

	@Test
	public void testValidateOk() {

		BidListDto bidListDto = new BidListDto();
		bidListDto.setAccount("Account test");
		bidListDto.setType("Type test");
		bidListDto.setBidQuantity(10d);

		BindingResult result = mock(BindingResult.class);
		Model model = mock(Model.class);

		String response = bidListController.validate(bidListDto, result, model);

		assertThat(result.hasErrors()).isFalse();
		assertThat(response).isEqualTo("redirect:/bidList/list");

	}

	@Test
	public void testValidateKo() {

		BindingResult result = mock(BindingResult.class);
		Model model = mock(Model.class);
		BidListDto bidListDto = new BidListDto();

		when(result.hasErrors()).thenReturn(true);

		String response = bidListController.validate(bidListDto, result, model);

		assertThat(result.hasErrors()).isTrue();
		assertThat(response).isEqualTo("bidList/add");

	}

	@Test
	public void testUpdateBidOk() {

		BindingResult result = mock(BindingResult.class);
		Model model = mock(Model.class);
		BidListDto bidDtoToUpdate = new BidListDto();

		Collection<BidList> bidsBeforeUpdate = bidListRepository.findAll();
		for (BidList bid : bidsBeforeUpdate) {
			if (bid.getAccount().equals("Account test two")) {
				bidDtoToUpdate.setBidListId(bid.getBidListId());
				bidDtoToUpdate.setAccount(bid.getAccount());
				bidDtoToUpdate.setType(bid.getType());
				bidDtoToUpdate.setBidQuantity(77d);

				bidListController.updateBid(bid.getBidListId(), bidDtoToUpdate, result, model);
			}
		}

		Optional<BidList> bidUpdated = bidListRepository.findByBidListId(bidDtoToUpdate.getBidListId());

		assertThat(bidUpdated.get().getBidQuantity() == 77d).isTrue();
	}

	@Test
	public void testUpdateBidKo() {

		BindingResult result = mock(BindingResult.class);
		Model model = mock(Model.class);
		BidListDto bidDtoToUpdate = new BidListDto();
		String response = "";

		when(result.hasErrors()).thenReturn(true);

		Collection<BidList> bidsBeforeUpdate = bidListRepository.findAll();
		for (BidList bid : bidsBeforeUpdate) {
			if (bid.getAccount().equals("Account test two")) {
				bidDtoToUpdate.setBidListId(bid.getBidListId());
				bidDtoToUpdate.setAccount(bid.getAccount());
				bidDtoToUpdate.setType("");
				bidDtoToUpdate.setBidQuantity(bid.getBidQuantity());

				response = bidListController.updateBid(bid.getBidListId(), bidDtoToUpdate, result, model);
			}
		}

		Optional<BidList> bidUpdated = bidListRepository.findByBidListId(bidDtoToUpdate.getBidListId());

		assertThat(result.hasErrors()).isTrue();
		assertThat(response).isEqualTo("/bidList/update/" + bidDtoToUpdate.getBidListId());
		assertThat(bidUpdated.get().getType()).isEqualTo("Type test two");
	}

	@Test
	public void testDeleteBid() {
		Model model = mock(Model.class);

		Collection<BidList> bidsBeforeDelete = bidListRepository.findAll();
		int listSize = bidsBeforeDelete.size();
		for (BidList bid : bidsBeforeDelete) {
			if (bid.getAccount().equals("Account test one")) {
				bidListController.deleteBid(bid.getBidListId(), model);
			}
		}
		Collection<BidList> bidsAfterDelete = bidListRepository.findAll();
		assertThat(bidsAfterDelete.size() < listSize).isTrue();
	}

}
