package com.cotodel.hrms.auth.server.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.ErupiBankMasterEntity;
@Repository
public interface BankMasterRepository extends JpaRepository<ErupiBankMasterEntity,Long>{
	
		@Query("select s  from ErupiBankMasterEntity s where s.bankCode = ?1")
	  public ErupiBankMasterEntity findByBankCode(String  bankcode);
	
	
}
