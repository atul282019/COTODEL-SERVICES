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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name="employee")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="employee_seq" , sequenceName="employee_seq", allocationSize=1)
public class EmployeeEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="employee_seq")	
	@Column(name="employee_id")
	private Long employeeId;
	
	@ManyToOne
    @JoinColumn(name = "employer_id")
    private EmployerEntity employer;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@CreationTimestamp
	@Column(name="date_of_birth")
	private Date dateOfBirth;
	
	private String gender;
	
	@Column(name="mobile")
	private String Mobile;
	
	@Column(name="pic_url")
	private String picUrl;
	
	private String email;
	
	private String address;
	
	@Column(name="role_id")
	private Long roleId;
	
	private String username;
	
	private String pwd;
	
	@Column(name="bank_account")
	private String bankAccount;
	
	private String ifsc;
	
	private String urn;
	
	private String pan;
	
	private String aadhaar;
	
	private String extra1;
	
	private String extra2;
	
	private boolean status;
	
	private String intextra1;
	
}
