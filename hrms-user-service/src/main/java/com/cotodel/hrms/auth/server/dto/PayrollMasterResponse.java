package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.PayrollMasterEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayrollMasterResponse {
	
	  private boolean status;
	  List<PayrollMasterEntity> data;
	  private String txnId;
	  private String timestamp;
}
