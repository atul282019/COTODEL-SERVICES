package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DecryptedResponse {
	
	private String merchantId;
	private String status;
	private String success;
	private String message;
	private String merchantTranId;
	private String response;
	private String amount;
	private String umn;
	private String expiryDate;
	private String uuid;
	private String responseCode;
	
}
