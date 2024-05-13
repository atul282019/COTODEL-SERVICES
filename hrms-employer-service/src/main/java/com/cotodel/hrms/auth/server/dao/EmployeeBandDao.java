package com.cotodel.hrms.auth.server.dao;

import com.cotodel.hrms.auth.server.model.EmployeeBandEntity;

public interface EmployeeBandDao {
	public EmployeeBandEntity saveDetails(EmployeeBandEntity employeeBandEntity);
	public EmployeeBandEntity getEmployeeBandDetails(String bandId);
}
