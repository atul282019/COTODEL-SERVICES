package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cotodel.hrms.auth.server.model.EmployeeIncentiveEntity;

public interface EmployeeIncentiveRepository extends JpaRepository<EmployeeIncentiveEntity, Long>{
	
	
	@Query("select s  from EmployeeIncentiveEntity s where s.employerId = ?1")
	  public List<EmployeeIncentiveEntity> findByEmployerId(Long emplid);
	
	@Query("select s  from EmployeeIncentiveEntity s where  s.employerId = ?1")
	  public EmployeeIncentiveEntity findEmployeeId(Long employerId);
}
