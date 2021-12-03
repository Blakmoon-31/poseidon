package com.nnk.springboot.poseidon.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Data
@Getter
@Setter
@NoArgsConstructor
public class TradeDto {

	private Integer tradeId;

	@NotBlank(message = "Account is mandatory")
	private String account;

	@NotBlank(message = "Type is mandatory")
	private String type;

	private Double buyQuantity;

}
