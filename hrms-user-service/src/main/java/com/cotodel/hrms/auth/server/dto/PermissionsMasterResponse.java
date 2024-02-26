package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.PermissionsMaster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionsMasterResponse {
	
	  private boolean status;
	  private String message;
	  List<PermissionsMaster> data;
	  private String timestamp;
}
