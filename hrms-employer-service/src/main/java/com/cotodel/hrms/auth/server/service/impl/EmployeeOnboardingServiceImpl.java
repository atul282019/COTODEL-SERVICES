package com.cotodel.hrms.auth.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.EmployeeOnboardingDao;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingRequest;
import com.cotodel.hrms.auth.server.model.EmployeeOnboardingEntity;
import com.cotodel.hrms.auth.server.service.EmployeeOnboardingService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;
@Repository
public class EmployeeOnboardingServiceImpl implements EmployeeOnboardingService{

	@Autowired
	EmployeeOnboardingDao  employeeOnboardingDao;
	
	@Override
	public EmployeeOnboardingRequest saveEmployeeDetails(EmployeeOnboardingRequest request) {
		
		String response="";
		try {
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);		
			EmployeeOnboardingEntity employeeOnboarding=new EmployeeOnboardingEntity();
			CopyUtility.copyProperties(request,employeeOnboarding);
			employeeOnboarding=employeeOnboardingDao.saveDetails(employeeOnboarding);
			response=MessageConstant.RESPONSE_SUCCESS;
			request.setResponse(response);
		} catch (Exception e) {
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);
		}
		return request;

	}


	@Override
	public List<EmployeeOnboardingEntity> getEmployeeDetailsList(Long employerid) {
		List<EmployeeOnboardingEntity> employeeOnboading=null;
		try {
			employeeOnboading=employeeOnboardingDao.getEmployeeOnboardingList(employerid);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return employeeOnboading;
	}
}
