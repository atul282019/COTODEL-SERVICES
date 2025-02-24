package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cotodel.hrms.auth.server.model.DirectorOnboardingEntity;
import com.cotodel.hrms.auth.server.model.EmployeeOnboardingEntity;

public interface DirectorOnboardingRepository extends JpaRepository<DirectorOnboardingEntity, Long>{
	
	
	@Query("select s  from DirectorOnboardingEntity s where s.orgId = ?1 ")
	public List<DirectorOnboardingEntity> findByOnboardingList(Long emplid);
	
	
}
