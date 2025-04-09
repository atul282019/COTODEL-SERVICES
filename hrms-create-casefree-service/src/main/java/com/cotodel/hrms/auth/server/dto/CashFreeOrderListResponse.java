package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.CashFreeOrderWebHookEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashFreeOrderListResponse {
	
	 private boolean status;
	 private String message;
	 private List<CashFreeOrderWebHookEntity> data;
	 private String timestamp;
}
