package com.cotodel.hrms.auth.server.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.DirectorOnboardingDao;
import com.cotodel.hrms.auth.server.dto.DirectorOnboardingRequest;
import com.cotodel.hrms.auth.server.model.DirectorOnboardingEntity;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.DirectorOnboardingService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;
@Repository
public class DirectorOnboardingServiceImpl implements DirectorOnboardingService{

	@Autowired
	DirectorOnboardingDao  directorOnboardingDao;
	
	@Autowired
	ApplicationConstantConfig  applicationConstantConfig;

	

	@Override
	public DirectorOnboardingRequest saveDirectorDetails(DirectorOnboardingRequest request) {
		String response="";
		DirectorOnboardingEntity directorOnboardingEntity=null;
		try {
			response=MessageConstant.RESPONSE_FAILED;			
			request.setResponse(response);
			directorOnboardingEntity=new DirectorOnboardingEntity();
			CopyUtility.copyProperties(request,directorOnboardingEntity);
			directorOnboardingEntity.setStatus(1);
			directorOnboardingEntity.setCreationDate(LocalDateTime.now());
			directorOnboardingDao.saveDetails(directorOnboardingEntity);
			request.setResponse(MessageConstant.RESPONSE_SUCCESS);
		}catch (DataIntegrityViolationException ex) {
	           
		}catch (Exception e) {
			e.printStackTrace();
		}
		return request;
	}

	@Override
	public List<DirectorOnboardingEntity> getDirectorDetailsList(Long employerid) {
		// TODO Auto-generated method stub
		return directorOnboardingDao.getEmployeeOnboardingList(employerid);
	}

}
