package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.ExpenseReimbursementEntity;
@Repository
public interface ExpenseReimbursementRepository extends JpaRepository<ExpenseReimbursementEntity,Long>{
	
	@Query("select s  from ExpenseReimbursementEntity s where s.employeeId = ?1")
	  public List<ExpenseReimbursementEntity> findByEmployeeId(Long emplid);
}
