package com.nnk.springboot.poseidon.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserDto {

	private Integer id;

	@NotBlank(message = "Username is mandatory")
	@Size(max = 125)
	private String username;

	@NotBlank(message = "Password is mandatory")
	@Size(min = 8, max = 125)
	private String password;

	@NotBlank(message = "FullName is mandatory")
	@Size(max = 125)
	private String fullname;

	@NotBlank(message = "Role is mandatory")
	@Size(max = 125)
	private String role;

}
