package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherTxnRequest {
		
	private Long detailsId;//bigint	id_pk of issuancedetails	
	private Long workFlowId;//bigint FK	Work FlowID 100001 for initiate reuest, 100002 for creation,100003 for fail,100004 for confirmationpending, 100005 for redemption
	//private VoucherTypeMasterEntity voucherId;//id_pk of vouchermaster		
	private String responseCode;	
	private Long accountId;	
	private Long orgIdPk;
	private String response;
	

}




