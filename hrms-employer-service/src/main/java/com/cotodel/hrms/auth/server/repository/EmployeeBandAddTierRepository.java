package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.EmployeeBandAddTierEntity;
@Repository
public interface EmployeeBandAddTierRepository extends JpaRepository<EmployeeBandAddTierEntity,Long>{
	
	@Query("select s  from EmployeeBandAddTierEntity s where s.employeeBandId = ?1")
	public List<EmployeeBandAddTierEntity> getBandAddTier(Long employeeBandId);
	
}