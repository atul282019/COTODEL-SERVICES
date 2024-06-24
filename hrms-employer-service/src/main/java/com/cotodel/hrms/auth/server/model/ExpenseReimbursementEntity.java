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
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="expense_reimbursement")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="expense_reimbursement_seq" , sequenceName="expense_reimbursement_seq", allocationSize=1)

public class ExpenseReimbursementEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="expense_reimbursement_seq")	
	@Column(name="id")
	private Long id;	
	
	@Column(name = "employer_id")
	private long employerId;
	
	@Column(name = "employee_id")
	private long employeeId;
	
	@Column(name="status")
	private long status;
	
	@Column(name="expense_category")
	private String expenseCategory;
	
	@Lob
	@Type(type="org.hibernate.type.BinaryType")
    private byte[] file;
	
	@Column(name="file_name")
	private String fileName;
	
	@Column(name="file_type")
	private String fileType;
	
	private LocalDate  created_date ;
	
	@Column(name="date_of_expense")
	private String dateOfExpense;
	
	@Column(name="expense_title")
	private String expenseTitle;
	
	@Column(name="vendor_name")
	private String vendorName;
	
	@Column(name="invoice_number")
	private String invoiceNumber;
	 
	@Column(name="currency")
	private String currency;
	
	@Column(name="amount")
	private String amount;	
	
	@Column(name="mode_of_payment")
	private String modeOfPayment;
	
	@Column(name="remarks")
	private String remarks;
		
}
