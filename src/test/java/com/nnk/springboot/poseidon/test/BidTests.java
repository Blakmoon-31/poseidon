package com.nnk.springboot.poseidon.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.nnk.springboot.poseidon.domain.BidList;
import com.nnk.springboot.poseidon.repository.BidListRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BidTests {

	@Autowired
	private BidListRepository bidListRepository;

	@Test
	public void bidListTest() {
		BidList bid = new BidList("Account Test", "Type Test", 10d);

		// Save
		bid = bidListRepository.save(bid);
		assertThat(bid.getBidListId()).isNotNull();
		assertThat(bid.getBidQuantity()).isEqualTo(10d);

		// Update
		bid.setBidQuantity(20d);
		bid = bidListRepository.save(bid);
		assertThat(bid.getBidQuantity()).isEqualTo(20d);

		// Find
		List<BidList> listResult = bidListRepository.findAll();
		assertThat(listResult.size() > 0).isTrue();

		// Delete
		Integer id = bid.getBidListId();
		bidListRepository.delete(bid);
		Optional<BidList> bidList = bidListRepository.findById(id);
		assertThat(bidList.isPresent()).isFalse();
	}
}
