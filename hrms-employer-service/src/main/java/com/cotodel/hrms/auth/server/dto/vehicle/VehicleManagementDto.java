package com.cotodel.hrms.auth.server.dto.vehicle;

import java.time.LocalDateTime;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleManagementDto {
	private Long id;	
	private String vehicleType;	
	private String vehicleNo;	
	private LocalDateTime creationDate;	
	private String createdBy;	
	private int status;	
	private Long orgId;	
	private String makeModel;	
	private String manufacturingYear;	
	private String fuelType;	
	private String creationType;////creation Type--bulk or single	
	private String applicationType;////upload Type--web  or mobile	
	private String ownerName;
	private String ownerAddress;
	private String ownership_type;
	private String chassisNumber;	
	private String engineNumber;	
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
	private String response;
	private String ownershipDriver;
	private String assignmentDriver;
	private String assignmentType;
	private String clientName;
	private String selectedTimeperiod;// time period in days 
	private String vehicleNumber;
	private String vehicleManufactor;
	private String vehicleId;
	private String vehicleFuelTypeId;
	private String vehicleFuelType;
	
	
//}},
//{ "mData": "vehicleType" },
//{ "mData": "vehicleNumber" },
//{ "mData": "vehicleFuelType" },
//{ "mData": "vehicleManufactor" },
//{ "mData": "vehicleManufactor" },
//
//id aur status

}
