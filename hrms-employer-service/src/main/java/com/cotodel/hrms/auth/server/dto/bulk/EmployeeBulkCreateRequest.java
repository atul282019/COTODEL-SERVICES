package com.cotodel.hrms.auth.server.dto.bulk;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeBulkCreateRequest {
	
	private String[] arrayofid;
	private Long orgId;	
	private String response;
	private String responseApi;
	private Long bulktblId;
	private String consent;
	private String otpValidationStatus;
	private String clientKey;
	private String hash;
}
