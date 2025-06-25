package com.cotodel.hrms.auth.server.dto.vehicle;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeOnboardingDriverResponse {
	
	 private boolean status;
	 private String message;
	 private List<EmployeeOnboardingDto> data;
	 private String txnId;
	 private String timestamp;
	  
}
