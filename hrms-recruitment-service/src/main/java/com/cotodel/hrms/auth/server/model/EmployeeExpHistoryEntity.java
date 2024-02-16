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
@Table(name="employee_exp_history")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="employee_exp_history_seq" , sequenceName="employee_exp_history_seq", allocationSize=1)
public class EmployeeExpHistoryEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="employee_exp_history_seq")
	@Column(name="id")
	private Long id;
	
	@Column(name="org_id")
	private Long orgId;//FK from org master
	
	@OneToOne
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;
	
	@OneToOne
    @JoinColumn(name = "employer_id")
    private EmployerEntity employer;
	
	@Column(name="org_name")
	private String orgName;
	
	@Column(name="domain")
	private String domain;
	
	@CreationTimestamp
	@Column(name="from_date")
	private Date fromDate;
	
	@CreationTimestamp
	@Column(name="to_date")
	private Date toDate;
	
	@Column(name="validation_status")
	private boolean validationStatus;
	private String extra1;
	private String extra2;
	
}