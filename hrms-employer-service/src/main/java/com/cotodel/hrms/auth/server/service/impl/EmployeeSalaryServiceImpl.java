package com.cotodel.hrms.auth.server.service.impl;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.cotodel.hrms.auth.server.dao.EmployeeSalaryDao;
import com.cotodel.hrms.auth.server.dto.EmployeeSalaryRequest;
import com.cotodel.hrms.auth.server.model.EmployeeSalaryEntity;
import com.cotodel.hrms.auth.server.service.EmployeeSalaryService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;
@Transactional
@Repository
public class EmployeeSalaryServiceImpl implements EmployeeSalaryService{
	
	@Autowired
	EmployeeSalaryDao  employeeSalaryDao;	

	@Override
	public EmployeeSalaryRequest saveSalaryDetails(EmployeeSalaryRequest user) {
		String response="";
		try {
			response=MessageConstant.RESPONSE_FAILED;
			user.setResponse(response);
		
		EmployeeSalaryEntity employeeSalaryEntity=new EmployeeSalaryEntity();
		CopyUtility.copyProperties(user,employeeSalaryEntity);
		employeeSalaryEntity=employeeSalaryDao.saveDetails(employeeSalaryEntity);	

		response=MessageConstant.RESPONSE_SUCCESS;
		user.setResponse(response);
		} catch (Exception e) {
			response=MessageConstant.RESPONSE_FAILED;
			user.setResponse(response);
		}
		return user;
	}

	@Override
	public List<EmployeeSalaryEntity> getEmpSalaryList(Long empid) {
		return employeeSalaryDao.getEmployeeSalary(empid);
	}

	@Override
	public EmployeeSalaryEntity getEmpProfile( Long employerid) {
		return employeeSalaryDao.getEmplSalary( employerid);
	}
	
}
