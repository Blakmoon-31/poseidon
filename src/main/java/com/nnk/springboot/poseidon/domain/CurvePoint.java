package com.nnk.springboot.poseidon.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "curvepoint")
public class CurvePoint {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Integer id;

	@NotNull(message = "Curve Id must not be null")
	@Column(name = "CurveId")
	private Integer curveId;

	@Column(name = "asOfDate")
	private LocalDateTime asOfDate;

	@NotNull(message = "Term must not be null")
	@Column(name = "term")
	private Double term;

	@NotNull(message = "Value must not be null")
	@Column(name = "value")
	private Double value;

	@Column(name = "creationDate")
	private LocalDateTime creationDate;

}
