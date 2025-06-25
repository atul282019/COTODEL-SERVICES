package com.cotodel.hrms.auth.server.dto.vehicle;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleTripDefauldDto {
	private LocalDateTime tripStartDate;
	private LocalDateTime tripEndDate;
	private String driverName2;	
	private String driverMobile;
	private String dlNumber;	
	private String clientName;
	private String clientCity;
	
}
