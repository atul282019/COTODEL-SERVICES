package com.cotodel.hrms.auth.server.dto;

import com.cotodel.hrms.auth.server.model.EmployeeProfileEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployerDetailsResponse {
	
	 private boolean status;
	 private String message;
	 EmployeeProfileEntity data;
	 private String txnId;
	 private String timestamp;
}
