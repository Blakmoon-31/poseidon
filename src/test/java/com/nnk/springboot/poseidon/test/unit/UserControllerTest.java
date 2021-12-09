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

import com.nnk.springboot.poseidon.controller.UserController;
import com.nnk.springboot.poseidon.domain.User;
import com.nnk.springboot.poseidon.dto.UserDto;
import com.nnk.springboot.poseidon.repository.UserRepository;
import com.nnk.springboot.poseidon.service.UserService;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private UserController userController;

	@Autowired
	private UserRepository userRepository;

	@Mock
	private UserService userService;

	@BeforeAll
	public void initTestData() {

		User userOne = new User();
		userOne.setUsername("User test one");
		userOne.setPassword("Type test #1");
		userOne.setFullname("Full name test one");
		userOne.setRole("ADMIN");

		userRepository.save(userOne);

		User userTwo = new User();
		userTwo.setUsername("User test two");
		userTwo.setPassword("Type test #2");
		userTwo.setFullname("Full name test two");
		userTwo.setRole("ADMIN");

		userRepository.save(userTwo);
	}

	@AfterAll
	public void resetTestData() {

		Collection<User> users = userRepository.findAll();
		for (User user : users) {
			if (user.getUsername().equals("User test") || user.getUsername().equals("User test two")) {
				userRepository.deleteById(user.getId());
			}
		}
	}

	@Test
	public void testHomeUserList() throws Exception {

		Model model = mock(Model.class);
		String response = userController.homeUserList(model);

		assertThat(response).isEqualTo("user/list");

	}

	@Test
	public void testAddUserForm() {

		UserDto userDto = new UserDto();

		String response = userController.addUserForm(userDto);

		assertThat(response).isEqualTo("user/add");

	}

	@Test
	public void testValidateUserOk() {

		UserDto userDto = new UserDto();
		userDto.setUsername("User test");
		userDto.setPassword("Password test #3");
		userDto.setFullname("Fullname test");
		userDto.setRole("USER");

		BindingResult result = mock(BindingResult.class);
		Model model = mock(Model.class);

		String response = userController.validateUser(userDto, result, model);

		assertThat(result.hasErrors()).isFalse();
		assertThat(response).isEqualTo("redirect:/user/list");

	}

	@Test
	public void testValidateUserKoValidation() {

		BindingResult result = mock(BindingResult.class);
		Model model = mock(Model.class);
		UserDto userDto = new UserDto();

		when(result.hasErrors()).thenReturn(true);

		String response = userController.validateUser(userDto, result, model);

		assertThat(result.hasErrors()).isTrue();
		assertThat(response).isEqualTo("user/add");

	}

	@Test
	public void testValidateUserKoPassword() {

		BindingResult result = mock(BindingResult.class);
		Model model = mock(Model.class);
		UserDto userDto = new UserDto();
		userDto.setUsername("User test ko");
		userDto.setPassword("Password");
		userDto.setFullname("Fullname test ko");
		userDto.setRole("USER");

//		when(result.hasErrors()).thenReturn(true);

		String response = userController.validateUser(userDto, result, model);

		assertThat(response).isEqualTo("user/add");

	}

	@Test
	public void testUpdateUserOk() {

		BindingResult result = mock(BindingResult.class);
		Model model = mock(Model.class);
		UserDto userDtoToUpdate = new UserDto();

		Collection<User> usersBeforeUpdate = userRepository.findAll();
		for (User user : usersBeforeUpdate) {
			if (user.getUsername().equals("User test two")) {
				userDtoToUpdate.setId(user.getId());
				userDtoToUpdate.setUsername(user.getUsername());
				userDtoToUpdate.setPassword(user.getPassword());
				userDtoToUpdate.setFullname("Fullename updated");
				userDtoToUpdate.setRole(user.getRole());

				userController.updateUser(user.getId(), userDtoToUpdate, result, model);
			}
		}

		Optional<User> userUpdated = userRepository.findById(userDtoToUpdate.getId());

		assertThat(userUpdated.get().getFullname()).isEqualTo("Fullename updated");
	}

	@Test
	public void testUpdateUserKo() {

		BindingResult result = mock(BindingResult.class);
		Model model = mock(Model.class);
		UserDto userDtoToUpdate = new UserDto();
		String response = "";

		when(result.hasErrors()).thenReturn(true);

		Collection<User> usersBeforeUpdate = userRepository.findAll();
		for (User user : usersBeforeUpdate) {
			if (user.getUsername().equals("User test two")) {
				userDtoToUpdate.setId(user.getId());
				userDtoToUpdate.setUsername(user.getUsername());
				userDtoToUpdate.setPassword(user.getPassword());
				userDtoToUpdate.setFullname(user.getFullname());
				userDtoToUpdate.setRole(null);

				response = userController.updateUser(user.getId(), userDtoToUpdate, result, model);
			}
		}

		Optional<User> userUpdated = userRepository.findById(userDtoToUpdate.getId());

		assertThat(result.hasErrors()).isTrue();
		assertThat(response).isEqualTo("/user/update/" + userDtoToUpdate.getId());
		assertThat(userUpdated.get().getRole()).isEqualTo("ADMIN");
	}

	@Test
	public void testDeleteUser() {
		Model model = mock(Model.class);

		Collection<User> usersBeforeDelete = userRepository.findAll();
		int listSize = usersBeforeDelete.size();
		for (User bid : usersBeforeDelete) {
			if (bid.getUsername().equals("User test one")) {
				userController.deleteUser(bid.getId(), model);
			}
		}
		Collection<User> usersAfterDelete = userRepository.findAll();
		assertThat(usersAfterDelete.size() < listSize).isTrue();
	}

}
