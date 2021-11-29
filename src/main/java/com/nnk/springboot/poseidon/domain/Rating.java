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
@Table(name = "rating")
public class Rating {
	// TODO: Map columns in data table RATING with corresponding java fields

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Integer id;

	@NotBlank(message = "MoodysRating is mandatory")
	@Size(max = 125)
	@Column(name = "moodysRating")
	private String moodysRating;

	@NotBlank(message = "SandPRating is mandatory")
	@Size(max = 125)
	@Column(name = "sandPRating")
	private String sandPRating;

	@NotBlank(message = "FitchRating is mandatory")
	@Size(max = 125)
	@Column(name = "fitchRating")
	private String fitchRating;

	@Column(name = "orderNumber")
	private Integer orderNumber;

}