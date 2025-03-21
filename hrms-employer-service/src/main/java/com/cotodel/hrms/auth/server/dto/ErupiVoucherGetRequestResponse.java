package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import com.cotodel.hrms.auth.server.model.ErupiVoucherCreationRequestEntity;

import java.util.List;

import com.cotodel.hrms.auth.server.model.ErupiVoucherCreationRequestEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherGetRequestResponse {
	
	 private boolean status;
	 private String message;
	 List<ErupiVoucherCreationRequestEntity> data;
	 private String txnId;
	 private String timestamp;
}
