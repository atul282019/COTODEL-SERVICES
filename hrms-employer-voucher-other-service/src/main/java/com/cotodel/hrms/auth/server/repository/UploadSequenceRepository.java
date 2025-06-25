package com.cotodel.hrms.auth.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.UploadSequenceEntity;
@Repository
public interface UploadSequenceRepository extends JpaRepository<UploadSequenceEntity,Long>{
	
	 @Query(value = "SELECT nextval('upload_sequence')", nativeQuery = true)
	 Long getNextSeriesId();
	
}
