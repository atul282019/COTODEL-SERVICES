package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.model.vehicle.VehicleManagementEntity;

public interface VehicleManagementDao {
	public VehicleManagementEntity saveVehicleManagementDetails(VehicleManagementEntity employeeEntity);
	public List<VehicleManagementEntity> getVehicleManagement(Long orgId);
	public VehicleManagementEntity getVehicleManagementBySequenceId(String vehicleSequenceId);
	public VehicleManagementEntity getVehicleManagementById(Long id);
}
