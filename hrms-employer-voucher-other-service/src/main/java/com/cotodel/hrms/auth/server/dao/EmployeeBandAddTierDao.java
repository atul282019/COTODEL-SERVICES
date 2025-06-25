package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.model.EmployeeBandAddTierEntity;

public interface EmployeeBandAddTierDao {	
	public List<EmployeeBandAddTierEntity> saveDetails(List<EmployeeBandAddTierEntity> employeeBandAddTierEntity);
	public List<EmployeeBandAddTierEntity> getDetails(Long bandtierId);
	public void deleteDetails(Long employeeBandId);
}
