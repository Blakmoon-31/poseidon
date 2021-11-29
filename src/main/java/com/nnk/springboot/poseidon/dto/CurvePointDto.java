package com.nnk.springboot.poseidon.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CurvePointDto {

	private Integer id;

	@NotNull(message = "Curve Id must not be null")
	private Integer curveId;

	private Double term;

	private Double value;

}