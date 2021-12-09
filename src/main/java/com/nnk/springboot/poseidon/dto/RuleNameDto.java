package com.nnk.springboot.poseidon.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RuleNameDto {

	private Integer id;

	@NotBlank(message = "Name is mandatory")
	@Size(max = 125)
	private String name;

	@NotBlank(message = "Description is mandatory")
	@Size(max = 125)
	private String description;

	@NotBlank(message = "Json is mandatory")
	@Size(max = 125)
	private String json;

	@NotBlank(message = "Template is mandatory")
	@Size(max = 125)
	private String template;

	@NotBlank(message = "Sql is mandatory")
	@Size(max = 125)
	private String sqlStr;

	@NotBlank(message = "SqlPart is mandatory")
	@Size(max = 125)
	private String sqlPart;

}
