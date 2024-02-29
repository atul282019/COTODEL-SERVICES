package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyEmployeeResponse {
	
	 private boolean status;
	 private String message;
	 CompanyEmployeeRequest data;
	 private String txnId;
	 private String timestamp;
}
