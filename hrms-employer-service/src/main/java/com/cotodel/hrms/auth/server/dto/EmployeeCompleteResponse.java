package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeCompleteResponse {
	
	 private boolean status;
	 private String message;
	 private int  profile;
	 private String txnId;
	 private String timestamp;
}
