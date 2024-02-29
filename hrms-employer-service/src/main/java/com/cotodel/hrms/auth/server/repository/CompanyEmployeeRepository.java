package com.cotodel.hrms.auth.server.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.CompanyEmployeeEntity;
@Repository
public interface CompanyEmployeeRepository extends JpaRepository<CompanyEmployeeEntity, Long>{
	@Query("select c from CompanyEmployeeEntity c where c.id = ?1 ")
	CompanyEmployeeEntity findByCompanyEmployeeEntity(Long employeeId);
}
