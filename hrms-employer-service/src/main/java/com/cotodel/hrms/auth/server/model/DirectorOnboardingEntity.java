package com.cotodel.hrms.auth.server.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name="director_onboarding")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="director_onboarding_seq" , sequenceName="director_onboarding_seq", allocationSize=1)
public class DirectorOnboardingEntity  implements Serializable{
	
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="director_onboarding_seq")
	@Column(name="id")
	private Long id;
	
	@Column(name="employer_id")
	private Long employerId;
	
	@Column(name="name")
	private String name;
	
	@Column(name="email")
	private String email;
	
	@Column(name="mobile")
	private String mobile;
	
	@Column(name="her_date")
	private LocalDate herDate;
	
	@Column(name="ctc")
	private String ctc;
	
	@Column(name="status")
	private int status;
	
	@Column(name="employee_payroll_id")
	private Long employeePayrollId;	

	@Column(name="address")
	private String address;

	@CreationTimestamp
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="proof_of_identity")
	private String proofOfIdentity;
	
	@Column(name="pan")
	private String pan;

}
