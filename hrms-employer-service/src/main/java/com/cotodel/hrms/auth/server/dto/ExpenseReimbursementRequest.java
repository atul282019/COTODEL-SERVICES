package com.cotodel.hrms.auth.server.dto;

import java.time.LocalDate;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseReimbursementRequest {
	private Long id;
	private byte[] file;
	private long employeeId;
	private long employerId;	
	private String expenseCategory;	
	private String fileName;	
	private String fileType;	
	private LocalDate  created_date ;	
	private String dateOfExpense;	
	private String expenseTitle;	
	private String vendorName;	
	private String invoiceNumber;	 
	private String currency;
	private String amount;
	private String approvedAmount;
	private String modeOfPayment;	
	private String remarks;
	private String response;
	private String updatedBy;
	private String username;
	private String approvedOrRejected;
	private String rejectedRemarks;
	private String fileInput;
}
