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
@Table(name="employment_details")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="employment_details_seq" , sequenceName="employment_details_seq", allocationSize=1)
public class EmploymentDetails implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="employment_details_seq")	
	@Column(name="id")
	private Long id;
	
	private String position;
	private String department;
	private LocalDate hireDate;
	private Long salary;
	
	
}
