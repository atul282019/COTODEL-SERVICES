package com.cotodel.hrms.auth.server.dto;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashFreeOrderHistory {

	private Long id;	
	private Float orderAmount;	
	private String orderCurrency;	
	private String customerId;	
	private String customerName;	
	private String customerEmail;	
	private String customerPhone;	
	private String orderId;
	private String orderStatus;
	private String cfPaymentId;	
	private String paymentStatus;	
	private Float paymentAmount;	
	private String paymentCurrency;	
	private String paymentMessage;	
	private String paymentTime;	
	private String bankReference;	
	private String paymentGroup;	
	private String errorCode;	
	private String errorDescription;	
	private String errorReason;	
	private String gatewayName;	
	private String gatewayOrderId;	
	private String gatewayPaymentId;	
	private String gatewayOrderReferenceId;	
	private String eventTime;	
	private String type;	
	private String serviceCharge;	
	private String serviceTax;	
	private String settlementAmount;	
	private String settlementCurrency;	
	private String serviceChargeDiscount;
}
