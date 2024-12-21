package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.EmployeeProfileAddress;
import com.cotodel.hrms.auth.server.model.EmployeeProfileEntity;

public interface EmployeeProfileDao {
	public EmployeeProfileEntity saveDetails(EmployeeProfileEntity employeeProfileEntity);
	public List<EmployeeProfileEntity> getEmployeeDetails(Long emplrid);
	public EmployeeProfileEntity getEmplDetails(Long employerid);
	public List<EmployeeProfileAddress> getCompAddress(Long emplrid);
	
}
