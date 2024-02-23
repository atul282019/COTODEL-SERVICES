package com.cotodel.hrms.auth.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.EmployeeDao;
import com.cotodel.hrms.auth.server.dto.EmployeeRequest;
import com.cotodel.hrms.auth.server.model.EmployeeEntity;
import com.cotodel.hrms.auth.server.model.EmployerEntity;
import com.cotodel.hrms.auth.server.service.EmployeeService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;
@Repository
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	EmployeeDao  employeeDao;
	
	@Override
	public EmployeeRequest saveEmployeeDetails(EmployeeRequest request) {
		
		EmployerEntity employerEntity=null;
		String response="";
		try {
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);		
			EmployeeEntity employee=new EmployeeEntity();
			CopyUtility.copyProperties(request,employee);
			employee.setEmployer(request.getEmployer());
			//employee.setPan(user.getPan());
		
			employee=employeeDao.saveDetails(employee);
			//user.setEmployeeId(employee.getEmployeeId());
			response=MessageConstant.RESPONSE_SUCCESS;
			request.setResponse(response);
		} catch (Exception e) {
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);
		}
		return request;

	}
	
}
