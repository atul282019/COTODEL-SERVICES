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
	
	@Query("select s  from ErupiLinkAccountEntity s where s.orgId =:orgId ")
	  public List<ErupiLinkAccountEntity> findErupiListByOrgId(@Param("orgId") Long orgId);
	
	@Query("select s  from ErupiLinkAccountEntity s where s.orgId =:orgId and s.accstatus='1' ")
	  public List<ErupiLinkAccountEntity> findErupiListByOrgIdWithStatus(@Param("orgId") Long orgId);
	
	@Query("select s  from ErupiLinkAccountEntity s where s.acNumber = ?1")
	  public ErupiLinkAccountEntity findErupiAccNumber(String accNumber);
	
		@Modifying
	    @Transactional
	    @Query(value = "UPDATE erupi_linkaccount  SET ps_flag ='Secondary' WHERE org_id =:orgId", nativeQuery = true)
	    public int updateAllAsSecondry(@Param("orgId") Long orgId);
		
		@Modifying
	    @Transactional
	    @Query(value = "UPDATE erupi_linkaccount  SET ps_flag ='Primary' WHERE org_id =:orgId and acnumber=:acNumber", nativeQuery = true)
		public int updateAccAsPrimary(@Param("orgId") Long orgId,@Param("acNumber") String acNumber);
		
		@Query("select s  from ErupiLinkAccountEntity s where s.psFlag='Primary' and s.orgId = ?1")
		public ErupiLinkAccountEntity findPrimaryAccountByOrgId(Long orgId);
		
		
		@Modifying
	    @Transactional
	    @Query(value = "UPDATE erupi_linkaccount  SET accstatus ='0' WHERE org_id =:orgId and acnumber=:acNumber", nativeQuery = true)
		public int updateAccDisable(@Param("orgId") Long orgId,@Param("acNumber") String acNumber);
		
		@Modifying
	    @Transactional
	    @Query(value = "UPDATE erupi_linkaccount  SET accstatus ='1' WHERE org_id =:orgId and acnumber=:acNumber", nativeQuery = true)
		public int updateAccEnable(@Param("orgId") Long orgId,@Param("acNumber") String acNumber);
		
}
