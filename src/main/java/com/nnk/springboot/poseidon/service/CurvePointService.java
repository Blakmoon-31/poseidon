package com.nnk.springboot.poseidon.service;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.poseidon.domain.CurvePoint;
import com.nnk.springboot.poseidon.dto.CurvePointDto;
import com.nnk.springboot.poseidon.mapper.MapStructMapper;
import com.nnk.springboot.poseidon.repository.CurvePointRepository;

@Service
public class CurvePointService {

	@Autowired
	private MapStructMapper mapStructMapper;

	@Autowired
	private CurvePointRepository curvePointRepository;

	public Collection<CurvePointDto> getCurvePoints() {

		Collection<CurvePoint> curvePoints = curvePointRepository.findAll();

		Collection<CurvePointDto> curvePointDtos = mapStructMapper.curvePointsToCurvePointDtos(curvePoints);

		return curvePointDtos;
	}

	public Optional<CurvePointDto> getCurvePointById(Integer id) {

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

		CurvePoint curvePointToDelete = mapStructMapper.curvePointDtoToCurvePoint(curvePointDtoToDelete);

		curvePointRepository.delete(curvePointToDelete);

	}

	@Transactional
	public CurvePointDto saveCurvePoint(@Valid CurvePointDto curvePointDtoToSave) {

		CurvePoint curvePointToSave = mapStructMapper.curvePointDtoToCurvePoint(curvePointDtoToSave);

		CurvePoint curvePointSaved = curvePointRepository.save(curvePointToSave);

		return mapStructMapper.curvePointToCurvePointDto(curvePointSaved);

	}
}
