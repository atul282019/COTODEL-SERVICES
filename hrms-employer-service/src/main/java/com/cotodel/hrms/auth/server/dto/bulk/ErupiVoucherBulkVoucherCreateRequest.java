package com.cotodel.hrms.auth.server.dto.bulk;

import java.time.LocalDate;

import com.cotodel.hrms.auth.server.model.VoucherTypeMasterEntity;
import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherBulkVoucherCreateRequest {
	
	//List<ErupiBulkIdRequest> data;	
	private String[] arrayofid;
	private String subMerchantId;
	private String merchantId;
	private String mcc;
	private String type;
	private String createdby;	
	private VoucherTypeMasterEntity voucherId;//id_pk of vouchermaster	
	private String redemtionType;
	private String payerVA;
	private String purposeCode;
	private String bankcode;
	private String voucherCode;
	private String voucherType;
	private String voucherDesc;    	 	
    private LocalDate creationDate;	
	private Long accountId;
	private Long orgId;	
	private String accountNumber;			
	//private EntryModeMasterEntity entrymodeIdPk;//id_pk of vouchermaster		discuss
	private String response;
	private String responseApi;
	private String merchanttxnid;
	private String creationmode;	
	private Long bulktblId;
	private String beneficiaryID;
	private String mandateType;
	private String payeeVPA;
	
	
	 
}
