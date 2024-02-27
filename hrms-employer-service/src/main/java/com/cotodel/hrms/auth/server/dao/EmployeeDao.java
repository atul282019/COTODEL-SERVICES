package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.model.EmployeeEntity;

public interface EmployeeDao {
	public EmployeeEntity saveDetails(EmployeeEntity employeeEntity);
	public EmployeeEntity getUser(Long employeeId);
	public List<EmployeeEntity> getEmployee();
}
