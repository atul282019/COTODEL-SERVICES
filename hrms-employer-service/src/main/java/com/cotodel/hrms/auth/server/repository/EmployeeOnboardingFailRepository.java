package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cotodel.hrms.auth.server.model.EmployeeOnboardingFailEntity;

public interface EmployeeOnboardingFailRepository extends JpaRepository<EmployeeOnboardingFailEntity, Long>{
	
	
	@Query("select s  from EmployeeOnboardingFailEntity s where s.employerId = ?1")
	  public List<EmployeeOnboardingFailEntity> findByOnboardingFailList(Long emplid);
}
