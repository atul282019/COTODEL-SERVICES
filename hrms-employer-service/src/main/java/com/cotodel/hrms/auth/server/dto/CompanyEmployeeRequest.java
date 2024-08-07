package com.cotodel.hrms.auth.server.dto;

import com.cotodel.hrms.auth.server.model.CompanyEmployeeEntity;
import com.cotodel.hrms.auth.server.model.LeaveRequest;
import com.cotodel.hrms.auth.server.model.PerformanceReview;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyEmployeeRequest {
	
   
    private CompanyEmployeeEntity employee;    
	LeaveRequest leaveRequest;		
	//Timesheet timesheet;	
	PerformanceReview performanceReview;
	//private EmploymentDetails employmentDetails;
//	private Supervisor supervisor;
//	private Skills skills;
//	private Certifications certifications;
//	private EmergencyContacts emergencyContacts;
//	private LeaveRequest leaveRequest;
//	private Timesheet timesheet;
//	private PerformanceReview performanceReview;
	private String response;
}
