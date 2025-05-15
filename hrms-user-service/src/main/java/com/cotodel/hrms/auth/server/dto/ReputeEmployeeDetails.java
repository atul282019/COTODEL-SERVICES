package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReputeEmployeeDetails {
	private String username;
	private String email;
	private String mobile;
	private String companyId;
	private String orgname;
	private String noofEmp;
	private String privacyCheck;
	private String whatsupCheck;
	private boolean erupistatus;
	private String captcha;
	private int employerId;
	private String role;
	private String hrmsId;
	private String hrmsName;
	private String employeeName;
	private String response;
	private String employeeId;
	private String managerEmployeeId;
	
}
