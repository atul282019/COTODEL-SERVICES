package com.cotodel.hrms.auth.server.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.ErupiVoucherInitiateDetailsDao;
import com.cotodel.hrms.auth.server.dao.ErupiVoucherTxnDao;
import com.cotodel.hrms.auth.server.dto.DecryptedResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreateDetailsRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedDto;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherRevokeDetailsRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherRevokeRequest;
import com.cotodel.hrms.auth.server.dto.VoucherCreateRequest;
import com.cotodel.hrms.auth.server.model.ErupiVoucherCreationDetailsEntity;
import com.cotodel.hrms.auth.server.model.ErupiVoucherTxnDetailsEntity;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.ErupiVoucherInitiateDetailsService;
import com.cotodel.hrms.auth.server.util.CommonUtility;
import com.cotodel.hrms.auth.server.util.CommonUtils;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Repository
public class ErupiVoucherInitiateDetailsServiceImpl implements ErupiVoucherInitiateDetailsService{


	private static final Logger logger = LoggerFactory.getLogger(ErupiVoucherInitiateDetailsServiceImpl.class);
	@Autowired
	ErupiVoucherInitiateDetailsDao  erupiVoucherInitiateDetailsDao;
	
	@Autowired
	ErupiVoucherTxnDao  erupiVoucherTxnDao;
	
