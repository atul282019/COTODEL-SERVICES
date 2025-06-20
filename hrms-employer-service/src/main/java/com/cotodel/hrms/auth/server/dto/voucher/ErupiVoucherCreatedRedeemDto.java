package com.cotodel.hrms.auth.server.dto.voucher;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherCreatedRedeemDto {
	private Long id;
	private String name;
	private String mobile;	
	private Float amount;	
	private String merchanttxnId;
	private String purposeCode;
	private String mcc;
	private String redemtionType;
	private LocalDate creationDate;
	private LocalDate expDate;
	private String type;
	private String voucherCode;	
	private String purposeDesc;
	private String mccDesc;
	private String accountNumber;
	private String bankcode;
	private byte[] bankIcon;
	private String redeemAmount;
	private byte[] mccMainIcon;
}
