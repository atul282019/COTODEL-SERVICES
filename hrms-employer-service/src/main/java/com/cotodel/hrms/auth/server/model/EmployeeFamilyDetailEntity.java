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
@Table(name="employee_family_detail")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="employee_family_detail_seq" , sequenceName="employee_family_detail_seq", allocationSize=1)
public class EmployeeFamilyDetailEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="employee_family_detail_seq")	
	@Column(name="id")
	private Long id;	
	@Column(name="employee_id")
	private Long employeeId;
	@Column(name="employer_id")
	private Long employerId;
	private String name;	
	@Column(name="dob")
	private LocalDate dob;	
	private String relation;
	private String nominee;
	@Column(name="insurance_no")
	private String insuranceNo;
	private String mobile;
	private String email;
	private String remarks;
	
}
