package com.cotodel.hrms.auth.server.dto.vehicle;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreateDetailsRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleBulkCreateResponse {
	private boolean status;
	 private String message;
	 List<VehicleManagementBulkCreateRequest> data;
	 private String txnId;
	 private String timestamp;
	
}
