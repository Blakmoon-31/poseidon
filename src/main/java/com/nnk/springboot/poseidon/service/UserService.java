package com.nnk.springboot.poseidon.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nnk.springboot.poseidon.domain.User;
import com.nnk.springboot.poseidon.dto.UserDto;
import com.nnk.springboot.poseidon.dto.UserListDto;
import com.nnk.springboot.poseidon.mapper.MapStructMapper;
import com.nnk.springboot.poseidon.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private MapStructMapper mapStructMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Collection<UserListDto> getUsers() {

		Collection<User> users = userRepository.findAll();

		Collection<UserListDto> userListDtos = mapStructMapper.usersToUserDtos(users);

		return userListDtos;
	}

	@Transactional
	public String saveUser(@Valid UserDto userDtoToSave) {

		// Validating password format
		final String USER_PASSWORD = userDtoToSave.getPassword();
		boolean isPasswordValid = isValidPassword(USER_PASSWORD);

		if (isPasswordValid) {
			userDtoToSave.setPassword(passwordEncoder.encode(userDtoToSave.getPassword()));
			User userToSave = mapStructMapper.userDtoToUser(userDtoToSave);
			userRepository.save(userToSave);
			return "User saved";
		} else {
			return "Invalid password";
		}

	}

	public Optional<UserDto> getUserById(Integer id) {

		Optional<User> user = userRepository.findById(id);

		if (user.isPresent()) {
			Optional<UserDto> userDto = Optional.of(mapStructMapper.userToUserDto(user.get()));
			return userDto;
		} else {
			Optional<UserDto> userDto = Optional.empty();
			return userDto;
		}
	}

	@Transactional
	public void deleteUser(UserDto userDtoToDelete) {

		User userToDelete = mapStructMapper.userDtoToUser(userDtoToDelete);

		userRepository.delete(userToDelete);

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Objects.requireNonNull(username);
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				getGrantedAuthorities(user));
	}

	private List<GrantedAuthority> getGrantedAuthorities(User user) {

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(user.getRole()));

		return authorities;
	}

	public boolean isValidPassword(final String PASSWORD) {
		boolean result = false;
		try {
			if (PASSWORD != null) {
				// _________________________
				// Parameters
				final String MIN_LENGHT = "8";
				// final String MAX_LENGHT = "125";
				final boolean SPECIAL_CHAR_NEEDED = true;

				// _________________________
				// Modules
				final String ONE_DIGIT = "(?=.*[0-9])"; // (?=.*[0-9]) a digit must occur at least once
				// final String LOWER_CASE = "(?=.*[a-z])"; //(?=.*[a-z]) a lower case letter
				// must occur at least once, not needed in project
				final String UPPER_CASE = "(?=.*[A-Z])"; // (?=.*[A-Z]) an upper case letter must occur at least once

				final String MIN_CHAR = ".{" + MIN_LENGHT + ",}"; // .{8,} at least 8
				// characters
				// final String MIN_MAX_CHAR = ".{" + MIN_LENGHT + "," + MAX_LENGHT + "}"; //
				// .{8,125} represents minimum
				// of
				// 8 characters and maximum of
				// 125 characters

				final String SPECIAL_CHAR;
				if (SPECIAL_CHAR_NEEDED)
					SPECIAL_CHAR = "(?=.*[@#$%^&+=])"; // (?=.*[@#$%^&+=]) a special character must occur at least once
				else
					SPECIAL_CHAR = "";
				// _________________________
				// Pattern
				// String pattern =
				// "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
				final String PATTERN = ONE_DIGIT + UPPER_CASE + SPECIAL_CHAR + MIN_CHAR;
				// _________________________
				result = PASSWORD.matches(PATTERN);
				// _________________________
			}

		} catch (Exception ex) {
			result = false;
		}

		return result;
	}

}
