package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.ExpenseCategoryBandEntity;
@Repository
public interface ExpenseCategoryBandRepository extends JpaRepository<ExpenseCategoryBandEntity,Long>{
	

	@Query("select s  from ExpenseCategoryBandEntity s where s.bandId = ?1 ")
	public ExpenseCategoryBandEntity findByEmployeeBandId(String bandid);
	
	@Query("select s  from ExpenseCategoryBandEntity s")
	public ExpenseCategoryBandEntity findByEmployeeBandId();
	
	@Query("select s  from ExpenseCategoryBandEntity s where s.expenseCode = ?1 ")
	public ExpenseCategoryBandEntity findByEmployeeExpenseCode(String expenseCode);
	
	@Query("select s  from ExpenseCategoryBandEntity s where s.employerId = ?1 ")
	public List<ExpenseCategoryBandEntity> findByExpenseEmpoyerId(Long employerid);
}
