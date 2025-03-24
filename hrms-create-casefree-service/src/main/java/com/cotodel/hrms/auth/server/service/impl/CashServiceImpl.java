package com.cotodel.hrms.auth.server.service.impl;


import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.CashFreeDao;
import com.cotodel.hrms.auth.server.dao.LinkSubMultipleAccountTempDao;
import com.cotodel.hrms.auth.server.dto.OrderIdResponse;
import com.cotodel.hrms.auth.server.dto.OrderResponse;
import com.cotodel.hrms.auth.server.dto.OrderUserRequest;
import com.cotodel.hrms.auth.server.entity.CashFreeOrderEntity;
import com.cotodel.hrms.auth.server.entity.LinkSubAccountMultipleTempEntity;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.CashService;
import com.cotodel.hrms.auth.server.util.AccountType;
import com.cotodel.hrms.auth.server.util.CommonUtility;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.google.gson.Gson;

@Repository
public class CashServiceImpl implements CashService {

	private static final Logger logger = LoggerFactory.getLogger(CashServiceImpl.class);

	@Autowired
	ApplicationConstantConfig applicationConstantConfig;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	CashFreeDao cashFreeDao;
	
	@Autowired
	LinkSubMultipleAccountTempDao linkSubMultipleAccountTempDao;
	
	

	@Override
	public OrderUserRequest callOrderApi(OrderUserRequest orderUserRequest) {
		String message = "";
		OrderResponse orderResponse = null;
		CashFreeOrderEntity cashFreeOrderEntity = new CashFreeOrderEntity();
		CashFreeOrderEntity caEntity = new CashFreeOrderEntity();
		try {

			logger.info("In side callOrderApi:::" + orderUserRequest);

			String customerId = "USER" + getCustomerId();

			orderUserRequest.setCustomerId(customerId);
			CopyUtility.copyProperties(orderUserRequest, cashFreeOrderEntity);
			cashFreeOrderEntity.setStatus(0);
			cashFreeDao.saveDetails(cashFreeOrderEntity);

			message = CommonUtility.userRequest(orderRequest(orderUserRequest),
					applicationConstantConfig.cashFreeClientId, applicationConstantConfig.cashFreeClientSecret,
					applicationConstantConfig.cashFreeOrderUrl);

			logger.info(message);

			if (message != null) {
				orderResponse = message == "" ? null : jsonToPOJO(message);
				caEntity = cashFreeDao.getDetails(orderResponse.getCustomer_details().getCustomer_id());
				caEntity = getCashFree(caEntity, orderResponse);
				cashFreeDao.saveDetails(caEntity);
				orderUserRequest.setPayment_session_id(orderResponse.getPayment_session_id());
			}
			logger.info(orderResponse.getPayment_session_id());
		} catch (Exception e) {
			logger.error("error Exception ...." + e.getMessage());
		}
		return orderUserRequest;
	}

	public long getCustomerId() {
		Query query = entityManager.createNativeQuery("SELECT nextval('customerid')");
		return ((Number) query.getSingleResult()).longValue();
	}

	public OrderResponse jsonToPOJO(String json) {

		Gson gson = new Gson();
		OrderResponse decryptedResponse = new OrderResponse();
		try {
			decryptedResponse = gson.fromJson(json, OrderResponse.class);
		} catch (Exception e) {
			logger.error("error in cashfree ..." + e.getMessage());
		}

		return decryptedResponse;
	}

	private String orderRequest(OrderUserRequest orderUserRequest) {
		JSONObject request = new JSONObject();
		request.put("order_amount", orderUserRequest.getOrderAmount());
		request.put("order_currency", orderUserRequest.getOrderCurrency());
		JSONObject customer = new JSONObject();
		customer.put("customer_id", orderUserRequest.getCustomerId());
		customer.put("customer_name", orderUserRequest.getCustomerName());
		customer.put("customer_email", orderUserRequest.getCustomerEmail());
		customer.put("customer_phone", "+91" + orderUserRequest.getCustomerPhone());
		request.put("customer_details", customer);
		JSONObject url = new JSONObject();
		url.put("return_url", "http://43.205.206.102:8082/cashfreeReturnUrl?order_id={order_id}");
		// url.put("return_url",
		// "http://43.205.206.102:8082/cashfreePaymentFailed?order_id={order_id}");
		url.put("notify_url", "http://cotodel.com/payment");
		request.put("order_meta", url);
		return request.toString();
	}

	private CashFreeOrderEntity getCashFree(CashFreeOrderEntity cashFreeOrderEntity, OrderResponse oredrResponse) {
		cashFreeOrderEntity.setCartDetails(oredrResponse.getCart_details());
		cashFreeOrderEntity.setCfOrderId(oredrResponse.getCf_order_id());
		cashFreeOrderEntity.setCreatedAt(oredrResponse.getCreated_at());
		cashFreeOrderEntity.setCustomerUid(oredrResponse.getCustomer_details().getCustomer_uid());
		cashFreeOrderEntity.setEntity(oredrResponse.getEntity());
		cashFreeOrderEntity.setOrderExpiryTime(oredrResponse.getOrder_expiry_time());
		cashFreeOrderEntity.setOrderId(oredrResponse.getOrder_id());
		cashFreeOrderEntity.setReturnUrl(oredrResponse.getOrder_meta().getReturn_url());
		cashFreeOrderEntity.setNotifyUrl(oredrResponse.getOrder_meta().getNotify_url());
		cashFreeOrderEntity.setPaymentMethods(oredrResponse.getOrder_meta().getPayment_methods());
		cashFreeOrderEntity.setOrderNote(oredrResponse.getOrder_note());
		cashFreeOrderEntity.setOrderSplits(oredrResponse.getOrder_splits());
		cashFreeOrderEntity.setOrderStatus(oredrResponse.getOrder_status());
		cashFreeOrderEntity.setOrderTags(oredrResponse.getOrder_tags());
		cashFreeOrderEntity.setPaymentSessionId(oredrResponse.getPayment_session_id());
		cashFreeOrderEntity.setOrderAmount(oredrResponse.getOrder_amount());
		cashFreeOrderEntity.setTerminalData(oredrResponse.getTerminal_data());
		return cashFreeOrderEntity;
	}

