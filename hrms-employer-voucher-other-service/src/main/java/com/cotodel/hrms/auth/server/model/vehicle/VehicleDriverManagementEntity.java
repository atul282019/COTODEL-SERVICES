package com.cotodel.hrms.auth.server.model.vehicle;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name="vehicle_driver_management", uniqueConstraints = @UniqueConstraint(columnNames = {"vehicle_no", "trip_start_date"}))
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="vehicle_driver_management_seq" , sequenceName="vehicle_driver_management_seq", allocationSize=1)
public class VehicleDriverManagementEntity  implements Serializable{
	
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="vehicle_driver_management_seq")
	@Column(name="trip_id")
	private Long id;
	
	@Column(name="driver_id")
	private Long driverId;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "vehicle_id")
    private VehicleManagementEntity vehicleId;
	
	@Column(name="vehicle_no")
	private String vehicleNo;
	
	@Column(name="trip_start_date")
	private LocalDateTime tripStartDate;
	
	@Column(name="trip_end_date")
	private LocalDateTime tripEndDate;
	
	@Column(name="trip_name")
	private String tripName;	
	
	@Column(name="creation_date")
	private LocalDateTime creationDate;
	
	@Column(name="created_by")
	private String createdBy;	
	
	@Column(name="status")
	private int status;
	
	@Column(name="remarks")
	private String remarks;
	
	@Column(name="org_id")
	private Long orgId;
	
	@Column(name="from_location")
	private String fromLocation;
	
	@Column(name="to_location")
	private String toLocation;	
	
	@Column(name="estimated_amount")
	private Float estimatedAmount;
	
	@Column(name="creation_type")
	private String creationType;////creation Type--bulk or single
	
	@Column(name="application_type")
	private String applicationType;////upload Type--web  or mobile
	
	@Column(name="client_type")
	private String clientType;
	
	@Column(name="client_name")
	private String clientName;
	
	@Column(name="assignment_type")
	private String assignmentType;
	
	@Column(name="ownership_type_id")
	private String ownership_type_id;
	
	@Column(name="driver_name_one")
	private String driverName1;
	
	@Column(name="assignment_type_id")
	private String assignmentType_id;
	
	@Column(name="driver_name_two")
	private String driverName2;
		
	@Column(name="driver_mobile")
	private String driverMobile;
	
	@Column(name="request_type")
	private String requestType;

	
}
