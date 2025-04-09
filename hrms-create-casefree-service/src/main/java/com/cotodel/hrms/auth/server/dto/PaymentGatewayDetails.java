package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentGatewayDetails {
	private String gateway_name;	
	private String gateway_order_id;
	private String gateway_payment_id;
	private String gateway_order_reference_id;    
}
