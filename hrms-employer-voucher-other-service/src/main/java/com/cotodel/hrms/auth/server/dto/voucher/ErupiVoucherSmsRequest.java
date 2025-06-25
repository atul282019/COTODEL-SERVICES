package com.cotodel.hrms.auth.server.dto.voucher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherSmsRequest {	
	
	private String merchantTranId;
	private String mobile;	
	private String beneficiaryId;
	private String umn;
	private String uuid;
	private String smsCategory;
	private String merchantId;
}

