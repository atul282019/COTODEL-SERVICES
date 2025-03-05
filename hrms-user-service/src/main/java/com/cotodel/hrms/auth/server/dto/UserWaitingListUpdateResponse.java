package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWaitingListUpdateResponse {
	
	  private boolean status;
	  private String message;
	  UserWaitingListUpdateRequest data;
	  private String txnId;
	  private String timestamp;
	  private String authToken;
}
