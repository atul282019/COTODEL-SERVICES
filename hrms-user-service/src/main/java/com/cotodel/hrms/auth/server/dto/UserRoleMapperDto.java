package com.cotodel.hrms.auth.server.dto;


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
@AllArgsConstructor
@NoArgsConstructor

public class UserRoleMapperDto implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id ;
	private String mobile ;	
	private int roleId ;	
	private Long orgId ;
	private LocalDateTime creationDate ;
	private String createdBy ;
	private int status ;
	private String statusDesc ;
    private String roleDesc;

}
