package com.cotodel.hrms.auth.server.dto;

import java.time.LocalDateTime;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;
import com.cotodel.hrms.auth.server.util.AccountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkMultipleAccountRequest {
	
	private Long id;	
	private Long linkId;	
	private String bankCode;//FK	
	private String bankName;	
	private String accountHolderName;	
	private String acNumber;
	private AccountType accountType;//Saving, Current	
	private String ifscCode;	
	private String createdby;		
	private Long orgId;	
	private String mobile;	
	private Float amountLimit;	
	private Float balance;	
	private String approvedby;	
	private String rejectedby;
	private String response;
	private String statusMessage;
	private LocalDateTime creationDate;
	private String orderId;
}
