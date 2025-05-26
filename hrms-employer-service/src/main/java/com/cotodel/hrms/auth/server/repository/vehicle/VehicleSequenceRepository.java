package com.cotodel.hrms.auth.server.repository.vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.vehicle.VehicleSequenceEntity;
@Repository
public interface VehicleSequenceRepository extends JpaRepository<VehicleSequenceEntity,Long>{
	
	 @Query(value = "SELECT nextval('vehicle_sequence')", nativeQuery = true)
	 Long getNextSeriesId();
	
}
