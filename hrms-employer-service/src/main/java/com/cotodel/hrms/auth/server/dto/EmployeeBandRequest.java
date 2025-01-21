package com.cotodel.hrms.auth.server.dto;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeBandRequest {
	
	private String bandEnabled;	
	private String employeeBandNo;	
	private String employeeBandNoAlpha;	
	private String employeeBandOrder;
	private long employerId;
	private long status;	
	private String response;
}
