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
@Table(name="expance_travel_advance")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="expance_travel_advance_seq" , sequenceName="expance_travel_advance_seq", allocationSize=1)

public class ExpanceTravelAdvanceEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="expance_travel_advance_seq")	
	@Column(name="id")
	private Long id;
	
	@Column(name = "employer_id" , unique = true)
    private Long employerId;
	
	@Column(name = "allow_employees_travel")
    private String allowEmployeesTravel;
	
	@Column(name = "allow_employees_cash")
    private String allowEmployeesCash;
	
	@Column(name = "employees_allow")
    private String employeesAllow;
	
	@Column(name = "name_employees_cash")
    private String nameEmployeesCash;
	
	@Column(name = "days_disbursal_cash")
    private String daysDisbursalCash;
	
	private LocalDate  created_date ;
	private LocalDate  modified_date ;
	
	@Column(name = "status")
    private Long status;
}
