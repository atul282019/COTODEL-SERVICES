package com.cotodel.hrms.auth.server.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBulkUploadRequest {
	@NotNull(message = "Organization cannot be null")
    @Min(value = 1, message = "Organization ID must be greater than or equal to 1")
	private Long orgId;
	@NotNull(message = "file cannot be null")
	private byte[] file;
	private String fileName;
	private String createdby;	
	private String response;
}
