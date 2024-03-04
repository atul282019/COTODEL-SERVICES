package com.cotodel.hrms.auth.server.service.impl;

import java.util.List;

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
			employee.setDocfile(request.getDocfile().getBytes());
			employee.setSigfile(request.getSigfile().getBytes());
			employee=employeeDetailsDao.saveDetails(employee);
			//user.setEmployeeId(employee.getEmployeeId());
			response=MessageConstant.RESPONSE_SUCCESS;
			request.setResponse(response);
			request.setId(employee.getEmpId());
		} catch (Exception e) {
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);
		}
		return request;

	}

	@Override
	public List<EmployeeDetailsEntity> getEmpDetailsList(Long empid) {
		// TODO Auto-generated method stub
		return employeeDetailsDao.getEmployeeDetails(empid);
	}

	
	
		

	

}
