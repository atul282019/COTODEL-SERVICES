package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkMultipleAccountResponse {
	
	 private boolean status;
	 private String message;
	 LinkMultipleAccountRequest data;
	 private String txnId;
	 private String timestamp;
}
