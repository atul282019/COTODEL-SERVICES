package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallApiSmsResponse {
	
	 private boolean status;
	 private String message;
	 DecryptedSmsResponse data;
	 private String timestamp;
	
	
}
