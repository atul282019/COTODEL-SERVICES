package com.cotodel.hrms.auth.server.dto;

import java.time.LocalDate;
import com.cotodel.hrms.auth.server.model.EntryModeMasterEntity;
import com.cotodel.hrms.auth.server.model.VoucherTypeMasterEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherInitiateDetailsRequest {
	
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
	private EntryModeMasterEntity entrymodeIdPk;//id_pk of vouchermaster		
	private String response;
}
