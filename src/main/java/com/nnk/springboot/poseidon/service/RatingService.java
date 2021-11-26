package com.nnk.springboot.poseidon.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.poseidon.domain.Rating;
import com.nnk.springboot.poseidon.repository.RatingRepository;

@Service
public class RatingService {

	@Autowired
	private RatingRepository ratingRepository;

	public List<Rating> getRatings() {

		List<Rating> ratings = ratingRepository.findAll();

		return ratings;
	}

	public Optional<Rating> getRatingById(Integer id) {

		Optional<Rating> rating = ratingRepository.findById(id);

		return rating;
	}

	@Transactional
	public void deleteRating(Rating ratingToDelete) {
		ratingRepository.delete(ratingToDelete);

	}

	@Transactional
	public void saveRating(Rating rating) {
		ratingRepository.save(rating);

	}

}
