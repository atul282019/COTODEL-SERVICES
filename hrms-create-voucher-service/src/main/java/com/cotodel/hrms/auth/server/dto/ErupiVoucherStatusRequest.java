package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherStatusRequest {	
	
	private String merchantTranId;
	private String mcc;	
	private String umn;
	private String merchantId;
	private String subMerchantId;
	private String transactionType;
}
