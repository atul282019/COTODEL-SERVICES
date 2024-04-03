package com.cotodel.hrms.auth.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.cotodel.hrms.auth.server.dao.EmployeeOnboardingFailDao;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingRequest;
import com.cotodel.hrms.auth.server.model.EmployeeOnboardingFailEntity;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.EmployeeOnboardingFailService;
import com.cotodel.hrms.auth.server.util.MessageConstant;
@Repository
public class EmployeeOnboardingServiceFailImpl implements EmployeeOnboardingFailService{

	@Autowired
	EmployeeOnboardingFailDao  employeeOnboardingFailDao;
	
	@Autowired
	ApplicationConstantConfig  applicationConstantConfig;
		
	



	@Override
	public EmployeeOnboardingRequest saveBulkFailEmployeeDetails(EmployeeOnboardingRequest request) {
		
		String response="";
		try {
			response = MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);
			EmployeeOnboardingFailEntity employeeOnboarding = new EmployeeOnboardingFailEntity();
			employeeOnboarding.setEmployerId(request.getEmployerId());
			employeeOnboarding.setFailValue(request.toString());
			employeeOnboarding = employeeOnboardingFailDao.saveDetails(employeeOnboarding);
			response = MessageConstant.RESPONSE_SUCCESS;
			request.setResponse(response);
			
		} catch (Exception e) {
			response = MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);
		}
		return request;

	}	
}
