package com.cotodel.hrms.auth.server.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cotodel.hrms.auth.server.dto.vehicle.VehicleBulkCreateRequest;
import com.cotodel.hrms.auth.server.dto.vehicle.VehicleBulkUploadSFListResponse;
import com.cotodel.hrms.auth.server.dto.vehicle.VehicleManagementBulkCreateRequest;
import com.cotodel.hrms.auth.server.dto.vehicle.VehicleManagementBulkUploadRequest;

@Service
public interface VehicleManagementBulkService {

	public VehicleBulkUploadSFListResponse saveVehicleBulkFileNew(VehicleManagementBulkUploadRequest vehicleManagementBulkUploadRequest);
	public List<VehicleManagementBulkCreateRequest> createErupiVoucherBulkFile(VehicleBulkCreateRequest vehicleBulkCreateRequest);
	
}
