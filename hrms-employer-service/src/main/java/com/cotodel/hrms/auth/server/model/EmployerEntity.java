package com.cotodel.hrms.auth.server.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

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
		
//	@OneToOne
//    @JoinColumn(name = "signup_id")
//    private SignUpEntity signup;
	
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
	//Extra field add but 
	
	@Column(name="brand_name")
	private String brandName;
	
	@Column(name="pan_details")
	private String panDetails;
	
	@Column(name="company_name")
	private String companyName;
	
	@Column(name="office_address")
	private String officeAddress;
	
	@Column(name="address_line")
	private String addressLine;
	
	@Column(name="pin_code")
	private String pinCode;
	
	@Column(name="state_code")
	private String stateCode;
   
	@Column(name="payroll_enabled_flag")
	private boolean payrollEnabledFlag=false;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name="paid_date")
	private Date paidDate;
    
	@Column(name="run_payroll_flag")
	private boolean runPayrollFlag=false;
    
	@Column(name="salary_advances_flag")
	private boolean salaryAdvancesFlag=false;
    
	
}
