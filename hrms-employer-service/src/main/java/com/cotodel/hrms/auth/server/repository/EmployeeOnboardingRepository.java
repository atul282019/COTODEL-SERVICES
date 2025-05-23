package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cotodel.hrms.auth.server.model.EmployeeOnboardingEntity;

public interface EmployeeOnboardingRepository extends JpaRepository<EmployeeOnboardingEntity, Long>{
	
	
	@Query("select s  from EmployeeOnboardingEntity s where s.employerId = ?1 ")
	public List<EmployeeOnboardingEntity> findByOnboardingList(Long emplid);
	
	@Query("select s  from EmployeeOnboardingEntity s where s.mobile = ?1")
	public EmployeeOnboardingEntity findByOnboarding(String mobile);
	
	@Query("select s  from EmployeeOnboardingEntity s where s.id = ?1")
	public EmployeeOnboardingEntity findByOnboardingId(Long id);
	
	@Query("select s  from EmployeeOnboardingEntity s where s.managerId = ?1 and s.status=1 ")
	public List<EmployeeOnboardingEntity> findByOnboardingManagerId(Long managerId);
	
	@Query("select s  from EmployeeOnboardingEntity s where s.userDetailsId = ?1")
	public EmployeeOnboardingEntity findByOnboardingUserId(Long userId);
	
	@Query("select s  from EmployeeOnboardingEntity s where s.empOrCont ='Repute' and s.managerId is null ")
	public List<EmployeeOnboardingEntity> findByOnboardingReputeManagerId();
	
}
