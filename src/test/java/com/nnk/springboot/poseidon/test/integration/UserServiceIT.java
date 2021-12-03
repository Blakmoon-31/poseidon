package com.nnk.springboot.poseidon.test.integration;

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
import com.nnk.springboot.poseidon.dto.UserListDto;
import com.nnk.springboot.poseidon.service.UserService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class UserServiceIT {

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

		Collection<UserListDto> userListDtos = userService.getUsers();

		for (UserListDto user : userListDtos) {
			if (user.getFullname().equals("Fullname TestInit one") || user.getFullname().equals("FulleName Test")) {
				userService.deleteUser(userService.getUserById(user.getId()).get());
			}
		}

	}

	@Test
	public void testGetUsers() {

		Collection<UserListDto> userListDtos = userService.getUsers();

		assertThat(userListDtos.size() > 0).isTrue();
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

		Collection<UserListDto> userListDtosBeforeTest = userService.getUsers();
		int userCountBeforeTest = userListDtosBeforeTest.size();

		UserDto userDtoToSave = new UserDto();
		userDtoToSave.setFullname("FulleName Test");
		userDtoToSave.setUsername("Usernametest");
		userDtoToSave.setRole("ADMIN");
		userDtoToSave.setPassword("aaaaaaaa");

		String result = userService.saveUser(userDtoToSave);
		Collection<UserListDto> userListDtosAfterTest = userService.getUsers();

		assertThat(result).isEqualTo("Invalid password");

		assertThat(userListDtosAfterTest.size()).isEqualTo(userCountBeforeTest);

	}

	@Test
	public void testUserPasswordEncoding() {

		Collection<UserListDto> userListDtos = userService.getUsers();
		UserDto userDto = new UserDto();

		for (UserListDto user : userListDtos) {
			if (user.getFullname().equals("Fullname TestInit one")) {
				userDto = userService.getUserById(user.getId()).get();
			}
		}

		assertThat(userDto.getPassword()).isNotEqualTo("A1#bcdef");
	}

	@Test
	public void testUpdateUser() {

		Collection<UserListDto> userListDtos = userService.getUsers();
		UserDto userDtoToUpdate = new UserDto();

		for (UserListDto user : userListDtos) {
			if (user.getFullname().equals("Fullname TestInit one")) {
				userDtoToUpdate = userService.getUserById(user.getId()).get();
			}
		}

		userDtoToUpdate.setRole("ADMIN");
		userService.saveUser(userDtoToUpdate);
		UserDto userDtoUpdated = userService.getUserById(userDtoToUpdate.getId()).get();
		assertThat(userDtoUpdated.getRole()).isEqualTo("ADMIN");

	}

	@Test
	public void testDeleteUser() {

		Collection<UserListDto> userListDtos = userService.getUsers();
		UserDto userDtoToDelete = new UserDto();

		for (UserListDto user : userListDtos) {
			if (user.getFullname().equals("Fullname TestInit two")) {
				userDtoToDelete = userService.getUserById(user.getId()).get();
			}
		}

		Integer id = userDtoToDelete.getId();
		userService.deleteUser(userDtoToDelete);
		Optional<UserDto> userDtoDeleted = userService.getUserById(id);
		assertThat(userDtoDeleted.isPresent()).isFalse();

	}

}
