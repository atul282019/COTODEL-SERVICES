package com.cotodel.hrms.auth.server.dto;

import com.cotodel.hrms.auth.server.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpResponse {
	
	  private boolean status;
	  UserEntity userEntity;
	  private String txnId;
	  private String timestamp;
	  private String authToken;
}
