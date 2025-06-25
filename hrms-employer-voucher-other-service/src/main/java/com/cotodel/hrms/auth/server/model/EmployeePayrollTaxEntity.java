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
@Table(name="employee_payroll_tax")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="employee_payroll_tax_seq" , sequenceName="employee_payroll_tax_seq", allocationSize=1)
public class EmployeePayrollTaxEntity  implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="employee_payroll_tax_seq")
	@Column(name="id")
	private Long id;

	@Column(name = "employer_id")
    private Long employerId;
	@Column(name = "employee_id")
    private Long employeeId;
	
	@Column(name = "tax_code")
    private String taxCode;
	
	@Column(name = "tax_name")
    private String taxName;
	
	@Column(name = "tax_description")
    private String taxDescription;
	
	private String authoriser;
	
	@Column(name = "sl_no_type")
    private String slNoType;
	
	@Column(name = "deduction_time")
    private String deductionTime;
	
	@Column(name = "tax_rate")
    private String taxRate;
	
	@Column(name = "tax_pan_acc")
    private String taxPanAcc;
	
	@Column(name = "minimum_amount")
    private int minimumAmount;
	
	@Column(name = "tax_ref")
    private String taxRef;
	
	@Column(name = "adj_voucher_type")
    private String adjVoucherType;
	
	@Column(name = "supervisior")
    private String supervisior;
	
	@Column(name = "debit_account")
    private String debitAccount;
	
	@Column(name = "deposit_frequency")
    private String depositFrequency;
	
	@Column(name = "credit_account")
    private String creditAccount;
	
	@Column(name = "remarks")
    private String remarks;
	
	}
