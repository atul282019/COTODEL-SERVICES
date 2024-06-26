package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.model.EmployeeBandEntity;

public interface EmployeeBandDao {
	public EmployeeBandEntity saveDetails(EmployeeBandEntity employeeBandEntity);
	//public EmployeeBandEntity getEmployeeBandDetails(String bandId);
	public List<EmployeeBandEntity> getEmployeeBandList();
	public EmployeeBandEntity getEmployeeBandId(Long employerId);
	public void deleteDetails(Long id);
	public EmployeeBandEntity findByEmployeeBandED(Long employerId);
	
}
