package com.cotodel.hrms.auth.server.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cotodel.hrms.auth.server.model.VoucherTypeMasterEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherCreateDetailsRequest {
	
	private VoucherTypeMasterEntity voucherId;//id_pk of vouchermaster	
	private String name;	
	private String mobile;			
	private Float amount;	
    private LocalDate startDate;			
    private LocalDate expDate;	
    private String purposeCode;	
    private String consent;	
    private String otpValidationStatus;		 	
    private LocalDate creationDate;
	private String createdby;	
	private Long accountId;
	@NotNull(message = "Organization cannot be null")
    @Min(value = 1, message = "Organization ID must be greater than or equal to 1")
	private Long orgId;	
	private String accountNumber;			
	//private EntryModeMasterEntity entrymodeIdPk;//id_pk of vouchermaster		discuss
	private String response;
	private String responseApi;
	private String merchanttxnid;
	private String creationmode;	
	private Long bulktblId;
	private String redemtionType;	
	private String mcc;	
	private String extra1;	
	private String extra2;	
	private String extra3;
	private String beneficiaryID;
	private String payerVA;
	private String type;
	@NotBlank(message = "bankCode cannot be blank")
	private String bankcode;
	private String voucherCode;
	private String voucherType;
	private String voucherDesc;
	//private String createResponse;
	private String merchantId;
	private String subMerchantId;
	private String MandateType;
	private String PayeeVPA;
}
