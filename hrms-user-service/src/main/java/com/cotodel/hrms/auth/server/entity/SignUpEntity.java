package com.cotodel.hrms.auth.server.entity;

import java.io.Serializable;
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
@Table(name="sign_up")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="signup_seq" , sequenceName="signup_seq", allocationSize=1)
public class SignUpEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="signup_seq")
	@Column(name="signup_id")
	private Long signupId;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@CreationTimestamp
	@Column(name="date_of_birth")
	private Date dateOfBirth;
	
	private String gender;
	
	@Column(name="contact_number")
	private String contactNumber;
	private String email;
	private String address;
	
	@Column(name="org_type")
	private String orgType;
	
	@Column(name="org_name")
	private String orgName;
	
	private String mobile;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="v_status_email")
	private String vstatusEmail;
	
	@Column(name="v_status_mobile")
	private String vstatusMobile;
	
	@CreationTimestamp
	@Column(name="v_status_email_date")
	private Date vstatusEmailDate;
	
	@CreationTimestamp
	@Column(name="v_status_mobile_date")
	private Date vstatusMobileDate;
	
	private String username;
	private String pwd;
	private boolean status;
	private Long intextra;
	private String extra1;

	private String extra2;
	private String extra3;
	private String intextra1;
	private String freetext;
	private String remarks;
	
//	 @OneToOne(mappedBy = "signup", cascade = CascadeType.ALL)
//	 private EmployerEntity employer;
	
	
}
