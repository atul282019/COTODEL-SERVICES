package com.cotodel.hrms.auth.server.entity;

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
@Table(name="role_master")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="role_master_id_pk_seq" , sequenceName="role_master_id_pk_seq", allocationSize=1)
public class RoleMasterEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="role_master_id_pk_seq")
	@Column(name="id_pk")
	private Long id;
	
	@Column(name="role_id")
	private Long roleId;
	
	@Column(name="role_desc")
	private String roleDesc;
	
	@Column(name="status")
	private int status;	
	
	@Column(name="org_id")
	private Long orgId;
	
	@Column(name="creation_date")
	private LocalDateTime creationDate;
	
	@Column(name="created_by")
	private String createdBy;
	
}
