package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.LinkSubAccountMultipleTempEntity;
@Repository
public interface LinkSubMultipleAccountTempRepository extends JpaRepository<LinkSubAccountMultipleTempEntity,Long>{
		
	@Query("select s  from LinkSubAccountMultipleTempEntity s WHERE s.orgId=:orgId order by s.creationDate desc")
	public List<LinkSubAccountMultipleTempEntity> getLinkMultipleAccount(@Param("orgId") Long orgId);
	
	@Query("select s  from LinkSubAccountMultipleTempEntity s WHERE s.orgId=:orgId and s.status='1'")
	public List<LinkSubAccountMultipleTempEntity> getLinkMultipleAccountByOrgId(@Param("orgId") Long orgId);
	
	@Query("select s  from LinkSubAccountMultipleTempEntity s WHERE s.acNumber=:acNumber and s.orgId=:orgId and s.status='1'")
	public LinkSubAccountMultipleTempEntity getLinkMultipleAccountByAccNoOrgId(@Param("acNumber") String acNumber,@Param("orgId") Long orgId);
	
	@Query("select s  from LinkSubAccountMultipleTempEntity s WHERE s.id=:id ")
	public LinkSubAccountMultipleTempEntity getdetailById(@Param("id") Long id);
	
	@Query("select s  from LinkSubAccountMultipleTempEntity s order by s.creationDate desc")
	public List<LinkSubAccountMultipleTempEntity> getLinkMultipleAccountList();
	
	
}
