package com.nnk.springboot.poseidon.test.integration;

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

import com.nnk.springboot.poseidon.dto.RatingDto;
import com.nnk.springboot.poseidon.service.RatingService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class RatingServiceIT {

	@Autowired
	private RatingService ratingService;

	@BeforeAll
	public void initTestData() {

		RatingDto ratingForTests = new RatingDto();
		ratingForTests.setMoodysRating("Moodys TestInit one");
		ratingForTests.setSandPRating("Sand TestInit one");
		ratingForTests.setFitchRating("Fitch TestInit one");
		ratingForTests.setOrderNumber(20);

		ratingService.saveRating(ratingForTests);

		ratingForTests.setMoodysRating("Moodys TestInit two");
		ratingForTests.setSandPRating("Sand TestInit two");
		ratingForTests.setFitchRating("Fitch TestInit two");
		ratingForTests.setOrderNumber(30);

		ratingService.saveRating(ratingForTests);

	}

	@AfterAll
	public void resetTestData() {

		Collection<RatingDto> ratingDtos = ratingService.getRatings();

		for (RatingDto rating : ratingDtos) {
			if (rating.getMoodysRating().equals("Moodys TestInit one")
					|| rating.getMoodysRating().equals("Moodys Test")) {
				ratingService.deleteRating(rating);
			}
		}

	}

	@Test
	public void testSaveRating() {

		RatingDto ratingDtoToSave = new RatingDto();
		ratingDtoToSave.setMoodysRating("Moodys Test");
		ratingDtoToSave.setSandPRating("Sand Test");
		ratingDtoToSave.setFitchRating("Fitch Test");
		ratingDtoToSave.setOrderNumber(10);

		ratingDtoToSave = ratingService.saveRating(ratingDtoToSave);
		assertThat(ratingDtoToSave.getId()).isNotNull();
		assertThat(ratingDtoToSave.getOrderNumber()).isEqualTo(10);

	}

	@Test
	public void testUpdateRating() {

		Collection<RatingDto> ratingDtos = ratingService.getRatings();
		RatingDto ratingDtoToUpdate = new RatingDto();

		for (RatingDto rating : ratingDtos) {
			if (rating.getMoodysRating().equals("Moodys TestInit one")) {
				ratingDtoToUpdate = rating;
			}
		}

		ratingDtoToUpdate.setOrderNumber(20);
		ratingDtoToUpdate = ratingService.saveRating(ratingDtoToUpdate);
		assertThat(ratingDtoToUpdate.getOrderNumber()).isEqualTo(20);

	}

	@Test
	public void testGetRatings() {

		Collection<RatingDto> ratingDtos = ratingService.getRatings();

		assertThat(ratingDtos.size() > 0).isTrue();
	}

	@Test
	public void testDeleteRating() {

		Collection<RatingDto> ratingDtos = ratingService.getRatings();
		RatingDto ratingDtoToDelete = new RatingDto();

		for (RatingDto rating : ratingDtos) {
			if (rating.getMoodysRating().equals("Moodys TestInit two")) {
				ratingDtoToDelete = rating;
			}
		}

		Integer id = ratingDtoToDelete.getId();
		ratingService.deleteRating(ratingDtoToDelete);
		Optional<RatingDto> ratingDtoDeleted = ratingService.getRatingById(id);
		assertThat(ratingDtoDeleted.isPresent()).isFalse();
	}
}
