package com.nnk.springboot.poseidon.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CurvePointDto {

	private Integer id;

	@NotNull(message = "Curve Id must not be null")
	private Integer curveId;

	@NotNull(message = "Term must not be null")
	private Double term;

	@NotNull(message = "Value must not be null")
	private Double value;

}
