package com.cotodel.hrms.auth.server.repository.vehicle;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cotodel.hrms.auth.server.dto.vehicle.VehicleTripDefauldDto;
import com.cotodel.hrms.auth.server.dto.vehicle.VehicleTripDto;
import com.cotodel.hrms.auth.server.model.vehicle.VehicleDriverManagementEntity;

public interface VehicleTripManagementRepository extends JpaRepository<VehicleDriverManagementEntity, Long>{
	

	@Query("select new com.cotodel.hrms.auth.server.dto.vehicle.VehicleTripDto(a.tripStartDate,a.tripEndDate,a.driverName2,a.driverMobile,"
			+ "'',a.clientName,'')   from VehicleDriverManagementEntity a where a.vehicleId.id =:id")
	  public List<VehicleTripDto> findByVehicleTripList(@Param("id") Long id);
	
	@Query(value = "SELECT a.trip_start_date, a.trip_end_date, a.driver_name_two, a.driver_mobile, '' AS dl_number, a.client_name, '' AS client_city " +
            "FROM hrms1.vehicle_driver_management a WHERE a.vehicle_id = :id " +
            "ORDER BY a.trip_id DESC LIMIT 1", nativeQuery = true)
		Object[] findTopByVehicleIdOrderByTripStartDateDesc(@Param("id") Long id);
	
}
