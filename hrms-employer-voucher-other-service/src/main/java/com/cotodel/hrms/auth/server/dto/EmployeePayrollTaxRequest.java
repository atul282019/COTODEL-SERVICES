package com.cotodel.hrms.auth.server.dto;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeePayrollTaxRequest  {
	
	

    private Long employerId;
    private Long employeeId;	
    private String taxCode;	
    private String taxName;	
    private String taxDescription;	
	private String authoriser;	
    private String slNoType;	
    private String deductionTime;	
    private String taxRate;	
    private String taxPanAcc;	
    private int minimumAmount;	
    private String taxRef;	
    private String adjVoucherType;	
    private String supervisior;	
    private String debitAccount;	
    private String depositFrequency;	
    private String creditAccount;	
    private String remarks;
    private String response;
	}
