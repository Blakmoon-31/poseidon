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

import com.nnk.springboot.poseidon.dto.BidListDto;
import com.nnk.springboot.poseidon.service.BidListService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class BidListTests {

	@Autowired
	private BidListService bidListService;

	@BeforeAll
	public void initTestData() {

		BidListDto bidListForTests = new BidListDto();
		bidListForTests.setAccount("Account TestInit one");
		bidListForTests.setType("Type TestInit one");
		bidListForTests.setBidQuantity(5d);

		bidListService.saveBid(bidListForTests);

		bidListForTests.setAccount("Account TestInit two");
		bidListForTests.setType("Type TestInit two");
		bidListForTests.setBidQuantity(7d);

		bidListService.saveBid(bidListForTests);

	}

	@AfterAll
	public void resetTestData() {

		Collection<BidListDto> bidListDtos = bidListService.getBidLists();

		for (BidListDto bid : bidListDtos) {
			if (bid.getAccount().equals("Account TestInit one") || bid.getAccount().equals("Account Test")) {
				bidListService.deleteBidList(bid);
			}
		}

	}

	@Test
	public void testSaveBidList() {

		BidListDto bidListDto = new BidListDto();
		bidListDto.setAccount("Account Test");
		bidListDto.setType("Type Test");
		bidListDto.setBidQuantity(10d);

		bidListDto = bidListService.saveBid(bidListDto);
		assertThat(bidListDto.getBidListId()).isNotNull();
		assertThat(bidListDto.getBidQuantity()).isEqualTo(10d);

	}

	@Test
	public void testUpdateBidlist() {

		Collection<BidListDto> bidListDtos = bidListService.getBidLists();
		BidListDto bidListDtoToUpdate = new BidListDto();

		for (BidListDto bid : bidListDtos) {
			if (bid.getAccount().equals("Account TestInit one")) {
				bidListDtoToUpdate = bid;
			}
		}

		bidListDtoToUpdate.setBidQuantity(20d);
		bidListDtoToUpdate = bidListService.saveBid(bidListDtoToUpdate);
		assertThat(bidListDtoToUpdate.getBidQuantity()).isEqualTo(20d);

	}

	@Test
	public void testGetBidLists() {

		Collection<BidListDto> bidListDtos = bidListService.getBidLists();

		assertThat(bidListDtos.size() > 0).isTrue();
	}

	@Test
	public void testDeleteBidList() {

		Collection<BidListDto> bidListDtos = bidListService.getBidLists();
		BidListDto bidListDtoToDelete = new BidListDto();

		for (BidListDto bid : bidListDtos) {
			if (bid.getAccount().equals("Account TestInit two")) {
				bidListDtoToDelete = bid;
			}
		}

		Integer id = bidListDtoToDelete.getBidListId();
		bidListService.deleteBidList(bidListDtoToDelete);
		Optional<BidListDto> bidListDtoDeleted = bidListService.getBidListByBidListId(id);
		assertThat(bidListDtoDeleted.isPresent()).isFalse();
	}
}
