package com.cotodel.hrms.auth.server.dto;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherCreateRequest {
	
	private Long id;
	private Long employerId;
	private Long employeeId;
	private String name;
	private String mobile;
	private String voucherType;
	private String voucherSubType;
	private Float amount;
	private String remarks;
	private String response;
}
