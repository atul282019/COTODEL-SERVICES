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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="vehicle_bulk_upload_fail",indexes = {@Index(name = "idx_fail_orgid_filename", columnList = "org_id, file_name")})
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="vehicle_bulk_upload_fail_seq" , sequenceName="vehicle_bulk_upload_fail_seq", allocationSize=1)
public class VehiclerBulkUploadFailEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="vehicle_bulk_upload_fail_seq")
	private Long id;
	
	@Column(name="vehicle_no")
	private String vehicleNumber;
	
	@Column(name="file_name")
	private String fileName;
	
	@Column(name = "creation_date")
    private LocalDateTime creationDate;
	
	@Column(name = "org_id")
    private Long orgId;

	@Column(name="createdby", length=49)
	private String createdby;
		
	@Column(name="bulktbl_id")
	private Long bulktblId;
	
	@Column(name="status")
	private int status;
	
	@Column(name="message")
	private String message;

}
