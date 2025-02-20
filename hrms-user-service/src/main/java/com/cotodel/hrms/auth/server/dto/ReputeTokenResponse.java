package com.cotodel.hrms.auth.server.dto;

import com.cotodel.hrms.auth.server.entity.ReputeTokenEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReputeTokenResponse {
	
	  private boolean status;
	  private String message;
	  ReputeTokenEntity data;
	  private String txnId;
	  private String timestamp;
	  private String authToken;
}
