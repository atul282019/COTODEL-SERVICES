package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallApiStatusResponse {
	
	 private boolean status;
	 private String message;
	 DecryptedStatusResponse data;
	 private String timestamp;
	
	
}
