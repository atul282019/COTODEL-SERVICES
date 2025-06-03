package com.cotodel.hrms.auth.server.dto.vehicle;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleBulkCreateRequest {
	
	private String[] arrayofid;
	private String createdby;	
	private Long orgId;				
	private String response;
	private String responseApi;
	private String creationmode;	
	private Long bulktblId;
	private String consent;
	private String otpValidationStatus;
	private String clientKey;
	private String hash;
}
