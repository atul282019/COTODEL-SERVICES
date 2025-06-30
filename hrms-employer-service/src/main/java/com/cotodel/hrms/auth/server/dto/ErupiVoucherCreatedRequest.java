package com.cotodel.hrms.auth.server.dto;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherCreatedRequest {
	private Long orgId;
	private String purposeCode;
	private String accNumber;
	private String userName;
	private String timePeriod;
	private Long employeeId;
	private Long id;
	private String mobile;
}
