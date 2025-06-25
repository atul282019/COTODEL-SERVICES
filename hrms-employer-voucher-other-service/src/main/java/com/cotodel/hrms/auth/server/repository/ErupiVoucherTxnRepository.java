package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.ErupiVoucherTxnDetailsEntity;
@Repository
public interface ErupiVoucherTxnRepository extends JpaRepository<ErupiVoucherTxnDetailsEntity,Long>{
	
	@Query("select s  from ErupiVoucherTxnDetailsEntity s where s.orgId = ?1")
	public List<ErupiVoucherTxnDetailsEntity> findByOrgId(Long orgId);
	
//	@Query(value = "select s  from ErupiVoucherTxnDetailsEntity s where s.detailsId = ?1 and workFlowId=?2 LIMIT 1" ,nativeQuery = true)
//	public ErupiVoucherTxnDetailsEntity findByDetailId(Long id,Long workflowid);
	
	
	 @Query(value = "SELECT * FROM erupi_voucher_txn_details WHERE details_id = :id AND workflowid = :workflowid LIMIT 1", nativeQuery = true)
	 ErupiVoucherTxnDetailsEntity findByDetailId(@Param("id") Long id, @Param("workflowid") Long workflowId);
	 
	 @Query(value = "select s  from ErupiVoucherTxnDetailsEntity s where s.detailsId = :id  ")
	 List<ErupiVoucherTxnDetailsEntity> findByDetailIdWithRedeem(@Param("id") Long id);
}
