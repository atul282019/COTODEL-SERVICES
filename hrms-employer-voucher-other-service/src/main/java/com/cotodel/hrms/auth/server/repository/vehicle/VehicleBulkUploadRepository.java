package com.cotodel.hrms.auth.server.repository.vehicle;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cotodel.hrms.auth.server.model.vehicle.VehicleBulkUploadEntity;

public interface VehicleBulkUploadRepository extends JpaRepository<VehicleBulkUploadEntity, Long>{

}
