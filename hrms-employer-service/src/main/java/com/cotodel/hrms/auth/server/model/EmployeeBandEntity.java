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
@Table(name="employee_band")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="employee_band_seq" , sequenceName="employee_band_seq", allocationSize=1)

public class EmployeeBandEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="employee_band_seq")	
	@Column(name="id")
	private Long id;	
	
	@Column(name="band_enabled")
	private String bandEnabled;
	
	@Column(name="employee_band_no")
	private String employeeBandNo;	
	
	@Column(name="employee_band_no_alpha")
	private String employeeBandNoAlpha;
	
	@Column(name="employee_band_order")
	private String employeeBandOrder;	
	
	@Column(name = "employer_id")
	private long employerId;
	
	@Column(name="status")
	private long status;	
	
}
