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
@Table(name="payroll")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="payroll_seq" , sequenceName="payroll_seq", allocationSize=1)
public class PayrollEntity  implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="payroll_seq")
	@Column(name="payroll_id")
	private Long payrollId;
	
//	@Column(name="employee_id")
//	private Long employeeId;
	
	@OneToOne
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;
	
//	@Column(name="employer_id")
//	private Long employerId;
	
	@OneToOne
    @JoinColumn(name = "employer_id")
    private EmployerEntity employer;
	
	@Column(name="salary")
	private Long salary;
	
	private Long bonus;
	
	private Long detuction;
	
	@Column(name="net_salary")
	private Long netSalary;
	
	@CreationTimestamp
	@Column(name="payroll_date")
	private Date payrollDate;
	

}
