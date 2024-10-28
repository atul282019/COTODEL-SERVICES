package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallApiVoucherCreateResponse {
	private String expiryDate;
	private String merchantId;
	private boolean success;
	private String response;
	private String Amount;
	private String UMN;
	private String message;
	private String UUID;
	private String merchantTranId;
	private String status;
	
	
}




