package com.cotodel.hrms.auth.server.service.impl;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.EmployeeDetailsDao;
import com.cotodel.hrms.auth.server.dto.EmployeeDetailsRequest;
import com.cotodel.hrms.auth.server.model.EmployeeDetailsEntity;
import com.cotodel.hrms.auth.server.service.EmployeeDetailsService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;
@Repository
public class EmployeeDetailsServiceImpl implements EmployeeDetailsService{

	

	@Autowired
	EmployeeDetailsDao  employeeDetailsDao;
	
			
	@Override
	public EmployeeDetailsRequest saveEmpDetails(EmployeeDetailsRequest request) {
		
		String response="";
		try {
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);		
			EmployeeDetailsEntity employee=new EmployeeDetailsEntity();
			CopyUtility.copyProperties(request,employee);
			if(request.getDocfile()!=null)
				employee.setDocfile(Base64.getDecoder().decode(request.getDocfile()));
			if(request.getSigfile()!=null)
				employee.setSigfile(Base64.getDecoder().decode(request.getSigfile()));
			employee=employeeDetailsDao.saveDetails(employee);
			
			response=MessageConstant.RESPONSE_SUCCESS;
			request.setResponse(response);
			request.setId(employee.getEmpId());
		} catch (Exception e) {
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);
		}
		return request;

	}


	
}
