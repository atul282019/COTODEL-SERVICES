package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.model.vehicle.VehicleBulkUploadEntity;
import com.cotodel.hrms.auth.server.model.vehicle.VehicleBulkUploadSuccessEntity;
import com.cotodel.hrms.auth.server.model.vehicle.VehicleManagementEntity;
import com.cotodel.hrms.auth.server.model.vehicle.VehiclerBulkUploadFailEntity;

public interface VehicleBulkUploadDao {
	public VehicleBulkUploadEntity saveDetails(VehicleBulkUploadEntity vehicleBulkUploadEntity);
	public VehicleBulkUploadSuccessEntity saveSuccessDetails(VehicleBulkUploadSuccessEntity vehicleBulkUploadSuccessEntity);
	public VehiclerBulkUploadFailEntity saveFailDetails(VehiclerBulkUploadFailEntity vehiclerBulkUploadFailEntity);
	public List<VehicleBulkUploadSuccessEntity> findSuccessList(Long orgId, String fileName);
	public List<VehiclerBulkUploadFailEntity> findFailList(Long orgId, String fileName);
	public VehicleBulkUploadSuccessEntity findSuccessDetails(Long id);
	public int updateSuccessFlag(Long id);
	
}
