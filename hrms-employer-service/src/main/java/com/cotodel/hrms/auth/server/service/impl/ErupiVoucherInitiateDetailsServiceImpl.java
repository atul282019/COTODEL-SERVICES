package com.cotodel.hrms.auth.server.service.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import com.cotodel.hrms.auth.server.dto.ErupiVoucherSummaryDto;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherTxnRequest;
import com.cotodel.hrms.auth.server.dto.VoucherCreateRequest;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreateSummaryDto;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherRevokeDetailsSingleRequest;
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
			voucherCreateRequest.setMerchantId(request.getMerchantId());
			voucherCreateRequest.setSubMerchantId(request.getSubMerchantId());
			
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
					if(decryptedResponse.getStatus().equalsIgnoreCase("CREATE-SUCCESS")) {
					//erupiVoucherTxnDetailsEntity.setResponse(data.toString());
					request.setResponseApi(decryptedResponse.getMessage());
					erupiVoucherTxnDetailsEntity.setDetailsId(erupiVoucherInitiateDetailsEntity.getId());
					int updatework=erupiVoucherInitiateDetailsDao.updateWorkflowId(erupiVoucherInitiateDetailsEntity.getId(), 100003l);
					erupiVoucherTxnDetailsEntity.setWorkFlowId(100003l);
					erupiVoucherTxnDetailsEntity.setResponseJson(decryptedResponse.getApiResponse());
					erupiVoucherTxnDetailsEntity=setResponseValue(decryptedResponse,erupiVoucherTxnDetailsEntity);
					erupiVoucherTxnDetailsEntity=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity);
					}else {
						response=MessageConstant.RESPONSE_FAILED;
						request.setResponse(response);
						request.setResponseApi(decryptedResponse.getMessage());
						int updatework=erupiVoucherInitiateDetailsDao.updateWorkflowId(erupiVoucherInitiateDetailsEntity.getId(), 100004l);
						erupiVoucherTxnDetailsEntity.setWorkFlowId(100004l);
						erupiVoucherTxnDetailsEntity.setResponseJson(decryptedResponse.getApiResponse());
						erupiVoucherTxnDetailsEntity=setResponseValue(decryptedResponse,erupiVoucherTxnDetailsEntity);
						erupiVoucherTxnDetailsEntity=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity);
					}
					logger.info("erupiVoucherTxnDetailsEntity"+erupiVoucherTxnDetailsEntity);
				}else {
					//loginservice.sendEmailVerificationCompletion(userForm);
					//request.setCreateResponse(response1);
					response=MessageConstant.RESPONSE_FAILED;
					request.setResponse(response);
					erupiVoucherTxnDetailsEntity.setDetailsId(erupiVoucherInitiateDetailsEntity.getId());
					JSONObject data = profileJsonRes.getJSONObject("data");
					DecryptedResponse decryptedResponse= jsonToPOJO(data.toString());
					//decryptedResponse.getResponseCode()
					request.setResponseApi("Bad request some field are missing");
					//erupiVoucherTxnDetailsEntity.setResponse(data.toString());
					erupiVoucherTxnDetailsEntity.setWorkFlowId(100004l);
					erupiVoucherTxnDetailsEntity.setResponseJson(decryptedResponse.getApiResponse());
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
				
				erupiVoucherInitiateDetailsEntity=erupiVoucherInitiateDetailsDao.getErupiVoucherCreationDetails(request.getDetailsId());
				if(erupiVoucherInitiateDetailsEntity==null) {
					response=MessageConstant.DETAIL_ID;
					erupiVoucherRevokeDetailsRequest.setResponse(response);
					return erupiVoucherRevokeDetailsRequest;
				}
				
//				LocalDate eventDate = LocalDate.now();	
//				erupiVoucherInitiateDetailsEntity.setCreationDate(eventDate);
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
				voucherCreateRequest.setType("Revoke");
				//voucherCreateRequest.setMerchantId(request.getm);
				
				
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
						erupiVoucherRevokeDetailsRequest.setResponse(response);
						//
						JSONObject data = profileJsonRes.getJSONObject("data");
						DecryptedResponse decryptedResponse= jsonToPOJO(data.toString());
						
						if(decryptedResponse.getStatus().equalsIgnoreCase("REVOKE-SUCCESS")) {
							//erupiVoucherTxnDetailsEntity.setResponse(data.toString());
							request.setResponseApi(decryptedResponse.getMessage());
							erupiVoucherTxnDetailsEntity.setDetailsId(erupiVoucherInitiateDetailsEntity.getId());
							int updatework=erupiVoucherInitiateDetailsDao.updateWorkflowId(erupiVoucherInitiateDetailsEntity.getId(), 100003l);
							erupiVoucherTxnDetailsEntity.setWorkFlowId(100005l);
							erupiVoucherTxnDetailsEntity.setResponseJson(decryptedResponse.getApiResponse());
							erupiVoucherTxnDetailsEntity=setResponseValue(decryptedResponse,erupiVoucherTxnDetailsEntity);
							erupiVoucherTxnDetailsEntity=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity);
							}else {
								response=MessageConstant.RESPONSE_FAILED;
								request.setResponse(response);
								erupiVoucherRevokeDetailsRequest.setResponse(decryptedResponse.getMessage());
								request.setResponseApi(decryptedResponse.getMessage());
								//int updatework=erupiVoucherInitiateDetailsDao.updateWorkflowId(erupiVoucherInitiateDetailsEntity.getId(), 100003l);
								//erupiVoucherTxnDetailsEntity.setWorkFlowId(100003l);
								erupiVoucherTxnDetailsEntity.setResponseJson(decryptedResponse.getApiResponse());
								erupiVoucherTxnDetailsEntity=setResponseValue(decryptedResponse,erupiVoucherTxnDetailsEntity);
								erupiVoucherTxnDetailsEntity=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity);
							}
						
						
						
