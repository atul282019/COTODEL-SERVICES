package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.CashFreeOrderHistory;
import com.cotodel.hrms.auth.server.dto.CurrentMonthLimitResponse;
import com.cotodel.hrms.auth.server.dto.OrderIdResponse;
import com.cotodel.hrms.auth.server.dto.OrderUserRequest;
import com.cotodel.hrms.auth.server.dto.OrderUserUpdateRequest;

public interface CashService {

	public OrderUserRequest  callOrderApi(OrderUserRequest orderUserRequest);
	public OrderIdResponse  callOrderIdApi(OrderUserRequest orderUserRequest);
	public OrderIdResponse  callOrderIdApiView(OrderUserRequest orderUserRequest);
	public OrderUserUpdateRequest  callOrderIdApiUpdate(OrderUserUpdateRequest orderUserUpdateRequest);
	public List<CashFreeOrderHistory>  callOrderIdApiList(OrderUserRequest orderUserRequest);
	public CurrentMonthLimitResponse  cashFreeCurrentMonthAmount(OrderUserRequest orderUserRequest);
}
