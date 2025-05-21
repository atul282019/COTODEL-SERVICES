package com.cotodel.hrms.auth.server.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.cotodel.hrms.auth.server.dao.VehicleManagementDao;
import com.cotodel.hrms.auth.server.dto.vehicle.VehicleManagementSaveRequest;
import com.cotodel.hrms.auth.server.model.vehicle.VehicleManagementEntity;
import com.cotodel.hrms.auth.server.service.VehicleManagementService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Repository
public class VehicleManagementServiceImpl implements VehicleManagementService{

	@Autowired
	VehicleManagementDao  vehicleManagementDao;

	@Override
	public VehicleManagementSaveRequest saveVehicleManagement(VehicleManagementSaveRequest request) {
		String response = "";
		VehicleManagementEntity vehicleManagementEntity = null;
		try {
			if(request.getOrgId()==null) {
				request.setResponse(MessageConstant.ORGNULL);
			}
			response = MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);
			vehicleManagementEntity = new VehicleManagementEntity();
			CopyUtility.copyProperties(request, vehicleManagementEntity);
			vehicleManagementEntity.setStatus(1);
			vehicleManagementEntity.setCreationDate(LocalDateTime.now());
			vehicleManagementEntity = vehicleManagementDao.saveVehicleManagementDetails(vehicleManagementEntity);
			response = MessageConstant.RESPONSE_SUCCESS;
			request.setResponse(response);

		} catch (Exception e) {
			response = MessageConstant.RESPONSE_FAILED;
			// e.printStackTrace();
			request.setResponse(response);
		}
		return request;
	}

	@Override
	public List<VehicleManagementEntity> getVehicleManagement(Long employerid) {
		List<VehicleManagementEntity> vehicleList=vehicleManagementDao.getVehicleManagement(employerid);
		return vehicleList;
	}




	
}
