package com.cotodel.hrms.auth.server.dao;

import com.cotodel.hrms.auth.server.model.EmployerEntity;

public interface EmployerDao {
	public EmployerEntity saveDetails(EmployerEntity employerEntity);
	public EmployerEntity getUser(Long employerId);
}
