package com.cotodel.hrms.auth.server.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReputeEmployee {
	private String employeeId;
    private String employeeName;
    private Date dob;
    private String gender;
    private String department;
    private String employmentType;
    private String designation;
    private Date doj;
    private Date doe;
    private String officialEmailId;
    private String personalEmailId;
    private String mobileNumber;
    private String pincode;
    private String employmentStatus;
    private String grade;
    private String managerEmployeeId;
}
