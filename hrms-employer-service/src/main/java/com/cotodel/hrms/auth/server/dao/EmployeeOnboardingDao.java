package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingDto;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingUserListDto;
import com.cotodel.hrms.auth.server.model.EmployeeOnboardingEntity;

public interface EmployeeOnboardingDao {
	public EmployeeOnboardingEntity saveDetails(EmployeeOnboardingEntity employeeEntity);
	public EmployeeOnboardingEntity getEmployeeOnboarding(String mobile);
	public EmployeeOnboardingEntity getEmployeeOnboardingId(Long id);
	public List<EmployeeOnboardingEntity> getEmployeeOnboardingList(Long employerId);
	public List<EmployeeOnboardingEntity> getEmployeeOnboardingManagerId(Long managerId);
	public EmployeeOnboardingEntity getEmployeeOnboardingUserId(Long userId);
	public List<EmployeeOnboardingEntity> getReputeEmployeeOnboarding();
	public List<EmployeeOnboardingDto> getDriverEmployeeOnboarding(Long orgId,String name);
	public List<EmployeeOnboardingEntity> getEmployeeOnboardingActiveList(Long employerId);
	public List<EmployeeOnboardingDto> getDriverEmployeeOnboardingAssign(Long orgId,String name);
	public List<EmployeeOnboardingUserListDto> getEmployeeOnboardingUserList(Long orgId,String name);
}
