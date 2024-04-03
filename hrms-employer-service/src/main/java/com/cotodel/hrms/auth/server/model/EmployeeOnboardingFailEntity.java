package com.cotodel.hrms.auth.server.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="employee_onboarding_fail")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="employee_onboarding_fail_seq" , sequenceName="employee_onboarding_fail_seq", allocationSize=1)
public class EmployeeOnboardingFailEntity  implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="employee_onboarding_fail_seq")
	@Column(name="id")
	private Long id;	
	@Column(name="employer_id")
	private Long employerId;
	@Lob
	private byte[] failValue;
	@Column(name="status")
	private Long status;
	@CreationTimestamp
	@Column(name="creation_date")
	private Date creationDate;

}
