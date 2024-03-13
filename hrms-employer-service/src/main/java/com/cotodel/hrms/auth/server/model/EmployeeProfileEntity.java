package com.cotodel.hrms.auth.server.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
@Table(name="employee_profile")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="employee_profile_seq" , sequenceName="employee_profile_seq", allocationSize=1)
public class EmployeeProfileEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="employee_profile_seq")	
	@Column(name="id")
	private Long id;
	
	@Column(name="employer_id")
	private Long employerId;
	
	
	@Column(name="gstn_no")
	private String gstnNo;
	
	@Column(name="org_type")
	private String orgType;
	
	private String pan;
	
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
	
	@Column(name="paid_date")
	private LocalDate paidDate;
    
	@Column(name="run_payroll_flag")
	private boolean runPayrollFlag=false;
    
	@Column(name="salary_advances_flag")
	private boolean salaryAdvancesFlag=false;
	
	@Column(name="profile_complete")
	private int profileComplete;
}

