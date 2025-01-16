package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dto.ExpenseReimbursementDto;
import com.cotodel.hrms.auth.server.model.ExpenseReimbursementEntity;
@Repository
public interface ExpenseReimbursementRepository extends JpaRepository<ExpenseReimbursementEntity,Long>{
	
	@Query("select s  from ExpenseReimbursementEntity s where s.employeeId = ?1")
	  public List<ExpenseReimbursementEntity> findByEmployeeId(Long emplid);
	
	@Query("select new com.cotodel.hrms.auth.server.dto.ExpenseReimbursementDto(a.id,a.sequenceId,a.expenseCategory,"
			+ "CONCAT(b.name,'|',b.userDetailsId,'|',b.jobTitle) as name, b.depratment,a.amount,a.created_date) "
			+ "from ExpenseReimbursementEntity a join EmployeeOnboardingEntity b on a.employeeId=b.userDetailsId "
			+ " and a.employeeId=:employeeId   order by a.created_date desc ")
	public List<ExpenseReimbursementDto> findExpenseReimByEmpId(@Param("employeeId") Long employeeId);
	
	@Query("select new com.cotodel.hrms.auth.server.dto.ExpenseReimbursementDto(a.id,a.sequenceId,a.expenseCategory,"
			+ "CONCAT(b.name,'|',b.userDetailsId,'|',b.jobTitle) as name, b.depratment,a.amount,a.created_date) "
			+ "from ExpenseReimbursementEntity a join EmployeeOnboardingEntity b on a.employeeId=b.userDetailsId "
			+ " and a.employerId=:employerId   order by a.created_date desc ")
	public List<ExpenseReimbursementDto> findExpenseReimByEmplrId(@Param("employerId") Long employerId);
}
