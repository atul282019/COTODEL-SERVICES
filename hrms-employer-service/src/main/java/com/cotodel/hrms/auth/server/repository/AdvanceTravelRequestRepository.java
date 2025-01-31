package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
}
