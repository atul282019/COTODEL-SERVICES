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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="vehicle_type_master")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="vehicle_type_master_seq" , sequenceName="vehicle_type_master_seq", allocationSize=1)
public class VehicleTypeMasterEntity  implements Serializable{
	
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="vehicle_type_master_seq")
	@Column(name="id")
	private Long id;

	@Column(name="vechile_name")
	private String vechileName;
	
	@Column(name="vechile_type")
	private String vechileType;
	
	@Column(name="creation_date")
	private LocalDateTime creationDate;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="status")
	private int status;
		
}
