package com.cotodel.hrms.auth.server.dto.voucher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherStatusApiRequest {	
	private String merchantTranId;
	private String mcc;	
	private String umn;
	private String merchantId;
	private String subMerchantId;
	private String transactionType;
	
}
