package com.cotodel.hrms.auth.server.dto.bulk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherBulkUploadRequest {
	private Long orgId;	
	private byte[] file;
	private String fileName;
	private String response;
	private String purposeCode;	
	private Long accountId;
	private String mcc;	
	private String beneficiaryID;
	private String payerVA;
	private String type;
	private String bankcode;
	private String voucherCode;
	private String voucherDesc;
	private String merchantId;
	private String subMerchantId;
	private String createdby;
}
