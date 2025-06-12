package com.cotodel.hrms.auth.server.dto.voucher;

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
	private String merchantTranId;
	private String voucherIssueDate;
	private String voucherRedeemedDate;	
	private String voucherExpiryDate;
	private String voucherStatus;
	private String success;
	private String response;
	private String responseCode;
	private String apiResponse;
	private String umn;
	private String uuid;
}


