package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cotodel.hrms.auth.server.model.EmployeeDetailsEntity;

public interface EmployeeDetailsRepository extends JpaRepository<EmployeeDetailsEntity, Long>{
	
	
	@Query("select s  from EmployeeDetailsEntity s where s.employerId = ?1")
	  public List<EmployeeDetailsEntity> findByEmployerId(Long emplid);
}
