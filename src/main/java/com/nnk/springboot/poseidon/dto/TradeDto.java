package com.nnk.springboot.poseidon.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class TradeDto {

	private Integer tradeId;

	@NotBlank(message = "Account is mandatory")
	private String account;

	@NotBlank(message = "Type is mandatory")
	private String type;

	private Double buyQuantity;

}
