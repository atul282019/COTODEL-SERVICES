package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import com.cotodel.hrms.auth.server.model.EmployeeDetailsEntity;
import com.cotodel.hrms.auth.server.model.EmployeeFamilyDetailEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeFamilyGetDetailsResponse {
	
	 private boolean status;
	 private String message;
	 List<EmployeeFamilyDetailEntity> data;
	 private String txnId;
	 private String timestamp;
}
