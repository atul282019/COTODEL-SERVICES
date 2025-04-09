package com.cotodel.hrms.auth.server.dto;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderIdResponse {
	private String orderAmount;
	private String orderCurrency;
	private String customerId;
	private String customerName;
	private String customerEmail;
	private String customerPhone;
	private Long orgId;
	private String payment_session_id;
	private String orderId;	
	private String customer_uid;
	private String cart_details;
	private String cf_order_id;
	private String created_at;
	private String entity;
	private String order_expiry_time;
	private String return_url;
	private String notify_url;
	private String payment_methods;
	private String order_note;
	private String order_splits[];
	private String order_status;
	private String order_tags;
	private String terminal_data;
	private String serviceCharge;	
	private String serviceTax;	
	private String settlementAmount;
}
