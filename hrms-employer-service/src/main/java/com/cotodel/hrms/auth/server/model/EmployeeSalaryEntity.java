package com.cotodel.hrms.auth.server.model;

import java.io.Serializable;

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
@Table(name="employee_salary")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="employee_salary_seq" , sequenceName="employee_salary_seq", allocationSize=1)
public class EmployeeSalaryEntity  implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="employee_salary_seq")
	@Column(name="id")
	private Long id;

	@Column(name = "employer_id")
    private Long employerId;
	@Column(name = "employee_id")
    private Long employeeId;
	
	@Column(name = "company_id")
    private Long companyId;
	
	@Column(name = "company_name")
    private String companyName;
	
	@Column(name = "short_name")
    private String shortName;
	
	@Column(name = "nature")
    private String nature;
	
	@Column(name = "type")
    private String type;
	
	@Column(name = "uom")
    private String uom;
	
	@Column(name = "unit")
    private String unit;
	
	@Column(name = "max_limit")
    private String maxLimit;
	
	@Column(name = "dependent_uom")
    private String dependentUom;
	
	@Column(name = "dependent_name")
    private String dependentName;
	
	@Column(name = "paid_to")
    private String paidTo;
	
	@Column(name = "payable_frequency")
    private String payableFrequency;
	
	@Column(name = "modifyable")
    private String modifyable;
	
	@Column(name = "active")
    private String active;
	
	@Column(name = "mandatory")
    private String mandatory;
	
	@Column(name = "impact_by_attendance")
    private String impactByAttendance;
	
	@Column(name = "taxable")
    private String taxable;
	
	@Column(name = "printable")
    private String printable;
	
	@Column(name = "slabs_allowed")
    private String slabsAllowed;
	
	@Column(name = "account")
    private String account;
	
	@Column(name = "subledger")
    private String subledger;
	
	@Column(name = "remarks")
    private String remarks;
	
	@Column(name = "brief_description")
    private String briefDescription;	
		
	}
