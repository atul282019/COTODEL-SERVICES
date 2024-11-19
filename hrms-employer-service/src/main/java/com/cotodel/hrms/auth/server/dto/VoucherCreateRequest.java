package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherCreateRequest {
	private String merchantTranId;
	private String beneficiaryID;
	private String mobileNumber;
	private String beneficiaryName;
	private String amount;
	private String expiry;
	private String purposeCode;
	private String mcc;
	private String voucherRedemptionType;
	private String payerVA;
	private String type;
	private String merchantId;
	private String subMerchantId;
}
