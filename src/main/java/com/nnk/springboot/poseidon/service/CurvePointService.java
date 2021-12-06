package com.nnk.springboot.poseidon.service;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.poseidon.domain.CurvePoint;
import com.nnk.springboot.poseidon.dto.CurvePointDto;
import com.nnk.springboot.poseidon.mapper.MapStructMapper;
import com.nnk.springboot.poseidon.repository.CurvePointRepository;

@Service
public class CurvePointService {

	private static Logger logger = LoggerFactory.getLogger(CurvePointService.class);

	@Autowired
	private MapStructMapper mapStructMapper;

	@Autowired
	private CurvePointRepository curvePointRepository;

	public Collection<CurvePointDto> getCurvePoints() {
		logger.info("Obtaining list of curvePoints, mapping in curvePointDtos");

		Collection<CurvePoint> curvePoints = curvePointRepository.findAll();

		Collection<CurvePointDto> curvePointDtos = mapStructMapper.curvePointsToCurvePointDtos(curvePoints);

		return curvePointDtos;
	}

	public Optional<CurvePointDto> getCurvePointById(Integer id) {
		logger.info("Obtaining curvePoint with id " + id + ", mapping in curvePointDto");

		Optional<CurvePoint> curvePoint = curvePointRepository.findById(id);

		if (curvePoint.isPresent() == true) {
			Optional<CurvePointDto> curvePointDto = Optional
					.of(mapStructMapper.curvePointToCurvePointDto(curvePoint.get()));
			return curvePointDto;
		} else {
			Optional<CurvePointDto> curvePointDto = Optional.empty();
			return curvePointDto;
		}

	}

	@Transactional
	public void deleteCurvePoint(CurvePointDto curvePointDtoToDelete) {
		logger.info("Deleting curvePoint");

		CurvePoint curvePointToDelete = mapStructMapper.curvePointDtoToCurvePoint(curvePointDtoToDelete);

		curvePointRepository.delete(curvePointToDelete);

	}

	@Transactional
	public CurvePointDto saveCurvePoint(@Valid CurvePointDto curvePointDtoToSave) {
		logger.info("Saving curvePoint, return mapping curvePointDto");

		CurvePoint curvePointToSave = mapStructMapper.curvePointDtoToCurvePoint(curvePointDtoToSave);

		CurvePoint curvePointSaved = curvePointRepository.save(curvePointToSave);

		return mapStructMapper.curvePointToCurvePointDto(curvePointSaved);

	}
}
