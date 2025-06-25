package com.cotodel.hrms.auth.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.AdvanceRequestSettingEntity;
@Repository
public interface ExpenseTravelAdvanceRepository extends JpaRepository<AdvanceRequestSettingEntity,Long>{
		
	
	@Query("select s  from AdvanceRequestSettingEntity s where s.employerId = ?1 ")
	public AdvanceRequestSettingEntity findByEmpoyerId(Long employerid);
	
}
