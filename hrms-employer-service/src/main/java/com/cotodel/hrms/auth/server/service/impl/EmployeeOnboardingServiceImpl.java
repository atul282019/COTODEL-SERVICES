package com.cotodel.hrms.auth.server.service.impl;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import com.cotodel.hrms.auth.server.dao.EmployeeOnboardingDao;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingRequest;
import com.cotodel.hrms.auth.server.dto.UserRequest;
import com.cotodel.hrms.auth.server.model.EmployeeOnboardingEntity;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.EmployeeOnboardingService;
import com.cotodel.hrms.auth.server.util.CommonUtility;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;
@Repository
public class EmployeeOnboardingServiceImpl implements EmployeeOnboardingService{

	@Autowired
	EmployeeOnboardingDao  employeeOnboardingDao;
	
	@Autowired
	ApplicationConstantConfig  applicationConstantConfig;
	
	@Override
	public EmployeeOnboardingRequest saveEmployeeDetails(EmployeeOnboardingRequest request) {
		
		String response="";
		String response1="";
		String tokenvalue="";
		TokenGeneration token=new TokenGeneration();
		UserRequest userRequest=new UserRequest();
		try {
			tokenvalue = token.getToken(applicationConstantConfig.getTokenUrl);
			userRequest.setUsername(request.getName());
			userRequest.setMobile(request.getMobile());
			userRequest.setEmail(request.getEmail());
			userRequest.setEmployerid(request.getEmployerId()==null?0:request.getEmployerId().intValue());
			response1 = CommonUtility.userRequest(tokenvalue, MessageConstant.gson.toJson(userRequest),
					applicationConstantConfig.userServiceAddUrl);
			if (!ObjectUtils.isEmpty(response1)) {
				JSONObject demoRes = new JSONObject(response1);
				boolean status = demoRes.getBoolean("status");
				if (status) {

					response = MessageConstant.RESPONSE_FAILED;
					request.setResponse(response);
					EmployeeOnboardingEntity employeeOnboarding = new EmployeeOnboardingEntity();
					CopyUtility.copyProperties(request, employeeOnboarding);
					employeeOnboarding = employeeOnboardingDao.saveDetails(employeeOnboarding);
					response = MessageConstant.RESPONSE_SUCCESS;
					request.setResponse(response);
				} else if (!status) {
					response = demoRes.getString("message");
					request.setResponse(response);
				}

			}
		} catch (Exception e) {
			response = MessageConstant.RESPONSE_FAILED;
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