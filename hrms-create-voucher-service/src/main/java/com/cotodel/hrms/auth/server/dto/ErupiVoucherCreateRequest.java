package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherCreateRequest {	
	
	private String merchantId;
	private String merchantTranId;
	private String subMerchantId;
	private String beneficiaryID;
	private String mobileNumber;
	private String beneficiaryName;
	private String amount;
	private String expiry;
	private String purposeCode;
	private String mcc;	
	private String type;
	private String voucherRedemptionType;
	private String payerVA;
	
}
