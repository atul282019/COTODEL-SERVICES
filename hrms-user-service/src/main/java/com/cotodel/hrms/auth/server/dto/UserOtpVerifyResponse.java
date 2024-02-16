package com.cotodel.hrms.auth.server.dto;


import com.cotodel.hrms.auth.server.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOtpVerifyResponse {
	
	  private boolean status;
	  private String message;
	  private String txnId;
	  private String timestamp;
	  private UserEntity data;
}
