package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataUpdateRequest {
	private Order order;
	private Payment payment;
	private CustomerDetails customer_details;
	private ErrorDetails error_details;
	private PaymentGatewayDetails payment_gateway_details;
	private ChargesDetail charges_details;
}
