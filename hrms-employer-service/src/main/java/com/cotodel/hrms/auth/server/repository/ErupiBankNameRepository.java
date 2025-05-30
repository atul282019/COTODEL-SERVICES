package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.ErupiBankNameMasterEntity;
@Repository
public interface ErupiBankNameRepository  extends JpaRepository<ErupiBankNameMasterEntity, Long>{
	@Query("select s  from ErupiBankNameMasterEntity s where s.status='1'")
	public List<ErupiBankNameMasterEntity> getErupiBankNameMaster();
	
	
}
