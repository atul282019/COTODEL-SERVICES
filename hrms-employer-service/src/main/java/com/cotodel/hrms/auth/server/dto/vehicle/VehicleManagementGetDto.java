package com.cotodel.hrms.auth.server.dto.vehicle;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleManagementGetDto {
	private Long id;	
	private String vehicleType;	
	private String vehicleNumber;	
	private LocalDateTime creationDate;	
	private String createdBy;	
	private int status;	
	private Long orgId;	
	private String vehicleModel;	
	private String manufacturingYear;	
	private String fuelType;	
	private String creationType;////creation Type--bulk or single
	private String applicationType;////upload Type--web  or mobile
	private String ownerName;////upload Type--web  or mobile
	private String ownerAddress;//	
	private String ownership_type;//	
	private String chassisNumber;//	
	private String engineNumber;//	
	private String color;	
	private String authority;	
	private String cubicCapacity;	
	private int seat;	
	private String weight;	
	private String insuranceCompany;	
	private String fitness;	
	private String tax;	
	private String financer;	
	private String permitNumber;	
	private String ownershipDriver;		
	private String assignmentDriver;	
	private String assignmentType;	
	private String clientName;	
	private String selectedTimeperiod;	
	private String driverCumOwner;	
	private String vehicleManufactor;	
	private String vehicleId;	
	private String vehicleFuelTypeId;	
	private String vehicleFuelType;	
	private String ownership_type_id;	
	private String driverName1;	
	private String assignmentType_id;	
	private String driverName2;	
	private Long driverId;	
	private String driverMobile;	
	private String requestType;
	private LocalDate startDate;	
	private LocalDate endDate;
	private String  noOfDays;
	private String  assignedDriver;
	private String  tripStatus;//
	private String  vehicleSequenceId;
}
