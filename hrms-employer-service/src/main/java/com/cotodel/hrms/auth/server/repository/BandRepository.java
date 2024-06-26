package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.BandEntity;
@Repository
public interface BandRepository extends JpaRepository<BandEntity,Long>{
	
	@Query("select s  from BandEntity s where s.bandId = ?1 ")
	public List<BandEntity> findByBand(Long employerid);
}
