package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.ErupiLinkAccountEntity;
@Repository
public interface ErupiLinkAccountRepository extends JpaRepository<ErupiLinkAccountEntity,Long>{
	
	@Query("select s  from ErupiLinkAccountEntity s where s.orgId = ?1")
	  public ErupiLinkAccountEntity findByOrgId(Long orgId);
	
	@Query("select s  from ErupiLinkAccountEntity s where s.orgId = ?1")
	  public List<ErupiLinkAccountEntity> findErupiListByOrgId(Long orgId);
	
	@Query("select s  from ErupiLinkAccountEntity s where s.acNumber = ?1")
	  public ErupiLinkAccountEntity findErupiAccNumber(String accNumber);
	
		@Modifying
	    @Transactional
	    @Query(value = "UPDATE erupi_linkaccount  SET ps_flag ='S' WHERE org_id =:orgId", nativeQuery = true)
	    public int updateAllAsSecondry(@Param("orgId") Long orgId);
		
		@Modifying
	    @Transactional
	    @Query(value = "UPDATE erupi_linkaccount  SET ps_flag ='P' WHERE org_id =:orgId and acnumber=:acNumber", nativeQuery = true)
		public int updateAccAsPrimary(@Param("orgId") Long orgId,@Param("acNumber") String acNumber);
		
		
//		@Modifying
//	    @Query(value = "UPDATE expense_category SET expense_category = :expenseCategory,expense_code = :expenseCode,day_to_expiry=:expirydays, expense_limit=:expenseLimit WHERE id = :id ", nativeQuery = true)
//		public int updateAmountById(@Param(value = "expenseCategory") String expenseCategory,@Param(value = "expenseCode") String expenseCode,@Param(value = "expirydays") String expirydays,@Param(value = "expenseLimit") String expenseLimit,@Param(value = "id") Long id);
//		
		
	
}
