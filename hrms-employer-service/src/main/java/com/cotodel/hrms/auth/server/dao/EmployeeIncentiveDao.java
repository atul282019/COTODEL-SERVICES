package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.model.EmployeeIncentiveEntity;

public interface EmployeeIncentiveDao {
	public EmployeeIncentiveEntity saveDetails(EmployeeIncentiveEntity employeeIncentiveEntity);
	public List<EmployeeIncentiveEntity> getEmployeeIncentive(Long emplrid);
	public EmployeeIncentiveEntity getEmplIncentive(Long employerid);
}
