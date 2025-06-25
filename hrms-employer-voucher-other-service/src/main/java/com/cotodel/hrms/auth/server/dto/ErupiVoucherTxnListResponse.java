package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import com.cotodel.hrms.auth.server.model.ErupiVoucherTxnDetailsEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherTxnListResponse {
	
	 private boolean status;
	 private String message;
	 List<ErupiVoucherTxnDetailsEntity> data;
	 private String txnId;
	 private String timestamp;
}
