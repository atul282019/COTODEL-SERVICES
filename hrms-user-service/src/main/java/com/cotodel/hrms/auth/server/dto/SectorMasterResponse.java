package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.SectorMaster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectorMasterResponse {
	
	  private boolean status;
	  List<SectorMaster> data;
	  private String txnId;
	  private String timestamp;
}
