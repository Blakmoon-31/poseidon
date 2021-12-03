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

import com.nnk.springboot.poseidon.dto.CurvePointDto;
import com.nnk.springboot.poseidon.service.CurvePointService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class CurvePointServiceIT {

	@Autowired
	private CurvePointService curvePointService;

	@BeforeAll
	public void initTestData() {

		CurvePointDto curvePointDto = new CurvePointDto();
		curvePointDto.setCurveId(3);
		curvePointDto.setTerm(30d);
		curvePointDto.setValue(300d);

		curvePointService.saveCurvePoint(curvePointDto);

		curvePointDto.setCurveId(2);
		curvePointDto.setTerm(20d);
		curvePointDto.setValue(200d);

		curvePointService.saveCurvePoint(curvePointDto);

	}

	@AfterAll
	public void resetTestData() {

		Collection<CurvePointDto> curvePointDtos = curvePointService.getCurvePoints();

		for (CurvePointDto curve : curvePointDtos) {
			if (curve.getCurveId() == 10 && curve.getTerm() == 10d && curve.getValue() == 30d) {
				curvePointService.deleteCurvePoint(curve);
			} else {
				if (curve.getCurveId() == 20 && curve.getTerm() == 30d && curve.getValue() == 300d) {
					curvePointService.deleteCurvePoint(curve);
				}
			}
		}

	}

	@Test
	public void curvePointTest() {
		CurvePointDto curvePointDto = new CurvePointDto();
		curvePointDto.setCurveId(10);
		curvePointDto.setTerm(10d);
		curvePointDto.setValue(30d);

		curvePointDto = curvePointService.saveCurvePoint(curvePointDto);
		assertThat(curvePointDto.getId()).isNotNull();
		assertThat(curvePointDto.getCurveId()).isEqualTo(10);

	}

	@Test
	public void testUpdateCurvePoint() {

		Collection<CurvePointDto> curvePointDtos = curvePointService.getCurvePoints();
		CurvePointDto curvePointDtoToUpdate = new CurvePointDto();

		for (CurvePointDto curve : curvePointDtos) {
			if (curve.getCurveId() == 3 && curve.getTerm() == 30d && curve.getValue() == 300d) {
				curvePointDtoToUpdate = curve;
			}
		}

		curvePointDtoToUpdate.setCurveId(20);
		curvePointDtoToUpdate = curvePointService.saveCurvePoint(curvePointDtoToUpdate);
		assertThat(curvePointDtoToUpdate.getCurveId()).isEqualTo(20);

	}

	@Test
	public void testGetCurvePoints() {

		Collection<CurvePointDto> curvePointDtos = curvePointService.getCurvePoints();

		assertThat(curvePointDtos.size() > 0).isTrue();
	}

	@Test
	public void testDeleteCurvePoint() {

		Collection<CurvePointDto> curvePointDtos = curvePointService.getCurvePoints();
		CurvePointDto curvePointDtoToDelete = new CurvePointDto();

		for (CurvePointDto curve : curvePointDtos) {
			if (curve.getCurveId() == 2 && curve.getTerm() == 20d && curve.getValue() == 200d) {
				curvePointDtoToDelete = curve;
			}
		}

		Integer id = curvePointDtoToDelete.getId();
		curvePointService.deleteCurvePoint(curvePointDtoToDelete);
		Optional<CurvePointDto> curvePointDtoDeleted = curvePointService.getCurvePointById(id);
		assertThat(curvePointDtoDeleted.isPresent()).isFalse();

	}

}
