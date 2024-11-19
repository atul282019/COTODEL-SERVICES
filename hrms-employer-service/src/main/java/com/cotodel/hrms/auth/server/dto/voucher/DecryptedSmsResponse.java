package com.cotodel.hrms.auth.server.dto.voucher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DecryptedSmsResponse {
	
	private String merchantId;
	private String merchantTranId;
	private String responseCode;
	private String description;	
	private String response_Status;
	private String uuid;	
	private String actCode;	
	private String sms_Category;
	private String umn;
	
	}

