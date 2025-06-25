package com.cotodel.hrms.auth.server.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="expense_reimbursement",uniqueConstraints = @UniqueConstraint(columnNames = {"invoice_number"}),
indexes = {@Index(name = "idx_employee_id", columnList = "employee_id")})
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="expense_reimbursement_seq" , sequenceName="expense_reimbursement_seq", allocationSize=1)

public class ExpenseReimbursementEntity  implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="expense_reimbursement_seq")	
	@Column(name="id")
	private Long id;	
	
	@Column(name = "sequence_id")
	private String sequenceId;
	
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
	
	private LocalDateTime  created_date ;
	
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
	
	@Column(name="approved_amount")
	private String approvedAmount;
	
	@Column(name="mode_of_payment")
	private String modeOfPayment;
	
	@Column(name="remarks")
	private String remarks;
	
	@Transient
	private String statusMessage;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="updated_date")
	private LocalDate updatedDate;
	
	@Column(name="updated_by")
	private String updatedBy;
	
	@Column(name="approved_date")
	private LocalDate approvedDate;
	
	@Column(name="approved_by")
	private String approvedBy;
	
	@Column(name="rejected_remarks")
	private String rejectedRemarks;
	
	@Column(name="workflowid")
	private Long workFlowId;
}
