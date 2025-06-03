package com.cotodel.hrms.auth.server.dto.vehicle;

import java.util.List;

import com.cotodel.hrms.auth.server.model.vehicle.VehicleBulkUploadSuccessEntity;
import com.cotodel.hrms.auth.server.model.vehicle.VehiclerBulkUploadFailEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleBulkUploadSFListResponse {
	
	private String totalCount;
	private String successCount;
	private String failCount;
	List<VehicleBulkUploadSuccessEntity> success;
	List<VehiclerBulkUploadFailEntity> fail;
	private String response;
	
}
