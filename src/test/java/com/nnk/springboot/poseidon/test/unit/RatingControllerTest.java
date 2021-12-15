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

import com.nnk.springboot.poseidon.controller.RatingController;
import com.nnk.springboot.poseidon.domain.Rating;
import com.nnk.springboot.poseidon.dto.RatingDto;
import com.nnk.springboot.poseidon.repository.RatingRepository;
import com.nnk.springboot.poseidon.service.RatingService;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
public class RatingControllerTest {

	@Autowired
	private RatingController ratingController;

	@Autowired
	private RatingRepository ratingRepository;

	@Mock
	private RatingService ratingService;

	@BeforeAll
	public void initTestData() {

		Rating ratingOne = new Rating();
		ratingOne.setMoodysRating("Moodys test one");
		ratingOne.setSandPRating("SandP test one");
		ratingOne.setFitchRating("Fitch test one");
		ratingOne.setOrderNumber(1);

		ratingRepository.save(ratingOne);

		Rating ratingTwo = new Rating();
		ratingTwo.setMoodysRating("Moodys test two");
		ratingTwo.setSandPRating("SandP test two");
		ratingTwo.setFitchRating("Fitch test two");
		ratingTwo.setOrderNumber(2);

		ratingRepository.save(ratingTwo);

	}

	@AfterAll
	public void resetTestData() {

		Collection<Rating> ratings = ratingRepository.findAll();
		for (Rating rating : ratings) {
			if (rating.getMoodysRating().equals("Moodys test two") || rating.getMoodysRating().equals("Moodys test")) {
				ratingRepository.deleteById(rating.getId());
			}
		}
	}

	@Test
	public void testHomeRatingList() throws Exception {

		Model model = mock(Model.class);
		String response = ratingController.homeRatingList(model);

		assertThat(response).isEqualTo("rating/list");

	}

	@Test
	public void testAddRatingForm() {

		RatingDto ratingDto = new RatingDto();

		String response = ratingController.addRatingForm(ratingDto);

		assertThat(response).isEqualTo("rating/add");

	}

	@Test
	public void testValidateRatingOk() {

		RatingDto ratingDto = new RatingDto();
		ratingDto.setMoodysRating("Moodys test");
		ratingDto.setSandPRating("SandP test");
		ratingDto.setFitchRating("Fitch test");
		ratingDto.setOrderNumber(3);

		BindingResult result = mock(BindingResult.class);
		Model model = mock(Model.class);

		String response = ratingController.validateRating(ratingDto, result, model);

		assertThat(result.hasErrors()).isFalse();
		assertThat(response).isEqualTo("redirect:/rating/list");

	}

	@Test
	public void testValidateRatingKo() {

		BindingResult result = mock(BindingResult.class);
		Model model = mock(Model.class);
		RatingDto ratingDto = new RatingDto();

		when(result.hasErrors()).thenReturn(true);

		String response = ratingController.validateRating(ratingDto, result, model);

		assertThat(result.hasErrors()).isTrue();
		assertThat(response).isEqualTo("rating/add");

	}

	@Test
	public void testUpdateRatingOk() {

		BindingResult result = mock(BindingResult.class);
		Model model = mock(Model.class);
		RatingDto ratingDtoToUpdate = new RatingDto();

		Collection<Rating> ratingsBeforeUpdate = ratingRepository.findAll();
		for (Rating rating : ratingsBeforeUpdate) {
			if (rating.getMoodysRating().equals("Moodys test two")) {
				ratingDtoToUpdate.setId(rating.getId());
				ratingDtoToUpdate.setMoodysRating(rating.getMoodysRating());
				ratingDtoToUpdate.setSandPRating(rating.getSandPRating());
				ratingDtoToUpdate.setFitchRating(rating.getFitchRating());
				ratingDtoToUpdate.setOrderNumber(4);

				ratingController.updateRating(rating.getId(), ratingDtoToUpdate, result, model);
			}
		}

		Optional<Rating> ratingUpdated = ratingRepository.findById(ratingDtoToUpdate.getId());

		assertThat(ratingUpdated.get().getOrderNumber() == 4).isTrue();
	}

	@Test
	public void testUpdateRatingKo() {

		BindingResult result = mock(BindingResult.class);
		Model model = mock(Model.class);
		RatingDto ratingDtoToUpdate = new RatingDto();
		String response = "";

		when(result.hasErrors()).thenReturn(true);

		Collection<Rating> ratingsBeforeUpdate = ratingRepository.findAll();
		for (Rating rating : ratingsBeforeUpdate) {
			if (rating.getMoodysRating().equals("Moodys test two")) {
				ratingDtoToUpdate.setId(rating.getId());
				ratingDtoToUpdate.setMoodysRating(rating.getMoodysRating());
				ratingDtoToUpdate.setSandPRating(null);
				ratingDtoToUpdate.setFitchRating(rating.getFitchRating());
				ratingDtoToUpdate.setOrderNumber(rating.getOrderNumber());

				response = ratingController.updateRating(rating.getId(), ratingDtoToUpdate, result, model);
			}
		}

		Optional<Rating> ratingUpdated = ratingRepository.findById(ratingDtoToUpdate.getId());

		assertThat(result.hasErrors()).isTrue();
		assertThat(response).isEqualTo("/rating/update");
		assertThat(ratingUpdated.get().getSandPRating()).isEqualTo("SandP test two");
	}

	@Test
	public void testDeleteRating() {
		Model model = mock(Model.class);

		Collection<Rating> ratingsBeforeDelete = ratingRepository.findAll();
		int listSize = ratingsBeforeDelete.size();
		for (Rating rating : ratingsBeforeDelete) {
			if (rating.getMoodysRating().equals("Moodys test one")) {
				ratingController.deleteRating(rating.getId(), model);
			}
		}
		Collection<Rating> ratingsAfterDelete = ratingRepository.findAll();
		assertThat(ratingsAfterDelete.size() < listSize).isTrue();
	}

}
