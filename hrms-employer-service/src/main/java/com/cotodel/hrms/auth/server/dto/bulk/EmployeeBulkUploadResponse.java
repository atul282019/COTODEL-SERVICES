package com.cotodel.hrms.auth.server.dto.bulk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeBulkUploadResponse {
	private boolean status;
	 private String message;
	 EmployeeBulkUploadSFListResponse data;
	 private String txnId;
	 private String timestamp;
	
}
