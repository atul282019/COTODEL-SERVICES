package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeConfirmOnboardingResponse {
	
	 private boolean status;
	 private String message;
	 EmployeeOnboardingListRequest data;
	  private String txnId;
	  private String timestamp;
}
