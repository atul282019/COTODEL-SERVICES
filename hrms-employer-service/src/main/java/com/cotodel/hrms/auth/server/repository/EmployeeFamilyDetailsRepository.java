package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cotodel.hrms.auth.server.model.EmployeeFamilyDetailEntity;

public interface EmployeeFamilyDetailsRepository extends JpaRepository<EmployeeFamilyDetailEntity, Long>{
	
	
	@Query("select s  from EmployeeFamilyDetailEntity s where s.employeeId = ?1")
	  public List<EmployeeFamilyDetailEntity> findByEmployerId(Long emplid);
}
