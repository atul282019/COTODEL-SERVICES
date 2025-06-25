package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.ExpenseCategoryMasterEntity;
@Repository
public interface ExpenseCategoryMasterRepository extends JpaRepository<ExpenseCategoryMasterEntity,Long>{
	

	
	@Query("select s  from ExpenseCategoryMasterEntity s where s.status='1'")
	public List<ExpenseCategoryMasterEntity> getExpenseCategoryMaster();	
	
	
}
