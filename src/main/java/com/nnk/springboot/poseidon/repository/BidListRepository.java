package com.nnk.springboot.poseidon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nnk.springboot.poseidon.domain.BidList;

@Repository
public interface BidListRepository extends JpaRepository<BidList, Integer> {

	Optional<BidList> findByBidListId(Integer id);

}
