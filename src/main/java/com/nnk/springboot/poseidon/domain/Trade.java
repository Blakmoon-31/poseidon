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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "trade")
public class Trade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TradeId")
	private Integer tradeId;

	@NotBlank(message = "Account is mandatory")
	@Column(name = "account")
	private String account;

	@NotBlank(message = "Type is mandatory")
	@Column(name = "type")
	private String type;

	@NotNull(message = "Buy Quantity must not be null")
	@Column(name = "buyQuantity")
	private Double buyQuantity;

	@Column(name = "sellQuantity")
	private Double sellQuantity;

	@Column(name = "buyPrice")
	private Double buyPrice;

	@Column(name = "sellPrice")
	private Double sellPrice;

	@Column(name = "benchmark")
	private String benchmark;

	@Column(name = "tradeDate")
	private LocalDateTime tradeDate;

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
