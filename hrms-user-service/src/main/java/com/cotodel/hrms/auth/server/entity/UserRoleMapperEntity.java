package com.cotodel.hrms.auth.server.entity;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user_role_mapper")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="user_role_mapper_id_pk_seq" , sequenceName="user_role_mapper_id_pk_seq", allocationSize=1)
public class UserRoleMapperEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_role_mapper_id_pk_seq")
	@Column(name="id_pk")
	private Long id ;
	
	@Column(name="mobile")
	private String mobile ;
	
	@Column(name="role_id")
	private int roleId ;
	
	@Column(name="org_id")
	private Long orgId ;

	@Column(name="creation_date")
	private LocalDateTime creationDate ;
	
	@Column(name="created_by")
	private String createdBy ;
	
	@Column(name="status")
	private int status ;
	
	@Column(name="status_desc")
	private String statusDesc ;
	
	@Column(name="consent")
	private String consent ;
	
	@Transient
    private String roleDesc;
	

}
