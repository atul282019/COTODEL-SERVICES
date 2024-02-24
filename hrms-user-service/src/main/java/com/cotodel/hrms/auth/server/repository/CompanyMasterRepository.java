package com.cotodel.hrms.auth.server.repository;

import java.util.List;

/**
 * @author vinay
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cotodel.hrms.auth.server.entity.CompanyMaster;

public interface CompanyMasterRepository extends JpaRepository<CompanyMaster, String> {
	
	  @Query("select s  from CompanyMaster s ")
	  public List<CompanyMaster> getByCompanyList();
}
