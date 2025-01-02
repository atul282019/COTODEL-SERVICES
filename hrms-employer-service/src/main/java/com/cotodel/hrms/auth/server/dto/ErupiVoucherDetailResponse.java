package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherDetailResponse {
	
	 private boolean status;
	 private String message;	
	 ErupiVoucherTotalDetailDto issueDetail;
	 private String txnId;
	 private String timestamp;
}
