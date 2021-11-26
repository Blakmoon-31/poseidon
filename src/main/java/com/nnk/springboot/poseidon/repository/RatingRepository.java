package com.nnk.springboot.poseidon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nnk.springboot.poseidon.domain.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

}
