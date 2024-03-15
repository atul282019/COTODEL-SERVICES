package com.cotodel.hrms.auth.server.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.EmployeeIncentiveDao;
import com.cotodel.hrms.auth.server.dto.EmployeeIncentiveRequest;
import com.cotodel.hrms.auth.server.model.EmployeeIncentiveEntity;
import com.cotodel.hrms.auth.server.service.EmployeeIncentiveService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;
@Transactional
@Repository
public class EmployeeIncentiveServiceImpl implements EmployeeIncentiveService{
	
	@Autowired
	EmployeeIncentiveDao  employeeIncentiveDao;	

	@Override
	public EmployeeIncentiveRequest saveIncentiveDetails(EmployeeIncentiveRequest user) {
		String response="";
		try {
			response=MessageConstant.RESPONSE_FAILED;
			user.setResponse(response);
		
		EmployeeIncentiveEntity employeeSalaryEntity=new EmployeeIncentiveEntity();
		CopyUtility.copyProperties(user,employeeSalaryEntity);
		employeeSalaryEntity=employeeIncentiveDao.saveDetails(employeeSalaryEntity);	

		response=MessageConstant.RESPONSE_SUCCESS;
		user.setResponse(response);
		} catch (Exception e) {
			response=MessageConstant.RESPONSE_FAILED;
			user.setResponse(response);
		}
		return user;
	}

	@Override
	public List<EmployeeIncentiveEntity> getEmpIncentiveList(Long empid) {
		// TODO Auto-generated method stub
		return employeeIncentiveDao.getEmployeeIncentive(empid);
	}

	@Override
	public EmployeeIncentiveEntity getEmpIncentive(Long employerid) {
		// TODO Auto-generated method stub
		return employeeIncentiveDao.getEmplIncentive(employerid);
	}
		
}
