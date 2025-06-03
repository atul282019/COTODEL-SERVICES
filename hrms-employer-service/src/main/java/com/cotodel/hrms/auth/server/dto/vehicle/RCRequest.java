package com.cotodel.hrms.auth.server.dto.vehicle;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RCRequest {
	private Long orgId;
	private String id_number;
	private String createdBy;
	
}
