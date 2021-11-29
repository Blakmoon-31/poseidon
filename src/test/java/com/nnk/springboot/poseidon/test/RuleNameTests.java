package com.nnk.springboot.poseidon.test;

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

import com.nnk.springboot.poseidon.dto.RuleNameDto;
import com.nnk.springboot.poseidon.service.RuleNameService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class RuleNameTests {

	@Autowired
	private RuleNameService ruleNameService;

	@BeforeAll
	public void initTestData() {

		RuleNameDto ruleName = new RuleNameDto();
		ruleName.setName("Rule Name TestInit one");
		ruleName.setDescription("Description TestInit one");
		ruleName.setJson("Json TestInit one");
		ruleName.setTemplate("Template TestInit one");
		ruleName.setSqlStr("SqlStr TestInit one");
		ruleName.setSqlPart("SqlPart TestInit one");

		ruleNameService.saveRuleName(ruleName);

		ruleName.setName("Rule Name TestInit two");
		ruleName.setDescription("Description TestInit two");
		ruleName.setJson("Json TestInit two");
		ruleName.setTemplate("Template TestInit two");
		ruleName.setSqlStr("SqlStr TestInit two");
		ruleName.setSqlPart("SqlPart TestInit two");

		ruleNameService.saveRuleName(ruleName);

	}

	@AfterAll
	public void resetTestData() {

		Collection<RuleNameDto> ruleNameDtos = ruleNameService.getRuleNames();

		for (RuleNameDto rule : ruleNameDtos) {
			if (rule.getName().equals("Rule Name TestInit one") || rule.getName().equals("Rule Name Test")) {
				ruleNameService.deleteRuleName(rule);
			}
		}
	}

	@Test
	public void testSaveRuleName() {

		RuleNameDto ruleNameDto = new RuleNameDto();
		ruleNameDto.setName("Rule Name Test");
		ruleNameDto.setDescription("Description Test");
		ruleNameDto.setJson("Json Test");
		ruleNameDto.setTemplate("Template Test");
		ruleNameDto.setSqlStr("SQL Test");
		ruleNameDto.setSqlPart("SQL Part Test");

		ruleNameDto = ruleNameService.saveRuleName(ruleNameDto);
		assertThat(ruleNameDto.getId()).isNotNull();
		assertThat(ruleNameDto.getName()).isEqualTo("Rule Name Test");

	}

	@Test
	public void testUpdateRuleName() {

		Collection<RuleNameDto> ruleNameDtos = ruleNameService.getRuleNames();
		RuleNameDto ruleNameDtoToUpdate = new RuleNameDto();

		for (RuleNameDto rule : ruleNameDtos) {
			if (rule.getName().equals("Rule Name TestInit one")) {
				ruleNameDtoToUpdate = rule;
			}
		}

		ruleNameDtoToUpdate.setDescription("Description update");
		ruleNameDtoToUpdate = ruleNameService.saveRuleName(ruleNameDtoToUpdate);
		assertThat(ruleNameDtoToUpdate.getDescription()).isEqualTo("Description update");
	}

	@Test
	public void testGetRuleNames() {

		Collection<RuleNameDto> ruleNameDtos = ruleNameService.getRuleNames();

		assertThat(ruleNameDtos.size() > 0).isTrue();
	}

	@Test
	public void testDeleteRuleName() {

		Collection<RuleNameDto> ruleNameDtos = ruleNameService.getRuleNames();
		RuleNameDto ruleNameDtoToDelete = new RuleNameDto();

		for (RuleNameDto rule : ruleNameDtos) {
			if (rule.getName().equals("Rule Name TestInit two")) {
				ruleNameDtoToDelete = rule;
			}
		}

		Integer id = ruleNameDtoToDelete.getId();
		ruleNameService.deleteRuleName(ruleNameDtoToDelete);
		Optional<RuleNameDto> ruleNameDtoDeleted = ruleNameService.getRuleNameById(id);
		assertThat(ruleNameDtoDeleted.isPresent()).isFalse();

	}
}
