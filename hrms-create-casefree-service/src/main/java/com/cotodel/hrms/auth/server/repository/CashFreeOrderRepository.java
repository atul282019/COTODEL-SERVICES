package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.entity.CashFreeOrderEntity;

@Repository
public interface CashFreeOrderRepository extends JpaRepository<CashFreeOrderEntity,Long>{
	
	@Query("select s  from CashFreeOrderEntity s where s.customerId = ?1")
	public CashFreeOrderEntity findBycustomerId(String userid);
	
	@Query("select s  from CashFreeOrderEntity s where s.orgId = ?1")
	public List<CashFreeOrderEntity> findByOrgId(Long orgId);
		
}
