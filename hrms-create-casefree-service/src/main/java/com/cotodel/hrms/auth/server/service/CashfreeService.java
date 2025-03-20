package com.cotodel.hrms.auth.server.service;

import org.springframework.stereotype.Service;

import com.cashfree.pg.ApiException;
import com.cashfree.pg.ApiResponse;
import com.cashfree.pg.Cashfree;
import com.cashfree.pg.model.CreateOrderRequest;
import com.cashfree.pg.model.CustomerDetails;
import com.cashfree.pg.model.OrderEntity;

@Service
public class CashfreeService {
	
//	static void createOrder() {
//		  CustomerDetails customerDetails = new CustomerDetails();
//		  customerDetails.setCustomerId("123");
//		  customerDetails.setCustomerPhone("9999999999");
//
//		  CreateOrderRequest request = new CreateOrderRequest();
//		  request.setOrderAmount(1.0);
//		  request.setOrderCurrency("INR");
//		  request.setCustomerDetails(customerDetails);
//		  try {
//		    Cashfree cashfree = new Cashfree(null, null, null, null, null, null);
//		    ApiResponse<OrderEntity> response = cashfree.PGCreateOrder(request, null, null, null);
//		    System.out.println(response.getData().getOrderId());
//
//		  } catch (ApiException e) {
//		    throw new RuntimeException(e);
//		  }
//	}
//	public static void main(String[] args) {
//		createOrder();
//	}
}
