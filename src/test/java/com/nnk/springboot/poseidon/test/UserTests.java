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

import com.nnk.springboot.poseidon.dto.UserDto;
import com.nnk.springboot.poseidon.service.UserService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class UserTests {

	@Autowired
	private UserService userService;

	@BeforeAll
	public void initTestData() {

		UserDto userDto = new UserDto();
		userDto.setFullname("Fullname TestInit one");
		userDto.setUsername("Username TestInit one");
		userDto.setRole("USER");
		userDto.setPassword("A1#bcdef");

		userService.saveUser(userDto);

		userDto.setFullname("Fullname TestInit two");
		userDto.setUsername("Username TestInit two");
		userDto.setRole("USER");
		userDto.setPassword("A1#bcdef");

		userService.saveUser(userDto);

	}

	@AfterAll
	public void resetTestData() {

		Collection<UserDto> userDtos = userService.getUsers();

		for (UserDto user : userDtos) {
			if (user.getFullname().equals("Fullname TestInit one") || user.getFullname().equals("FulleName Test")) {
				userService.deleteUser(user);
			}
		}

	}

	@Test
	public void testGetUsers() {

		Collection<UserDto> userDtos = userService.getUsers();

		assertThat(userDtos.size() > 0).isTrue();
	}

	@Test
	public void testGetUserById() {

		UserDto userDto = userService.getUserById(1).get();

		assertThat(userDto.getUsername()).isEqualTo("admin");
	}

	@Test
	public void testSaveUserCorrectPassword() {

		UserDto userDtoToSave = new UserDto();
		userDtoToSave.setFullname("FulleName Test");
		userDtoToSave.setUsername("Usernametest");
		userDtoToSave.setRole("ADMIN");
		userDtoToSave.setPassword("A1#bcdef");

		String result = userService.saveUser(userDtoToSave);

		assertThat(result).isEqualTo("User saved");

	}

	@Test
	public void testSaveUserIncorrectPassword() {

		Collection<UserDto> userDtosBeforeTest = userService.getUsers();
		int userCountBeforeTest = userDtosBeforeTest.size();

		UserDto userDtoToSave = new UserDto();
		userDtoToSave.setFullname("FulleName Test");
		userDtoToSave.setUsername("Usernametest");
		userDtoToSave.setRole("ADMIN");
		userDtoToSave.setPassword("aaaaaaaa");

		String result = userService.saveUser(userDtoToSave);
		Collection<UserDto> userDtosAfterTest = userService.getUsers();

		assertThat(result).isEqualTo("Invalid password");

		assertThat(userDtosAfterTest.size()).isEqualTo(userCountBeforeTest);

	}

	@Test
	public void testUserPasswordEncoding() {

		Collection<UserDto> userDtos = userService.getUsers();
		UserDto userDto = new UserDto();

		for (UserDto user : userDtos) {
			if (user.getFullname().equals("Fullname TestInit one")) {
				userDto = user;
			}
		}

		assertThat(userDto.getPassword()).isNotEqualTo("A1#bcdef");
	}

	@Test
	public void testUpdateUser() {

		Collection<UserDto> userDtos = userService.getUsers();
		UserDto userDtoToUpdate = new UserDto();

		for (UserDto user : userDtos) {
			if (user.getFullname().equals("Fullname TestInit one")) {
				userDtoToUpdate = user;
			}
		}

		userDtoToUpdate.setRole("ADMIN");
		userService.saveUser(userDtoToUpdate);
		UserDto userDtoUpdated = userService.getUserById(userDtoToUpdate.getId()).get();
		assertThat(userDtoUpdated.getRole()).isEqualTo("ADMIN");

	}

	@Test
	public void testDeleteUser() {

		Collection<UserDto> userDtos = userService.getUsers();
		UserDto userDtoToDelete = new UserDto();

		for (UserDto user : userDtos) {
			if (user.getFullname().equals("Fullname TestInit two")) {
				userDtoToDelete = user;
			}
		}

		Integer id = userDtoToDelete.getId();
		userService.deleteUser(userDtoToDelete);
		Optional<UserDto> userDtoDeleted = userService.getUserById(id);
		assertThat(userDtoDeleted.isPresent()).isFalse();

	}

}
