package com.nnk.springboot.poseidon.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Id")
	private Integer id;

	@NotBlank(message = "Username is mandatory")
	@Size(max = 125)
	@Column(name = "username")
	private String username;

	@NotBlank(message = "Password is mandatory")
	@Size(min = 8, max = 125)
	@Column(name = "password")
	private String password;

	@NotBlank(message = "FullName is mandatory")
	@Size(max = 125)
	@Column(name = "fullname")
	private String fullname;

	@NotBlank(message = "Role is mandatory")
	@Size(max = 125)
	@Column(name = "role")
	private String role;

}
