package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.VehicleDriverManagementDao;
import com.cotodel.hrms.auth.server.dto.vehicle.VehicleTripDto;
import com.cotodel.hrms.auth.server.model.vehicle.VehicleDriverManagementEntity;
import com.cotodel.hrms.auth.server.repository.vehicle.VehicleTripManagementRepository;
@Repository
public class VehicleDriverManagementDaoImpl implements VehicleDriverManagementDao{

	@Autowired
	VehicleTripManagementRepository vehicleTripManagementRepository;

	@Override
	public VehicleDriverManagementEntity saveVehicleTripManagementDetails(VehicleDriverManagementEntity vehicleManagementEntity) {
		// TODO Auto-generated method stub
		return vehicleTripManagementRepository.saveAndFlush(vehicleManagementEntity);
	}

	@Override
	public List<VehicleTripDto> getVehicleTripList(Long id) {
		// TODO Auto-generated method stub
		return vehicleTripManagementRepository.findByVehicleTripList(id);
	}

	@Override
	public Object[] getVehicleTrip(Long id) {
		// TODO Auto-generated method stub
		return vehicleTripManagementRepository.findTopByVehicleIdOrderByTripStartDateDesc(id);
	}

	@Override
	public List<VehicleDriverManagementEntity> getTripsEndingTodayOrLater(Long driverId) {
		// TODO Auto-generated method stub
		return vehicleTripManagementRepository.findTripsEndingTodayOrLater(driverId);
	}
	
}
