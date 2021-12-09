package com.nnk.springboot.poseidon.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RatingDto {

	private Integer id;

	@NotBlank(message = "MoodysRating is mandatory")
	@Size(max = 125)
	private String moodysRating;

	@NotBlank(message = "SandPRating is mandatory")
	@Size(max = 125)
	private String sandPRating;

	@NotBlank(message = "FitchRating is mandatory")
	@Size(max = 125)
	private String fitchRating;

	private Integer orderNumber;

}
