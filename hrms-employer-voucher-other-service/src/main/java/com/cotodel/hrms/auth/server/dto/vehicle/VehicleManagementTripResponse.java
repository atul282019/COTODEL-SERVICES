package com.cotodel.hrms.auth.server.dto.vehicle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleManagementTripResponse {
	
	 private boolean status;
	 private String message;
	 private VehicleManagementTripRequest data;
	 private String txnId;
	 private String timestamp;
	  
}
