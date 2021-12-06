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

import com.nnk.springboot.poseidon.controller.CurvePointController;
import com.nnk.springboot.poseidon.domain.CurvePoint;
import com.nnk.springboot.poseidon.dto.CurvePointDto;
import com.nnk.springboot.poseidon.repository.CurvePointRepository;
import com.nnk.springboot.poseidon.service.CurvePointService;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
public class CurvePointControllerTest {

	@Autowired
	private CurvePointController curvePointController;

	@Autowired
	private CurvePointRepository curvePointRepository;

	@Mock
	private CurvePointService curvePointService;

	@BeforeAll
	public void initTests() {

		CurvePoint curveOne = new CurvePoint();
		curveOne.setCurveId(1);
		curveOne.setTerm(11d);
		curveOne.setValue(110d);

		curvePointRepository.save(curveOne);

		CurvePoint curveTwo = new CurvePoint();
		curveTwo.setCurveId(2);
		curveTwo.setTerm(22d);
		curveTwo.setValue(220d);

		curvePointRepository.save(curveTwo);

	}

	@AfterAll
	public void resetTestsData() {

		Collection<CurvePoint> curvePoints = curvePointRepository.findAll();
		for (CurvePoint curve : curvePoints) {
			if (curve.getCurveId() == 3 && curve.getTerm() == 33d && curve.getValue() == 330d) {
				curvePointRepository.deleteById(curve.getId());
			} else {
				if (curve.getCurveId() == 2 && curve.getTerm() == 22d) {
					curvePointRepository.deleteById(curve.getId());
				}
			}
		}
	}

	@Test
	public void testRequestCurvePointPage() throws Exception {

		Model model = mock(Model.class);
		String response = curvePointController.home(model);

		assertThat(response).isEqualTo("curvePoint/list");

	}

	@Test
	public void testAddCurvePointForm() {

		CurvePointDto curvePointDto = new CurvePointDto();

		String response = curvePointController.addCurvePointForm(curvePointDto);

		assertThat(response).isEqualTo("curvePoint/add");

	}

	@Test
	public void testValidateOk() {

		CurvePointDto curvePointDto = new CurvePointDto();
		curvePointDto.setCurveId(3);
		curvePointDto.setTerm(33d);
		curvePointDto.setValue(330d);

		BindingResult result = mock(BindingResult.class);
		Model model = mock(Model.class);

		String response = curvePointController.validate(curvePointDto, result, model);

		assertThat(result.hasErrors()).isFalse();
		assertThat(response).isEqualTo("redirect:/curvePoint/list");

	}

	@Test
	public void testValidateKo() {

		BindingResult result = mock(BindingResult.class);
		Model model = mock(Model.class);
		CurvePointDto curvePointDto = new CurvePointDto();

		when(result.hasErrors()).thenReturn(true);
		String response = curvePointController.validate(curvePointDto, result, model);

		assertThat(result.hasErrors()).isTrue();
		assertThat(response).isEqualTo("curvePoint/add");

	}

	@Test
	public void testUpdateCurvePointOk() {

		BindingResult result = mock(BindingResult.class);
		Model model = mock(Model.class);
		CurvePointDto curvePointDtoToUpdate = new CurvePointDto();

		Collection<CurvePoint> curvepointssBeforeUpdate = curvePointRepository.findAll();
		for (CurvePoint curve : curvepointssBeforeUpdate) {
			if (curve.getCurveId() == 2 && curve.getTerm() == 22d && curve.getValue() == 220d) {
				curvePointDtoToUpdate.setId(curve.getId());
				curvePointDtoToUpdate.setCurveId(curve.getCurveId());
				curvePointDtoToUpdate.setTerm(curve.getTerm());
				curvePointDtoToUpdate.setValue(440d);

				curvePointController.updateCurvePoint(curve.getId(), curvePointDtoToUpdate, result, model);
			}
		}

		Optional<CurvePoint> curvePointUpdated = curvePointRepository.findById(curvePointDtoToUpdate.getId());

		assertThat(curvePointUpdated.get().getValue() == 440d).isTrue();
	}

	@Test
	public void testUpdateCurvePointKo() {

		BindingResult result = mock(BindingResult.class);
		Model model = mock(Model.class);
		CurvePointDto curvePointDtoToUpdate = new CurvePointDto();
		String response = "";

		when(result.hasErrors()).thenReturn(true);

		Collection<CurvePoint> curvepointssBeforeUpdate = curvePointRepository.findAll();
		for (CurvePoint curve : curvepointssBeforeUpdate) {
			if (curve.getCurveId() == 2 && curve.getTerm() == 22d && curve.getValue() == 220d) {
				curvePointDtoToUpdate.setId(curve.getId());
				curvePointDtoToUpdate.setCurveId(null);
				curvePointDtoToUpdate.setTerm(curve.getTerm());
				curvePointDtoToUpdate.setValue(curve.getValue());

				response = curvePointController.updateCurvePoint(curve.getId(), curvePointDtoToUpdate, result, model);
			}
		}

		Optional<CurvePoint> curvePointUpdated = curvePointRepository.findById(curvePointDtoToUpdate.getId());

		assertThat(result.hasErrors()).isTrue();
		assertThat(response).isEqualTo("/curvePoint/update/" + curvePointDtoToUpdate.getId());
		assertThat(curvePointUpdated.get().getCurveId()).isEqualTo(2);
	}

	@Test
	public void testDeleteCurvePoint() {
		Model model = mock(Model.class);

		Collection<CurvePoint> curvePointsBeforeDelete = curvePointRepository.findAll();
		int listSize = curvePointsBeforeDelete.size();
		for (CurvePoint curve : curvePointsBeforeDelete) {
			if (curve.getCurveId() == 1 && curve.getTerm() == 11d && curve.getValue() == 110d) {
				curvePointController.deleteCurvePoint(curve.getId(), model);
			}
		}
		Collection<CurvePoint> curvePointsAfterDelete = curvePointRepository.findAll();
		assertThat(curvePointsAfterDelete.size() < listSize).isTrue();
	}
}
