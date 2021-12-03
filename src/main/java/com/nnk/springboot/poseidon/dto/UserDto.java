package com.nnk.springboot.poseidon.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Data
@Getter
@Setter
@NoArgsConstructor
/*
 * DTO of user used for add, update and delete methods
 */
public class UserDto {

	private Integer id;

	@NotBlank(message = "Username is mandatory")
	@Size(max = 125)
	private String username;

	@NotBlank(message = "Password is mandatory")
	private String password;

	@NotBlank(message = "FullName is mandatory")
	@Size(max = 125)
	private String fullname;

	@NotBlank(message = "Role is mandatory")
	@Size(max = 125)
	private String role;

}
