package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
	private String cart_details;
	private String cf_order_id;
	private String created_at;
	private CustomerDetails customer_details;
	private String entity;
	private Float order_amount;	
	private String order_currency;
	private String order_expiry_time;
	private String order_id;
	private OrderMeta order_meta;
	private String order_note;
	private String order_splits[];
	private String order_status;
	private String order_tags;
	private String payment_session_id;
	private String terminal_data;
}
