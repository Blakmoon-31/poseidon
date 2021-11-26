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
@Table(name = "rulename")
public class RuleName {
	// TODO: Map columns in data table RULENAME with corresponding java fields

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Integer id;

	@NotBlank(message = "Name is mandatory")
	@Size(max = 125)
	@Column(name = "name")
	private String name;

	@NotBlank(message = "Description is mandatory")
	@Size(max = 125)
	@Column(name = "description")
	private String description;

	@NotBlank(message = "Json is mandatory")
	@Size(max = 125)
	@Column(name = "json")
	private String json;

	@NotBlank(message = "Template is mandatory")
	@Size(max = 125)
	@Column(name = "template")
	private String template;

	@NotBlank(message = "Sql is mandatory")
	@Size(max = 125)
	@Column(name = "sqlStr")
	private String sqlStr;

	@NotBlank(message = "SqlPart is mandatory")
	@Size(max = 125)
	@Column(name = "sqlPart")
	private String sqlPart;

	public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart) {
		super();
		this.name = name;
		this.description = description;
		this.json = json;
		this.template = template;
		this.sqlStr = sqlStr;
		this.sqlPart = sqlPart;
	}

}
