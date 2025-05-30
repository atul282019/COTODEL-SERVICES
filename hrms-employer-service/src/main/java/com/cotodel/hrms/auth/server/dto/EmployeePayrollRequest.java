package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import com.cotodel.hrms.auth.server.model.EmployeePayrollNewEntity;
import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeePayrollRequest {
	
    private Long employerId;
    private Long employeeId;
	private String salaryComponentBasic;
    private String perCtcBasic;
    private String perBasic ;
    private String taxableBasic;
    private String salaryComponentHra;
    private String perCtcHra;
    private String perHra ;
    private String taxableHra;  
    private String salaryComponentSpecial;
    private String perCtcSpecial;
    private String perSpecial ;
    private String taxableSpecial;
    private String salaryComponentLta;
    private String perCtcLta;
    private String perLta ;
    private String taxableLta;
    private Integer status; 
    private String response;
    List<EmployeePayrollNewEntity> list;
	}
