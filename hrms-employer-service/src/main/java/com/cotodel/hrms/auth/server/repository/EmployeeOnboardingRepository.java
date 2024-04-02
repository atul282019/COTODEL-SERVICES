package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cotodel.hrms.auth.server.model.EmployeeOnboardingEntity;

public interface EmployeeOnboardingRepository extends JpaRepository<EmployeeOnboardingEntity, Long>{
	
	
	@Query("select s  from EmployeeOnboardingEntity s where s.employerId = ?1")
	  public List<EmployeeOnboardingEntity> findByOnboardingList(Long emplid);
}
