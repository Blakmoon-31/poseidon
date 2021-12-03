package com.nnk.springboot.poseidon.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
/*
 * DTO of user used for get list method
 */
public class UserListDto {

	private Integer id;

	private String username;

	private String fullname;

	private String role;

}
