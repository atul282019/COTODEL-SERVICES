package com.cotodel.hrms.auth.server.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.ManagerMasterDao;
import com.cotodel.hrms.auth.server.dto.ManagerMasterRequest;
import com.cotodel.hrms.auth.server.model.EmployeeOnboardingEntity;
import com.cotodel.hrms.auth.server.model.ManagerLblMasterEntity;
import com.cotodel.hrms.auth.server.service.ManagerMasterService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;

@Repository
public class ManagerMasterServiceImpl implements ManagerMasterService{
	private static final Logger logger = LoggerFactory.getLogger(ManagerMasterServiceImpl.class);

	@Autowired
	ManagerMasterDao managerDao;
	
	@Override
	public ManagerMasterRequest saveManagerMaster(ManagerMasterRequest request) {
		String response = "";
		ManagerLblMasterEntity managerLblMasterEntity = null;
		try {
			response = MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);
			managerLblMasterEntity = new ManagerLblMasterEntity();

			CopyUtility.copyProperties(request, managerLblMasterEntity);
			managerLblMasterEntity.setCreationDate(LocalDateTime.now());
			managerLblMasterEntity.setStatus(1);
			managerLblMasterEntity = managerDao.saveDetails(managerLblMasterEntity);
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
	public List<ManagerLblMasterEntity> getManagerMaster(Long orgId) {
		
		List<ManagerLblMasterEntity> managerLblMasterEntities=new ArrayList<>();
		try {
			managerLblMasterEntities=managerDao.getManagerDetails(orgId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return managerLblMasterEntities;
	}
	




	
}
