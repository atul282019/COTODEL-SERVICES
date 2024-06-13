package com.cotodel.hrms.auth.server.dto;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.cotodel.hrms.auth.server.model.CategoryEmployeeBandEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
	private String modeOfPayment;	
	private String remarks;
	private String response;

}
