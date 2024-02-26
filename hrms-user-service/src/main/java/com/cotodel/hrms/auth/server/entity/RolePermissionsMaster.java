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
@Table(name="role_permissions")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="role_permissions_seq" , sequenceName="role_permissions_seq", allocationSize=1)
public class RolePermissionsMaster implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="role_permissions_seq")
	@Column(name="role_id")
	private int roleId;	
	private int permission_id;
	@Column(nullable=false)
	private int employer_id;
	private int status ;
	private String user_id;
}
