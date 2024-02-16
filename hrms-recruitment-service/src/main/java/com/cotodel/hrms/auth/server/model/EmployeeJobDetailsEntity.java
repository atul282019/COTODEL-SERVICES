package com.cotodel.hrms.auth.server.model;

import java.io.Serializable;

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

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="employee_job_details")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="employee_job_details_seq" , sequenceName="employee_job_details_seq", allocationSize=1)
public class EmployeeJobDetailsEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="employee_job_details_seq")
	@Column(name="job_detail_id")
	private Long jobDetailId;
	
	@OneToOne
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;
	
	@OneToOne
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;
	
	@OneToOne
    @JoinColumn(name = "position_id")
    private PositionEntity position;
	
	@Column(name="employment_type")
	private String employmentType;	

	@CreationTimestamp
	@Column(name="joining_date")
	private Date joiningDate;
	
	@OneToOne
    @JoinColumn(name = "employer_id")
    private EmployerEntity employer;	
}
