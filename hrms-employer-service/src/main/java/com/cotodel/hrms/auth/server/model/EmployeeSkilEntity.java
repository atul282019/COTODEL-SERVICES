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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="employee_skil")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="employee_skil_seq" , sequenceName="employee_skil_seq", allocationSize=1)
public class EmployeeSkilEntity implements Serializable{
	

	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="employee_skil_seq")
	@Column(name="id")
	private Long id;
	
	@Column(name="education_id")
	private Long educationId;//FK from education master – Highest education
	
	@OneToOne
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;
		
	@OneToOne
    @JoinColumn(name = "employer_id")
    private EmployerEntity employer;
	
	@Column(name="skil_set")
	private String skillSet;
	
	private String domain;
	
	@CreationTimestamp
	@Column(name="from_date")
	private Date fromDate;
	
	@CreationTimestamp
	@Column(name="to_date")
	private Date toDate;
	
	@Column(name="validation_status")
	private boolean validationStatus;
	
	private String cvurl;
	
	private String extra1;
	private String extra2;
	
	
}
