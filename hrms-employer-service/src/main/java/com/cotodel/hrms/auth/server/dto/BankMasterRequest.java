package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankMasterRequest {
	
	 private String bankCode;
	 private String bankName;
	 private int status;
	 private byte[] bankLogo;
	 private String response;
}
