package com.cotodel.hrms.auth.server.dto;

import com.cotodel.hrms.auth.server.model.Certifications;
import com.cotodel.hrms.auth.server.model.CompanyEmployeeEntity;
import com.cotodel.hrms.auth.server.model.EmergencyContacts;
import com.cotodel.hrms.auth.server.model.EmploymentDetails;
import com.cotodel.hrms.auth.server.model.LeaveRequest;
import com.cotodel.hrms.auth.server.model.PerformanceReview;
import com.cotodel.hrms.auth.server.model.Skills;
import com.cotodel.hrms.auth.server.model.Supervisor;
import com.cotodel.hrms.auth.server.model.Timesheet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDetailsRequest {
	
	private CompanyEmployeeEntity employee;
	private EmploymentDetails employmentDetails;
	private Supervisor supervisor;
	private Skills skills;
	private Certifications certifications;
	private EmergencyContacts emergencyContacts;
	private LeaveRequest leaveRequest;
	private Timesheet timesheet;
	private PerformanceReview performanceReview;
		
}
