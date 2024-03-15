package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.EmployeeSalaryDao;
import com.cotodel.hrms.auth.server.model.EmployeeSalaryEntity;
import com.cotodel.hrms.auth.server.repository.EmployeeSalaryRepository;
@Repository
public class EmployeeSalaryDaoImpl implements EmployeeSalaryDao{

	@Autowired
	EmployeeSalaryRepository employeeSalaryRepository;

	@Override
	public EmployeeSalaryEntity saveDetails(EmployeeSalaryEntity employeeEntity) {
		// TODO Auto-generated method stub
		return employeeSalaryRepository.saveAndFlush(employeeEntity);
	}

	@Override
	public List<EmployeeSalaryEntity> getEmployeeSalary(Long emplrid) {
		// TODO Auto-generated method stub
		return employeeSalaryRepository.findByEmployerId(emplrid);
	}

	@Override
	public EmployeeSalaryEntity getEmplSalary(Long employerid) {
		// TODO Auto-generated method stub
		return employeeSalaryRepository.findEmployeeId(employerid);
	}

	

		
	
	

}
