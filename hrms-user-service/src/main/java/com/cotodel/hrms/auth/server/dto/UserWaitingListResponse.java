package com.cotodel.hrms.auth.server.dto;

import com.cotodel.hrms.auth.server.entity.UserEntity;
import com.cotodel.hrms.auth.server.entity.UserWaitingListEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWaitingListResponse {
	
	  private boolean status;
	  private String message;
	  UserWaitingListEntity data;
	  private String txnId;
	  private String timestamp;
	  private String authToken;
}
