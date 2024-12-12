package com.cotodel.hrms.auth.server.repository;

import java.util.List;

/**
 * @author vinay
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cotodel.hrms.auth.server.entity.PayrollMasterNewEntity;

public interface PayrollMasterNewRepository extends JpaRepository<PayrollMasterNewEntity, Long> {
		  
	  @Query("select s  from PayrollMasterNewEntity s where s.status=1")
	  public List<PayrollMasterNewEntity> getByPayrollMasterNewList();
}
