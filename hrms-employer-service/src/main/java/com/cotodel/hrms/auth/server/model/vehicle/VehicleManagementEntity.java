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
@Table(name="vehicle_management",uniqueConstraints = @UniqueConstraint(columnNames = {"vehicle_no"}))
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
	private String vehicleNo;
	
	@Column(name="creation_date")
	private LocalDateTime creationDate;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="status")
	private int status;
	
	@Column(name="org_id")
	private Long orgId;
	
	@Column(name="make_model")
	private String makeModel;
	
	@Column(name="manufacturing_year")
	private String manufacturingYear;

}
