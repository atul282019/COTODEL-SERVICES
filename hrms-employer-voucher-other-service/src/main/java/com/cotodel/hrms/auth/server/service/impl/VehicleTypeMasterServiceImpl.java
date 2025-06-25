package com.cotodel.hrms.auth.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.VehicleDriverManagementDao;
import com.cotodel.hrms.auth.server.dao.VehicleManagementDao;
import com.cotodel.hrms.auth.server.dao.VehicleTypeMasterDao;
import com.cotodel.hrms.auth.server.model.vehicle.VehicleTypeMasterEntity;
import com.cotodel.hrms.auth.server.service.VehicleTypeMasterService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Repository
public class VehicleTypeMasterServiceImpl implements VehicleTypeMasterService{

	
	
	@Autowired
	VehicleTypeMasterDao  vehicleTypeMasterDao;

	@Override
	public List<VehicleTypeMasterEntity> getVehicleTypeMaster() {
		// TODO Auto-generated method stub
		return vehicleTypeMasterDao.getVehicleTypeMasterDetails();
	}



	
}
