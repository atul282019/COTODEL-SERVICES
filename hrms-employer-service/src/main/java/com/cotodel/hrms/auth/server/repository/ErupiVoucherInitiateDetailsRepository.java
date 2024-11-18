package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import javax.persistence.Column;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedDto;
import com.cotodel.hrms.auth.server.model.ErupiVoucherCreationDetailsEntity;
@Repository
public interface ErupiVoucherInitiateDetailsRepository extends JpaRepository<ErupiVoucherCreationDetailsEntity,Long>{
	
	@Modifying
    @Transactional
    @Query(value = "UPDATE erupi_voucher_creation_details  SET workflowid =:workflowid WHERE id_pk =:id", nativeQuery = true)
    public int updateWorkflowId(@Param("id") Long id,@Param("workflowid") Long workflowid);
	
	@Query("select new com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedDto(c.id,c.name,c.mobile,c.amount,"
			+ "t.merchanttxnId,c.purposeCode,c.mcc,c.redemtionType,c.creationDate,c.expDate,w.type,c.voucherCode,c.voucherType,c.voucherDesc) "
			+ "from ErupiVoucherCreationDetailsEntity c"
			+ " join ErupiVoucherTxnDetailsEntity t on c.id = t.detailsId and t.workFlowId = c.workFlowId "
			+ "join WorkFlowMasterEntity w on c.workFlowId=w.workflowId  where   c.orgId =?1 ")
	public List<ErupiVoucherCreatedDto> findVoucherCreateList(Long orgId);
	
	 @Query(value = "SELECT count(1), SUM(amount), " +
             "(SELECT voucherdesc FROM voucher_type_master c WHERE c.id_pk = a.voucher_id_pk) AS vname " +
             "FROM erupi_voucher_creation_details a, erupi_voucher_txn_details b " +
             "WHERE a.id_pk = b.details_id AND b.workflowid ='100003' AND a.org_id =:orgId " +
             "GROUP BY a.voucher_id_pk", nativeQuery = true)
	public List<Object[]> getVoucherSummary(@Param("orgId") Long orgId);
	
}