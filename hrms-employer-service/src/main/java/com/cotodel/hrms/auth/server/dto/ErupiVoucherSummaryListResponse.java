package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherSummaryListResponse {
	
	 private boolean status;
	 private String message;
	 private Long totalCount;
	 private Long totalAmount; 
	 List<ErupiVoucherSummaryDto> data;
	 private String txnId;
	 private String timestamp;
}
