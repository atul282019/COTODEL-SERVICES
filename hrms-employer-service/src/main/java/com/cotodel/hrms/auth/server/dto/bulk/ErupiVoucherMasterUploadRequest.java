package com.cotodel.hrms.auth.server.dto.bulk;

import javax.validation.constraints.NotNull;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
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
