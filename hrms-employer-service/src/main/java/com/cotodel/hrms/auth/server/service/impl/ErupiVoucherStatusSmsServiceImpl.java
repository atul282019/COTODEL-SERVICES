package com.cotodel.hrms.auth.server.service.impl;

import java.time.LocalDateTime;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.ErupiVoucherInitiateDetailsDao;
import com.cotodel.hrms.auth.server.dao.ErupiVoucherTxnDao;
import com.cotodel.hrms.auth.server.dto.DecryptedResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherTxnRequest;
import com.cotodel.hrms.auth.server.dto.VoucherCreateRequest;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherSmsRequest;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherStatusSmsRequest;
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
	ApplicationConstantConfig applicationConstantConfig;
		   
	    
	    public  DecryptedResponse jsonToPOJO(String json) {
			
			Gson gson = new Gson();
			DecryptedResponse decryptedResponse =new DecryptedResponse();
			try {
				decryptedResponse = gson.fromJson(json, DecryptedResponse.class);
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
	    
	    private ErupiVoucherTxnDetailsEntity setResponseValue(DecryptedResponse decryptedResponse,ErupiVoucherTxnDetailsEntity erupiVoucherTxnDetailsEntity) {
	    	erupiVoucherTxnDetailsEntity.setUuid(decryptedResponse.getUuid()==null?"":decryptedResponse.getUuid());
	    	erupiVoucherTxnDetailsEntity.setUmn(decryptedResponse.getUmn()==null?"":decryptedResponse.getUmn());
	    	LocalDateTime eventDateTime = LocalDateTime.now();	
	    	erupiVoucherTxnDetailsEntity.setCreationDate(eventDateTime);
	    	erupiVoucherTxnDetailsEntity.setStatusApi(decryptedResponse.getStatus()==null?"":decryptedResponse.getStatus());
	    	erupiVoucherTxnDetailsEntity.setResponseCode(decryptedResponse.getResponseCode());
	    	erupiVoucherTxnDetailsEntity.setResultCallApi(decryptedResponse.getMessage()==null?"":decryptedResponse.getMessage());
	    	erupiVoucherTxnDetailsEntity.setResponse(decryptedResponse.getResponse()==null?"":decryptedResponse.getResponse());
	    	return erupiVoucherTxnDetailsEntity;
	    }



		@Override
		public ErupiVoucherStatusSmsRequest erupiVoucherStatusSmsDetails(ErupiVoucherStatusSmsRequest request) {
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
				
				ErupiVoucherSmsRequest voucherCreateRequest=new ErupiVoucherSmsRequest();				
				voucherCreateRequest.setMerchantTranId(erupiVoucherTxnDetailsEntity.getMerchanttxnId());
				voucherCreateRequest.setMobile(erupiVoucherInitiateDetailsEntity.getMobile());
				voucherCreateRequest.setBeneficiaryId(erupiVoucherInitiateDetailsEntity.getBeneficiaryID());
				voucherCreateRequest.setUmn(erupiVoucherTxnDetailsEntity.getUmn());
				voucherCreateRequest.setUuid(erupiVoucherTxnDetailsEntity.getUuid());
				voucherCreateRequest.setSmsCategory(erupiVoucherTxnDetailsEntity.getVoucherType());				
				
				log.info("Starting voucher create request ...."+erupiVoucherTxnDetailsEntity.getMerchanttxnId());
				
				//erupiVoucherTxnDetailsEntity=setRequestValue(voucherCreateRequest, erupiVoucherTxnDetailsEntity);
					
					String response1 = CommonUtility.userRequest("", MessageConstant.gson.toJson(voucherCreateRequest),
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
						DecryptedResponse decryptedResponse= jsonToPOJO(data.toString());
						
						if(decryptedResponse.getStatus().equalsIgnoreCase("REVOKE-SUCCESS")) {
							//erupiVoucherTxnDetailsEntity.setResponse(data.toString());
							request.setResponseApi(decryptedResponse.getMessage());
							//erupiVoucherTxnDetailsEntity.setId(null);
							//int updatework=erupiVoucherInitiateDetailsDao.updateWorkflowId(erupiVoucherInitiateDetailsEntity.getId(), 100003l);
							erupiVoucherTxnDetailsEntity2.setWorkFlowId(100006l);
							erupiVoucherTxnDetailsEntity2=setResponseValue(decryptedResponse,erupiVoucherTxnDetailsEntity2);
							erupiVoucherTxnDetailsEntity2=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity2);
							}else {
								response=MessageConstant.RESPONSE_FAILED;
								request.setResponse(response);
								erupiVoucherTxnDetailsEntity.setId(null);
								//erupiVoucherRevokeDetailsRequest.setResponse(decryptedResponse.getMessage());
								request.setResponseApi(decryptedResponse.getMessage());
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
						DecryptedResponse decryptedResponse= jsonToPOJO(data.toString());
						erupiVoucherTxnDetailsEntity2=setResponseValue(decryptedResponse,erupiVoucherTxnDetailsEntity2);
						erupiVoucherTxnDetailsEntity2=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity2);
						logger.info("erupiVoucherTxnDetailsEntity Revoke:"+erupiVoucherTxnDetailsEntity2);
					}
					
			//	}
				
				
				
			}catch (Exception e) {
				e.printStackTrace();
				log.error("Error in ErupiVoucherStatussmsServiceImpl. sms:......"+e.getMessage());
			}
			return request;
		}


   
}
