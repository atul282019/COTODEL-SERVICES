package com.cotodel.hrms.auth.server.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="employee_onboarding",uniqueConstraints = @UniqueConstraint(columnNames = {"user_details_id"}))
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="employee_onboarding_seq" , sequenceName="employee_onboarding_seq", allocationSize=1)
public class EmployeeOnboardingEntity  implements Serializable{
	
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="employee_onboarding_seq")
	@Column(name="id")
	private Long id;	
	@Column(name="employer_id")
	private Long employerId;
	@Column(name="emp_or_cont")
	private String empOrCont;
	@Column(name="name")
	private String name;
	@Column(name="email")
	private String email;
	@Column(name="mobile")
	private String mobile;
	@Column(name="her_date")
	private LocalDate herDate;
	@Column(name="job_title")
	private String jobTitle;
	@Column(name="depratment")
	private String depratment;
	@Column(name="manager_name")
	private String managerName;
	@Column(name="ctc")
	private String ctc;
	@Column(name="status")
	private int status;
	@Column(name="user_details_id", unique = true)
	private Long userDetailsId;
	@Column(name="mode")
	private Long mode;
	@Column(name="location")
	private String location;
	@Column(name="resident_of_india")
	private String residentOfIndia;	
	@CreationTimestamp
	@Column(name="creation_date")
	private LocalDateTime creationDate;
	@Column(name="proof_of_identity")
	private String proofOfIdentity;
	@Column(name="pan")
	private String pan;
	@Column(name="bank_account_number")
    private String bankAccountNumber;
	@Column(name="ifsc_code")
    private String ifscCode;
	@Column(name="beneficiary_name")
    private String beneficiaryName;
	
	@Column(name="manager_id")
	private Long managerId;
	
	@Column(name="manager_lbl")
	private String managerLbl;
	
	@Column(name="job_title_id")
	private Long jobTitleId;
	
	@Column(name="emp_code")
	private String empCode;
	
	@Lob
	@Type(type="org.hibernate.type.BinaryType")
	@Column(name = "emp_photo")
    private byte[] empPhoto;
	
	//change
}
