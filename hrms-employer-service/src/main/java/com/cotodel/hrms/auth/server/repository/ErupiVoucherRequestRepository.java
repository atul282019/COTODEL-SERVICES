package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.ErupiVoucherCreationRequestEntity;
@Repository
public interface ErupiVoucherRequestRepository extends JpaRepository<ErupiVoucherCreationRequestEntity,Long>{
	
	@Query("select s  from ErupiVoucherCreationRequestEntity s where s.employerId = ?1order by s.creationDate desc")
	public List<ErupiVoucherCreationRequestEntity> findByEmployerId(Long employerId);
	
	@Query("select s  from ErupiVoucherCreationRequestEntity s where s.employeeId = ?1order by s.creationDate desc")
	public List<ErupiVoucherCreationRequestEntity> findByEmployeeId(Long employeeId);
}