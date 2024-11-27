package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DecryptedStatusResponse {
	
	private String voucherBalance;	
	private String voucherAmt;	
	private String merchantId;		
	private String voucherRedeemedAmount;
	private String UUID;
	private String merchantTranId;	
	private String voucherIssueDate;	
	private String voucherRedeemedDate;	
	private String voucherExpiryDate;
	private String voucherStatus;
	private String success;
	private String UMN;
	private String response;
	private String responseCode;
	
	
}


