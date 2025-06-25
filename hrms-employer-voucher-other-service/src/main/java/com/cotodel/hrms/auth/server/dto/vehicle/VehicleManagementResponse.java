package com.cotodel.hrms.auth.server.dto.vehicle;

import java.util.List;

import com.cotodel.hrms.auth.server.model.vehicle.VehicleManagementEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleManagementResponse {
	
	 private boolean status;
	 private String message;
	 private List<VehicleManagementGetDto> data;
	 private String txnId;
	 private String timestamp;
	  
}
