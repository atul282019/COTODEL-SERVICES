package com.cotodel.hrms.auth.server.dto;

import java.time.LocalDateTime;

import com.cotodel.hrms.auth.server.model.VoucherTypeMasterEntity;
import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherTxnRequest {
		
	private Long detailsId;//bigint	id_pk of issuancedetails	
	private Long workFlowId;//bigint FK	Work FlowID 100001 for initiate reuest, 100002 for creation,100003 for fail,100004 for confirmationpending, 100005 for redemption
	private VoucherTypeMasterEntity voucherId;//id_pk of vouchermaster		
	private String responseCode;	
	private Long accountId;	
	private Long orgIdPk;
	private String response;
	private String responseMsg;
	private String type;
	private String voucherType;	
	private String uuid;
	private String umn;	
	private String merchanttxnId;	
	private String resultCallApi;	
	private String statusApi;	
	private String smsResponse;	
	private String smsActcode;	
	private String smsDesc;	     
	private String apiType;	
	private String createdBy;	
	private String mcc;	
	private String bankrrn;	
	private String txnstatus;	
	private LocalDateTime txninitdate;	
	private LocalDateTime txncompletiondate;	
	private String extra;	
	private String extra1;	
	private String extra2;	
	
}
