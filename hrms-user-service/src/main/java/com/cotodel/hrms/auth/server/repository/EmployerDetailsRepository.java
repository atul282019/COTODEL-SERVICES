package com.cotodel.hrms.auth.server.repository;

/**
 * @author vinay
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.entity.EmployerDetailsEntity;

@Repository
public interface EmployerDetailsRepository extends JpaRepository<EmployerDetailsEntity, Long> {
	@Query("select s from EmployerDetailsEntity s where s.employerId =:orgId  ")
	public EmployerDetailsEntity getEmployerDetailsByEmpId(@Param("orgId") Long orgId);
}
