package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.master.MccMasterEntity;



@Repository
public interface MccMasterRepository extends JpaRepository<MccMasterEntity, Long>{
	 
	 @Query("SELECT m " +
	           "FROM MccMasterEntity m WHERE m.purposeCode =?1 and m.status =1")
	 public List<MccMasterEntity> findMccMainIconByPurposeCode(String purposeCode);
	 
	
	 

}
