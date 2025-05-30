package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmployeeResponse {
	
	 private boolean status;
	 private String message;
	 private UpdateEmployeeStatusRequest data;
	 private String txnId;
	 private String timestamp;
	  
}
