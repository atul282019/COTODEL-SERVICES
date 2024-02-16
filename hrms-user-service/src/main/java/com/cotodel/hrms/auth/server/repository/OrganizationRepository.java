package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cotodel.hrms.auth.server.entity.OrganizationMaster;

public interface OrganizationRepository extends  JpaRepository<OrganizationMaster, Long> {
	

	  @Query("select s  from OrganizationMaster s where  s.status=1")
	  public List<OrganizationMaster> getAllList();
	 
	  

}
