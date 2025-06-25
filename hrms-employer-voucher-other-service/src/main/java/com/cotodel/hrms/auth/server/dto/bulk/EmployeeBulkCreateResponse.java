package com.cotodel.hrms.auth.server.dto.bulk;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeBulkCreateResponse {
	private boolean status;
	 private String message;
	 List<EmployeeOnboardingRequest> data;
	 private String txnId;
	 private String timestamp;
	
}
