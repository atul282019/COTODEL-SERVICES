package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.vehicle.VehicleManagementSaveRequest;
import com.cotodel.hrms.auth.server.model.vehicle.VehicleManagementEntity;

public interface VehicleManagementService {
	
	public VehicleManagementSaveRequest  saveVehicleManagement(VehicleManagementSaveRequest request);	
	public List<VehicleManagementEntity>  getVehicleManagement(Long employerid);	

}
