package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.VehicleTypeMasterDao;
import com.cotodel.hrms.auth.server.model.vehicle.VehicleTypeMasterEntity;
import com.cotodel.hrms.auth.server.repository.vehicle.VehicleTypeMasterRepository;
@Repository
public class VehicleTypeMasterDaoImpl implements VehicleTypeMasterDao{

	@Autowired
	VehicleTypeMasterRepository vehicleTypeMasterRepository;

	@Override
	public List<VehicleTypeMasterEntity> getVehicleTypeMasterDetails() {
		// TODO Auto-generated method stub
		return vehicleTypeMasterRepository.findAll();
	}
	
}
