package com.cotodel.hrms.auth.server.dto;

import com.cotodel.hrms.auth.server.entity.EmployerDetailsEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployerDetailsByEmpIdResponse {
	
	  private boolean status;
	  private String message;
	  EmployerDetailsEntity data;
	  private String txnId;
	  private String timestamp;
	  private String authToken;
}
