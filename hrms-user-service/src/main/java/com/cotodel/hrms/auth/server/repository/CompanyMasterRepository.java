package com.cotodel.hrms.auth.server.repository;

import java.util.List;

/**
 * @author vinay
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cotodel.hrms.auth.server.entity.CompanyMaster;
import com.cotodel.hrms.auth.server.entity.CompositeKey;

public interface CompanyMasterRepository extends JpaRepository<CompanyMaster, CompositeKey> {
	
	  @Query("select s  from CompanyMaster s ")
	  public List<CompanyMaster> getByCompanyList();
}
