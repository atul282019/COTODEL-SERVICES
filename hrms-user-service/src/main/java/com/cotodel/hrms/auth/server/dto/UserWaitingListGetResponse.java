package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.UserWaitingListEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWaitingListGetResponse {
	
	  private boolean status;
	  private String message;
	  List<UserWaitingListEntity> data;
	  private String txnId;
	  private String timestamp;
	  private String authToken;
}
