package com.cotodel.hrms.auth.server.dto.vehicle;

import com.cotodel.hrms.auth.server.model.vehicle.VehicleManagementEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleManagementByIdResponse {
	
	 private boolean status;
	 private String message;
	 private VehicleManagementEntity data;
	 private String txnId;
	 private String timestamp;
	  
}
