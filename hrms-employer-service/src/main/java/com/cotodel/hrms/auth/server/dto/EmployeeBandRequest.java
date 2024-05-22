package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeBandRequest {
	
	private String bandEnabled;	
	private String employeeBandNo;	
	private String employeeBandNoAlpha;	
	private String employeeBandOrder;	
	private long status;	
	private String response;
}