	@Autowired
    private EntityManager entityManager;
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
	@Override
	public ErupiVoucherCreateDetailsRequest saveErupiVoucherInitiateDetails(ErupiVoucherCreateDetailsRequest request) {
		String response="";
		log.info("Starting ErupiVoucherInitiateDetailsServiceImpl ... saveErupiVoucherInitiateDetails..");
		ErupiVoucherCreationDetailsEntity erupiVoucherInitiateDetailsEntity=null;
		ErupiVoucherTxnDetailsEntity erupiVoucherTxnDetailsEntity=null;
		JSONObject profileJsonRes=null;
		try {
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);	
			erupiVoucherInitiateDetailsEntity=new ErupiVoucherCreationDetailsEntity();
			erupiVoucherTxnDetailsEntity=new ErupiVoucherTxnDetailsEntity();
			CopyUtility.copyProperties(request,erupiVoucherInitiateDetailsEntity);
			CopyUtility.copyProperties(request,erupiVoucherTxnDetailsEntity);
			LocalDate eventDate = LocalDate.now();	
			erupiVoucherInitiateDetailsEntity.setCreationDate(eventDate);
			erupiVoucherInitiateDetailsEntity.setWorkFlowId(100001l);
			erupiVoucherInitiateDetailsEntity=erupiVoucherInitiateDetailsDao.saveDetails(erupiVoucherInitiateDetailsEntity);
			if(erupiVoucherInitiateDetailsEntity!=null) {
			VoucherCreateRequest voucherCreateRequest=new VoucherCreateRequest();
				
			String merchantTranId=getMerTranId(request.getBankcode());
			voucherCreateRequest.setMerchantTranId(merchantTranId);
			voucherCreateRequest.setAmount(request.getAmount().toString());
			voucherCreateRequest.setBeneficiaryID(request.getBeneficiaryID());
			voucherCreateRequest.setMobileNumber(request.getMobile());
			voucherCreateRequest.setBeneficiaryName(request.getName());
			String formattedValue = String.format("%.2f", request.getAmount());
			voucherCreateRequest.setAmount(formattedValue);
			String expdate=request.getExpDate().toString();
			voucherCreateRequest.setExpiry(expdate);
			voucherCreateRequest.setPurposeCode(request.getPurposeCode());
			voucherCreateRequest.setMcc(request.getMcc());
			voucherCreateRequest.setVoucherRedemptionType(request.getRedemtionType());
			voucherCreateRequest.setPayerVA(request.getPayerVA());
			voucherCreateRequest.setType(request.getType());

			
			
			log.info("Starting voucher create request ...."+merchantTranId);	
			erupiVoucherTxnDetailsEntity=setRequestValue(voucherCreateRequest, erupiVoucherTxnDetailsEntity);
				
				String response1 = CommonUtility.userRequest("", MessageConstant.gson.toJson(voucherCreateRequest),
						applicationConstantConfig.voucherServiceApiUrl+CommonUtils.sendVoucherCreate);
				log.info("Ending voucher create response1 ...."+response1);
				
				
				
				
				profileJsonRes= new JSONObject(response1);
				
				if(profileJsonRes.getBoolean("status")) { 
					//request.setCreateResponse(response1);
					response=MessageConstant.RESPONSE_SUCCESS;
					request.setResponse(response);
					JSONObject data = profileJsonRes.getJSONObject("data");
					DecryptedResponse decryptedResponse= jsonToPOJO(data.toString());
					//erupiVoucherTxnDetailsEntity.setResponse(data.toString());
					erupiVoucherTxnDetailsEntity.setDetailsId(erupiVoucherInitiateDetailsEntity.getId());
					int updatework=erupiVoucherInitiateDetailsDao.updateWorkflowId(erupiVoucherInitiateDetailsEntity.getId(), 100003l);
					erupiVoucherTxnDetailsEntity.setWorkFlowId(100003l);
					erupiVoucherTxnDetailsEntity=setResponseValue(decryptedResponse,erupiVoucherTxnDetailsEntity);
					erupiVoucherTxnDetailsEntity=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity);
					logger.info("erupiVoucherTxnDetailsEntity"+erupiVoucherTxnDetailsEntity);
				}else {
					//loginservice.sendEmailVerificationCompletion(userForm);
					//request.setCreateResponse(response1);
					response=MessageConstant.RESPONSE_FAILED;
					request.setResponse(response);
					erupiVoucherTxnDetailsEntity.setDetailsId(erupiVoucherInitiateDetailsEntity.getId());
					JSONObject data = profileJsonRes.getJSONObject("data");
					DecryptedResponse decryptedResponse= jsonToPOJO(data.toString());
					//erupiVoucherTxnDetailsEntity.setResponse(data.toString());
					erupiVoucherTxnDetailsEntity.setWorkFlowId(100004l);
					int updatework=erupiVoucherInitiateDetailsDao.updateWorkflowId(erupiVoucherInitiateDetailsEntity.getId(), 100004l);
					erupiVoucherTxnDetailsEntity=setResponseValue(decryptedResponse,erupiVoucherTxnDetailsEntity);
					erupiVoucherTxnDetailsEntity=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity);
					logger.info("erupiVoucherTxnDetailsEntity"+erupiVoucherTxnDetailsEntity);
				}
				
				
			}
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Error in ErupiVoucherInitiateDetailsServiceImpl......."+e.getMessage());
		}
		return request;
	}

	 

	    public long getMerchantTranId() {
	        Query query = entityManager.createNativeQuery("SELECT nextval('merchanttranid')");
	        return ((Number) query.getSingleResult()).longValue();
	    }
	    
	    public String getMerTranId(String bankcode) {
	    	bankcode=bankcode==null?"":bankcode;
	    	Long value=getMerchantTranId();
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHH");
	        String date = sdf.format(new Date());
	        String uniqueId=bankcode+date+value;
	        System.out.println(uniqueId);
	    	return uniqueId;
	    }
	    
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
	    	//LocalDateTime eventDateTime = LocalDateTime.now();	
	    	//erupiVoucherTxnDetailsEntity.setTxninitdate(eventDateTime);
	    	erupiVoucherTxnDetailsEntity.setMerchanttxnId(voucherCreateRequest.getMerchantTranId());
	    	erupiVoucherTxnDetailsEntity.setVoucherType(voucherCreateRequest.getType());
	    	erupiVoucherTxnDetailsEntity.setApiType(voucherCreateRequest.getType());
	    	return erupiVoucherTxnDetailsEntity;
	    }
	    
	    private ErupiVoucherTxnDetailsEntity setResponseValue(DecryptedResponse decryptedResponse,ErupiVoucherTxnDetailsEntity erupiVoucherTxnDetailsEntity) {
	    	erupiVoucherTxnDetailsEntity.setUuid(decryptedResponse.getUuid()==null?"":decryptedResponse.getUuid());
	    	erupiVoucherTxnDetailsEntity.setUmn(decryptedResponse.getUmn()==null?"":decryptedResponse.getUmn());
	    	LocalDateTime eventDateTime = LocalDateTime.now();	
	    	//erupiVoucherTxnDetailsEntity.setTxncompletiondate(eventDateTime);
	    	erupiVoucherTxnDetailsEntity.setCreationDate(eventDateTime);
	    	erupiVoucherTxnDetailsEntity.setStatusApi(decryptedResponse.getStatus()==null?"":decryptedResponse.getStatus());
	    	erupiVoucherTxnDetailsEntity.setResponseCode(decryptedResponse.getResponseCode());
	    	erupiVoucherTxnDetailsEntity.setResultCallApi(decryptedResponse.getMessage()==null?"":decryptedResponse.getMessage());
	    	erupiVoucherTxnDetailsEntity.setResponse(decryptedResponse.getResponse()==null?"":decryptedResponse.getResponse());
	    	return erupiVoucherTxnDetailsEntity;
	    }



		@Override
		public ErupiVoucherRevokeDetailsRequest erupiVoucherRevokeDetails(
				ErupiVoucherRevokeDetailsRequest erupiVoucherRevokeDetailsRequest) {
			String response="";
			log.info("Starting ErupiVoucherInitiateDetailsServiceImpl ... erupiVoucherRevokeDetails..");
			ErupiVoucherCreationDetailsEntity erupiVoucherInitiateDetailsEntity=null;
			ErupiVoucherTxnDetailsEntity erupiVoucherTxnDetailsEntity=null;
			JSONObject profileJsonRes=null;
			try {
				List<ErupiVoucherRevokeRequest> list=erupiVoucherRevokeDetailsRequest.getList();
				for(ErupiVoucherRevokeRequest request : list) {
				response=MessageConstant.RESPONSE_FAILED;
				request.setResponse(response);	
				erupiVoucherInitiateDetailsEntity=new ErupiVoucherCreationDetailsEntity();
				erupiVoucherTxnDetailsEntity=new ErupiVoucherTxnDetailsEntity();
				CopyUtility.copyProperties(request,erupiVoucherInitiateDetailsEntity);
				CopyUtility.copyProperties(request,erupiVoucherTxnDetailsEntity);
				LocalDate eventDate = LocalDate.now();	
				erupiVoucherInitiateDetailsEntity.setCreationDate(eventDate);
//				erupiVoucherInitiateDetailsEntity=erupiVoucherInitiateDetailsDao.saveDetails(erupiVoucherInitiateDetailsEntity);
//				if(erupiVoucherInitiateDetailsEntity!=null) {
				VoucherCreateRequest voucherCreateRequest=new VoucherCreateRequest();
					
				//String merchantTranId=getMerTranId(request.getBankcode());
				voucherCreateRequest.setMerchantTranId(request.getMerchanttxnid());
				voucherCreateRequest.setAmount(request.getAmount().toString());
				voucherCreateRequest.setBeneficiaryID(request.getBeneficiaryID());
				voucherCreateRequest.setMobileNumber(request.getMobile());
				voucherCreateRequest.setBeneficiaryName(request.getName());
				String formattedValue = String.format("%.2f", request.getAmount());
				voucherCreateRequest.setAmount(formattedValue);
				String expdate=request.getExpDate()==null?"":request.getExpDate().toString();
				voucherCreateRequest.setExpiry(expdate);
				voucherCreateRequest.setPurposeCode(request.getPurposeCode()==null?"":request.getPurposeCode());
				voucherCreateRequest.setMcc(request.getMcc());
				voucherCreateRequest.setVoucherRedemptionType(request.getRedemtionType());
				voucherCreateRequest.setPayerVA(request.getPayerVA());
				voucherCreateRequest.setType(request.getType());

				
				
				log.info("Starting voucher create request ...."+request.getMerchanttxnid());	
				erupiVoucherTxnDetailsEntity=setRequestValue(voucherCreateRequest, erupiVoucherTxnDetailsEntity);
					
					String response1 = CommonUtility.userRequest("", MessageConstant.gson.toJson(voucherCreateRequest),
							applicationConstantConfig.voucherServiceApiUrl+CommonUtils.sendVoucherCreate);
					log.info("Ending voucher create response1 ...."+response1);
					
					
					
					
					profileJsonRes= new JSONObject(response1);
					
					if(profileJsonRes.getBoolean("status")) { 
						//request.setCreateResponse(response1);
						response=MessageConstant.RESPONSE_SUCCESS;
						request.setResponse(response);
						JSONObject data = profileJsonRes.getJSONObject("data");
						DecryptedResponse decryptedResponse= jsonToPOJO(data.toString());
						erupiVoucherTxnDetailsEntity.setResponse(data.toString());
						erupiVoucherTxnDetailsEntity.setDetailsId(erupiVoucherInitiateDetailsEntity.getId());
						erupiVoucherTxnDetailsEntity.setWorkFlowId(100005l);
						erupiVoucherTxnDetailsEntity=setResponseValue(decryptedResponse,erupiVoucherTxnDetailsEntity);
						erupiVoucherTxnDetailsEntity=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity);
						logger.info("erupiVoucherTxnDetailsEntity"+erupiVoucherTxnDetailsEntity);
					}else {
						//loginservice.sendEmailVerificationCompletion(userForm);
						//request.setCreateResponse(response1);
						response=MessageConstant.RESPONSE_FAILED;
						request.setResponse(response);
						erupiVoucherTxnDetailsEntity.setDetailsId(erupiVoucherInitiateDetailsEntity.getId());
						JSONObject data = profileJsonRes.getJSONObject("data");
						DecryptedResponse decryptedResponse= jsonToPOJO(data.toString());
						//erupiVoucherTxnDetailsEntity.setResponse(data.toString());
						erupiVoucherTxnDetailsEntity.setWorkFlowId(100004l);
						erupiVoucherTxnDetailsEntity=setResponseValue(decryptedResponse,erupiVoucherTxnDetailsEntity);
						erupiVoucherTxnDetailsEntity=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity);
						logger.info("erupiVoucherTxnDetailsEntity"+erupiVoucherTxnDetailsEntity);
					}
					
			//	}
				}
				
				
				
			}catch (Exception e) {
				e.printStackTrace();
				log.error("Error in ErupiVoucherInitiateDetailsServiceImpl......."+e.getMessage());
			}
			return erupiVoucherRevokeDetailsRequest;
		}



		@Override
		public List<ErupiVoucherCreatedDto> getErupiVoucherCreateDetailsList(
				ErupiVoucherCreatedRequest request) {
			
			return erupiVoucherInitiateDetailsDao.getVoucherCreationList(request.getOrgId());
		}
	    
	    
	   
}