//						erupiVoucherTxnDetailsEntity.setResponse(data.toString());
//						erupiVoucherTxnDetailsEntity.setDetailsId(erupiVoucherInitiateDetailsEntity.getId());
//						erupiVoucherTxnDetailsEntity.setWorkFlowId(100005l);
//						erupiVoucherTxnDetailsEntity=setResponseValue(decryptedResponse,erupiVoucherTxnDetailsEntity);
//						erupiVoucherTxnDetailsEntity=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity);
						logger.info("erupiVoucherTxnDetailsEntity Revoke:"+erupiVoucherTxnDetailsEntity);
					}else {
						//loginservice.sendEmailVerificationCompletion(userForm);
						//request.setCreateResponse(response1);
						response=MessageConstant.RESPONSE_FAILED;
						request.setResponse(response);
						erupiVoucherRevokeDetailsRequest.setResponse(response);
						erupiVoucherTxnDetailsEntity.setDetailsId(erupiVoucherInitiateDetailsEntity.getId());
						JSONObject data = profileJsonRes.getJSONObject("data");
						DecryptedResponse decryptedResponse= jsonToPOJO(data.toString());
						//erupiVoucherTxnDetailsEntity.setResponse(data.toString());
						//erupiVoucherTxnDetailsEntity.setWorkFlowId(100004l);
						erupiVoucherTxnDetailsEntity.setResponseJson(decryptedResponse.getApiResponse());
						erupiVoucherTxnDetailsEntity=setResponseValue(decryptedResponse,erupiVoucherTxnDetailsEntity);
						erupiVoucherTxnDetailsEntity=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity);
						logger.info("erupiVoucherTxnDetailsEntity Revoke:"+erupiVoucherTxnDetailsEntity);
					}
					
			//	}
				}
				
				
				
			}catch (Exception e) {
				e.printStackTrace();
				log.error("Error in ErupiVoucherInitiateDetailsServiceImpl. Revoke:......"+e.getMessage());
			}
			return erupiVoucherRevokeDetailsRequest;
		}


		@Override
		public List<ErupiVoucherCreatedDto> getErupiVoucherCreateDetailsList(
				ErupiVoucherCreatedRequest request) {
					
			return erupiVoucherInitiateDetailsDao.getVoucherCreationList(request.getOrgId());
		}

		@Override
		public List<ErupiVoucherSummaryDto> getErupiVoucherSummaryList(ErupiVoucherCreatedRequest request) {
			 List<ErupiVoucherSummaryDto> voucherSummaryDTOList = new ArrayList<>();
			 //ErupiVoucherSummaryListDto erupiVoucherSummaryListDto=new ErupiVoucherSummaryListDto();
			try {
				List<Object[]> resultList = erupiVoucherInitiateDetailsDao.getVoucherSummary(request.getOrgId());
				  Long totalCount=0l;
				  Long totAmount=0l;
			        for (Object[] row : resultList) {
			            Long count = ((BigInteger) row[0]).longValue();          // count(1)
			            String type = (String) row[1];
			            Float totalAmount = (Float) row[2]; // SUM(amount)
			            String voucherName = (String) row[3]; // voucherdesc
			            Long totalAmt =totalAmount.longValue(); 
			            totalCount=totalCount+count;
			            totAmount=totAmount+totalAmt;
			            voucherSummaryDTOList.add(new ErupiVoucherSummaryDto(count,type,totalAmt, voucherName));
			        }

			        
			} catch (Exception e) {
				e.printStackTrace();
			}
			return voucherSummaryDTOList;
		}



		@Override
		public ErupiVoucherRevokeDetailsSingleRequest erupiVoucherRevokeSingleDetails(
				ErupiVoucherRevokeDetailsSingleRequest request) {
			String response="";
			log.info("Starting ErupiVoucherInitiateDetailsServiceImpl ... erupiVoucherRevokeSingleDetails..");
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

				VoucherCreateRequest voucherCreateRequest=new VoucherCreateRequest();
					
				//String merchantTranId=getMerTranId(request.getBankcode());
				voucherCreateRequest.setMerchantTranId(erupiVoucherTxnDetailsEntity.getMerchanttxnId());
				//voucherCreateRequest.setAmount(erupiVoucherInitiateDetailsEntity.getAmount().toString());
				voucherCreateRequest.setBeneficiaryID(erupiVoucherInitiateDetailsEntity.getBeneficiaryID());
				voucherCreateRequest.setMobileNumber(erupiVoucherInitiateDetailsEntity.getMobile());
				voucherCreateRequest.setBeneficiaryName(erupiVoucherInitiateDetailsEntity.getName());
				String formattedValue = String.format("%.2f", erupiVoucherInitiateDetailsEntity.getAmount());
				voucherCreateRequest.setAmount(formattedValue);
				String expdate=erupiVoucherInitiateDetailsEntity.getExpDate()==null?"":erupiVoucherInitiateDetailsEntity.getExpDate().toString();
				voucherCreateRequest.setExpiry(expdate);
				voucherCreateRequest.setPurposeCode(erupiVoucherInitiateDetailsEntity.getPurposeCode()==null?"":erupiVoucherInitiateDetailsEntity.getPurposeCode());
				voucherCreateRequest.setMcc(erupiVoucherInitiateDetailsEntity.getMcc());
				voucherCreateRequest.setVoucherRedemptionType(erupiVoucherInitiateDetailsEntity.getRedemtionType());
				voucherCreateRequest.setPayerVA(erupiVoucherInitiateDetailsEntity.getPayerVA());
				voucherCreateRequest.setType("Revoke");
				voucherCreateRequest.setMerchantId(erupiVoucherInitiateDetailsEntity.getMerchantId());
				voucherCreateRequest.setSubMerchantId(erupiVoucherInitiateDetailsEntity.getSubMerchantId());
				
				log.info("Starting voucher revoking single request ...."+erupiVoucherTxnDetailsEntity.getMerchanttxnId());	
				//erupiVoucherTxnDetailsEntity=setRequestValue(voucherCreateRequest, erupiVoucherTxnDetailsEntity);
					
					String response1 = CommonUtility.userRequest("", MessageConstant.gson.toJson(voucherCreateRequest),
							applicationConstantConfig.voucherServiceApiUrl+CommonUtils.sendVoucherCreate);
					log.info("Ending voucher revoking response1 ...."+response1);
					
					
					profileJsonRes= new JSONObject(response1);
					ErupiVoucherTxnRequest erupi=new ErupiVoucherTxnRequest();
					CopyUtility.copyProperties(erupiVoucherTxnDetailsEntity,erupi);
					ErupiVoucherTxnDetailsEntity erupiVoucherTxnDetailsEntity2=new ErupiVoucherTxnDetailsEntity();
					CopyUtility.copyProperties(erupi,erupiVoucherTxnDetailsEntity2);
					if(profileJsonRes.getBoolean("status")) { 
						//request.setCreateResponse(response1);
						response=MessageConstant.RESPONSE_SUCCESS;
						request.setResponse(response);
						
						JSONObject data = profileJsonRes.getJSONObject("data");
						DecryptedResponse decryptedResponse= jsonToPOJO(data.toString());
						
						
						if(decryptedResponse.getStatus().equalsIgnoreCase("REVOKE-SUCCESS")) {
							//erupiVoucherTxnDetailsEntity.setResponse(data.toString());
							request.setResponseApi(decryptedResponse.getMessage());
							//erupiVoucherTxnDetailsEntity.setId(null);
							int updatework=erupiVoucherInitiateDetailsDao.updateWorkflowId(erupiVoucherInitiateDetailsEntity.getId(), 100005l);
							erupiVoucherTxnDetailsEntity2.setWorkFlowId(100005l);
							erupiVoucherTxnDetailsEntity2.setResponseJson(decryptedResponse.getApiResponse());
							erupiVoucherTxnDetailsEntity2=setResponseValue(decryptedResponse,erupiVoucherTxnDetailsEntity2);
							erupiVoucherTxnDetailsEntity2=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity2);
							}else {
								response=MessageConstant.RESPONSE_FAILED;
								request.setResponse(response);
								//erupiVoucherRevokeDetailsRequest.setResponse(decryptedResponse.getMessage());
								request.setResponseApi(decryptedResponse.getMessage());
								//int updatework=erupiVoucherInitiateDetailsDao.updateWorkflowId(erupiVoucherInitiateDetailsEntity.getId(), 100003l);
								//erupiVoucherTxnDetailsEntity.setWorkFlowId(100003l);
								//erupiVoucherTxnDetailsEntity.setId(null);
								erupiVoucherTxnDetailsEntity2.setResponseJson(decryptedResponse.getApiResponse());
								erupiVoucherTxnDetailsEntity2=setResponseValue(decryptedResponse,erupiVoucherTxnDetailsEntity2);
								erupiVoucherTxnDetailsEntity2=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity2);
							}
						
						logger.info("erupiVoucherTxnDetailsEntity Revoke:"+erupiVoucherTxnDetailsEntity);
					}else {
						//loginservice.sendEmailVerificationCompletion(userForm);
						//request.setCreateResponse(response1);
						response=MessageConstant.RESPONSE_FAILED;
						request.setResponse(response);
						//erupiVoucherRevokeDetailsRequest.setResponse(response);
						//erupiVoucherTxnDetailsEntity.setId(null);
						
						JSONObject data = profileJsonRes.getJSONObject("data");
						DecryptedResponse decryptedResponse= jsonToPOJO(data.toString());
						//erupiVoucherTxnDetailsEntity.setResponse(data.toString());
						//erupiVoucherTxnDetailsEntity.setWorkFlowId(100004l);
						erupiVoucherTxnDetailsEntity2.setResponseJson(decryptedResponse.getApiResponse());
						erupiVoucherTxnDetailsEntity2=setResponseValue(decryptedResponse,erupiVoucherTxnDetailsEntity2);
						//erupiVoucherTxnDetailsEntity
						erupiVoucherTxnDetailsEntity2=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity2);
						logger.info("erupiVoucherTxnDetailsEntity Revoke:"+erupiVoucherTxnDetailsEntity2);
					}
					
		
				
				
				
			}catch (Exception e) {
				e.printStackTrace();
				log.error("Error in ErupiVoucherInitiateDetailsServiceImpl. Revoke:......"+e.getMessage());
			}
			return request;
		}



		@Override
		public ErupiVoucherRevokeDetailsSingleRequest erupiVoucherRedemDetails(
				ErupiVoucherRevokeDetailsSingleRequest request) {
			String response="";
			log.info("Starting ErupiVoucherInitiateDetailsServiceImpl ... erupiVoucherRedemDetails..");
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

				VoucherCreateRequest voucherCreateRequest=new VoucherCreateRequest();
					
				//String merchantTranId=getMerTranId(request.getBankcode());
				voucherCreateRequest.setMerchantTranId(erupiVoucherTxnDetailsEntity.getMerchanttxnId());
				//voucherCreateRequest.setAmount(erupiVoucherInitiateDetailsEntity.getAmount().toString());
				voucherCreateRequest.setBeneficiaryID(erupiVoucherInitiateDetailsEntity.getBeneficiaryID());
				voucherCreateRequest.setMobileNumber(erupiVoucherInitiateDetailsEntity.getMobile());
				voucherCreateRequest.setBeneficiaryName(erupiVoucherInitiateDetailsEntity.getName());
				String formattedValue = String.format("%.2f", erupiVoucherInitiateDetailsEntity.getAmount());
				voucherCreateRequest.setAmount(formattedValue);
				String expdate=erupiVoucherInitiateDetailsEntity.getExpDate()==null?"":erupiVoucherInitiateDetailsEntity.getExpDate().toString();
				voucherCreateRequest.setExpiry(expdate);
				voucherCreateRequest.setPurposeCode(erupiVoucherInitiateDetailsEntity.getPurposeCode()==null?"":erupiVoucherInitiateDetailsEntity.getPurposeCode());
				voucherCreateRequest.setMcc(erupiVoucherInitiateDetailsEntity.getMcc());
				voucherCreateRequest.setVoucherRedemptionType(erupiVoucherInitiateDetailsEntity.getRedemtionType());
				voucherCreateRequest.setPayerVA(erupiVoucherInitiateDetailsEntity.getPayerVA());
				voucherCreateRequest.setType("Revoke");
				voucherCreateRequest.setMerchantId(erupiVoucherInitiateDetailsEntity.getMerchantId());
				voucherCreateRequest.setSubMerchantId(erupiVoucherInitiateDetailsEntity.getSubMerchantId());
				
				log.info("Starting voucher revoking single request ...."+erupiVoucherTxnDetailsEntity.getMerchanttxnId());	
				//erupiVoucherTxnDetailsEntity=setRequestValue(voucherCreateRequest, erupiVoucherTxnDetailsEntity);
					
					String response1 = CommonUtility.userRequest("", MessageConstant.gson.toJson(voucherCreateRequest),
							applicationConstantConfig.voucherServiceApiUrl+CommonUtils.sendVoucherCreate);
					log.info("Ending voucher revoking response1 ...."+response1);
					
					
					profileJsonRes= new JSONObject(response1);
					ErupiVoucherTxnRequest erupi=new ErupiVoucherTxnRequest();
					CopyUtility.copyProperties(erupiVoucherTxnDetailsEntity,erupi);
					ErupiVoucherTxnDetailsEntity erupiVoucherTxnDetailsEntity2=new ErupiVoucherTxnDetailsEntity();
					CopyUtility.copyProperties(erupi,erupiVoucherTxnDetailsEntity2);
					if(profileJsonRes.getBoolean("status")) { 
						//request.setCreateResponse(response1);
						response=MessageConstant.RESPONSE_SUCCESS;
						request.setResponse(response);
						
						JSONObject data = profileJsonRes.getJSONObject("data");
						DecryptedResponse decryptedResponse= jsonToPOJO(data.toString());
						
						
						if(decryptedResponse.getStatus().equalsIgnoreCase("REVOKE-SUCCESS")) {
							//erupiVoucherTxnDetailsEntity.setResponse(data.toString());
							request.setResponseApi(decryptedResponse.getMessage());
							//erupiVoucherTxnDetailsEntity.setId(null);
							int updatework=erupiVoucherInitiateDetailsDao.updateWorkflowId(erupiVoucherInitiateDetailsEntity.getId(), 100005l);
							erupiVoucherTxnDetailsEntity2.setWorkFlowId(100005l);
							erupiVoucherTxnDetailsEntity2.setResponseJson(decryptedResponse.getApiResponse());
							erupiVoucherTxnDetailsEntity2=setResponseValue(decryptedResponse,erupiVoucherTxnDetailsEntity2);
							erupiVoucherTxnDetailsEntity2=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity2);
							}else {
								response=MessageConstant.RESPONSE_FAILED;
								request.setResponse(response);
								//erupiVoucherRevokeDetailsRequest.setResponse(decryptedResponse.getMessage());
								request.setResponseApi(decryptedResponse.getMessage());
								//int updatework=erupiVoucherInitiateDetailsDao.updateWorkflowId(erupiVoucherInitiateDetailsEntity.getId(), 100003l);
								//erupiVoucherTxnDetailsEntity.setWorkFlowId(100003l);
								//erupiVoucherTxnDetailsEntity.setId(null);
								erupiVoucherTxnDetailsEntity2.setResponseJson(decryptedResponse.getApiResponse());
								erupiVoucherTxnDetailsEntity2=setResponseValue(decryptedResponse,erupiVoucherTxnDetailsEntity2);
								erupiVoucherTxnDetailsEntity2=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity2);
							}
						
						logger.info("erupiVoucherTxnDetailsEntity Revoke:"+erupiVoucherTxnDetailsEntity);
					}else {
						//loginservice.sendEmailVerificationCompletion(userForm);
						//request.setCreateResponse(response1);
						response=MessageConstant.RESPONSE_FAILED;
						request.setResponse(response);
						//erupiVoucherRevokeDetailsRequest.setResponse(response);
						//erupiVoucherTxnDetailsEntity.setId(null);
						
						JSONObject data = profileJsonRes.getJSONObject("data");
						DecryptedResponse decryptedResponse= jsonToPOJO(data.toString());
						//erupiVoucherTxnDetailsEntity.setResponse(data.toString());
						//erupiVoucherTxnDetailsEntity.setWorkFlowId(100004l);
						erupiVoucherTxnDetailsEntity2=setResponseValue(decryptedResponse,erupiVoucherTxnDetailsEntity2);
						//erupiVoucherTxnDetailsEntity
						erupiVoucherTxnDetailsEntity2.setResponseJson(decryptedResponse.getApiResponse());
						erupiVoucherTxnDetailsEntity2=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity2);
						logger.info("erupiVoucherTxnDetailsEntity Redem:"+erupiVoucherTxnDetailsEntity2);
					}
					
		
				
				
				
			}catch (Exception e) {
				e.printStackTrace();
				log.error("Error in erupiVoucherRedemDetails.:......"+e.getMessage());
			}
			return request;
		}
	    
		@Override
		public List<ErupiVoucherCreateSummaryDto> getErupiVoucherCreateSummaryList(ErupiVoucherCreatedRequest request) {
			 List<ErupiVoucherCreateSummaryDto> voucherSummaryDTOList = new ArrayList<>();
			try {
				List<Object[]> resultList = erupiVoucherInitiateDetailsDao.getVoucherCreateSummary(request.getOrgId());
				  Long totalCount=0l;
				  Long totAmount=0l;
			        for (Object[] row : resultList) {
			            Long count = ((BigInteger) row[0]).longValue();			           
			            Float totalAmount = (Float) row[1]; // SUM(amount)
			            String voucherName = (String) row[2]; // voucherdesc
			            Long totalAmt =totalAmount.longValue(); 
			            totalCount=totalCount+count;
			            totAmount=totAmount+totalAmt;
			            voucherSummaryDTOList.add(new ErupiVoucherCreateSummaryDto(count,totalAmt, voucherName));
			        }
//			        erupiVoucherSummaryListDto.setData(voucherSummaryDTOList);
//			        erupiVoucherSummaryListDto.setTotalCount(totalCount);
//			        erupiVoucherSummaryListDto.setTotalAmount(totAmount);
			        
			} catch (Exception e) {
				e.printStackTrace();
			}
			return voucherSummaryDTOList;
		}
	   
}
