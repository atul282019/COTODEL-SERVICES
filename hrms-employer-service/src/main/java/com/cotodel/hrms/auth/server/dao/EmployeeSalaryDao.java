package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.model.EmployeeSalaryEntity;

public interface EmployeeSalaryDao {
	public EmployeeSalaryEntity saveDetails(EmployeeSalaryEntity employeeSalaryEntity);
	public List<EmployeeSalaryEntity> getEmployeeSalary(Long emplrid);
	public EmployeeSalaryEntity getEmplSalary(Long employerid);
}
