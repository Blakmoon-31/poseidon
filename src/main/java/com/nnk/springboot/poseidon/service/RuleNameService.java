package com.nnk.springboot.poseidon.service;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.poseidon.domain.RuleName;
import com.nnk.springboot.poseidon.repository.RuleNameRepository;

@Service
public class RuleNameService {

	@Autowired
	private RuleNameRepository ruleNameRepository;

	public Collection<RuleName> getRuleNames() {

		Collection<RuleName> ruleNames = ruleNameRepository.findAll();

		return ruleNames;
	}

	@Transactional
	public void saveRuleName(@Valid RuleName ruleName) {
		ruleNameRepository.save(ruleName);

	}

	public Optional<RuleName> getRuleNameById(Integer id) {
		Optional<RuleName> ruleName = ruleNameRepository.findById(id);

		return ruleName;
	}

	@Transactional
	public void deleteRuleName(RuleName ruleNameToDelete) {
		ruleNameRepository.delete(ruleNameToDelete);

	}

}
