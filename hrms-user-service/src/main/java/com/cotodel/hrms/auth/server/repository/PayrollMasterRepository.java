package com.cotodel.hrms.auth.server.repository;

import java.util.List;

/**
 * @author vinay
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cotodel.hrms.auth.server.entity.PayrollMasterEntity;

public interface PayrollMasterRepository extends JpaRepository<PayrollMasterEntity, Long> {
		  
	  @Query("select s  from PayrollMasterEntity s where s.status=1")
	  public List<PayrollMasterEntity> getByPayrollMasterList();
}
