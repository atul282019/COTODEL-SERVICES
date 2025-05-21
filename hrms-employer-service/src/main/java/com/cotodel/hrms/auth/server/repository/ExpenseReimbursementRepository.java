package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dto.ExpenseReimbursementDto;
import com.cotodel.hrms.auth.server.dto.ExpenseReimbursementFileDto;
import com.cotodel.hrms.auth.server.model.ExpenseReimbursementEntity;
@Repository
public interface ExpenseReimbursementRepository extends JpaRepository<ExpenseReimbursementEntity,Long>{
	
	@Query("select new com.cotodel.hrms.auth.server.dto.ExpenseReimbursementFileDto(a.id,a.sequenceId,a.expenseCategory,"
			+ "'' , '',a.amount,a.currency,"
			+ "a.created_date,a.modeOfPayment,a.expenseTitle,a.status,'',a.approvedBy,a.fileType,a.invoiceNumber,a.vendorName,a.remarks"
			+ ",a.approvedAmount)   from ExpenseReimbursementEntity a where a.employeeId =:emplid")
	  public List<ExpenseReimbursementFileDto> findByEmployeeId(@Param("emplid") Long emplid);
	
	@Query("select new com.cotodel.hrms.auth.server.dto.ExpenseReimbursementFileDto(a.id,a.sequenceId,a.expenseCategory,"
			+ "CONCAT(b.name,'|',b.userDetailsId,'|',b.jobTitle) as name, b.depratment,a.amount,a.currency,"
			+ "a.created_date,a.modeOfPayment,a.expenseTitle,a.status,'',a.approvedBy,a.fileType,a.invoiceNumber,a.vendorName,a.remarks"
			+ ",a.approvedAmount) "
			+ "from ExpenseReimbursementEntity a join EmployeeOnboardingEntity b on a.employeeId=b.userDetailsId "
			+ " and a.employeeId=:employeeId   order by a.created_date desc ")
	
//	@Query("select new com.cotodel.hrms.auth.server.dto.ExpenseReimbursementDto(a.id, a.sequenceId, a.expenseCategory, "
//	        + " '', '', a.amount, a.currency, "
//	        + " a.created_date, a.modeOfPayment, a.expenseTitle, a.status, '' as statusMessage, a.approvedBy, a.fileType, "
//	        + " a.invoiceNumber, a.vendorName, a.remarks, a.file) "
//	        + "from ExpenseReimbursementEntity a "
//	        + "where a.employeeId = :employeeId "
//	        + "order by a.created_date desc")
	public List<ExpenseReimbursementFileDto> findExpenseReimByEmpId(@Param("employeeId") Long employeeId);
	
	@Query("select new com.cotodel.hrms.auth.server.dto.ExpenseReimbursementFileDto(a.id,a.sequenceId,a.expenseCategory,"
			+ "CONCAT(b.name,'|',b.userDetailsId,'|',b.jobTitle) as name, b.depratment,a.amount,a.currency,"
			+ "a.created_date,a.modeOfPayment,a.expenseTitle,a.status,'',a.approvedBy,a.fileType,a.invoiceNumber,a.vendorName,a.remarks"
			+ ",a.approvedAmount) "
			+ "from ExpenseReimbursementEntity a join EmployeeOnboardingEntity b on a.employeeId=b.userDetailsId "
			+ " and a.employerId=:employerId   order by a.created_date desc ")
//	@Query("select new com.cotodel.hrms.auth.server.dto.ExpenseReimbursementDto(a.id, a.sequenceId, a.expenseCategory, "
//	        + "'' , '' , a.amount, a.currency, "
//	        + "a.created_date, a.modeOfPayment, a.expenseTitle, a.status, '', a.approvedBy, a.fileType, "
//	        + "a.invoiceNumber, a.vendorName, a.remarks, a.file) "
//	        + "from ExpenseReimbursementEntity a "
//	        + "where a.employerId = :employerId "
//	        + "order by a.created_date desc")
	public List<ExpenseReimbursementFileDto> findExpenseReimByEmplrId(@Param("employerId") Long employerId);

	
	@Query("select new com.cotodel.hrms.auth.server.dto.ExpenseReimbursementDto(a.id,a.sequenceId,a.expenseCategory,"
			+ "CONCAT(b.name,'|',b.userDetailsId,'|',b.jobTitle) as name, b.depratment,a.amount,a.currency,"
			+ "a.created_date,a.modeOfPayment,a.expenseTitle,a.status,'',a.approvedBy,a.fileType,a.invoiceNumber,a.vendorName,a.remarks"
			+ ",a.file) "
			+ "from ExpenseReimbursementEntity a join EmployeeOnboardingEntity b on a.employeeId=b.userDetailsId "
			+ " and a.id=:Id   order by a.created_date desc ")
	public ExpenseReimbursementDto findExpenseReimById(@Param("Id") Long Id);
}
