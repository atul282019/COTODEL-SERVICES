package com.cotodel.hrms.auth.server.service.impl;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.CashFreeDao;
import com.cotodel.hrms.auth.server.dao.CashFreeWebHookDao;
import com.cotodel.hrms.auth.server.dao.CashFreeWebHookLogDao;
import com.cotodel.hrms.auth.server.dao.LinkSubMultipleAccountTempDao;
import com.cotodel.hrms.auth.server.dto.ChargesDetail;
import com.cotodel.hrms.auth.server.dto.CustomerDetails;
import com.cotodel.hrms.auth.server.dto.ErrorDetails;
import com.cotodel.hrms.auth.server.dto.Order;
import com.cotodel.hrms.auth.server.dto.OrderIdResponse;
import com.cotodel.hrms.auth.server.dto.OrderResponse;
import com.cotodel.hrms.auth.server.dto.OrderUserRequest;
import com.cotodel.hrms.auth.server.dto.OrderUserUpdateRequest;
import com.cotodel.hrms.auth.server.dto.Payment;
import com.cotodel.hrms.auth.server.dto.PaymentGatewayDetails;
import com.cotodel.hrms.auth.server.entity.CashFreeOrderEntity;
import com.cotodel.hrms.auth.server.entity.CashFreeOrderWebHookEntity;
import com.cotodel.hrms.auth.server.entity.CashFreeWebHookLogEntity;
import com.cotodel.hrms.auth.server.entity.LinkSubAccountMultipleTempEntity;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.CashService;
import com.cotodel.hrms.auth.server.util.AccountType;
import com.cotodel.hrms.auth.server.util.CommonUtility;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;
import com.fasterxml.jackson.databind.ObjectMapper;
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
	CashFreeWebHookDao cashFreeWebHookDao;
	
	@Autowired
	CashFreeWebHookLogDao cashFreeWebHookLogDao;
	
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
		url.put("return_url", applicationConstantConfig.cashFreeOrderReturnUrl);
		//url.put("notify_url", applicationConstantConfig.cashFreeOrderNotifyUrl);
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
				
				System.out.println(orderResponse.getOrder_status());
				System.out.println(orderResponse.getOrder_status());
				
				if(orderResponse.getOrder_status()!=null && orderResponse.getOrder_status().equalsIgnoreCase("PAID") && !caEntity.getOrderStatus().equalsIgnoreCase("PAID"))
				{
					caEntity.setOrderStatus(orderResponse.getOrder_status());
					cashFreeDao.saveDetails(caEntity);				
				//
					LinkSubAccountMultipleTempEntity linkSubAccountMultipleTempEntity=new LinkSubAccountMultipleTempEntity();				
					linkSubAccountMultipleTempEntity.setOrgId(caEntity.getOrgId());
					linkSubAccountMultipleTempEntity.setOrderId(orderResponse.getOrder_id());					
					linkSubAccountMultipleTempEntity.setAccountHolderName(orderResponse.getCustomer_details().getCustomer_name());
					Float orderAmount= orderResponse.getOrder_amount();
					Float serviceTax=orderAmount * 0.34f / 100;
					Float serviceCharge=orderAmount * 1.9f / 100;
					BigDecimal serviceTaxRounded = new BigDecimal(serviceTax).setScale(2, RoundingMode.HALF_UP);
					BigDecimal serviceChargeRounded = new BigDecimal(serviceCharge).setScale(2, RoundingMode.HALF_UP);
					// Convert back to Float if necessary
					serviceTax = serviceTaxRounded.floatValue();
					serviceCharge = serviceChargeRounded.floatValue();
					Float paymentAmount=orderAmount-(serviceTax+serviceCharge);
					linkSubAccountMultipleTempEntity.setBalance(paymentAmount);
					linkSubAccountMultipleTempEntity.setAmountLimit(paymentAmount);
					linkSubAccountMultipleTempEntity.setServiceCharge(serviceCharge);
					linkSubAccountMultipleTempEntity.setServiceTax(serviceTax);
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

			}else {
				caEntity.setOrderStatus(orderResponse.getOrder_status());
				cashFreeDao.saveDetails(caEntity);	
				orderIdResponse.setOrgId(caEntity.getOrgId());
				orderIdResponse = getCashFreeOrderId(orderIdResponse, orderResponse);
			}
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
		Float orderAmount= orderResponse.getOrder_amount();
		Float serviceTax=orderAmount * 0.34f / 100;
		Float serviceCharge=orderAmount * 1.9f / 100;
		BigDecimal serviceTaxRounded = new BigDecimal(serviceTax).setScale(2, RoundingMode.HALF_UP);
		BigDecimal serviceChargeRounded = new BigDecimal(serviceCharge).setScale(2, RoundingMode.HALF_UP);
		// Convert back to Float if necessary
		serviceTax = serviceTaxRounded.floatValue();
		serviceCharge = serviceChargeRounded.floatValue();
		Float paymentAmount=orderAmount-(serviceTax+serviceCharge);
		orderIdResponse.setServiceCharge(serviceCharge.toString());
		orderIdResponse.setServiceTax(serviceTax.toString());
		orderIdResponse.setSettlementAmount(paymentAmount.toString());
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

	@Override
	public OrderIdResponse callOrderIdApiView(OrderUserRequest orderUserRequest) {
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
				
//				System.out.println(orderResponse.getOrder_status());
//				System.out.println(orderResponse.getOrder_status());
				
//				if(orderResponse.getOrder_status()!=null && orderResponse.getOrder_status().equalsIgnoreCase("PAID") && !caEntity.getOrderStatus().equalsIgnoreCase("PAID"))
//				{
//					caEntity.setOrderStatus(orderResponse.getOrder_status());
//					cashFreeDao.saveDetails(caEntity);				
				//
//					LinkSubAccountMultipleTempEntity linkSubAccountMultipleTempEntity=new LinkSubAccountMultipleTempEntity();				
//					linkSubAccountMultipleTempEntity.setOrgId(caEntity.getOrgId());
//					linkSubAccountMultipleTempEntity.setOrderId(orderResponse.getOrder_id());				
//					linkSubAccountMultipleTempEntity.setBalance(orderResponse.getOrder_amount());
//					linkSubAccountMultipleTempEntity.setAccountHolderName(orderResponse.getCustomer_details().getCustomer_name());
//					linkSubAccountMultipleTempEntity.setAmountLimit(orderResponse.getOrder_amount());
//					linkSubAccountMultipleTempEntity.setMobile(orderResponse.getCustomer_details().getCustomer_phone().substring(3));
//					linkSubAccountMultipleTempEntity.setCreationDate(LocalDateTime.now());
//					linkSubAccountMultipleTempEntity.setStatus(0l);
//					linkSubAccountMultipleTempEntity.setAccountType(AccountType.SAVING);
//					linkSubAccountMultipleTempEntity.setAcNumber(caEntity.getAcNumber());
//					linkSubAccountMultipleTempEntity.setBankName(caEntity.getBankName());
//					linkSubAccountMultipleTempEntity.setBankCode(caEntity.getBankCode());
//					linkSubAccountMultipleTempEntity.setCreatedby(caEntity.getCreatedBy());
//					linkSubAccountMultipleTempEntity.setStatusMessage("Requested");
//					linkSubAccountMultipleTempEntity=linkSubMultipleAccountTempDao.saveDetails(linkSubAccountMultipleTempEntity);
				//
				
				orderIdResponse.setOrgId(caEntity.getOrgId());
				orderIdResponse = getCashFreeOrderId(orderIdResponse, orderResponse);

			//}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error Exception ...." + e.getMessage());
		}
		return orderIdResponse;
	}

	@Override
	public OrderUserUpdateRequest callOrderIdApiUpdate(OrderUserUpdateRequest orderUserUpdateRequest) {
		CashFreeOrderEntity caEntity = new CashFreeOrderEntity();
		CashFreeOrderWebHookEntity cashFreeWebHookEntity = new CashFreeOrderWebHookEntity();
		CashFreeWebHookLogEntity cashFreeWebHookLogEntity=new CashFreeWebHookLogEntity();
		try {
			
			orderUserUpdateRequest.setResponse(MessageConstant.RESPONSE_FAILED);
			//caEntity = cashFreeDao.getDetails(orderUserUpdateRequest.getData().getCustomer_details().getCustomer_id());
			cashFreeWebHookEntity=getCashFreeOrderUpdate(cashFreeWebHookEntity, orderUserUpdateRequest);
			cashFreeWebHookDao.saveDetails(cashFreeWebHookEntity);
			
			cashFreeWebHookLogEntity.setCreationDate(LocalDateTime.now());
			cashFreeWebHookLogEntity.setOrderId(orderUserUpdateRequest.getData().getOrder().getOrder_id());
			ObjectMapper objectMapper = new ObjectMapper();
	        // Convert POJO to JSON string
	        String jsonString = objectMapper.writeValueAsString(orderUserUpdateRequest);
	        cashFreeWebHookLogEntity.setResponseJson(jsonString);
	        cashFreeWebHookLogDao.saveDetails(cashFreeWebHookLogEntity);
			orderUserUpdateRequest.setResponse(MessageConstant.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderUserUpdateRequest;
	}
	private CashFreeOrderWebHookEntity getCashFreeOrderUpdate(CashFreeOrderWebHookEntity caEntity, OrderUserUpdateRequest orderUserUpdateRequest) {
	    if (orderUserUpdateRequest == null || orderUserUpdateRequest.getData() == null) {
	        // handle the case when the request or its data is null
	        return caEntity; // or you can throw an exception, depending on your needs
	    }
	    
	    Order order = orderUserUpdateRequest.getData().getOrder();
	    if (order != null) {
	    	caEntity.setOrderAmount(order.getOrder_amount());
	    	caEntity.setOrderId(order.getOrder_id());
	    	caEntity.setOrderCurrency(order.getOrder_currency());      
	    }
	    CustomerDetails customerDetails = orderUserUpdateRequest.getData().getCustomer_details();
	    if (customerDetails != null) {
	    	caEntity.setCustomerId(customerDetails.getCustomer_id());
	    	caEntity.setCustomerName(customerDetails.getCustomer_name());
	    	caEntity.setCustomerEmail(customerDetails.getCustomer_email());
	    	caEntity.setCustomerPhone(customerDetails.getCustomer_phone());	       
	    }
	    
	    Payment payment = orderUserUpdateRequest.getData().getPayment();
	    if (payment != null) {
	        caEntity.setCfPaymentId(payment.getCf_payment_id());
	        caEntity.setPaymentStatus(payment.getPayment_status());
	        caEntity.setPaymentAmount(payment.getPayment_amount());
	        caEntity.setPaymentCurrency(payment.getPayment_currency());
	        caEntity.setPaymentMessage(payment.getPayment_message());
	        caEntity.setPaymentTime(payment.getPayment_time());
	        caEntity.setBankReference(payment.getBank_reference());
	        caEntity.setPaymentGroup(payment.getPayment_group());
	    }

	    ErrorDetails errorDetails = orderUserUpdateRequest.getData().getError_details();
	    if (errorDetails != null) {
	        caEntity.setErrorCode(errorDetails.getError_code());
	        caEntity.setErrorDescription(errorDetails.getError_description());
	        caEntity.setErrorReason(errorDetails.getError_reason());
	    }

	    PaymentGatewayDetails paymentGatewayDetails = orderUserUpdateRequest.getData().getPayment_gateway_details();
	    if (paymentGatewayDetails != null) {
	        caEntity.setGatewayName(paymentGatewayDetails.getGateway_name());
	        caEntity.setGatewayOrderId(paymentGatewayDetails.getGateway_order_id());
	        caEntity.setGatewayPaymentId(paymentGatewayDetails.getGateway_payment_id());
	        caEntity.setGatewayOrderReferenceId(paymentGatewayDetails.getGateway_order_reference_id());
	    }
	    
	    ChargesDetail chargesDetails = orderUserUpdateRequest.getData().getCharges_details();
	    if (chargesDetails != null) {
	    	caEntity.setServiceCharge(chargesDetails.getService_charge());
	    	caEntity.setServiceTax(chargesDetails.getService_tax());
	    	caEntity.setSettlementAmount(chargesDetails.getSettlement_amount());
	    	caEntity.setSettlementCurrency(chargesDetails.getSettlement_currency());
	    	caEntity.setServiceChargeDiscount(chargesDetails.getService_charge_discount());
	    }

	    if (orderUserUpdateRequest.getEvent_time() != null) {
	        caEntity.setEventTime(orderUserUpdateRequest.getEvent_time());
	    }

	    if (orderUserUpdateRequest.getType() != null) {
	        caEntity.setType(orderUserUpdateRequest.getType());
	    }

	    return caEntity;
	}

	@Override
	public List<CashFreeOrderWebHookEntity> callOrderIdApiList(OrderUserRequest orderUserRequest) {
		// TODO Auto-generated method stub
		List<CashFreeOrderWebHookEntity> finalList=new ArrayList<CashFreeOrderWebHookEntity>();
		try {
			List<CashFreeOrderEntity> list=cashFreeDao.getDetailsOrderId(orderUserRequest.getOrgId());
			if(list!=null && list.size()>0) {
			for (CashFreeOrderEntity cashFreeOrderEntity : list) {
				List<CashFreeOrderWebHookEntity> local=cashFreeWebHookDao.getDetails(cashFreeOrderEntity.getOrderId());
				if(local!=null && local.size()>0) {
					for (CashFreeOrderWebHookEntity cashFreeOrderEntity2 : local) {
						finalList.add(cashFreeOrderEntity2);
					}
				}
				
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finalList;
	}
	

}