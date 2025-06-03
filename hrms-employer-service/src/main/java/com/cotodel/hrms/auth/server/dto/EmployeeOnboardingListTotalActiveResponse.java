package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import com.cotodel.hrms.auth.server.model.EmployeeOnboardingEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeOnboardingListTotalActiveResponse {
	
	private boolean status;
	 private String message;
	 EmployeeOnboardingListActiveResponse data;
	 private String txnId;
	 private String timestamp;
	
}
