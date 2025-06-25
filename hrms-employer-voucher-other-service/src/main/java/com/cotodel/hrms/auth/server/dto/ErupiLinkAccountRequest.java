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
public class ErupiLinkAccountRequest {
	private Long id;
	//private ErupiBankMasterEntity bank;	
	private String bankCode;//FK	
	private String bankName;	
	private String accountHolderName;		
	private String acNumber;	
	private String conirmAccNumber;	
	private AccountType accountType;//Saving, Current	
	private String ifsc;	
	private String erupiFlag;//Y for enabled,N for Not enabled Default is null	
	private String createdby;	
	private LocalDateTime updateDate;	
	private String updatedby;		
	private String branchCode;	
	private Long orgId;	
	private String orgCode;
	private String employeCode;			
	private String authStatus;	//Y for Yes default value is null	
	private String authResponse;	
	private String mobile;	
	private Long accstatus;//Flag value 1 for Active and 0 for inactive Default is null
	private String tid;		
	private String merchentIid;	
	private String mcc;
	private String submurchentid;
	private String payerva;
	private String extra1;
	private String psFlag;
	private byte[] bankLogo;
	private String response;
	private String clientKey;
	private String hash; 
}

