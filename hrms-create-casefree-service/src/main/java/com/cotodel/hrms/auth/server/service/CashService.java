package com.cotodel.hrms.auth.server.service;

import com.cotodel.hrms.auth.server.dto.OrderIdResponse;
import com.cotodel.hrms.auth.server.dto.OrderUserRequest;

public interface CashService {

	public OrderUserRequest  callOrderApi(OrderUserRequest orderUserRequest);
	public OrderIdResponse  callOrderIdApi(OrderUserRequest orderUserRequest);

	
}
