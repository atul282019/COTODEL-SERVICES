package com.cotodel.hrms.auth.server.dto;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseBillReader {
	private String venderName;
	private String orderDate;
	private String name;
	private String response;
	private String order;
	private Double totalAmount;
	
}
