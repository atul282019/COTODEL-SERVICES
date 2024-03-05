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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="experience")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="experience_seq" , sequenceName="experience_seq", allocationSize=1)
public class ExperienceEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="experience_seq")	
	@Column(name="id")
	private Long id;
	@Column(name="employee_id")
	private Long employeeId;
	private String designation;
	private String company;	
	@Column(name="from_date")
	private LocalDate fromDate;	
	@Column(name="to_date")
	private LocalDate toDate;
	@Column(name="no_of_year")
	private String noOfYear;
	private String country;
	@Column(name="reference_email")
	private String referenceEmail;
	@Column(name="reference_mobile")
	private String referenceMobile;	
	private String remarks;
}
