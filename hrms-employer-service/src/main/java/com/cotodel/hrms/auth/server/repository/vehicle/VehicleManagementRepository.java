package com.cotodel.hrms.auth.server.repository.vehicle;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cotodel.hrms.auth.server.model.vehicle.VehicleManagementEntity;

public interface VehicleManagementRepository extends JpaRepository<VehicleManagementEntity, Long>{
	
	
	@Query("select s  from VehicleManagementEntity s where s.orgId = ?1 ")
	public List<VehicleManagementEntity> findByVehicleList(Long emplid);
	
	
}
