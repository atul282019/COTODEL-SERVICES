package com.cotodel.hrms.auth.server.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Table(name="employer")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="employer_seq" , sequenceName="employer_seq", allocationSize=1)
public class EmployerEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="employer_seq")
	@Column(name="employer_id")
	private Long employerId;
	
//	@OneToOne(mappedBy = "employer")
//	@Column(name="signup_id")
//    private SignUpEntity signUpEntity;
	
	@OneToOne
    @JoinColumn(name = "signup_id")
    private SignUpEntity signup;
	
	@Column(name="org_name")
	private String orgName;
	
	@Column(name="gstin")
	private String gstin;
	@Column(name="no_of_Emp")
	private String noOfEmp;
	@Column(name="contact_number")
	private String contactNumber;
	
	private String email;
	private String address;
	private String pan;
	
	@Column(name="org_type")
	private String orgType;
	
	@Column(name="org_cat")
	private String orgCat;
	
	@Column(name="status")
	private boolean Status;
	
	private String extra1;
	private String extra2;
	private String extra3;
	private String extra4;
	
	private Integer intexta1;
	
	@Column(name="approved_by")
	private String approvedBy;
	

	@CreationTimestamp
	@Column(name="approval_date")
	private Date approvalDate;

	@CreationTimestamp
	@Column(name="creation_date")
	private Date creationDate;
	

	@CreationTimestamp
	@Column(name="update_date")
	private Date updateDate;
	
	@Column(name="schema_name")
	private String schemaName;
	
	@Column(name="db_name")
	private String dbName;
	
	@Column(name="ip_details")
	private String ipDetails;
		
}
