package com.cotodel.hrms.auth.server.dto;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateReputeStatusRequest {
	private String companyId;	
	private String companyName;	
	private String hrmsId;	
	private String employeeId;	
	private String employeeName;	
	private String dob;	
	private String gender;	
	private String department;		
	private String employmentType ;		
	private String designation;
	private String doj;	
    private String officialEmailId;
    private String personalEmailId;
    private String mobileNumber;
    private String pincode;
    private String employmentStatus;
    private String grade;
    private String doe;
    private String managerEmployeeId; 
    private String response;
    private String company_cotodel;
    private String reputeEmployeeId;
}
