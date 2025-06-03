package com.cotodel.hrms.auth.server.repository.vehicle;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cotodel.hrms.auth.server.model.vehicle.VehiclerBulkUploadFailEntity;

public interface VehiclerBulkUploadFailRepository extends JpaRepository<VehiclerBulkUploadFailEntity, Long>{
	@Query("select s  from VehiclerBulkUploadFailEntity s where s.orgId = :orgId and s.fileName=:fileName")
	public List<VehiclerBulkUploadFailEntity> findByOrgIdAndFileName(@Param("orgId") Long orgId,
		    @Param("fileName") String fileName);
	
	
}
