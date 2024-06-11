package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cotodel.hrms.auth.server.model.EmployeeBandAddTierEntity;
@Repository
public interface EmployeeBandAddTierRepository extends JpaRepository<EmployeeBandAddTierEntity,Long>{
	
	@Query("select s  from EmployeeBandAddTierEntity s where s.employeeBandId = ?1")
	public List<EmployeeBandAddTierEntity> getBandAddTier(Long employeeBandId);
	
//	@Query("select s  from EmployeeBandAddTierEntity s where s.employeeBandId = ?1")
//	public List<EmployeeBandAddTierEntity> deleteDetails(Long employeeBandId);
	@Transactional    
	@Modifying
	@Query(value="DELETE FROM employee_band_add_tier WHERE employee_band_id= ?1 ",  nativeQuery = true)
	void deleteDetails(Long employeeBandId);
}
