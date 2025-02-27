package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchListResponse {
	
	  private boolean status;
	  private String message;
	  List<UserEntity> data;
	  private String txnId;
	  private String timestamp;
	  
	  
}
