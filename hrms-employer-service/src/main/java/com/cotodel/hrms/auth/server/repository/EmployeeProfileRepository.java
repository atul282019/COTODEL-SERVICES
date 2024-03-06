package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cotodel.hrms.auth.server.model.EmployeeProfileEntity;

public interface EmployeeProfileRepository extends JpaRepository<EmployeeProfileEntity, Long>{
	
	
	@Query("select s  from EmployeeProfileEntity s where s.employerId = ?1")
	  public List<EmployeeProfileEntity> findByEmployerId(Long emplid);
}
