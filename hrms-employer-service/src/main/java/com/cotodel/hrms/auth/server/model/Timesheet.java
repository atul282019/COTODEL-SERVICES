package com.cotodel.hrms.auth.server.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="timesheet")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="timesheet_seq" , sequenceName="timesheet_seq", allocationSize=1)
public class Timesheet implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="timesheet_seq")	
	@Column(name="id")
	private Long id;

//	@OneToOne
//    @JoinColumn(name = "company_employee_id",referencedColumnName ="id")
//    private CompanyEmployeeEntity employee;
	
	@Column(name = "company_employee_id")
	private Long employeeId;
	
	@Column(name="start_date")
	private LocalDate startDate;
	
	@Column(name="end_date")
	private LocalDate endDate;
	
	private String comments;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hours_id",referencedColumnName ="id")
	private Hours hours;
	
	
}
