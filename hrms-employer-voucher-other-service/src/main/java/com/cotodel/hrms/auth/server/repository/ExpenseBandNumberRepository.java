package com.cotodel.hrms.auth.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.ExpenseBandNumberEntity;
@Repository
public interface ExpenseBandNumberRepository extends JpaRepository<ExpenseBandNumberEntity,Long>{
		
	@Query("select s  from ExpenseBandNumberEntity s where s.id = ?1 ")
	public ExpenseBandNumberEntity findByEmployeeId(Long id);
	
	@Query("select s  from ExpenseBandNumberEntity s where s.employerId = ?1 ")
	public ExpenseBandNumberEntity findByExpenseEmpoyerId(Long employerid);
		
	
}
