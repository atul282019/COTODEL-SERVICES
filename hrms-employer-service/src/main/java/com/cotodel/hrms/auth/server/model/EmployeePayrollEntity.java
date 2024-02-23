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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="employee_payroll")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="employee_payroll_seq" , sequenceName="employee_payroll_seq", allocationSize=1)
public class EmployeePayrollEntity  implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="employee_payroll_seq")
	@Column(name="id")
	private Long id;

	@OneToOne
    @JoinColumn(name = "employer_id")
    private EmployerEntity employer;
	@Column(name="salary_component_basic")
	private String salaryComponentBasic;
	@Column(name="per_ctc_basic")
    private String perCtcBasic;
	@Column(name="per_basic")
    private String perBasic ;
	@Column(name="taxable_basic")
    private String taxableBasic;
	@Column(name="salary_component_hra")
    private String salaryComponentHra;
	@Column(name="per_ctc_hra")
    private String perCtcHra;
	@Column(name="per_hra")
    private String perHra ;
	@Column(name="taxable_hra")
    private String taxableHra;  
	@Column(name="salary_component_special")
    private String salaryComponentSpecial;
	@Column(name="per_ctc_special")
    private String perCtcSpecial;
	@Column(name="per_special")
    private String perSpecial ;
	@Column(name="taxable_special")
    private String taxableSpecial;
	@Column(name="salary_component_lta")
    private String salaryComponentLta;
	@Column(name="per_ctc_lta")
    private String perCtcLta;
	@Column(name="per_lta")
    private String perLta ;
	@Column(name="taxable_lta")
    private String taxableLta;
    private Integer status; 
}
