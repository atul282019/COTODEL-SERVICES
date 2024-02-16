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
@Table(name="benefits")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="benefits_seq" , sequenceName="benefits_seq", allocationSize=1)
public class BenefitsEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="benefits_seq")
	@Column(name="benefit_id")
	private Long benefitId;
	
	@OneToOne
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;
	
	@OneToOne
    @JoinColumn(name = "employer_id")
    private EmployerEntity employer;

	@CreationTimestamp
	@Column(name="start_date")
	private Date startDate;

	@CreationTimestamp
	@Column(name="end_date")
	private Date endDate;

	@CreationTimestamp
	@Column(name="creation_date")
	private Date creationDate;
	
	private String remarks;
	
}
