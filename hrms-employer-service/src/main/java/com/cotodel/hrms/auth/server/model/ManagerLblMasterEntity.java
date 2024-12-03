package com.cotodel.hrms.auth.server.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="manager_lbl_master")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="manager_lbl_master_manager_lbl_id_seq" , sequenceName="manager_lbl_master_manager_lbl_id_seq", allocationSize=1)
public class ManagerLblMasterEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="manager_lbl_master_manager_lbl_id_seq")
	@Column(name="manager_lbl_id")
	private Long id;
	
	@Column(name="manager_lbl_desc")
	private String managerLblDesc;
	
	@Column(name="creation_date")
	private LocalDateTime creationDate;
	
	@Column(name="created_by")
	private String createdBy;
	 
	@Column(name="org_id")
	private Long orgId;   
	
	@Column(name="status")
	private int status;
	
	@Column(name="updation_date")
	private LocalDateTime updationDate;

	@Column(name="updated_by")
	private String updatedBy;
	
	@Column(name="remarks")
	private String remarks;
}
