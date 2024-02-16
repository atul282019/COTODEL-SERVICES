package com.cotodel.hrms.auth.server.entity;


/**
 * @author vinay
 */

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.cotodel.hrms.auth.server.dto.ERole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="organizations")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="organization_seq" , sequenceName="organization_seq", allocationSize=1)
public class OrganizationMaster implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="organization_seq")
	private Long id;

	String org_type;
	String org_name;
	String org_cat;
	private int employer_id ;
	private LocalDate created_date;
	private int status;


}
