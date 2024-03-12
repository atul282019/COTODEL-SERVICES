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
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="employee_details")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="employee_details_seq" , sequenceName="employee_details_seq", allocationSize=1)
public class EmployeeDetailsEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="employee_details_seq")	
	@Column(name="emp_id")
	private Long empId;
	
	@Column(name="employer_id")
	private Long employerId;
	
		
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="middle_name")
	private String middleName;
	
	@Column(name="last_name")
	private String lastName;
	
	
	@Column(name="date_of_birth")
	private LocalDate dateOfBirth;
	
	private String nationality;
	
	private String maritalStatus;
	
	@Column(name="date_of_joining")
	private LocalDate dateOfJoining;
	
	private String designation;
	
	private String gender;
	
	private String department;
	
	private String location;
	
	@Column(name="pan_no")
	private String panNo;
	
	@Column(name="esi_no")
	private String esiNo;
	
	@Column(name="account_number")
	private String accountNumber;
	
	@Column(name="ifsc_code")
	private String ifscCode;
	
	@Column(name="uan_no")
	private String uanNo;
	
	@Column(name="bank_name")
	private String bankName;
	
	@Column(name="remarks")
	private String remarks;
	
	@Column(name="brief_description")
	private String briefDescription;
	
	@Lob
	@Type(type="org.hibernate.type.BinaryType")
	@Column(name="doc_file")
	private  byte[] docfile;
	
	@Column(name="doc_file_name")
	private String docFileName;
	
	@Lob
	@Type(type="org.hibernate.type.BinaryType")
	@Column(name="sig_file")
	private  byte[] sigfile;
	
	@Column(name="sig_file_name")
	private  String sigFileName;
	
	@Column(name="service_status")
	private String serviceStatus;
	
	@Column(name="employee_type")
	private String employeeType;
	
	@Column(name="category")
	private String category;
	
	@Column(name="active")
	private Boolean active=false;
	
	
}
