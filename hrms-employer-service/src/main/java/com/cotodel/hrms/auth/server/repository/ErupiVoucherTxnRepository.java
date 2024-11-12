package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.ErupiVoucherTxnDetailsEntity;
@Repository
public interface ErupiVoucherTxnRepository extends JpaRepository<ErupiVoucherTxnDetailsEntity,Long>{
	
	@Query("select s  from ErupiVoucherTxnDetailsEntity s where s.orgId = ?1")
	public List<ErupiVoucherTxnDetailsEntity> findByOrgId(Long orgId);

}
