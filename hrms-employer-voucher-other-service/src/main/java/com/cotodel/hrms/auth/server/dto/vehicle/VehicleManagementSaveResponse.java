package com.cotodel.hrms.auth.server.dto.vehicle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleManagementSaveResponse {
	
	 private boolean status;
	 private String message;
	 private VehicleManagementSaveRequest data;
	 private String txnId;
	 private String timestamp;
	  
}
