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
	private Long id;
	private String vehicleSequenceId;
	private String response;
	private String limit;
	
}
