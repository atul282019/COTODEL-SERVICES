package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import com.cotodel.hrms.auth.server.model.CategoryEmployeeBandEntity;
import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseCategoryBandRequest {
	private Long id;
	private String expenseCategory;	
	private String expenseCode;	
	private String bandFlag;	
	private String bandId;	
	private String bandPerDay;	//
	private String bandPerMonth;//
	private String dayToExpiry;
	private Long employerId;
	private Long status;
	private String expenseLimit;
	private String response;
	private List<CategoryEmployeeBandEntity> list;	
	private String[] listArray;
	private String clientKey;
	private String hash;
	private String distingushEmployeeBand;
	private String flag;
	
	
	
	
	

}
