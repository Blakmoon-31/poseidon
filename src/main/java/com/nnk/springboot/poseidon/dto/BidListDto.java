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
public class BidListDto {

	private Integer BidListId;

	@NotBlank(message = "Account is mandatory")
	@Size(max = 30)
	private String account;

	@NotBlank(message = "Type is mandatory")
	@Size(max = 30)
	private String type;

	private Double bidQuantity;

}
