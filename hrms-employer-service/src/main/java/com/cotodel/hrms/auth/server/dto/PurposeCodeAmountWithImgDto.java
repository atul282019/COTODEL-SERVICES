package com.cotodel.hrms.auth.server.dto;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurposeCodeAmountWithImgDto {
	
	private String purposeCode;
	private double totalAmount;
	private String voucherName;
	private byte[] mccMainIcon;
}