	@Override
	@Transactional
	public OrderIdResponse callOrderIdApi(OrderUserRequest orderUserRequest) {
		String message = "";
		OrderResponse orderResponse = null;
		CashFreeOrderEntity caEntity = new CashFreeOrderEntity();
		OrderIdResponse orderIdResponse = new OrderIdResponse();
		try {

			logger.info("In side callOrderApi:::" + orderUserRequest.getOrderId());

			String orderid = orderUserRequest.getOrderId();

			message = CommonUtility.getTokenRequest(orderid, applicationConstantConfig.cashFreeClientId,
					applicationConstantConfig.cashFreeClientSecret, applicationConstantConfig.cashFreeOrderIdUrl);

			logger.info(message);

			if (message != null) {
				orderResponse = message == "" ? null : jsonToPOJO(message);
				caEntity = cashFreeDao.getDetails(orderResponse.getCustomer_details().getCustomer_id());
								
				//end							
				
				if(orderResponse.getOrder_status()!=null && orderResponse.getOrder_status().equalsIgnoreCase("PAID") && !caEntity.getOrderStatus().equalsIgnoreCase("PAID"))
					
					caEntity.setOrderStatus(orderResponse.getOrder_status());
					cashFreeDao.saveDetails(caEntity);				
				//
					LinkSubAccountMultipleTempEntity linkSubAccountMultipleTempEntity=new LinkSubAccountMultipleTempEntity();				
					linkSubAccountMultipleTempEntity.setOrgId(caEntity.getOrgId());
					linkSubAccountMultipleTempEntity.setOrderId(orderResponse.getOrder_id());				
					linkSubAccountMultipleTempEntity.setBalance(orderResponse.getOrder_amount());
					linkSubAccountMultipleTempEntity.setAccountHolderName(orderResponse.getCustomer_details().getCustomer_name());
					linkSubAccountMultipleTempEntity.setAmountLimit(orderResponse.getOrder_amount());
					linkSubAccountMultipleTempEntity.setMobile(orderResponse.getCustomer_details().getCustomer_phone().substring(3));
					linkSubAccountMultipleTempEntity.setCreationDate(LocalDateTime.now());
					linkSubAccountMultipleTempEntity.setStatus(0l);
					linkSubAccountMultipleTempEntity.setAccountType(AccountType.SAVING);
					linkSubAccountMultipleTempEntity.setAcNumber(caEntity.getAcNumber());
					linkSubAccountMultipleTempEntity.setBankName(caEntity.getBankName());
					linkSubAccountMultipleTempEntity.setBankCode(caEntity.getBankCode());
					linkSubAccountMultipleTempEntity.setCreatedby(caEntity.getCreatedBy());
					linkSubAccountMultipleTempEntity.setStatusMessage("Requested");
					linkSubAccountMultipleTempEntity=linkSubMultipleAccountTempDao.saveDetails(linkSubAccountMultipleTempEntity);
				//
				orderIdResponse.setOrgId(caEntity.getOrgId());
				orderIdResponse = getCashFreeOrderId(orderIdResponse, orderResponse);

			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error Exception ...." + e.getMessage());
		}
		return orderIdResponse;
	}

	private OrderIdResponse getCashFreeOrderId(OrderIdResponse orderIdResponse, OrderResponse orderResponse) {
		orderIdResponse.setPayment_session_id(orderResponse.getPayment_session_id());
		orderIdResponse.setCustomerId(orderResponse.getCustomer_details().getCustomer_id());
		orderIdResponse.setOrderAmount(orderResponse.getOrder_amount().toString());
		orderIdResponse.setOrderCurrency(orderResponse.getOrder_currency());
		orderIdResponse.setCustomerPhone(orderResponse.getCustomer_details().getCustomer_phone());
		orderIdResponse.setCustomerName(orderResponse.getCustomer_details().getCustomer_name());
		orderIdResponse.setCustomerEmail(orderResponse.getCustomer_details().getCustomer_email());
		orderIdResponse.setCustomer_uid(orderResponse.getCustomer_details().getCustomer_uid());
		orderIdResponse.setCart_details(orderResponse.getCart_details());
		orderIdResponse.setCf_order_id(orderResponse.getCf_order_id());
		orderIdResponse.setOrderId(orderResponse.getOrder_id());
		orderIdResponse.setCreated_at(orderResponse.getCreated_at());
		orderIdResponse.setEntity(orderResponse.getEntity());
		orderIdResponse.setOrder_expiry_time(orderResponse.getOrder_expiry_time());
		orderIdResponse.setReturn_url(orderResponse.getOrder_meta().getReturn_url());
		orderIdResponse.setNotify_url(orderResponse.getOrder_meta().getNotify_url());
		orderIdResponse.setPayment_methods(orderResponse.getOrder_meta().getPayment_methods());
		orderIdResponse.setOrder_note(orderResponse.getOrder_note());
		orderIdResponse.setOrder_splits(orderResponse.getOrder_splits());
		orderIdResponse.setOrder_status(orderResponse.getOrder_status());
		orderIdResponse.setOrder_tags(orderResponse.getOrder_tags());
		orderIdResponse.setTerminal_data(orderResponse.getTerminal_data());
		return orderIdResponse;
	}

}