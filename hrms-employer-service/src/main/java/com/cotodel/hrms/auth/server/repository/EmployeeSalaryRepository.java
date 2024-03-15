package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cotodel.hrms.auth.server.model.EmployeeSalaryEntity;

public interface EmployeeSalaryRepository extends JpaRepository<EmployeeSalaryEntity, Long>{
	
	
	@Query("select s  from EmployeeSalaryEntity s where s.employerId = ?1")
	  public List<EmployeeSalaryEntity> findByEmployerId(Long emplid);
	
	@Query("select s  from EmployeeSalaryEntity s where  s.employerId = ?1")
	  public EmployeeSalaryEntity findEmployeeId(Long employerId);
}
