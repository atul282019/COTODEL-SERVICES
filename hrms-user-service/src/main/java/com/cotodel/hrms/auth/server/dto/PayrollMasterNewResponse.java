package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.PayrollMasterEntity;
import com.cotodel.hrms.auth.server.entity.PayrollMasterNewEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayrollMasterNewResponse {
	
	  private boolean status;
	  List<PayrollMasterNewEntity> data;
	  private String txnId;
	  private String timestamp;
}
