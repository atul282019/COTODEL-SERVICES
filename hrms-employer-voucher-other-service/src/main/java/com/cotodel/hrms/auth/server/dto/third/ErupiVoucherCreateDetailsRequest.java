package com.cotodel.hrms.auth.server.dto.third;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cotodel.hrms.auth.server.model.master.MccMasterEntity;
import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherCreateDetailsRequest {
	
	private String merchantTranId;
	private String subMerchantId;
	private String beneficiaryId;
	private String mobileNumber;
	private String beneficiaryName;
	private String amount;
	private String expiry;
	//"merchantTranId": "55765765765757", clientcode(4 digit)+10digit unique
	//"subMerchantId": "400899", 
	//"beneficiaryID": "8978653452", 
	//"mobileNumber": "9703507610", 
	//"beneficiaryName": "NAmeOfBeneficiary", 
	//"amount": "202", 
	//"expiry": "2022-06-17", 
	
	
	
	/*
	 * "purposeCode": "A3", "mcc": "5411", "VoucherRedemptionType": "SINGLE",
	 * "PayerVA": "merchant@icici", "type": "CREATE" “Clintname”:”nayara”
	 * “Clintcode”:”1234” “secretKey”:”123469666785665”
	 */

	private MccMasterEntity voucherId;
	private String name;	
	private String mobile;			
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
	private String merchantId;
	private String mandateType;
	private String payeeVPA;
	private String validity;
	private Long requestId;
	private String clientKey;
	private String hash;
	
	 
}
