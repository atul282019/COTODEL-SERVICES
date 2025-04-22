package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cotodel.hrms.auth.server.dto.AdvanceTravelDto;
import com.cotodel.hrms.auth.server.model.AdvanceTravelRequestEntity;

public interface AdvanceTravelRequestRepository extends JpaRepository<AdvanceTravelRequestEntity, Long>{
	
	//@Query("select s  from AdvanceTravelRequestEntity s where  s.employerId = ?1")
	@Query("select new com.cotodel.hrms.auth.server.dto.AdvanceTravelDto(a.id,a.sequenceId,a.expenseCategory"
			+ ",CONCAT(b.name,'|',b.userDetailsId,'|',b.jobTitle) as name, b.depratment,a.amount,a.currency"
			+ ",a.createdDate,a.modeOfPayment,a.status,'',a.approvedBy,a.remarks,a.approvedStatus,a.statusRemarks,a.requestType,a.paymentMode,a.approvedAmount) "
			+ "from AdvanceTravelRequestEntity a join EmployeeOnboardingEntity b on a.employeeId=b.userDetailsId "
			+ " and a.employerId = ?1 order by a.createdDate desc ")
	public List<AdvanceTravelDto> findEmployerId(Long employerId);
	//@Query("select s  from AdvanceTravelRequestEntity s where  s.employeeId = ?1")
	@Query("select new com.cotodel.hrms.auth.server.dto.AdvanceTravelDto(a.id,a.sequenceId,a.expenseCategory"
			+ ",CONCAT(b.name,'|',b.userDetailsId,'|',b.jobTitle) as name, b.depratment,a.amount,a.currency"
			+ ",a.createdDate,a.modeOfPayment,a.status,'',a.approvedBy,a.remarks,a.approvedStatus,a.statusRemarks,a.requestType,a.paymentMode,a.approvedAmount) "
			+ "from AdvanceTravelRequestEntity a join EmployeeOnboardingEntity b on a.employeeId=b.userDetailsId "
			+ " and a.employeeId = ?1 order by a.createdDate desc ")
	public List<AdvanceTravelDto> findEmployeeId(Long employeeId);
	
//	@Query("select s  from AdvanceTravelRequestEntity s where  s.employerId =:employerId and s.status=:status ")
//	public List<AdvanceTravelRequestEntity> findEmployerId(@Param(value = "employerId") Long employerId,@Param(value = "status") int status);
//	@Query("select s  from AdvanceTravelRequestEntity s where  s.employeeId =:employeeId and s.status=:status ")
//	public List<AdvanceTravelRequestEntity> findEmployeeId(@Param(value = "employeeId") Long employeeId,@Param(value = "status") int status);
	
	@Query("select s  from AdvanceTravelRequestEntity s where  s.employerId =:employerId ")
	public List<AdvanceTravelRequestEntity> findEmployerIdWithoutStatus(@Param(value = "employerId") Long employerId);
	@Query("select s  from AdvanceTravelRequestEntity s where  s.employeeId =:employeeId ")
	public List<AdvanceTravelRequestEntity> findEmployeeIdWithoutStatus(@Param(value = "employeeId") Long employeeId);
	
	@Transactional    
	@Modifying
	@Query(value="DELETE FROM advance_travel_request WHERE id= ?1 ",  nativeQuery = true)
	void deleteDetails(Long id);
	
	//@Query("select s  from AdvanceTravelRequestEntity s where  s.employerId =:employerId and s.status=1 ")
	@Query("select new com.cotodel.hrms.auth.server.dto.AdvanceTravelDto(a.id,a.sequenceId,a.expenseCategory"
			+ ",CONCAT(b.name,'|',b.userDetailsId,'|',b.jobTitle) as name, b.depratment,a.amount,a.currency"
			+ ",a.createdDate,a.modeOfPayment,a.status,'',a.approvedBy,a.remarks,a.approvedStatus,'',a.requestType,a.paymentMode,a.approvedAmount) "
			+ "from AdvanceTravelRequestEntity a join EmployeeOnboardingEntity b on a.employeeId=b.userDetailsId "
			+ " and a.employerId=:employerId  and a.status=1 order by a.createdDate desc ")
	public List<AdvanceTravelDto> findApprovalEmployerId(@Param(value = "employerId") Long employerId);
	
	@Query("select s  from AdvanceTravelRequestEntity s where  s.id = ?1")
	public AdvanceTravelRequestEntity getById(Long id);
}
