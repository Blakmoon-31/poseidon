package com.nnk.springboot.poseidon.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "bidlist")
public class BidList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BidListId")
	private Integer bidListId;

	@NotBlank(message = "Account is mandatory")
	@Size(max = 30)
	@Column(name = "account")
	private String account;

	@NotBlank(message = "Type is mandatory")
	@Size(max = 30)
	@Column(name = "type")
	private String type;

	@NotNull(message = "Bid Quantity must not be null")
	@Column(name = "bidQuantity")
	private Double bidQuantity;

	@Column(name = "askQuantity")
	private Double askQuantity;

	@Column(name = "bid")
	private Double bid;

	@Column(name = "ask")
	private Double ask;

	@Column(name = "benchmark")
	private String benchmark;

	@Column(name = "bidListDate")
	private LocalDateTime bidListDate;

	@Column(name = "commentary")
	private String commentary;

	@Column(name = "security")
	private String security;

	@Column(name = "status")
	private String status;

	@Column(name = "trader")
	private String trader;

	@Column(name = "book")
	private String book;

	@Column(name = "creationName")
	private String creationName;

	@Column(name = "creationDate")
	private LocalDateTime creationDate;

	@Column(name = "revisionName")
	private String revisionName;

	@Column(name = "revisionDate")
	private LocalDateTime revisionDate;

	@Column(name = "dealName")
	private String dealName;

	@Column(name = "dealType")
	private String dealType;

	@Column(name = "sourceListId")
	private String sourceListId;

	@Column(name = "side")
	private String side;

}
