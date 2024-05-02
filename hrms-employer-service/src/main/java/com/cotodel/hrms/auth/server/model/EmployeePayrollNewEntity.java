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
@Table(name="employee_payroll_new")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="employee_payroll_new_seq" , sequenceName="employee_payroll_new_seq", allocationSize=1)
public class EmployeePayrollNewEntity  implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="employee_payroll_new_seq")
	@Column(name="id")
	private Long id;

	@Column(name = "employer_id")
    private Long employerId;
	
	@Column(name = "employee_id")
    private Long employeeId;
	
	private String salary_component;
	
    private String per_ctc;
    
    private String per ;
    
    private String  taxable;
    
    private Integer status;
			
}
