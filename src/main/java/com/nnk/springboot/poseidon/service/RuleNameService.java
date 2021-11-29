package com.nnk.springboot.poseidon.service;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.poseidon.domain.RuleName;
import com.nnk.springboot.poseidon.dto.RuleNameDto;
import com.nnk.springboot.poseidon.mapper.MapStructMapper;
import com.nnk.springboot.poseidon.repository.RuleNameRepository;

@Service
public class RuleNameService {

	@Autowired
	private MapStructMapper mapStructMapper;

	@Autowired
	private RuleNameRepository ruleNameRepository;

	public Collection<RuleNameDto> getRuleNames() {

		Collection<RuleName> ruleNames = ruleNameRepository.findAll();

		Collection<RuleNameDto> ruleNameDtos = mapStructMapper.ruleNamesToRuleNameDtos(ruleNames);

		return ruleNameDtos;
	}

	@Transactional
	public RuleNameDto saveRuleName(@Valid RuleNameDto ruleNameDtoToSave) {

		RuleName ruleNameToSave = mapStructMapper.ruleNameDtoToRuleName(ruleNameDtoToSave);

		RuleName ruleNameSaved = ruleNameRepository.save(ruleNameToSave);

		return mapStructMapper.ruleNameToRuleNameDto(ruleNameSaved);

	}

	public Optional<RuleNameDto> getRuleNameById(Integer id) {

		Optional<RuleName> ruleName = ruleNameRepository.findById(id);

		if (ruleName.isPresent()) {
			Optional<RuleNameDto> ruleNameDto = Optional.of(mapStructMapper.ruleNameToRuleNameDto(ruleName.get()));
			return ruleNameDto;
		} else {
			Optional<RuleNameDto> ruleNameDto = Optional.empty();
			return ruleNameDto;
		}
	}

	@Transactional
	public void deleteRuleName(RuleNameDto ruleNameDtoToDelete) {

		RuleName ruleNameToDelete = mapStructMapper.ruleNameDtoToRuleName(ruleNameDtoToDelete);

		ruleNameRepository.delete(ruleNameToDelete);

	}

}
