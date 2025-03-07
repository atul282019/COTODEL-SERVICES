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
@Table(name="director_onboarding")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="director_onboarding_seq" , sequenceName="director_onboarding_seq", allocationSize=1)
public class DirectorOnboardingEntity  implements Serializable{
	
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="director_onboarding_seq")
	@Column(name="id")
	private Long id;
	
	@Column(name="org_id")
	private Long orgId;
	
	@Column(name="name")
	private String name;
	
	@Column(name="email")
	private String email;
	
	@Column(name="mobile")
	private String mobile;
	
	@Column(name="din")
	private String din;
	
	@Column(name="designation")
	private String designation;	
	
	@Column(name="address")
	private String address;

	@Column(name="creation_date")
	private LocalDateTime creationDate;

	@Column(name="created_by")
	private String createdby;
	
	@Column(name="status")
	private int status;
}
