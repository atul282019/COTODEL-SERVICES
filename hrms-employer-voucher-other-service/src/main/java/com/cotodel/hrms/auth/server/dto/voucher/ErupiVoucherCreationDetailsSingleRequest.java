package com.cotodel.hrms.auth.server.dto.voucher;

import java.util.List;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherCreationDetailsSingleRequest {

	private String merchantId;
	private String subMerchantId;
	private String mandateType;
	private String clientKey;
	private String hash;
	private String activeStatus;
	private String payeeVPA;
	private String consent;	
    private String otpValidationStatus;		 	
    private String creationDate;
	private String createdby;	
	private Long accountId;
	private Long orgId;	
	private String accountNumber;
	private String bankcode;
	private String response;
	private String responseApi;
	private String merchanttxnid;
	private String creationmode;
	private String payerVA;
	private List<ErupiVoucherCreationSubSingleRequest> erupiVoucherCreateDetails;
	
}
