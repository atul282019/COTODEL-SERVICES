package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.vehicle.VehicleTripDto;
import com.cotodel.hrms.auth.server.model.vehicle.VehicleDriverManagementEntity;

public interface VehicleDriverManagementDao {
	public VehicleDriverManagementEntity saveVehicleTripManagementDetails(VehicleDriverManagementEntity employeeEntity);
	public List<VehicleTripDto> getVehicleTripList(Long id);
	public Object[] getVehicleTrip(Long id);
	
	public List<VehicleDriverManagementEntity> getTripsEndingTodayOrLater(Long driverId);
	
}
