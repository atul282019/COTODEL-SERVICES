package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.IndustryMaster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndustryMasterResponse {
	
	  private boolean status;
	  List<IndustryMaster> data;
	  private String txnId;
	  private String timestamp;
}
