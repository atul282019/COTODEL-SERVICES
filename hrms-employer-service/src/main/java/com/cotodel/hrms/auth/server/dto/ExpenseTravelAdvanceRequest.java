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
public class ExpenseTravelAdvanceRequest {
	
	private Long id;
    private Long employerId;	
    private String allowEmployeesTravel;	
    private String allowEmployeesCash;	
    private String employeesAllow;	
    private String nameEmployeesCash;	
    private String daysDisbursalCash;	
	private LocalDate  created_date ;
	private String response;
}
