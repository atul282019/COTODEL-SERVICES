package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DecryptedSmsResponse {
	
	private String SMS_Category;
	private String ActCode;
	private String UMN;	
	private String merchantId;
	private String Response_Status;
	private String Description;
	private String merchantTranId;
	private String UUID;
	private String responseCode;
	
}


