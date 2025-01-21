package com.cotodel.hrms.auth.server.dto.bulk;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherBulkUploadRequest {
	@NotNull(message = "Organization cannot be null")
    @Min(value = 1, message = "Organization ID must be greater than or equal to 1")
	private Long orgId;
	@NotNull(message = "file cannot be null")
	private byte[] file;
	private String fileName;
	private String response;	
	private String purposeCode;	
	private Long accountId;
	@NotNull(message = "mcc cannot be null")
	private String mcc;
	@NotNull(message = "Beneficiary id cannot be null")
	private String beneficiaryID;
	@NotNull(message = "payer va cannot be null")
	private String payerVA;
	@NotNull(message = "type cannot be null")
	private String type;
	@NotNull(message = "bankcode cannot be null")
	private String bankcode;
	private String voucherCode;
	private String voucherDesc;
	@NotNull(message = "merchant id cannot be null")
	private String merchantId;
	@NotNull(message = "sub merchant id cannot be null")
	private String subMerchantId;
	private String createdby;	
	private Long voucherId;
	private String MandateType;
	private String PayeeVPA;
}
