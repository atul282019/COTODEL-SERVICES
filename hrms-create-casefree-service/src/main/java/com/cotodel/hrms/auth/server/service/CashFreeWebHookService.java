package com.cotodel.hrms.auth.server.service;

import com.cotodel.hrms.auth.server.dto.OrderUserRequest;

public interface CashFreeWebHookService {

	public OrderUserRequest  saveDetailsLog(OrderUserRequest orderUserRequest);
	
}
