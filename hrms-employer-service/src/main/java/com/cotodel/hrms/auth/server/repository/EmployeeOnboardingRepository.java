package com.cotodel.hrms.auth.server.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cotodel.hrms.auth.server.model.EmployeeOnboardingEntity;

public interface EmployeeOnboardingRepository extends JpaRepository<EmployeeOnboardingEntity, Long>{
	
	
	@Query("select s  from EmployeeOnboardingEntity s where s.employerId = ?1 and s.status='0'")
	public List<EmployeeOnboardingEntity> findByOnboardingList(Long emplid);
	
	@Query("select s  from EmployeeOnboardingEntity s where s.mobile = ?1")
	public EmployeeOnboardingEntity findByOnboarding(String mobile);
	
	@Query("select s  from EmployeeOnboardingEntity s where s.id = ?1")
	public EmployeeOnboardingEntity findByOnboardingId(Long id);
}
