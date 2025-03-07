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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="job_title_master")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="job_title_master_job_title_id_seq" , sequenceName="job_title_master_job_title_id_seq", allocationSize=1)
public class JobTitleMasterEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="job_title_master_job_title_id_seq")
	@Column(name="job_title_id")
	private Long id;
	
	@Column(name="job_disc")
	private String jobDisc;
	
	@Column(name="status")
	private int status;
	
	@Column(name="manager_lbl_id")
	private Long managerLblId;
	
	@Column(name="creation_date")
	private LocalDateTime creationDate;
	
	@Column(name="created_by")
	private String createdBy;
	 
	@Column(name="org_id")
	private Long orgId;   	
	
	@Column(name="updation_date")
	private LocalDateTime updationDate;

	@Column(name="updated_by")
	private String updatedBy;
	
	@Column(name="remarks")
	private String remarks;
}

     
  


