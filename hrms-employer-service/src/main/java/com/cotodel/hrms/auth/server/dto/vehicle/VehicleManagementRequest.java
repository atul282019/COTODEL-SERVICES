package com.cotodel.hrms.auth.server.dto.vehicle;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleManagementRequest {
		
	private Long orgId;
	private String response;
	
}
