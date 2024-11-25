package com.cotodel.hrms.auth.server.dto.bulk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherBulkUploadRequest {
	private Long orgId;	
	private byte[] file;
	private String fileName;
	private String response;
	
}
