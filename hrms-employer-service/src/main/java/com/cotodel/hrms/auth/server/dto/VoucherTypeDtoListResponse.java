package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import com.cotodel.hrms.auth.server.model.VoucherTypeMasterEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherTypeDtoListResponse {
	
	 private boolean status;
	 private String message;
	 List<VoucherTypeDto> data;
	 private String txnId;
	 private String timestamp;
}
