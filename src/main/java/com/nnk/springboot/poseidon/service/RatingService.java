package com.nnk.springboot.poseidon.service;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.poseidon.domain.Rating;
import com.nnk.springboot.poseidon.dto.RatingDto;
import com.nnk.springboot.poseidon.mapper.MapStructMapper;
import com.nnk.springboot.poseidon.repository.RatingRepository;

@Service
public class RatingService {

	@Autowired
	private MapStructMapper mapStructMapper;

	@Autowired
	private RatingRepository ratingRepository;

	public Collection<RatingDto> getRatings() {

		Collection<Rating> ratings = ratingRepository.findAll();

		Collection<RatingDto> ratingDtos = mapStructMapper.ratingsToRatingDtos(ratings);

		return ratingDtos;
	}

	public Optional<RatingDto> getRatingById(Integer id) {

		Optional<Rating> rating = ratingRepository.findById(id);

		if (rating.isPresent()) {
			Optional<RatingDto> ratingDto = Optional.of(mapStructMapper.ratingToRatingDto(rating.get()));
			return ratingDto;
		} else {
			Optional<RatingDto> ratingDto = Optional.empty();
			return ratingDto;
		}
	}

	@Transactional
	public void deleteRating(RatingDto ratingDtoToDelete) {

		Rating ratingToDelete = mapStructMapper.ratingDtoToRating(ratingDtoToDelete);

		ratingRepository.delete(ratingToDelete);

	}

	@Transactional
	public RatingDto saveRating(RatingDto ratingDtoToSave) {

		Rating ratingToSave = mapStructMapper.ratingDtoToRating(ratingDtoToSave);

		Rating ratingSaved = ratingRepository.save(ratingToSave);

		return mapStructMapper.ratingToRatingDto(ratingSaved);

	}

}
