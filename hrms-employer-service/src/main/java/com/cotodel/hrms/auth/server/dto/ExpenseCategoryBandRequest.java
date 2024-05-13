package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseCategoryBandRequest {
	
	private String expenseCategory;	
	private String expenseCode;	
	private String bandFlag;	
	private String bandId;	
	private String bandPerDay;	
	private String bandPerMonth;	
	private long status;
	private String response;
	
}
