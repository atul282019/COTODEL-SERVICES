package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.EmployeeEntity;
@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Long>{
	
	EmployeeEntity findByEmployeeId(Long employeeId);
	
	@Query(value="select *  from employee  ",nativeQuery = true)
	  public List<EmployeeEntity> getByEmployeeList();
}
