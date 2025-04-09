package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.entity.CashFreeOrderWebHookEntity;

@Repository
public interface CashFreeOrderWebHookRepository extends JpaRepository<CashFreeOrderWebHookEntity,Long>{
	@Query("select s  from CashFreeOrderWebHookEntity s where s.orderId = ?1")
	public List<CashFreeOrderWebHookEntity> findByOrderId(String orderId);
}
