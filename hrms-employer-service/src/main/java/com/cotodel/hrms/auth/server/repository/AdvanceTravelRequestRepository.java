package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cotodel.hrms.auth.server.dto.ExpenseReimbursementDto;
import com.cotodel.hrms.auth.server.model.AdvanceTravelRequestEntity;

public interface AdvanceTravelRequestRepository extends JpaRepository<AdvanceTravelRequestEntity, Long>{
	
	@Query("select s  from AdvanceTravelRequestEntity s where  s.employerId = ?1")
	public List<AdvanceTravelRequestEntity> findEmployerId(Long employerId);
	@Query("select s  from AdvanceTravelRequestEntity s where  s.employeeId = ?1")
	public List<AdvanceTravelRequestEntity> findEmployeeId(Long employeeId);
	
	@Query("select s  from AdvanceTravelRequestEntity s where  s.employerId =:employerId and s.status=:status ")
	public List<AdvanceTravelRequestEntity> findEmployerId(@Param(value = "employerId") Long employerId,@Param(value = "status") int status);
	@Query("select s  from AdvanceTravelRequestEntity s where  s.employeeId =:employeeId and s.status=:status ")
	public List<AdvanceTravelRequestEntity> findEmployeeId(@Param(value = "employeeId") Long employeeId,@Param(value = "status") int status);
	
	@Transactional    
	@Modifying
	@Query(value="DELETE FROM advance_travel_request WHERE id= ?1 ",  nativeQuery = true)
	void deleteDetails(Long id);
	
	@Query("select s  from AdvanceTravelRequestEntity s where  s.employerId =:employerId and s.status=1 ")
	public List<AdvanceTravelRequestEntity> findApprovalEmployerId(@Param(value = "employerId") Long employerId);
	
	@Query("select s  from AdvanceTravelRequestEntity s where  s.id = ?1")
	public AdvanceTravelRequestEntity getById(Long id);
}
