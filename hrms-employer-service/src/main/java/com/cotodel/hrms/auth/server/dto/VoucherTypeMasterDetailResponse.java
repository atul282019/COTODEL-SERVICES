package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import com.cotodel.hrms.auth.server.model.VoucherTypeMasterEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherTypeMasterDetailResponse {
	
	 private boolean status;
	 private String message;
	 VoucherTypeMasterEntity data;
	 private String txnId;
	 private String timestamp;
}
