package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import com.cotodel.hrms.auth.server.model.EmployeeDetailsEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeGetDetailsResponse {
	
	 private boolean status;
	 private String message;
	 List<EmployeeDetailsEntity> data;
	 private String txnId;
	 private String timestamp;
}
