package com.cotodel.hrms.auth.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.EmployeePayrollDao;
import com.cotodel.hrms.auth.server.dto.EmployeePayrollRequest;
import com.cotodel.hrms.auth.server.model.EmployeePayrollEntity;
import com.cotodel.hrms.auth.server.model.EmployerEntity;
import com.cotodel.hrms.auth.server.service.EmployeePayrollService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;
@Repository
public class EmployeePayrollServiceImpl implements EmployeePayrollService{

	@Autowired
	EmployeePayrollDao  employeePayrollDao;	
	

	@Override
	public EmployeePayrollRequest saveEmployeePayrollDetails(EmployeePayrollRequest request) {
		
		EmployerEntity employerEntity=null;
		String response="";
		try {
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);		
			EmployeePayrollEntity employee=new EmployeePayrollEntity();
			CopyUtility.copyProperties(request,employee);
			//employee.setEmployer(request.getEmployer());
		
			employee=employeePayrollDao.saveDetails(employee);
			response=MessageConstant.RESPONSE_SUCCESS;
			request.setResponse(response);
		} catch (Exception e) {
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);
		}
		return request;

	}
	
}
