package com.cotodel.hrms.auth.server.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.BankMasterDao;
import com.cotodel.hrms.auth.server.dao.ErupiVoucherInitiateDetailsDao;
import com.cotodel.hrms.auth.server.dao.ErupiVoucherRequestDao;
import com.cotodel.hrms.auth.server.dao.ErupiVoucherTxnDao;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherTxnRequest;
import com.cotodel.hrms.auth.server.dto.VoucherCreateRequest;
import com.cotodel.hrms.auth.server.dto.indianbank.ErupiIndianBankVoucherInquiryRequest;
import com.cotodel.hrms.auth.server.dto.indianbank.IndianBankVoucherCreateResponse;
import com.cotodel.hrms.auth.server.dto.indianbank.InputParamRequest;
import com.cotodel.hrms.auth.server.dto.voucher.DecryptedSmsResponse;
import com.cotodel.hrms.auth.server.dto.voucher.DecryptedStatusResponse;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherSmsRequest;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherStatusApiRequest;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherStatusRedeemResponse;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherStatusRequest;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherStatusSmsRequest;
import com.cotodel.hrms.auth.server.dto.voucher.RedempltionDetail;
import com.cotodel.hrms.auth.server.model.ErupiBankMasterEntity;
import com.cotodel.hrms.auth.server.model.ErupiVoucherCreationDetailsEntity;
import com.cotodel.hrms.auth.server.model.ErupiVoucherTxnDetailsEntity;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.ErupiVoucherStatusSmsService;
import com.cotodel.hrms.auth.server.util.CommonUtility;
import com.cotodel.hrms.auth.server.util.CommonUtils;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Repository
public class ErupiVoucherStatusSmsServiceImpl implements ErupiVoucherStatusSmsService{


	private static final Logger logger = LoggerFactory.getLogger(ErupiVoucherStatusSmsServiceImpl.class);
	@Autowired
	ErupiVoucherInitiateDetailsDao  erupiVoucherInitiateDetailsDao;
	
	
	
	@Autowired
	ErupiVoucherTxnDao  erupiVoucherTxnDao;
	
	@Autowired
	BankMasterDao bankMasterDao;
	
	@Autowired
	ErupiVoucherRequestDao  erupiVoucherRequestDao;
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
		   
	    
	    public  DecryptedSmsResponse jsonToPOJO(String json) {
			
			Gson gson = new Gson();
			DecryptedSmsResponse decryptedResponse =new DecryptedSmsResponse();
			try {
				decryptedResponse = gson.fromJson(json, DecryptedSmsResponse.class);
			} catch (Exception e) {
				logger.error("error in CallApiVoucherCreateResponse..."+e.getMessage());
			}
			
	        return decryptedResponse;
		}
	    
public  DecryptedStatusResponse jsonToPOJOStatus(String json) {
			
			Gson gson = new Gson();
			DecryptedStatusResponse decryptedResponse =new DecryptedStatusResponse();
			try {
				decryptedResponse = gson.fromJson(json, DecryptedStatusResponse.class);
			} catch (Exception e) {
				logger.error("error in CallApiVoucherCreateResponse..."+e.getMessage());
			}
			
	        return decryptedResponse;
		}
	    
	    private ErupiVoucherTxnDetailsEntity setRequestValue(VoucherCreateRequest voucherCreateRequest,ErupiVoucherTxnDetailsEntity erupiVoucherTxnDetailsEntity) {
	    	
	    	erupiVoucherTxnDetailsEntity.setMerchanttxnId(voucherCreateRequest.getMerchantTranId());
	    	erupiVoucherTxnDetailsEntity.setVoucherType(voucherCreateRequest.getType());
	    	erupiVoucherTxnDetailsEntity.setApiType(voucherCreateRequest.getType());
	    	return erupiVoucherTxnDetailsEntity;
	    }
	    
