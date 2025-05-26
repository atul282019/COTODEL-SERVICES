package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.VehicleManagementDao;
import com.cotodel.hrms.auth.server.model.vehicle.VehicleDriverManagementEntity;
import com.cotodel.hrms.auth.server.model.vehicle.VehicleManagementEntity;
import com.cotodel.hrms.auth.server.repository.vehicle.VehicleManagementRepository;
@Repository
public class VehicleManagementDaoImpl implements VehicleManagementDao{

	@Autowired
	VehicleManagementRepository vehicleManagementRepository;

	@Override
	public VehicleManagementEntity saveVehicleManagementDetails(VehicleManagementEntity vehicleManagementEntity) {
		// TODO Auto-generated method stub
		return vehicleManagementRepository.saveAndFlush(vehicleManagementEntity);
	}

	@Override
	public List<VehicleManagementEntity> getVehicleManagement(Long orgId) {
		return vehicleManagementRepository.findByVehicleList(orgId);
	}

	

	@Override
	public VehicleManagementEntity getVehicleManagementBySequenceId(String vehicleSequenceId) {
		// TODO Auto-generated method stub
		return vehicleManagementRepository.findByVehicleBySequenceId(vehicleSequenceId);
	}

	@Override
	public VehicleManagementEntity getVehicleManagementById(Long id) {
		// TODO Auto-generated method stub
		return vehicleManagementRepository.findByVehicleById(id);
	}

	


	
}
