package com.cotodel.hrms.auth.server.entity;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cash_free_order")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="cash_free_order_seq" , sequenceName="cash_free_order_seq", allocationSize=1)
public class CashFreeOrderEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="cash_free_order_seq")
	@Column(name="id")
	private Long id;
	
	
	@Column(name="order_amount")
	private Float orderAmount;
	
	@Column(name="order_currency")
	private String orderCurrency;
	
	@Column(name="customer_id")
	private String customerId;
	
	@Column(name="customer_name")
	private String customerName;
	
	@Column(name="customer_email")
	private String customerEmail;
	
	@Column(name="customer_phone")
	private String customerPhone;
	
	@Column(name="org_id")
	private Long orgId;
	
	@Column(name="cart_details")
	private String cartDetails;
	
	@Column(name="cf_order_id")
	private String cfOrderId;
	
	@Column(name="created_at")
	private String createdAt;
	
	@Column(name="customer_uid")
	private String customerUid;
	
	@Column(name="entity")
	private String entity;
	
	@Column(name="order_expiry_time")
	private String orderExpiryTime;
	
	@Column(name="order_id")
	private String orderId;
	
	@Column(name="return_url")
	private String returnUrl;
	
	@Column(name="notify_url")
	private String notifyUrl;
	
	@Column(name="payment_methods")
	private String paymentMethods;
	
	@Column(name="order_note")
	private String orderNote;
	
	@Column(name="order_splits")
	private String[] orderSplits;
	
	@Column(name="order_status")
	private String orderStatus;
	
	@Column(name="order_tags")
	private String orderTags;
	
	@Column(name="payment_session_id")
	private String paymentSessionId;
	
	@Column(name="terminal_data")
	private String terminalData;
	
	@Column(name="status")
	private int status;
	
	@Column(name="bankcode", length=99)
	private String bankCode;//FK
	
	@Column(name="bankname", length=99)
	private String bankName;
		
	@Column(name="acnumber", length=18)
	private String acNumber;
	
	@Column(name="created_by")
	private String createdBy;
	
}
