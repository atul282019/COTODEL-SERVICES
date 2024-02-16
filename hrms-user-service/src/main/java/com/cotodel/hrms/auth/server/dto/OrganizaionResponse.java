package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.OrganizationMaster;
import com.cotodel.hrms.auth.server.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizaionResponse {
	
	  private boolean status;
	  List<OrganizationMaster> data;
	  private String txnId;
	  private String timestamp;
}
