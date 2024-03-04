package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.model.EmployeeFamilyDetailEntity;

public interface EmployeeFamilyDetailsDao {
	public EmployeeFamilyDetailEntity saveDetails(EmployeeFamilyDetailEntity employeeEntity);
	public List<EmployeeFamilyDetailEntity> getEmployeeDetails(Long emplrid);
}
