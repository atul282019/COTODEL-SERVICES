package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.vehicle.VehicleManagementGetDto;
import com.cotodel.hrms.auth.server.dto.vehicle.VehicleManagementRequest;
import com.cotodel.hrms.auth.server.dto.vehicle.VehicleManagementSaveRequest;
import com.cotodel.hrms.auth.server.dto.vehicle.VehicleManagementTripRequest;
import com.cotodel.hrms.auth.server.dto.vehicle.VehicleTripDto;
import com.cotodel.hrms.auth.server.model.vehicle.VehicleManagementEntity;

public interface VehicleManagementService {
	
	public VehicleManagementSaveRequest  saveVehicleManagement(VehicleManagementSaveRequest request);	
	public List<VehicleManagementGetDto>  getVehicleManagement(VehicleManagementRequest vehicleManagementRequest);	
	//public VehicleManagementTripRequest  saveTripManagement(VehicleManagementTripRequest request);
	public List<VehicleTripDto>  getVehicleTripManagement(String  vehicleSequenceId);
	public VehicleManagementEntity  getVehicleManagementById(String  vehicleSequenceId);
	public VehicleManagementTripRequest  tripVehicleDetails(VehicleManagementTripRequest request);
}
