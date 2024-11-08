package com.cotodel.hrms.auth.server.dto;

import java.time.LocalDateTime;

import com.cotodel.hrms.auth.server.model.ErupiBankMasterEntity;
import com.cotodel.hrms.auth.server.util.AccountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiLinkAccountRequest {
	private Long id;
	private ErupiBankMasterEntity bank;	
	private String bankCode;//FK	
	private String bankName;	
	private String accountHolderName;		
	private String acNumber;	
	private String conirmAccNumber;	
	private AccountType accountType;//Saving, Current	
	private String ifsc;	
	private String erupiFlag;//Y for enabled,N for Not enabled Default is null	
	private LocalDateTime creationDate;	
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
	//Remarks:big int default value value is 0
	private String tid;		
	private String merchentIid;	
	private String mcc;
	private String submurchentid;
	private String payerva;
	private String extra1;
	private String psFlag;
	private String response;
}
