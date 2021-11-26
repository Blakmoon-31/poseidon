package com.nnk.springboot.poseidon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nnk.springboot.poseidon.domain.Trade;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Integer> {

	Optional<Trade> findByTradeId(Integer id);

}
