package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.model.DirectorOnboardingEntity;

public interface DirectorOnboardingDao {
	public DirectorOnboardingEntity saveDetails(DirectorOnboardingEntity employeeEntity);
	public List<DirectorOnboardingEntity> getEmployeeOnboardingList(Long employerId);
}
