package com.cotodel.hrms.auth.server.dto;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeOnboardingManagerRequest {
	private Long id;
	private Long employerId;
	private Long employeeId;
	private Long managerId;
	private String clientKey;
	private String hash;   
	private String loginuser;
	private String status;
	private String response;
	private String rejecctRemark;
}
