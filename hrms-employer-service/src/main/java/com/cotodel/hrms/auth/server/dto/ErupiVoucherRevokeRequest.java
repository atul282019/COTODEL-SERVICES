package com.cotodel.hrms.auth.server.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import com.cotodel.hrms.auth.server.model.EntryModeMasterEntity;
import com.cotodel.hrms.auth.server.model.VoucherTypeMasterEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherRevokeRequest {
	
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
	private Long orgId;	
	private String accountNumber;			
	//private EntryModeMasterEntity entrymodeIdPk;//id_pk of vouchermaster		discuss
	private String response;	
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
	//private String createResponse;
}
