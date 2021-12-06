package com.nnk.springboot.poseidon.service;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.poseidon.domain.RuleName;
import com.nnk.springboot.poseidon.dto.RuleNameDto;
import com.nnk.springboot.poseidon.mapper.MapStructMapper;
import com.nnk.springboot.poseidon.repository.RuleNameRepository;

@Service
public class RuleNameService {

	private static Logger logger = LoggerFactory.getLogger(RuleNameService.class);

	@Autowired
	private MapStructMapper mapStructMapper;

	@Autowired
	private RuleNameRepository ruleNameRepository;

	public Collection<RuleNameDto> getRuleNames() {
		logger.info("Obtaining list of ruleNames, mapping in ruleNameDtos");

		Collection<RuleName> ruleNames = ruleNameRepository.findAll();

		Collection<RuleNameDto> ruleNameDtos = mapStructMapper.ruleNamesToRuleNameDtos(ruleNames);

		return ruleNameDtos;
	}

	public Optional<RuleNameDto> getRuleNameById(Integer id) {
		logger.info("Obtaining ruleName with id " + id + ", mapping in ruleNameDto");

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
		logger.info("Deleting ruleName");

		RuleName ruleNameToDelete = mapStructMapper.ruleNameDtoToRuleName(ruleNameDtoToDelete);

		ruleNameRepository.delete(ruleNameToDelete);

	}

	@Transactional
	public RuleNameDto saveRuleName(@Valid RuleNameDto ruleNameDtoToSave) {
		logger.info("Saving ruleName, return mapping ruleNameDto");

		RuleName ruleNameToSave = mapStructMapper.ruleNameDtoToRuleName(ruleNameDtoToSave);

		RuleName ruleNameSaved = ruleNameRepository.save(ruleNameToSave);

		return mapStructMapper.ruleNameToRuleNameDto(ruleNameSaved);

	}

}
