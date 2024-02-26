package com.cotodel.hrms.auth.server.entity;


/**
 * @author vinay
 */

import java.io.Serializable;

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
@Table(name="permissions")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="permissions_seq" , sequenceName="permissions_seq", allocationSize=1)
public class PermissionsMaster implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="permissions_seq")
	@Column(name="permission_id")
	private Long permissionId;	
	private String name;
	private String description;
	private int employer_id ;
	private int status;
}

