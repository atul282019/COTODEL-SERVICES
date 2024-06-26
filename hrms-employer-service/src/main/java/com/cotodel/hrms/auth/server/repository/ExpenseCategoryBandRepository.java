package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
	
	@Query("select s  from ExpenseCategoryBandEntity s where s.id = ?1 ")
	public ExpenseCategoryBandEntity findByEmployeeId(Long id);
	
	@Query("select s  from ExpenseCategoryBandEntity s where s.employerId = ?1 ")
	public List<ExpenseCategoryBandEntity> findByExpenseEmpoyerId(Long employerid);
	@Query("select s  from ExpenseCategoryBandEntity s where s.masterId = ?1 and s.employerId = ?2")
	public ExpenseCategoryBandEntity findByEmployeeExpenseCodeWithEmployer(Long id,Long employerId);
	
	@Modifying
    @Query(value = "UPDATE expense_category SET expense_category = :expenseCategory,expense_code = :expenseCode,day_to_expiry=:expirydays, expense_limit=:expenseLimit WHERE id = :id ", nativeQuery = true)
	public int updateAmountById(@Param(value = "expenseCategory") String expenseCategory,@Param(value = "expenseCode") String expenseCode,@Param(value = "expirydays") String expirydays,@Param(value = "expenseLimit") String expenseLimit,@Param(value = "id") Long id);
}
