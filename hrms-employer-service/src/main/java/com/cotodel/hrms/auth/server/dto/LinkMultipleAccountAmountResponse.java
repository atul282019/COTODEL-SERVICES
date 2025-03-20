package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkMultipleAccountAmountResponse {
	
	 private boolean status;
	 private String message;
	 private String balance;
	 private String txnId;
	 private String timestamp;
}
