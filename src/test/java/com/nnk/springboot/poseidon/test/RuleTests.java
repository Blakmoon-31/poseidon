package com.nnk.springboot.poseidon.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.nnk.springboot.poseidon.domain.RuleName;
import com.nnk.springboot.poseidon.repository.RuleNameRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RuleTests {

	@Autowired
	private RuleNameRepository ruleNameRepository;

	@Test
	public void ruleTest() {
		RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");

		// Save
		rule = ruleNameRepository.save(rule);
		assertThat(rule.getId()).isNotNull();
		assertThat(rule.getName().equals("Rule Name")).isTrue();

		// Update
		rule.setName("Rule Name Update");
		rule = ruleNameRepository.save(rule);
		assertThat(rule.getName().equals("Rule Name Update")).isTrue();

		// Find
		List<RuleName> listResult = ruleNameRepository.findAll();
		assertThat(listResult.size() > 0).isTrue();

		// Delete
		Integer id = rule.getId();
		ruleNameRepository.delete(rule);
		Optional<RuleName> ruleList = ruleNameRepository.findById(id);
		assertThat(ruleList.isPresent()).isFalse();
	}
}
