package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.LinkSubAccountMultipleEntity;
@Repository
public interface LinkSubMultipleAccountRepository extends JpaRepository<LinkSubAccountMultipleEntity,Long>{
	@Modifying
    @Transactional
    @Query(value = "UPDATE link_sub_account_multiple  SET balance =:balance WHERE id =:id", nativeQuery = true)
    public int updateBalance(@Param("balance") Float balance,@Param("id") Long id);	
	
	@Query("select s  from LinkSubAccountMultipleEntity s ")
	public List<LinkSubAccountMultipleEntity> getLinkMultipleAccount();
	
	@Query("select s  from LinkSubAccountMultipleEntity s WHERE s.orgId=:orgId and s.status='1'")
	public List<LinkSubAccountMultipleEntity> getLinkMultipleAccountByOrgId(@Param("orgId") Long orgId);
	
	@Query("select s  from LinkSubAccountMultipleEntity s WHERE s.acNumber=:acNumber and s.orgId=:orgId and s.status='1'")
	public LinkSubAccountMultipleEntity getLinkMultipleAccountByAccNoOrgId(@Param("acNumber") String acNumber,@Param("orgId") Long orgId);
}
