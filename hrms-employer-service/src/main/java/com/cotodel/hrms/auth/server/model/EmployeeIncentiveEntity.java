package com.cotodel.hrms.auth.server.model;

import java.io.Serializable;
import java.time.LocalDate;

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
@Table(name="employee_incentive")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="employee_incentive_seq" , sequenceName="employee_incentive_seq", allocationSize=1)
public class EmployeeIncentiveEntity  implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="employee_incentive_seq")
	@Column(name="id")
	private Long id;

	@Column(name = "employer_id")
    private Long employerId;
	@Column(name = "employee_id")
    private Long employeeId;
	
	@Column(name = "provider_first_name")
    private String providerFirstName;
	
	@Column(name = "provider_last_name")
    private String providerLastName;
	
	@Column(name = "provider_cpa_number")
    private String providerCpaNumber;
	
	@Column(name = "provider_phone_number")
    private String providerPhoneNumber;
	
	@Column(name = "provide_email")
    private String provideEmail;
	
	@Column(name = "national_insurance_number")
    private String nationalInsuranceNumber;
	
	@Column(name = "declaration")
    private String declaration;
	
	@Column(name = "employee_detail")
    private String employeeDetail;
	
	@Column(name = "employee_first_name")
    private String employeeFirstName;
	
	@Column(name = "employee_last_name")
    private String employeeLastName;
	
	@Column(name = "company_address")
    private String companyAddress;
	
	@Column(name = "company_contact_person_name")
    private String companyContactPersonName;
	
	@Column(name = "phone_number")
    private String phoneNumber;
	
	@Column(name = "bank_name")
    private String bankName;
	
	@Column(name = "bank_account_name")
    private String bankAccountName;
	
	@Column(name = "bank_account_number")
    private String bankAccountNumber;
	
	@Column(name = "job_details")
    private String jobDetails;
	
	@Column(name = "job_title")
    private String jobTitle;
	@CreationTimestamp
	@Column(name = "job_start_date")
    private LocalDate jobStartDate;
	
	@Column(name = "payroll_number")
    private String payrollNumber;
	
	@Column(name = "job_type")
    private String jobType;
	
	@Column(name = "employer_claim_detail")
    private String employerClaimDetail;
	
	@Column(name = "date")
    private LocalDate date;
	
	@Column(name = "employer_name")
    private String employerName;
	
	@Column(name = "employer_signature_file")
    private String employerSignatureFile;
	
	@Column(name = "employer_signature_name")
    private String employerSignatureName;
			
	}
