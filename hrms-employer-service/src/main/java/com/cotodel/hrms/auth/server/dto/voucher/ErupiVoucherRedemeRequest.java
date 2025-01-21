package com.cotodel.hrms.auth.server.dto.voucher;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherRedemeRequest {
	private String merchantId;
	private String subMerchantId;	
	private String terminalId;	
	private String bankRRN;	
	private String merchantTranId;	
	private String payerName;
	private String payerMobile;	
	private String payerVA;
	private String payerAmount;	
	private String txnStatus;
	private String responseCode;
	private String txnInitDate;
	private String txnCompletionDate;
	private String umn;
	private String payeeName;
}
