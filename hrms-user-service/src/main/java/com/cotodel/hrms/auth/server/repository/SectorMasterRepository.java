package com.cotodel.hrms.auth.server.repository;

import java.util.List;

/**
 * @author vinay
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cotodel.hrms.auth.server.entity.SectorMaster;

public interface SectorMasterRepository extends JpaRepository<SectorMaster, Long> {
	
	  @Query("select s  from SectorMaster s ")
	  public List<SectorMaster> getBySectorMasterList();
}
