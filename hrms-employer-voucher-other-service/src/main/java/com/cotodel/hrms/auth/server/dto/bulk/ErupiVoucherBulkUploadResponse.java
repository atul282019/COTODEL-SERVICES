package com.cotodel.hrms.auth.server.dto.bulk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherBulkUploadResponse {
	private boolean status;
	 private String message;
	 ErupiVoucherBulkUploadSFListResponse data;
	 private String txnId;
	 private String timestamp;
	
}
