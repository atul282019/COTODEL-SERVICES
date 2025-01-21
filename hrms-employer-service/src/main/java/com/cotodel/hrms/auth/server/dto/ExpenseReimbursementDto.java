package com.cotodel.hrms.auth.server.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseReimbursementDto implements Serializable{
	
	private Long id;
	private String sequenceId;
	private String expenseCategory;
	private String name;
	private String depratment;
	private String amount;
	private String currency;
	private LocalDate createationDate;
	private String modeOfPayment;
	private String expenseTitle;
	private long status;
	private String statusMessage;
	private String approvedBy;
	private String fileType;
	private String invoiceNumber;
	private String vendorName;
	private String remarks;
	private byte[] file;
	
	
}
