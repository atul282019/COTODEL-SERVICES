package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.RoleMaster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleMasterResponse {
	
	  private boolean status;
	  private String message;
	  List<RoleMaster> data;
	  private String timestamp;
}
