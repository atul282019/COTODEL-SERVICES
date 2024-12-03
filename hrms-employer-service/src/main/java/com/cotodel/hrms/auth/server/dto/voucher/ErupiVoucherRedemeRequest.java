package com.cotodel.hrms.auth.server.dto.voucher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherRedemeRequest {
	private String merchantId;
	private String subMerchantId;	
	private String terminalId;	
	private String BankRRN;	
	private String merchantTranId;	
	private String PayerName;
	private String PayerMobile;	
	private String PayerVA;
	private String PayerAmount;	
	private String TxnStatus;
	private String responseCode;
	private String TxnInitDate;
	private String TxnCompletionDate;
	private String UMN;
	private String PayeeVPA;
	private String PayeeName;
	private String RespCodeDescription;
}
