package com.cotodel.hrms.auth.server.repository;

import java.util.List;

/**
 * @author vinay
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cotodel.hrms.auth.server.entity.IndustryMaster;

public interface IndustryMasterRepository extends JpaRepository<IndustryMaster, Long> {
	
	  @Query("select s  from IndustryMaster s ")
	  public List<IndustryMaster> getByIndustryList();
}