	    private ErupiVoucherTxnDetailsEntity setResponseValue(DecryptedSmsResponse decryptedResponse,ErupiVoucherTxnDetailsEntity erupiVoucherTxnDetailsEntity) {
	    	
	    	LocalDateTime eventDateTime = LocalDateTime.now();	
	    	erupiVoucherTxnDetailsEntity.setCreationDate(eventDateTime);
	    	erupiVoucherTxnDetailsEntity.setSmsResponse(decryptedResponse.getResponse_Status());
	    	erupiVoucherTxnDetailsEntity.setSmsActcode(decryptedResponse.getActCode());
	    	erupiVoucherTxnDetailsEntity.setSmsDesc(decryptedResponse.getDescription());
	    	erupiVoucherTxnDetailsEntity.setStatusApi(decryptedResponse.getResponse_Status()==null?"":decryptedResponse.getResponse_Status());
	    	erupiVoucherTxnDetailsEntity.setResponseCode(decryptedResponse.getResponseCode());
	    	erupiVoucherTxnDetailsEntity.setResultCallApi(decryptedResponse.getDescription()==null?"":decryptedResponse.getDescription());
	    	erupiVoucherTxnDetailsEntity.setResponse(decryptedResponse.getActCode()==null?"":decryptedResponse.getActCode());
	    	return erupiVoucherTxnDetailsEntity;
	    }
	    
 private ErupiVoucherTxnDetailsEntity setResponseStatusValue(DecryptedStatusResponse decryptedResponse,ErupiVoucherTxnDetailsEntity erupiVoucherTxnDetailsEntity) {
	    	
	    	LocalDateTime eventDateTime = LocalDateTime.now();	
	    	erupiVoucherTxnDetailsEntity.setCreationDate(eventDateTime);
	    	erupiVoucherTxnDetailsEntity.setResponseJson(decryptedResponse.getApiResponse());
	    	erupiVoucherTxnDetailsEntity.setStatusApi(decryptedResponse.getSuccess());
	    	//erupiVoucherTxnDetailsEntity.setSmsResponse(decryptedResponse.getVoucherStatus());
	    	erupiVoucherTxnDetailsEntity.setMerchanttxnId(decryptedResponse.getMerchantTranId());
	    	erupiVoucherTxnDetailsEntity.setVoucherBalance(decryptedResponse.getVoucherBalance());
	    	erupiVoucherTxnDetailsEntity.setVoucherAmt(decryptedResponse.getVoucherAmt());
	    	erupiVoucherTxnDetailsEntity.setVoucherRedeemedAmount(decryptedResponse.getVoucherRedeemedAmount());
	    	erupiVoucherTxnDetailsEntity.setVoucherIssueDate(decryptedResponse.getVoucherIssueDate());
	    	erupiVoucherTxnDetailsEntity.setVoucherRedeemedDate(decryptedResponse.getVoucherRedeemedDate());
	    	erupiVoucherTxnDetailsEntity.setVoucherExpiryDate(decryptedResponse.getVoucherExpiryDate());
	    	erupiVoucherTxnDetailsEntity.setVoucherStatus(decryptedResponse.getVoucherStatus());
	    	erupiVoucherTxnDetailsEntity.setMerchantId(decryptedResponse.getMerchantId());
	    	erupiVoucherTxnDetailsEntity.setResponse(decryptedResponse.getResponse());
	    	erupiVoucherTxnDetailsEntity.setResponseCode(decryptedResponse.getResponseCode());
	    	erupiVoucherTxnDetailsEntity.setUmn(decryptedResponse.getUmn());
	    	erupiVoucherTxnDetailsEntity.setUuid(decryptedResponse.getUuid());
	    	return erupiVoucherTxnDetailsEntity;
	    }



