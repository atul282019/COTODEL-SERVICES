package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.ErupiVoucherCreationRequestEntity;
@Repository
public interface ErupiVoucherRequestRepository extends JpaRepository<ErupiVoucherCreationRequestEntity,Long>{
	
	@Query("select s  from ErupiVoucherCreationRequestEntity s where s.employerId =:employerId order by s.creationDate desc")
	public List<ErupiVoucherCreationRequestEntity> findByEmployerId(@Param("employerId") Long employerId);
	
	@Query("select s  from ErupiVoucherCreationRequestEntity s where s.employeeId =:employeeId order by s.creationDate desc")
	public List<ErupiVoucherCreationRequestEntity> findByEmployeeId(@Param("employeeId") Long employeeId);
	
	@Query("select s  from ErupiVoucherCreationRequestEntity s where s.employerId =:employerId and s.status='3' order by s.creationDate desc")
	public List<ErupiVoucherCreationRequestEntity> findByApprovedEmployerId(@Param("employerId") Long employerId);
	
	@Query("select s  from ErupiVoucherCreationRequestEntity s where s.employeeId =:employeeId and s.status='3' order by s.creationDate desc")
	public List<ErupiVoucherCreationRequestEntity> findByApprovedEmployeeId(@Param("employeeId") Long employeeId);
}