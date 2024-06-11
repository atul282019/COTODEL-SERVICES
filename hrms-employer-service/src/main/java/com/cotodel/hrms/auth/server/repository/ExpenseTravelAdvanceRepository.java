package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.ExpanceTravelAdvanceEntity;
@Repository
public interface ExpenseTravelAdvanceRepository extends JpaRepository<ExpanceTravelAdvanceEntity,Long>{
		
	@Query("select s  from ExpanceTravelAdvanceEntity s where s.employerId = ?1 ")
	public List<ExpanceTravelAdvanceEntity> findByExpenseEmpoyerId(Long employerid);
	
	
	
}
