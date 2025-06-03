package com.cotodel.hrms.auth.server.dto.vehicle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleBulkUploadResponse {
	private boolean status;
	 private String message;
	 VehicleBulkUploadSFListResponse data;
	 private String txnId;
	 private String timestamp;
	
}