		@Override
		public ErupiVoucherStatusSmsRequest erupiVoucherSmsDetails(ErupiVoucherStatusSmsRequest request) {
			String response="";
			log.info("Starting ErupiVoucherInitiateDetailsServiceImpl ... erupiVoucherRevokeDetails..");
			ErupiVoucherCreationDetailsEntity erupiVoucherInitiateDetailsEntity=null;
			ErupiVoucherTxnDetailsEntity erupiVoucherTxnDetailsEntity=null;
			JSONObject profileJsonRes=null;
			try {
				
				response=MessageConstant.RESPONSE_FAILED;
				request.setResponse(response);	
				erupiVoucherInitiateDetailsEntity=new ErupiVoucherCreationDetailsEntity();
				erupiVoucherTxnDetailsEntity=new ErupiVoucherTxnDetailsEntity();
				//CopyUtility.copyProperties(request,erupiVoucherInitiateDetailsEntity);
				//CopyUtility.copyProperties(request,erupiVoucherTxnDetailsEntity);
				
				erupiVoucherInitiateDetailsEntity=erupiVoucherInitiateDetailsDao.getErupiVoucherCreationDetails(request.getId());
				if(erupiVoucherInitiateDetailsEntity==null) {
					response=MessageConstant.DETAIL_ID;
					request.setResponse(response);
					return request;
				}
				
				erupiVoucherTxnDetailsEntity=erupiVoucherTxnDao.findByDetailId(erupiVoucherInitiateDetailsEntity.getId(), erupiVoucherInitiateDetailsEntity.getWorkFlowId());
				if(erupiVoucherTxnDetailsEntity==null) {
					response=MessageConstant.DETAIL_ID;
					request.setResponse(response);
					return request;
				}
				if(erupiVoucherInitiateDetailsEntity.getBankcode().equalsIgnoreCase("INDB")) {

					ErupiVoucherSmsRequest voucherCreateRequest=new ErupiVoucherSmsRequest();				
					voucherCreateRequest.setMerchantTranId(erupiVoucherTxnDetailsEntity.getMerchanttxnId());
					voucherCreateRequest.setMobile(erupiVoucherInitiateDetailsEntity.getMobile());
					voucherCreateRequest.setBeneficiaryId(erupiVoucherInitiateDetailsEntity.getBeneficiaryID());
					voucherCreateRequest.setUmn(erupiVoucherTxnDetailsEntity.getUmn());
					voucherCreateRequest.setUuid(erupiVoucherTxnDetailsEntity.getUuid());
					String sms=erupiVoucherTxnDetailsEntity.getVoucherType();
					if(erupiVoucherTxnDetailsEntity.getVoucherType().equalsIgnoreCase("CREATE")) {
						sms="Creation";
					}
					voucherCreateRequest.setSmsCategory(sms);				
					voucherCreateRequest.setMerchantId(erupiVoucherInitiateDetailsEntity.getMerchantId());
					log.info("Starting voucher create request ...."+erupiVoucherTxnDetailsEntity.getMerchanttxnId());
					
					//erupiVoucherTxnDetailsEntity=setRequestValue(voucherCreateRequest, erupiVoucherTxnDetailsEntity);
					ErupiIndianBankVoucherInquiryRequest erInquiryRequest=new ErupiIndianBankVoucherInquiryRequest();
					erInquiryRequest.setAction("inquiry");
					erInquiryRequest.setSubaction("inquiry");
					erInquiryRequest.setEntityID("INB");
					InputParamRequest inputparam=new InputParamRequest();
					inputparam.setTxnrefID(erupiVoucherTxnDetailsEntity.getTxnRefId());
					inputparam.setCheckSum("");
					String formattedValue = String.format("%.2f", erupiVoucherInitiateDetailsEntity.getAmount());
					inputparam.setOrgTxnAmount(formattedValue);
					inputparam.setTxndatetime(erupiVoucherTxnDetailsEntity.getTxnDateTime());
					inputparam.setUmn(erupiVoucherTxnDetailsEntity.getUmn());
					
					erInquiryRequest.setInputparam(inputparam);
						String response1 = CommonUtility.userRequestWiout("", MessageConstant.gson.toJson(erInquiryRequest),
								applicationConstantConfig.voucherServiceApiUrl+CommonUtils.sendIndianVoucherInquary);
						log.info("Ending voucher create response1 ...."+response1);
						
						
						
						
						profileJsonRes= new JSONObject(response1);
						ErupiVoucherTxnRequest erupi=new ErupiVoucherTxnRequest();
						CopyUtility.copyProperties(erupiVoucherTxnDetailsEntity,erupi);
						ErupiVoucherTxnDetailsEntity erupiVoucherTxnDetailsEntity2=new ErupiVoucherTxnDetailsEntity();
						CopyUtility.copyProperties(erupi,erupiVoucherTxnDetailsEntity2);
						if(profileJsonRes.getBoolean("status")) { 
							//request.setCreateResponse(response1);
							response=MessageConstant.RESPONSE_SUCCESS;
							request.setResponse(response);
							//erupiVoucherRevokeDetailsRequest.setResponse(response);
							//
							JSONObject data = profileJsonRes.getJSONObject("data");
							//DecryptedSmsResponse decryptedResponse= jsonToPOJO(data.toString());
							IndianBankVoucherCreateResponse decryptedResponse= jsonToPojoIndianBank(data.toString());
							if(decryptedResponse.getTxnStatus().equalsIgnoreCase("00")) {
								//erupiVoucherTxnDetailsEntity.setResponse(data.toString());
								request.setResponseApi(decryptedResponse.getTxnMsg());
								//erupiVoucherTxnDetailsEntity.setId(null);
								//int updatework=erupiVoucherInitiateDetailsDao.updateWorkflowId(erupiVoucherInitiateDetailsEntity.getId(), 100003l);
								erupiVoucherTxnDetailsEntity2.setWorkFlowId(100006l);
								erupiVoucherTxnDetailsEntity2.setOrgId(erupiVoucherInitiateDetailsEntity.getOrgId());
								erupiVoucherTxnDetailsEntity2=setResponseValueIndianBank(decryptedResponse,erupiVoucherTxnDetailsEntity2);
								erupiVoucherTxnDetailsEntity2=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity2);
								}else {
									response=MessageConstant.RESPONSE_FAILED;
									request.setResponse(response);
									//erupiVoucherTxnDetailsEntity.setId(null);
									//erupiVoucherRevokeDetailsRequest.setResponse(decryptedResponse.getMessage());
									request.setResponseApi(decryptedResponse.getTxnMsg());
									erupiVoucherTxnDetailsEntity2.setWorkFlowId(100009l);
									erupiVoucherTxnDetailsEntity2.setOrgId(erupiVoucherInitiateDetailsEntity.getOrgId());
									erupiVoucherTxnDetailsEntity2=setResponseValueIndianBank(decryptedResponse,erupiVoucherTxnDetailsEntity2);
									erupiVoucherTxnDetailsEntity2=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity2);
								}
							
							logger.info("erupiVoucherTxnDetailsEntity Sms:"+erupiVoucherTxnDetailsEntity);
						}else {

							response=MessageConstant.RESPONSE_FAILED;
							request.setResponse(response);
							//erupiVoucherRevokeDetailsRequest.setResponse(response);
							//erupiVoucherTxnDetailsEntity.setId(null);
							JSONObject data = profileJsonRes.getJSONObject("data");
							IndianBankVoucherCreateResponse decryptedResponse= jsonToPojoIndianBank(data.toString());
							erupiVoucherTxnDetailsEntity2.setWorkFlowId(100009l);
							erupiVoucherTxnDetailsEntity2=setResponseValueIndianBank(decryptedResponse,erupiVoucherTxnDetailsEntity2);
							erupiVoucherTxnDetailsEntity2=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity2);
							logger.info("erupiVoucherTxnDetailsEntity sms:"+erupiVoucherTxnDetailsEntity2);
						}
						
					
				}else {
				ErupiVoucherSmsRequest voucherCreateRequest=new ErupiVoucherSmsRequest();				
				voucherCreateRequest.setMerchantTranId(erupiVoucherTxnDetailsEntity.getMerchanttxnId());
				voucherCreateRequest.setMobile(erupiVoucherInitiateDetailsEntity.getMobile());
				voucherCreateRequest.setBeneficiaryId(erupiVoucherInitiateDetailsEntity.getBeneficiaryID());
				voucherCreateRequest.setUmn(erupiVoucherTxnDetailsEntity.getUmn());
				voucherCreateRequest.setUuid(erupiVoucherTxnDetailsEntity.getUuid());
				String sms=erupiVoucherTxnDetailsEntity.getVoucherType();
				if(erupiVoucherTxnDetailsEntity.getVoucherType().equalsIgnoreCase("CREATE")) {
					sms="Creation";
				}
				voucherCreateRequest.setSmsCategory(sms);				
				voucherCreateRequest.setMerchantId(erupiVoucherInitiateDetailsEntity.getMerchantId());
				log.info("Starting voucher create request ...."+erupiVoucherTxnDetailsEntity.getMerchanttxnId());
				
				//erupiVoucherTxnDetailsEntity=setRequestValue(voucherCreateRequest, erupiVoucherTxnDetailsEntity);
					
					String response1 = CommonUtility.userRequestWiout("", MessageConstant.gson.toJson(voucherCreateRequest),
							applicationConstantConfig.voucherServiceApiUrl+CommonUtils.sendVoucherSms);
					log.info("Ending voucher create response1 ...."+response1);
					
					
					
					
					profileJsonRes= new JSONObject(response1);
					ErupiVoucherTxnRequest erupi=new ErupiVoucherTxnRequest();
					CopyUtility.copyProperties(erupiVoucherTxnDetailsEntity,erupi);
					ErupiVoucherTxnDetailsEntity erupiVoucherTxnDetailsEntity2=new ErupiVoucherTxnDetailsEntity();
					CopyUtility.copyProperties(erupi,erupiVoucherTxnDetailsEntity2);
					if(profileJsonRes.getBoolean("status")) { 
						//request.setCreateResponse(response1);
						response=MessageConstant.RESPONSE_SUCCESS;
						request.setResponse(response);
						//erupiVoucherRevokeDetailsRequest.setResponse(response);
						//
						JSONObject data = profileJsonRes.getJSONObject("data");
						DecryptedSmsResponse decryptedResponse= jsonToPOJO(data.toString());
						
						if(decryptedResponse.getResponse_Status().equalsIgnoreCase("Success")) {
							//erupiVoucherTxnDetailsEntity.setResponse(data.toString());
							request.setResponseApi(decryptedResponse.getDescription());
							//erupiVoucherTxnDetailsEntity.setId(null);
							//int updatework=erupiVoucherInitiateDetailsDao.updateWorkflowId(erupiVoucherInitiateDetailsEntity.getId(), 100003l);
							erupiVoucherTxnDetailsEntity2.setWorkFlowId(100006l);
							erupiVoucherTxnDetailsEntity2.setOrgId(erupiVoucherInitiateDetailsEntity.getOrgId());
							erupiVoucherTxnDetailsEntity2=setResponseValue(decryptedResponse,erupiVoucherTxnDetailsEntity2);
							erupiVoucherTxnDetailsEntity2=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity2);
							}else {
								response=MessageConstant.RESPONSE_FAILED;
								request.setResponse(response);
								//erupiVoucherTxnDetailsEntity.setId(null);
								//erupiVoucherRevokeDetailsRequest.setResponse(decryptedResponse.getMessage());
								request.setResponseApi(decryptedResponse.getDescription());
								erupiVoucherTxnDetailsEntity2.setWorkFlowId(100009l);
								erupiVoucherTxnDetailsEntity2.setOrgId(erupiVoucherInitiateDetailsEntity.getOrgId());
								erupiVoucherTxnDetailsEntity2=setResponseValue(decryptedResponse,erupiVoucherTxnDetailsEntity2);
								erupiVoucherTxnDetailsEntity2=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity2);
							}
						
						logger.info("erupiVoucherTxnDetailsEntity Sms:"+erupiVoucherTxnDetailsEntity);
					}else {

						response=MessageConstant.RESPONSE_FAILED;
						request.setResponse(response);
						//erupiVoucherRevokeDetailsRequest.setResponse(response);
						//erupiVoucherTxnDetailsEntity.setId(null);
						JSONObject data = profileJsonRes.getJSONObject("data");
						DecryptedSmsResponse decryptedResponse= jsonToPOJO(data.toString());
						erupiVoucherTxnDetailsEntity2.setWorkFlowId(100009l);
						erupiVoucherTxnDetailsEntity2=setResponseValue(decryptedResponse,erupiVoucherTxnDetailsEntity2);
						erupiVoucherTxnDetailsEntity2=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity2);
						logger.info("erupiVoucherTxnDetailsEntity sms:"+erupiVoucherTxnDetailsEntity2);
					}
					
				}
				
				
				
			}catch (Exception e) {
				e.printStackTrace();
				log.error("Error in ErupiVoucherStatussmsServiceImpl. sms:......"+e.getMessage());
			}
			return request;
		}

		@Override
		public ErupiVoucherStatusRequest erupiVoucherStatusDetails(ErupiVoucherStatusRequest request) {
			
			String response="";
			log.info("Starting ErupiVoucherInitiateDetailsServiceImpl ... erupiVoucherStatusDetails..");
			ErupiVoucherCreationDetailsEntity erupiVoucherInitiateDetailsEntity=null;
			ErupiVoucherTxnDetailsEntity erupiVoucherTxnDetailsEntity=null;
			JSONObject profileJsonRes=null;
			try {
				
				response=MessageConstant.RESPONSE_FAILED;
				request.setResponse(response);	
				erupiVoucherInitiateDetailsEntity=new ErupiVoucherCreationDetailsEntity();
				erupiVoucherTxnDetailsEntity=new ErupiVoucherTxnDetailsEntity();
				
				erupiVoucherInitiateDetailsEntity=erupiVoucherInitiateDetailsDao.getErupiVoucherCreationDetails(request.getId());
				if(erupiVoucherInitiateDetailsEntity==null) {
					response=MessageConstant.DETAIL_ID;
					request.setResponse(response);
					return request;
				}
				
				erupiVoucherTxnDetailsEntity=erupiVoucherTxnDao.findByDetailId(erupiVoucherInitiateDetailsEntity.getId(), erupiVoucherInitiateDetailsEntity.getWorkFlowId());
				if(erupiVoucherTxnDetailsEntity==null) {
					response=MessageConstant.DETAIL_ID;
					request.setResponse(response);
					return request;
				}
				ErupiVoucherStatusApiRequest erupiVoucherStatusApiRequest=new ErupiVoucherStatusApiRequest();
				erupiVoucherStatusApiRequest.setMerchantTranId(erupiVoucherTxnDetailsEntity.getMerchanttxnId());
				erupiVoucherStatusApiRequest.setMcc(erupiVoucherInitiateDetailsEntity.getMcc());
				erupiVoucherStatusApiRequest.setUmn(erupiVoucherTxnDetailsEntity.getUmn());
				erupiVoucherStatusApiRequest.setMerchantId(erupiVoucherInitiateDetailsEntity.getMerchantId());
				erupiVoucherStatusApiRequest.setSubMerchantId(erupiVoucherInitiateDetailsEntity.getSubMerchantId());
				erupiVoucherStatusApiRequest.setTransactionType("V");


				log.info("Starting voucher status request ...."+erupiVoucherTxnDetailsEntity.getMerchanttxnId());
									
					String response1 = CommonUtility.userRequestWiout("", MessageConstant.gson.toJson(erupiVoucherStatusApiRequest),
							applicationConstantConfig.voucherServiceApiUrl+CommonUtils.sendVoucherStatus);
					log.info("Ending voucher status response1 ...."+response1);
					
					profileJsonRes= new JSONObject(response1);
					ErupiVoucherTxnRequest erupi=new ErupiVoucherTxnRequest();
					CopyUtility.copyProperties(erupiVoucherTxnDetailsEntity,erupi);
					ErupiVoucherTxnDetailsEntity erupiVoucherTxnDetailsEntity2=new ErupiVoucherTxnDetailsEntity();
					CopyUtility.copyProperties(erupi,erupiVoucherTxnDetailsEntity2);
					if(profileJsonRes.getBoolean("status")) { 
						//request.setCreateResponse(response1);
						response=MessageConstant.RESPONSE_SUCCESS;
						request.setResponse(response);
						//
						JSONObject data = profileJsonRes.getJSONObject("data");
						DecryptedStatusResponse decryptedResponse= jsonToPOJOStatus(data.toString());
						
						if(decryptedResponse.getSuccess().equalsIgnoreCase("true")) {
							request.setResponseApi(decryptedResponse.getVoucherStatus());
							//int updatework=erupiVoucherInitiateDetailsDao.updateWorkflowId(erupiVoucherInitiateDetailsEntity.getId(), 100003l);
							erupiVoucherTxnDetailsEntity2.setWorkFlowId(100008l);
							erupiVoucherTxnDetailsEntity2=setResponseStatusValue(decryptedResponse,erupiVoucherTxnDetailsEntity2);
							erupiVoucherTxnDetailsEntity2.setOrgId(erupiVoucherInitiateDetailsEntity.getOrgId());
							erupiVoucherTxnDetailsEntity2=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity2);
							}else {
								response=MessageConstant.RESPONSE_FAILED;
								request.setResponse(response);
								request.setResponseApi(decryptedResponse.getVoucherStatus());
								erupiVoucherTxnDetailsEntity2.setWorkFlowId(100010l);
								erupiVoucherTxnDetailsEntity2=setResponseStatusValue(decryptedResponse,erupiVoucherTxnDetailsEntity2);
								erupiVoucherTxnDetailsEntity2.setOrgId(erupiVoucherInitiateDetailsEntity.getOrgId());
								erupiVoucherTxnDetailsEntity2=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity2);
							}
						
						//logger.info("erupiVoucherTxnDetailsEntity status:"+erupiVoucherTxnDetailsEntity);
					}else {

						response=MessageConstant.RESPONSE_FAILED;
						request.setResponse(response);
						JSONObject data = profileJsonRes.getJSONObject("data");
						DecryptedStatusResponse decryptedResponse= jsonToPOJOStatus(data.toString());
						erupiVoucherTxnDetailsEntity2.setWorkFlowId(100010l);
						erupiVoucherTxnDetailsEntity2=setResponseStatusValue(decryptedResponse,erupiVoucherTxnDetailsEntity2);
						erupiVoucherTxnDetailsEntity2=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity2);
						//logger.info("erupiVoucherTxnDetailsEntity status:"+erupiVoucherTxnDetailsEntity2);
					}
				
			}catch (Exception e) {
				e.printStackTrace();
				log.error("Error in ErupiVoucherStatussmsServiceImpl. status:......"+e.getMessage());
			}
			return request;
		}
		public  IndianBankVoucherCreateResponse jsonToPojoIndianBank(String json) {
			
			Gson gson = new Gson();
			IndianBankVoucherCreateResponse decryptedResponse =new IndianBankVoucherCreateResponse();
			try {
				decryptedResponse = gson.fromJson(json, IndianBankVoucherCreateResponse.class);
			} catch (Exception e) {
				logger.error("error in CallApiVoucherCreateResponse..."+e.getMessage());
			}
			
	        return decryptedResponse;
		}
		
		 private ErupiVoucherTxnDetailsEntity setResponseValueIndianBank(IndianBankVoucherCreateResponse decryptedResponse,ErupiVoucherTxnDetailsEntity erupiVoucherTxnDetailsEntity) {
		    	erupiVoucherTxnDetailsEntity.setUmn(decryptedResponse.getRespParam().getUmn());
		    	LocalDateTime eventDateTime = LocalDateTime.now();	
		    	//erupiVoucherTxnDetailsEntity.setTxncompletiondate(eventDateTime);
		    	erupiVoucherTxnDetailsEntity.setCreationDate(eventDateTime);
		    	erupiVoucherTxnDetailsEntity.setStatusApi(decryptedResponse.getTxnStatus());
		    	erupiVoucherTxnDetailsEntity.setResponseCode(decryptedResponse.getTxnStatus());
		    	erupiVoucherTxnDetailsEntity.setResultCallApi(decryptedResponse.getTxnMsg());
		    	erupiVoucherTxnDetailsEntity.setTxnRefId(decryptedResponse.getRespParam().getTxnRefId());
		    	erupiVoucherTxnDetailsEntity.setTxnDateTime(decryptedResponse.getRespParam().getTxnDateTime());
		    	return erupiVoucherTxnDetailsEntity;
		    }

		@Override
		public ErupiVoucherStatusRedeemResponse erupiVoucherStatusDetailsHistory(ErupiVoucherStatusRequest request) {
			String response="";
			log.info("Starting ErupiVoucherInitiateDetailsServiceImpl ... erupiVoucherStatusDetailsHistory..");
			ErupiVoucherCreationDetailsEntity erupiVoucherInitiateDetailsEntity=null;
			List<ErupiVoucherTxnDetailsEntity> erupiVoucherTxnDetailsList=new ArrayList<ErupiVoucherTxnDetailsEntity>();
			JSONObject profileJsonRes=null;
			ErupiVoucherStatusRedeemResponse redeemResponse=new ErupiVoucherStatusRedeemResponse();
			try {
				
				response=MessageConstant.RESPONSE_FAILED;
				redeemResponse.setResponse(response);	
				erupiVoucherInitiateDetailsEntity=new ErupiVoucherCreationDetailsEntity();
				
				erupiVoucherInitiateDetailsEntity=erupiVoucherInitiateDetailsDao.getErupiVoucherCreationDetails(request.getId());
				if(erupiVoucherInitiateDetailsEntity==null) {
					response=MessageConstant.DETAIL_ID;
					redeemResponse.setResponse(response);
					return redeemResponse;
				}
				redeemResponse.setVoucherCode(erupiVoucherInitiateDetailsEntity.getVoucherCode());
				redeemResponse.setVoucherDesc(erupiVoucherInitiateDetailsEntity.getVoucherDesc());
				redeemResponse.setVoucherAmount(erupiVoucherInitiateDetailsEntity.getAmount().toString());
				redeemResponse.setIssueDate(erupiVoucherInitiateDetailsEntity.getCreationDate().toString());
				redeemResponse.setMerchantTranId(erupiVoucherInitiateDetailsEntity.getMerchanttxnid());
				redeemResponse.setName(erupiVoucherInitiateDetailsEntity.getName());
				redeemResponse.setMobile(erupiVoucherInitiateDetailsEntity.getMobile());
				redeemResponse.setExpDate(erupiVoucherInitiateDetailsEntity.getExpDate().toString());
				redeemResponse.setAccountNumber(erupiVoucherInitiateDetailsEntity.getAccountNumber());
				ErupiBankMasterEntity erupiBankMasterEntity=bankMasterDao.getDetails(erupiVoucherInitiateDetailsEntity.getBankcode());
				if(erupiBankMasterEntity!=null) {
					redeemResponse.setBankLogo(erupiBankMasterEntity.getBankLogo());
				}
				byte[] mccMainIcon=erupiVoucherRequestDao.getVoucherCreationRequestPurposeCode(erupiVoucherInitiateDetailsEntity.getPurposeCode());
				redeemResponse.setVoucherLogo(mccMainIcon);
				List<RedempltionDetail> list=new ArrayList<RedempltionDetail>();
				boolean redeemFlag=false;
				erupiVoucherTxnDetailsList=erupiVoucherTxnDao.findByDetailIdWithRedeem(erupiVoucherInitiateDetailsEntity.getId());
				if(erupiVoucherTxnDetailsList==null && erupiVoucherTxnDetailsList.size()>0) {
					response=MessageConstant.DETAIL_ID;
					redeemResponse.setResponse(response);
					return redeemResponse;
				}else {
				
					for (ErupiVoucherTxnDetailsEntity erEntity: erupiVoucherTxnDetailsList) {
						if(erEntity.getWorkFlowId()!=null && erEntity.getWorkFlowId()==100007) {
							RedempltionDetail redempltionDetail=new RedempltionDetail();
							redempltionDetail.setAmount(erEntity.getPayerAmount());
							redempltionDetail.setBankrrn(erEntity.getBankrrn());
							redempltionDetail.setMarchantName(erEntity.getPayeeName());
							String formattedDate = erEntity.getCreationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
							redempltionDetail.setTransactionDate(formattedDate);
							list.add(redempltionDetail);
							redeemFlag=true;
						}
					}
				}
				redeemResponse.setData(list);
				ErupiVoucherTxnDetailsEntity erupiVoucherTxnDetailsEntity=new ErupiVoucherTxnDetailsEntity();
				erupiVoucherTxnDetailsEntity=erupiVoucherTxnDetailsList.get(0);
				ErupiVoucherStatusApiRequest erupiVoucherStatusApiRequest=new ErupiVoucherStatusApiRequest();
				erupiVoucherStatusApiRequest.setMerchantTranId(erupiVoucherTxnDetailsEntity.getMerchanttxnId());
				erupiVoucherStatusApiRequest.setMcc(erupiVoucherInitiateDetailsEntity.getMcc());
				erupiVoucherStatusApiRequest.setUmn(erupiVoucherTxnDetailsEntity.getUmn());
				erupiVoucherStatusApiRequest.setMerchantId(erupiVoucherInitiateDetailsEntity.getMerchantId());
				erupiVoucherStatusApiRequest.setSubMerchantId(erupiVoucherInitiateDetailsEntity.getSubMerchantId());
				erupiVoucherStatusApiRequest.setTransactionType("V");


				log.info("Starting voucher status request ...."+erupiVoucherTxnDetailsEntity.getMerchanttxnId());
									
					String response1 = CommonUtility.userRequestWiout("", MessageConstant.gson.toJson(erupiVoucherStatusApiRequest),
							applicationConstantConfig.voucherServiceApiUrl+CommonUtils.sendVoucherStatus);
					log.info("Ending voucher status response1 ...."+response1);
					
					profileJsonRes= new JSONObject(response1);
					ErupiVoucherTxnRequest erupi=new ErupiVoucherTxnRequest();
					DecryptedStatusResponse decryptedResponse=null;
					if(profileJsonRes.getBoolean("status")) { 
						//request.setCreateResponse(response1);
						response=MessageConstant.RESPONSE_SUCCESS;
						request.setResponse(response);
						//
						JSONObject data = profileJsonRes.getJSONObject("data");
						decryptedResponse= jsonToPOJOStatus(data.toString());
						
						if(decryptedResponse.getSuccess().equalsIgnoreCase("true")) {
							request.setResponseApi(decryptedResponse.getVoucherStatus());
							}else {
								response=MessageConstant.RESPONSE_FAILED;
								request.setResponse(response);
								request.setResponseApi(decryptedResponse.getVoucherStatus());
							}
						
					}else {

						response=MessageConstant.RESPONSE_FAILED;
						request.setResponse(response);
						JSONObject data = profileJsonRes.getJSONObject("data");
						decryptedResponse= jsonToPOJOStatus(data.toString());
					}
					redeemResponse.setActiveAmount(decryptedResponse.getVoucherBalance());
					redeemResponse.setAmountSpent(decryptedResponse.getVoucherRedeemedAmount());
					redeemResponse.setVoucherStatus(decryptedResponse.getVoucherStatus());
					redeemResponse.setResponse(MessageConstant.RESPONSE_SUCCESS);
			}catch (Exception e) {
				e.printStackTrace();
				log.error("Error in ErupiVoucherStatussmsServiceImpl. status:......"+e.getMessage());
			}
			return redeemResponse;
		}
   
}
