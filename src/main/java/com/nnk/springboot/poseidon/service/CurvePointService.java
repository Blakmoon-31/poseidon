package com.nnk.springboot.poseidon.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.poseidon.domain.CurvePoint;
import com.nnk.springboot.poseidon.repository.CurvePointRepository;

@Service
public class CurvePointService {

	@Autowired
	private CurvePointRepository curvePointRepository;

	public Collection<CurvePoint> getCurvePoints() {

		List<CurvePoint> curvePoints = curvePointRepository.findAll();

		return curvePoints;
	}

	public Optional<CurvePoint> getCurvePointById(Integer id) {

		Optional<CurvePoint> curvePoint = curvePointRepository.findById(id);

		return curvePoint;
	}

	@Transactional
	public void deleteCurvepoint(CurvePoint curvePointToDelete) {

		curvePointRepository.delete(curvePointToDelete);

	}

	@Transactional
	public void saveCurvePoint(@Valid CurvePoint curvePoint) {

		curvePointRepository.save(curvePoint);

	}
}
