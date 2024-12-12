package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.RoleMasterEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleMasterNewResponse {
	
	  private boolean status;
	  private String message;
	  List<RoleMasterEntity> data;
	  private String timestamp;
}
