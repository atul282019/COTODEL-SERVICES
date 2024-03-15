package com.cotodel.hrms.auth.server.dto;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeSalaryRequest {
	
    private Long employerId;
    private Long employeeId;	
    private Long companyId;	
    private String companyName;	
    private String shortName;	
    private String nature;	
    private String type;	
    private String uom;
    private String unit;
    private String maxLimit;
    private String dependentUom;
    private String dependentName;
    private String paidTo;
    private String payableFrequency;	
    private String modifyable;	
    private String active;	
    private String mandatory;	
    private String impactByAttendance;	
    private String taxable;	
    private String printable;	
    private String slabsAllowed;
    private String account;
    private String subledger;
    private String remarks;
	private String briefDescription;	
    private String response;
    
}
