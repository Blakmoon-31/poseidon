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

import com.nnk.springboot.poseidon.controller.RuleNameController;
import com.nnk.springboot.poseidon.domain.RuleName;
import com.nnk.springboot.poseidon.dto.RuleNameDto;
import com.nnk.springboot.poseidon.repository.RuleNameRepository;
import com.nnk.springboot.poseidon.service.RuleNameService;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
public class RuleNameControllerTest {

	@Autowired
	private RuleNameController ruleNameController;

	@Autowired
	private RuleNameRepository ruleNameRepository;

	@Mock
	private RuleNameService ruleNameService;

	@BeforeAll
	public void initTestData() {

		RuleName ruleNameOne = new RuleName();
		ruleNameOne.setName("Name test one");
		ruleNameOne.setDescription("Description test one");
		ruleNameOne.setJson("Json test one");
		ruleNameOne.setTemplate("Template test one");
		ruleNameOne.setSqlStr("SqlS test one");
		ruleNameOne.setSqlPart("SqlPart test one");

		ruleNameRepository.save(ruleNameOne);

		RuleName ruleNameTwo = new RuleName();
		ruleNameTwo.setName("Name test two");
		ruleNameTwo.setDescription("Description test two");
		ruleNameTwo.setJson("Json test two");
		ruleNameTwo.setTemplate("Template test two");
		ruleNameTwo.setSqlStr("SqlS test two");
		ruleNameTwo.setSqlPart("SqlPart test two");

		ruleNameRepository.save(ruleNameTwo);

	}

	@AfterAll
	public void resetTestData() {

		Collection<RuleName> ruleNames = ruleNameRepository.findAll();
		for (RuleName rule : ruleNames) {
			if (rule.getName().equals("Name test two") || rule.getName().equals("Name test")) {
				ruleNameRepository.delete(rule);
			}
		}

	}

	@Test
	public void testHomeRuleNameList() throws Exception {

		Model model = mock(Model.class);
		String response = ruleNameController.homeRuleNameList(model);

		assertThat(response).isEqualTo("ruleName/list");

	}

	@Test
	public void testAddRuleNameForm() {

		RuleNameDto ruleNameDto = new RuleNameDto();

		String response = ruleNameController.addRuleNameForm(ruleNameDto);

		assertThat(response).isEqualTo("ruleName/add");

	}

	@Test
	public void testValidateOk() {

		RuleNameDto ruleNameDto = new RuleNameDto();
		ruleNameDto.setName("Name test");
		ruleNameDto.setDescription("Description test");
		ruleNameDto.setJson("Json test");
		ruleNameDto.setTemplate("Template test");
		ruleNameDto.setSqlStr("SqlS test");
		ruleNameDto.setSqlPart("SqlPart test");

		BindingResult result = mock(BindingResult.class);
		Model model = mock(Model.class);

		String response = ruleNameController.validateRuleName(ruleNameDto, result, model);

		assertThat(result.hasErrors()).isFalse();
		assertThat(response).isEqualTo("redirect:/ruleName/list");

	}

	@Test
	public void testValidateRuleNameKo() {

		BindingResult result = mock(BindingResult.class);
		Model model = mock(Model.class);
		RuleNameDto ruleNameDto = new RuleNameDto();

		when(result.hasErrors()).thenReturn(true);

		String response = ruleNameController.validateRuleName(ruleNameDto, result, model);

		assertThat(result.hasErrors()).isTrue();
		assertThat(response).isEqualTo("ruleName/add");

	}

	@Test
	public void testUpdateRuleNameOk() {

		BindingResult result = mock(BindingResult.class);
		Model model = mock(Model.class);
		RuleNameDto ruleNameDtoToUpdate = new RuleNameDto();

		Collection<RuleName> ruleNamesBeforeUpdate = ruleNameRepository.findAll();
		for (RuleName rule : ruleNamesBeforeUpdate) {
			if (rule.getName().equals("Name test two")) {
				ruleNameDtoToUpdate.setId(rule.getId());
				ruleNameDtoToUpdate.setName(rule.getName());
				ruleNameDtoToUpdate.setDescription("Description updated");
				ruleNameDtoToUpdate.setJson(rule.getJson());
				ruleNameDtoToUpdate.setTemplate(rule.getTemplate());
				ruleNameDtoToUpdate.setSqlStr(rule.getSqlStr());
				ruleNameDtoToUpdate.setSqlPart(rule.getSqlPart());

				ruleNameController.updateRuleName(rule.getId(), ruleNameDtoToUpdate, result, model);
			}
		}

		Optional<RuleName> bidUpdated = ruleNameRepository.findById(ruleNameDtoToUpdate.getId());

		assertThat(bidUpdated.get().getDescription()).isEqualTo("Description updated");
	}

	@Test
	public void testUpdateRuleNameKo() {

		BindingResult result = mock(BindingResult.class);
		Model model = mock(Model.class);
		RuleNameDto ruleNameDtoToUpdate = new RuleNameDto();
		String response = "";

		when(result.hasErrors()).thenReturn(true);

		Collection<RuleName> ruleNamesBeforeUpdate = ruleNameRepository.findAll();
		for (RuleName rule : ruleNamesBeforeUpdate) {
			if (rule.getName().equals("Name test two")) {
				ruleNameDtoToUpdate.setId(rule.getId());
				ruleNameDtoToUpdate.setName(rule.getName());
				ruleNameDtoToUpdate.setDescription(rule.getDescription());
				ruleNameDtoToUpdate.setJson(null);
				ruleNameDtoToUpdate.setTemplate(rule.getTemplate());
				ruleNameDtoToUpdate.setSqlStr(rule.getSqlStr());
				ruleNameDtoToUpdate.setSqlPart(rule.getSqlPart());

				response = ruleNameController.updateRuleName(rule.getId(), ruleNameDtoToUpdate, result, model);
			}
		}

		Optional<RuleName> ruleNameUpdated = ruleNameRepository.findById(ruleNameDtoToUpdate.getId());

		assertThat(result.hasErrors()).isTrue();
		assertThat(response).isEqualTo("/ruleName/update");
		assertThat(ruleNameUpdated.get().getJson()).isEqualTo("Json test two");
	}

	@Test
	public void testDeleteRuleName() {
		Model model = mock(Model.class);

		Collection<RuleName> ruleNamesBeforeDelete = ruleNameRepository.findAll();
		int listSize = ruleNamesBeforeDelete.size();
		for (RuleName rule : ruleNamesBeforeDelete) {
			if (rule.getName().equals("Name test one")) {
				ruleNameController.deleteRuleName(rule.getId(), model);
			}
		}
		Collection<RuleName> ruleNamesAfterDelete = ruleNameRepository.findAll();
		assertThat(ruleNamesAfterDelete.size() < listSize).isTrue();
	}

}
