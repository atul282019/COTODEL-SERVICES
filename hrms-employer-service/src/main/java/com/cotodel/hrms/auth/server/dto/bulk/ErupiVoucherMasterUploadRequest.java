package com.cotodel.hrms.auth.server.dto.bulk;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherMasterUploadRequest {
	
	@NotNull(message = "file cannot be null")
	private byte[] file;
	private String fileName;	
	private String createdby;	
	private Long voucherId;
	private String MandateType;
	private String PayeeVPA;
}
