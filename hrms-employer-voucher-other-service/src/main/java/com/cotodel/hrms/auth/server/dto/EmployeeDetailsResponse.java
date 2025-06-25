package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDetailsResponse {
	
	 private boolean status;
	 private String message;
	 EmployeeDetailsRequest data;
	 private String txnId;
	 private String timestamp;
}
