package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cotodel.hrms.auth.server.model.EmployeeProfileEntity;

public interface EmployeeProfileRepository extends JpaRepository<EmployeeProfileEntity, Long>{
	
	
	@Query("select s  from EmployeeProfileEntity s where s.employerId = ?1")
	  public List<EmployeeProfileEntity> findByEmployerId(Long emplid);
	
	@Query("select s  from EmployeeProfileEntity s where  s.employerId = ?1")
	  public EmployeeProfileEntity findEmployeeId(Long employerId);
	
	@Query(value = "select s.id,address_line,s.office_address  from employee_profile s where  s.employer_id = ?1", nativeQuery = true)
	public List<Object[]> findEmployerAddress(Long employerId);
	
	
}
