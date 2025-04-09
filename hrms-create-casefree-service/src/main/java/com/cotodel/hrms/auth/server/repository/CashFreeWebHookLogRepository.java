package com.cotodel.hrms.auth.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.entity.CashFreeOrderEntity;
import com.cotodel.hrms.auth.server.entity.CashFreeWebHookLogEntity;

@Repository
public interface CashFreeWebHookLogRepository extends JpaRepository<CashFreeWebHookLogEntity,Long>{
			
}
