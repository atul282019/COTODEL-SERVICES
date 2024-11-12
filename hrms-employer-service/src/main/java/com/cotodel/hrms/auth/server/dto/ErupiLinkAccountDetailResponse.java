package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import com.cotodel.hrms.auth.server.model.ErupiLinkAccountEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiLinkAccountDetailResponse {
	
	 private boolean status;
	 private String message;
	 private ErupiLinkAccountEntity data;
	 private String txnId;
	 private String timestamp;
}
