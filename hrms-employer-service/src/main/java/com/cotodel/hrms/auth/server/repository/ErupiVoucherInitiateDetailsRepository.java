package com.cotodel.hrms.auth.server.repository;

import java.util.List;

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
			+ "t.merchanttxnId,c.purposeCode,c.mcc,c.redemtionType) "
			+ "from ErupiVoucherCreationDetailsEntity c"
			+ " join ErupiVoucherTxnDetailsEntity t on c.id = t.detailsId and t.workFlowId = c.workFlowId where   c.orgId =?1 ")
	public List<ErupiVoucherCreatedDto> findVoucherCreateList(Long orgId);
	
	
	
}
