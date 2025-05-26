package com.cotodel.hrms.auth.server.dto.vehicle;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleTripDto {
	private String tripStartDate;
	private String tripEndDate;
	private String driverName2;	
	private String driverMobile;
	private String dlNumber;	
	private String clientName;
	private String clientCity;
	
	public VehicleTripDto(LocalDateTime tripStartDate, LocalDateTime tripEndDate,
            String driverName2, String driverMobile, String dlNumber,
            String clientName, String clientCity) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
		this.tripStartDate = tripStartDate.format(formatter);
		this.tripEndDate = tripEndDate.format(formatter);
		this.driverName2 = driverName2;
		this.driverMobile = driverMobile;
		this.dlNumber = dlNumber;
		this.clientName = clientName;
		this.clientCity = clientCity;
}
}
