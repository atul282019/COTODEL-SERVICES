package com.cotodel.hrms.auth.server.dto.vehicle;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleTripListResponse {
	
	 private boolean status;
	 private String message;
	 private List<VehicleTripDto> data;
	 private String txnId;
	 private String timestamp;
	  
}
