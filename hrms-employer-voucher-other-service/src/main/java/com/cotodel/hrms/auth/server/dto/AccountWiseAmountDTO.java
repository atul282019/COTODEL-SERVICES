package com.cotodel.hrms.auth.server.dto;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountWiseAmountDTO {
	
	private String accountNumber;
	private Long orgId;
	private double totalAmount;
	private double redeemAmount;
	private String bankName;
	private byte[] bankLogo;
}
