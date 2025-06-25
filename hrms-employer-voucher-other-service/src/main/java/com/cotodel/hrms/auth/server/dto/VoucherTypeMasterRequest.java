package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VoucherTypeMasterRequest {
	
	private Long id;
	private String voucherCode;	
	private String voucherType;	
	private String voucherSubType;	
	private String voucherDesc;
	private String purposeCode;
	private String createdby;	
	private String response;
	private byte[] voucherLogo;
	private Long status;
}
