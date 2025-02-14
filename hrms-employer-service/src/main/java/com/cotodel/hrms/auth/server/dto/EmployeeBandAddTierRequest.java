package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import com.cotodel.hrms.auth.server.model.EmployeeBandAddTierEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeBandAddTierRequest {
	
	private String bandEnabled;	
	private String employeeBandNo;	
	private String employeeBandNoAlpha;	
	private String employeeBandOrder;
	private long status;		
	private String introAddTierFlag;
	private long employerId;
	private String[] listArray;
	private List<EmployeeBandAddTierEntity> list;
	private String response;
}
