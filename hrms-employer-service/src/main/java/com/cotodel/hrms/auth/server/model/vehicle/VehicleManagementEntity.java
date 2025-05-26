package com.cotodel.hrms.auth.server.model.vehicle;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="vehicle_management",uniqueConstraints = @UniqueConstraint(columnNames = {"vehicle_no"}),
indexes = {@Index(name = "idx_vehicle_sequence_id", columnList = "vehicle_sequence_id")})
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="vehicle_management_seq" , sequenceName="vehicle_management_seq", allocationSize=1)
public class VehicleManagementEntity  implements Serializable{
	
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="vehicle_management_seq")
	@Column(name="vehicle_id")
	private Long id;
	
	@Column(name="vachile_type")
	private String vehicleType;
	
	@Column(name="vehicle_no")
	private String vehicleNumber;
	
	@Column(name="creation_date")
	private LocalDateTime creationDate;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="status")
	private int status;
	
	@Column(name="org_id")
	private Long orgId;
	
	@Column(name="vehicle_model")
	private String vehicleModel;
	
	@Column(name="manufacturing_year")
	private String manufacturingYear;
	
	@Column(name="fuel_type")
	private String fuelType;
	
	@Column(name="creation_type")
	private String creationType;////creation Type--bulk or single
	
	@Column(name="application_type")
	private String applicationType;////upload Type--web  or mobile
	
	@Column(name="owner_name")
	private String ownerName;////upload Type--web  or mobile
	
	@Column(name="owner_address")
	private String ownerAddress;//
	
	@Column(name="ownership_type")
	private String ownership_type;//
	
	@Column(name="chassis_number")
	private String chassisNumber;//
	
	@Column(name="engine_number")
	private String engineNumber;//
	
	@Column(name="color")
	private String color;
	
	@Column(name="authority")
	private String authority;
	
	@Column(name="cubic_capacity")
	private String cubicCapacity;
	
	@Column(name="seat")
	private int seat;
	
	@Column(name="weight")
	private String weight;
	
	@Column(name="insurance_company")
	private String insuranceCompany;
	
	@Column(name="fitness")
	private String fitness;
	
	@Column(name="tax")
	private String tax;
	
	@Column(name="financer")
	private String financer;
	
	@Column(name="permit_number")
	private String permitNumber;
	
	@Column(name="ownership_driver")
	private String ownershipDriver;
		
	@Column(name="assignment_driver")
	private String assignmentDriver;
	
	@Column(name="assignment_type")
	private String assignmentType;
	
	@Column(name="client_name")
	private String clientName;
	
	@Column(name="selected_timeperiod")
	private String selectedTimeperiod;
	
	@Column(name="driver_cum_owner")
	private String driverCumOwner;
	
	@Column(name="vehicle_manufactor")
	private String vehicleManufactor;
	
	@Column(name="vehicleid")
	private String vehicleId;
	
	@Column(name="vehicle_fuel_type_id")
	private String vehicleFuelTypeId;
	
	@Column(name="vehicle_fuel_type")
	private String vehicleFuelType;
	
	@Column(name="ownership_type_id")
	private String ownership_type_id;
	
	@Column(name="driver_name_one")
	private String driverName1;
	
	@Column(name="assignment_type_id")
	private String assignmentType_id;
	
	@Column(name="driver_name_two")
	private String driverName2;
	
	@Column(name="driver_id")
	private Long driverId;
	
	@Column(name="driver_mobile")
	private String driverMobile;
	
	@Column(name="request_type")
	private String requestType;
	
	@Column(name="vehicle_sequence_id")
	private String vehicleSequenceId;
	
	
}
