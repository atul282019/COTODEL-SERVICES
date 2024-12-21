package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cotodel.hrms.auth.server.dto.EmployeeProfileAddress;
import com.cotodel.hrms.auth.server.model.EmployeeProfileEntity;

public interface EmployeeProfileRepository extends JpaRepository<EmployeeProfileEntity, Long>{
	
	
	@Query("select s  from EmployeeProfileEntity s where s.employerId = ?1")
	  public List<EmployeeProfileEntity> findByEmployerId(Long emplid);
	
	@Query("select s  from EmployeeProfileEntity s where  s.employerId = ?1")
	  public EmployeeProfileEntity findEmployeeId(Long employerId);
	
	@Query("select new com.cotodel.hrms.auth.server.dto.EmployeeProfileAddress(s.id,CONCAT(s.officeAddress, '-', s.pinCode) as officeAddress)  from EmployeeProfileEntity s where  s.employerId = ?1")
	public List<EmployeeProfileAddress> findEmployerAddress(Long employerId);
	
	
}
