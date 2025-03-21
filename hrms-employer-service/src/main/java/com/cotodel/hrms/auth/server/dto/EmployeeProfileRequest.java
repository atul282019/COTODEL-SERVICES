package com.cotodel.hrms.auth.server.dto;

import java.time.LocalDate;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeProfileRequest {
	private String gstnNo;
	private String orgType;
	private String orgsubType;
	private String organizationType;	
    private String pan;
    private String brandName;
    private String panDetails;
    private String companyName;
    private String officeAddress;
    private String addressLine;
    private String pinCode;
    private String stateCode;
    private boolean payrollEnabledFlag;
    private LocalDate paidDate;
    private boolean runPayrollFlag;
    private boolean salaryAdvancesFlag;
    private Long signupId; 
    private String response;
    private Long employerId;
    private Long employeeId;
    private Long orgId;
    private Long id;
}
