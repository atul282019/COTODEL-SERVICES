package com.cotodel.hrms.auth.server.service.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.BankMasterDao;
import com.cotodel.hrms.auth.server.dao.ErupiVoucherInitiateDetailsDao;
import com.cotodel.hrms.auth.server.dao.ErupiVoucherRequestDao;
import com.cotodel.hrms.auth.server.dao.ErupiVoucherTxnDao;
import com.cotodel.hrms.auth.server.dao.LinkSubMultipleAccountDao;
import com.cotodel.hrms.auth.server.dao.SecurityClientAndSecretDao;
import com.cotodel.hrms.auth.server.dto.DecryptedResponse;
import com.cotodel.hrms.auth.server.dto.DirectorOnboardingRequest;
import com.cotodel.hrms.auth.server.dto.ErupiMultipleVoucherCreateRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherBankListDto;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreateDetailsRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedDateWiseDto;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedDateWiseRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedDto;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherRevokeDetailsRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherRevokeRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherSingleCreateDetailsRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherSummaryDto;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherTotalDetailDto;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherTxnRequest;
import com.cotodel.hrms.auth.server.dto.LinkMultipleAccountUpdate;
import com.cotodel.hrms.auth.server.dto.VoucherCreateRequest;
import com.cotodel.hrms.auth.server.dto.indianbank.ErupiIndianBankVoucherRevokeRequest;
import com.cotodel.hrms.auth.server.dto.indianbank.IndianBankVoucherCreateResponse;
import com.cotodel.hrms.auth.server.dto.indianbank.InputParamRRequest;
import com.cotodel.hrms.auth.server.dto.indianbank.VoucherCreateIndianBankRequest;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreateListRequest;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreateOldDto;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreateSummaryDto;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherRedemeRequest;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherRevokeDetailsSingleRequest;
import com.cotodel.hrms.auth.server.model.ErupiBankMasterEntity;
import com.cotodel.hrms.auth.server.model.ErupiLinkAccountEntity;
import com.cotodel.hrms.auth.server.model.ErupiVoucherCreationDetailsEntity;
import com.cotodel.hrms.auth.server.model.ErupiVoucherCreationRequestEntity;
import com.cotodel.hrms.auth.server.model.ErupiVoucherTxnDetailsEntity;
import com.cotodel.hrms.auth.server.model.LinkSubAccountMultipleEntity;
import com.cotodel.hrms.auth.server.model.SecurityClientAndSecretEntity;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.ErupiLinkAccountService;
import com.cotodel.hrms.auth.server.service.ErupiVoucherInitiateDetailsService;
import com.cotodel.hrms.auth.server.service.LinkMultipleAccountService;
import com.cotodel.hrms.auth.server.util.CommonUtility;
import com.cotodel.hrms.auth.server.util.CommonUtils;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;
import com.cotodel.hrms.auth.server.util.ValidateConstants;
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
    private BankMasterDao bankMasterDao;
	
	@Autowired
    private LinkSubMultipleAccountDao linkSubMultipleAccountDao;
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
	@Autowired
	LinkMultipleAccountService linkMultipleAccountService;
	
	@Autowired
	ErupiLinkAccountService erupiLinkAccountService;
	
	@Autowired
	ErupiVoucherRequestDao  erupiVoucherRequestDao;
	
	@Autowired
	SecurityClientAndSecretDao  secretDao;
	
	@Override
	public ErupiVoucherCreateDetailsRequest saveErupiVoucherInitiateDetails(ErupiVoucherCreateDetailsRequest request) {
		String response="";
		log.info("Starting ErupiVoucherInitiateDetailsServiceImpl ... saveErupiVoucherInitiateDetails..");
		ErupiVoucherCreationDetailsEntity erupiVoucherInitiateDetailsEntity=null;
		ErupiVoucherTxnDetailsEntity erupiVoucherTxnDetailsEntity=null;
		JSONObject profileJsonRes=null;
		try {
			String merchantTranId=getMerTranId(request.getBankcode());
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);	
			if(request.getMandateType()!=null && request.getMandateType().equalsIgnoreCase("04")) {
				if(request.getPayeeVPA()==null || request.getPayeeVPA().equalsIgnoreCase("")) {
					response=MessageConstant.RESPONSE_FAILED;
					request.setResponse(response);
					request.setResponseApi(MessageConstant.PAYEEVPA);
					return request;
				}
				
			}
			//request.setMandateType("01");
			//request.setPayeeVPA("invaciauat@prepaidicici");
			erupiVoucherInitiateDetailsEntity=new ErupiVoucherCreationDetailsEntity();
			erupiVoucherTxnDetailsEntity=new ErupiVoucherTxnDetailsEntity();
			CopyUtility.copyProperties(request,erupiVoucherInitiateDetailsEntity);
			CopyUtility.copyProperties(request,erupiVoucherTxnDetailsEntity);
			LocalDate eventDate = LocalDate.now();	
			erupiVoucherInitiateDetailsEntity.setCreationDate(eventDate);
			erupiVoucherInitiateDetailsEntity.setWorkFlowId(100001l);
			erupiVoucherInitiateDetailsEntity.setMerchanttxnid(merchantTranId);
			
			erupiVoucherInitiateDetailsEntity=erupiVoucherInitiateDetailsDao.saveDetails(erupiVoucherInitiateDetailsEntity);
			if(erupiVoucherInitiateDetailsEntity!=null) {
			VoucherCreateRequest voucherCreateRequest=new VoucherCreateRequest();
				
			
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
			voucherCreateRequest.setMandateType(request.getMandateType());
			voucherCreateRequest.setPayeeVPA(request.getPayeeVPA());
			log.info("Starting voucher create request ...."+merchantTranId);	
			erupiVoucherTxnDetailsEntity=setRequestValue(voucherCreateRequest, erupiVoucherTxnDetailsEntity);
				
				String response1 = CommonUtility.userRequestWiout("", MessageConstant.gson.toJson(voucherCreateRequest),
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
						erupiVoucherTxnDetailsEntity.setDetailsId(erupiVoucherInitiateDetailsEntity.getId());
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
		}catch (DataIntegrityViolationException ex) {
	            // Handle the specific exception here
	           // throw new CustomVoucherException("Voucher creation failed: " + ex.getMessage(), ex);
	        request.setResponseApi("exception DataException");
		}catch (Exception e) {
			e.printStackTrace();
			request.setResponseApi("exception:Bad request some field are missing");
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
				voucherCreateRequest.setMandateType(request.getMandateType());
				voucherCreateRequest.setPayeeVPA(request.getPayeeVPA());
				//voucherCreateRequest.setMerchantId(request.getm);
				
				
				log.info("Starting voucher create request ...."+request.getMerchanttxnid());	
				erupiVoucherTxnDetailsEntity=setRequestValue(voucherCreateRequest, erupiVoucherTxnDetailsEntity);
					
					String response1 = CommonUtility.userRequestWiout("", MessageConstant.gson.toJson(voucherCreateRequest),
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
			
			
			String query="";
			LocalDate today = LocalDate.now();
			LocalDate startDate =null;
			LocalDate endDate =null;
			//CM--Current Month
			//LM--Last Month
			//CFY--Current Financial Year
			//LFY--Last Financial Year
			//AH-All History
			if(request.getTimePeriod()==null) {
				startDate = today;
			    endDate = today;
			}else if(request.getTimePeriod().equalsIgnoreCase("CM")) {				 
			    startDate = today.with(TemporalAdjusters.firstDayOfMonth());
			    endDate = today.with(TemporalAdjusters.lastDayOfMonth());
			}else if(request.getTimePeriod().equalsIgnoreCase("LM")) {				 
			    startDate = today.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());;
			    endDate = today.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
			}else if(request.getTimePeriod().equalsIgnoreCase("CFY")){
				int currentYear = today.getYear();
		        // Check if today is before April 1st, if yes, the financial year is last year to current year
		        if (today.isBefore(LocalDate.of(currentYear, Month.APRIL, 1))) {
		        	startDate = LocalDate.of(currentYear - 1, Month.APRIL, 1);
		        	endDate = LocalDate.of(currentYear, Month.MARCH, 31);
		        } else {
		            // Otherwise, the financial year is from this year to next year
		        	startDate = LocalDate.of(currentYear, Month.APRIL, 1);
		            endDate = LocalDate.of(currentYear + 1, Month.MARCH, 31);
		        }
			}else if(request.getTimePeriod().equalsIgnoreCase("LFY")) {
				int currentYear = today.getYear();   
		       		        
		        // Check if today is before April 1st, if yes, the last financial year is the previous year's start to end
		        if (today.isBefore(LocalDate.of(currentYear, Month.APRIL, 1))) {
		        	startDate = LocalDate.of(currentYear - 2, Month.APRIL, 1);
		        	endDate = LocalDate.of(currentYear - 1, Month.MARCH, 31);
		        } else {
		            // Otherwise, the last financial year was from the previous year (April to March)
		        	startDate = LocalDate.of(currentYear - 1, Month.APRIL, 1);
		        	endDate = LocalDate.of(currentYear - 1, Month.MARCH, 31);
		        }
			}else if(request.getTimePeriod().equalsIgnoreCase("AH")) {
				int currentYear = today.getYear();		        
		       		        
		        
		            // Otherwise, the last financial year was from the previous year (April to March)
		        	startDate =LocalDate.of(2024 - 1, Month.APRIL, 1);
		        	endDate =today  ;
		        
			}
			else {
				startDate = today;
			    endDate = today;
			}
			System.out.println("startDate:"+startDate);
			System.out.println("endDate:"+endDate);
			List<ErupiVoucherCreatedDto> erupiVoucherCreatedDtos= erupiVoucherInitiateDetailsDao.getVoucherCreationList(request.getOrgId(),startDate,endDate);
			for (ErupiVoucherCreatedDto erupiVoucherCreatedDto : erupiVoucherCreatedDtos) {
				ErupiBankMasterEntity erBankMasterEntity=bankMasterDao.getDetails(erupiVoucherCreatedDto.getBankcode());
				String accNumber=replaceExceptLastFour(erupiVoucherCreatedDto.getAccountNumber());
				erupiVoucherCreatedDto.setAccountNumber(accNumber);
				if(erBankMasterEntity==null) {
					erupiVoucherCreatedDto.setBankIcon(null);
				}else {
					erupiVoucherCreatedDto.setBankIcon(erBankMasterEntity.getBankLogo());
				}
				
			}
			return erupiVoucherCreatedDtos;
			
		}
		
		public static String replaceExceptLastFour(String input) {
	        if (input != null && input.length() > 3) {
	            StringBuilder prefix = new StringBuilder();
	            for (int i = 0; i < input.length() - 4; i++) {
	                prefix.append("x");
	            }
	            String suffix = input.substring(input.length() - 4);
	            return prefix.toString() + suffix;
	        }
	        return input;
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
				if(erupiVoucherInitiateDetailsEntity.getBankcode().equalsIgnoreCase("INDB")) {
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
					voucherCreateRequest.setMandateType(erupiVoucherInitiateDetailsEntity.getMandateType());
					voucherCreateRequest.setPayeeVPA(erupiVoucherInitiateDetailsEntity.getPayeeVPA());
					log.info("Starting voucher revoking single request ...."+erupiVoucherTxnDetailsEntity.getMerchanttxnId());	
					//erupiVoucherTxnDetailsEntity=setRequestValue(voucherCreateRequest, erupiVoucherTxnDetailsEntity);
						
					ErupiIndianBankVoucherRevokeRequest eruRequest=new ErupiIndianBankVoucherRevokeRequest();
					eruRequest.setAction("revoke");
					eruRequest.setSubaction("revoke");
					eruRequest.setEntityId("INB");
					InputParamRRequest inputparam=new InputParamRRequest();
					inputparam.setTxnrefID(erupiVoucherTxnDetailsEntity.getTxnRefId());
					inputparam.setUmn(erupiVoucherTxnDetailsEntity.getUmn());
					inputparam.setOrgTxnId("");
					inputparam.setOrgTxnDate(erupiVoucherTxnDetailsEntity.getTxnDateTime());
					inputparam.setOrgTxnAmount(erupiVoucherInitiateDetailsEntity.getAmount().toString());
					inputparam.setTxndatetime(erupiVoucherTxnDetailsEntity.getTxnDateTime());
					inputparam.setCheckSum("");
					eruRequest.setInputparam(inputparam);
						String response1 = CommonUtility.userRequestWiout("", MessageConstant.gson.toJson(eruRequest),
								applicationConstantConfig.voucherServiceApiUrl+CommonUtils.sendIndianVoucherRevoke);
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
							//DecryptedResponse decryptedResponse= jsonToPOJO(data.toString());
							IndianBankVoucherCreateResponse decryptedResponse= jsonToPojoIndianBank(data.toString());
							
							if(decryptedResponse.getTxnStatus().equalsIgnoreCase("00")) {
								//erupiVoucherTxnDetailsEntity.setResponse(data.toString());
								request.setResponseApi(decryptedResponse.getTxnMsg());
								//erupiVoucherTxnDetailsEntity.setId(null);
								int updatework=erupiVoucherInitiateDetailsDao.updateWorkflowId(erupiVoucherInitiateDetailsEntity.getId(), 100005l);
								erupiVoucherTxnDetailsEntity2.setWorkFlowId(100005l);
								erupiVoucherTxnDetailsEntity2.setResponseJson(data.toString());
								erupiVoucherTxnDetailsEntity2=setResponseValueIndianBank(decryptedResponse,erupiVoucherTxnDetailsEntity2);
								erupiVoucherTxnDetailsEntity2.setOrgId(erupiVoucherInitiateDetailsEntity.getOrgId());
								erupiVoucherTxnDetailsEntity2=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity2);
								//
								LinkSubAccountMultipleEntity liEntity=linkSubMultipleAccountDao.getLinkMultipleAccountByAccNoOrgId(erupiVoucherInitiateDetailsEntity.getAccountNumber(), erupiVoucherInitiateDetailsEntity.getOrgId());
								if(liEntity!=null) {
									LinkMultipleAccountUpdate linkMultipleAccountUpdate=new LinkMultipleAccountUpdate();
									linkMultipleAccountUpdate.setId(liEntity.getId());
									linkMultipleAccountUpdate.setAmount(erupiVoucherInitiateDetailsEntity.getAmount());
									linkMultipleAccountUpdate.setOrgId(liEntity.getOrgId());
									linkMultipleAccountUpdate.setMerchantId(erupiVoucherInitiateDetailsEntity.getMerchanttxnid());
									linkMultipleAccountUpdate.setAcNumber(erupiVoucherInitiateDetailsEntity.getAccountNumber());
									linkMultipleAccountUpdate.setMobile(erupiVoucherInitiateDetailsEntity.getMobile());
									LinkMultipleAccountUpdate update=linkMultipleAccountService.saveMultipleAccountOrUpdateCr(linkMultipleAccountUpdate);
								}
								}else {
									response=MessageConstant.RESPONSE_FAILED;
									request.setResponse(response);
									//erupiVoucherRevokeDetailsRequest.setResponse(decryptedResponse.getMessage());
									request.setResponseApi(decryptedResponse.getTxnMsg());
									//int updatework=erupiVoucherInitiateDetailsDao.updateWorkflowId(erupiVoucherInitiateDetailsEntity.getId(), 100003l);
									//erupiVoucherTxnDetailsEntity.setWorkFlowId(100003l);
									//erupiVoucherTxnDetailsEntity.setId(null);
									erupiVoucherTxnDetailsEntity2.setWorkFlowId(100012l);
									erupiVoucherTxnDetailsEntity2.setVoucherType("Revoke");
									erupiVoucherTxnDetailsEntity2.setOrgId(erupiVoucherInitiateDetailsEntity.getOrgId());
									erupiVoucherTxnDetailsEntity2.setResponseJson(data.toString());
									erupiVoucherTxnDetailsEntity2=setResponseValueIndianBank(decryptedResponse,erupiVoucherTxnDetailsEntity2);
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
							//DecryptedResponse decryptedResponse= jsonToPOJO(data.toString());
							IndianBankVoucherCreateResponse decryptedResponse= jsonToPojoIndianBank(data.toString());
							//erupiVoucherTxnDetailsEntity.setResponse(data.toString());
							//erupiVoucherTxnDetailsEntity.setWorkFlowId(100004l);
							erupiVoucherTxnDetailsEntity2.setWorkFlowId(100012l);
							erupiVoucherTxnDetailsEntity2.setOrgId(erupiVoucherInitiateDetailsEntity.getOrgId());
							erupiVoucherTxnDetailsEntity2.setResponseJson(data.toString());
							erupiVoucherTxnDetailsEntity2=setResponseValueIndianBank(decryptedResponse,erupiVoucherTxnDetailsEntity2);
							//erupiVoucherTxnDetailsEntity
							erupiVoucherTxnDetailsEntity2=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity2);
							logger.info("erupiVoucherTxnDetailsEntity Revoke:"+erupiVoucherTxnDetailsEntity2);
						}
				}else {
					
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
				voucherCreateRequest.setMandateType(erupiVoucherInitiateDetailsEntity.getMandateType());
				voucherCreateRequest.setPayeeVPA(erupiVoucherInitiateDetailsEntity.getPayeeVPA());
				log.info("Starting voucher revoking single request ...."+erupiVoucherTxnDetailsEntity.getMerchanttxnId());	
				//erupiVoucherTxnDetailsEntity=setRequestValue(voucherCreateRequest, erupiVoucherTxnDetailsEntity);
					
					String response1 = CommonUtility.userRequestWiout("", MessageConstant.gson.toJson(voucherCreateRequest),
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
							erupiVoucherTxnDetailsEntity2.setOrgId(erupiVoucherInitiateDetailsEntity.getOrgId());
							erupiVoucherTxnDetailsEntity2=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity2);
							LinkSubAccountMultipleEntity liEntity=linkSubMultipleAccountDao.getLinkMultipleAccountByAccNoOrgId(erupiVoucherInitiateDetailsEntity.getAccountNumber(), erupiVoucherInitiateDetailsEntity.getOrgId());
							if(liEntity!=null) {
								LinkMultipleAccountUpdate linkMultipleAccountUpdate=new LinkMultipleAccountUpdate();
								linkMultipleAccountUpdate.setId(liEntity.getId());
								linkMultipleAccountUpdate.setAmount(erupiVoucherInitiateDetailsEntity.getAmount());
								linkMultipleAccountUpdate.setOrgId(liEntity.getOrgId());
								linkMultipleAccountUpdate.setMerchantId(erupiVoucherInitiateDetailsEntity.getMerchanttxnid());
								linkMultipleAccountUpdate.setAcNumber(erupiVoucherInitiateDetailsEntity.getAccountNumber());
								linkMultipleAccountUpdate.setMobile(erupiVoucherInitiateDetailsEntity.getMobile());
								LinkMultipleAccountUpdate update=linkMultipleAccountService.saveMultipleAccountOrUpdateCr(linkMultipleAccountUpdate);
							}
							}else {
								response=MessageConstant.RESPONSE_FAILED;
								request.setResponse(response);
								//erupiVoucherRevokeDetailsRequest.setResponse(decryptedResponse.getMessage());
								request.setResponseApi(decryptedResponse.getMessage());
								//int updatework=erupiVoucherInitiateDetailsDao.updateWorkflowId(erupiVoucherInitiateDetailsEntity.getId(), 100003l);
								//erupiVoucherTxnDetailsEntity.setWorkFlowId(100003l);
								//erupiVoucherTxnDetailsEntity.setId(null);
								erupiVoucherTxnDetailsEntity2.setWorkFlowId(100012l);
								erupiVoucherTxnDetailsEntity2.setVoucherType("Revoke");
								erupiVoucherTxnDetailsEntity2.setOrgId(erupiVoucherInitiateDetailsEntity.getOrgId());
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
						erupiVoucherTxnDetailsEntity2.setWorkFlowId(100012l);
						erupiVoucherTxnDetailsEntity2.setOrgId(erupiVoucherInitiateDetailsEntity.getOrgId());
						erupiVoucherTxnDetailsEntity2.setResponseJson(decryptedResponse.getApiResponse());
						erupiVoucherTxnDetailsEntity2=setResponseValue(decryptedResponse,erupiVoucherTxnDetailsEntity2);
						//erupiVoucherTxnDetailsEntity
						erupiVoucherTxnDetailsEntity2=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity2);
						logger.info("erupiVoucherTxnDetailsEntity Revoke:"+erupiVoucherTxnDetailsEntity2);
					}
					
			}
				
				
				
			}catch (Exception e) {
				e.printStackTrace();
				log.error("Error in ErupiVoucherInitiateDetailsServiceImpl. Revoke:......"+e.getMessage());
			}
			return request;
		}



		@Override
		public String erupiVoucherRedemDetails(ErupiVoucherRedemeRequest request) {
			String response="";
			log.info("Starting ErupiVoucherInitiateDetailsServiceImpl ... erupiVoucherRedemDetails..");
			ErupiVoucherCreationDetailsEntity erupiVoucherInitiateDetailsEntity=null;
			ErupiVoucherTxnDetailsEntity erupiVoucherTxnDetailsEntity=null;
			JSONObject profileJsonRes=null;
			//String response=""
			try {
				
				response=MessageConstant.RESPONSE_FAILED;
				//request.setResponse(response);	
				erupiVoucherInitiateDetailsEntity=new ErupiVoucherCreationDetailsEntity();
				erupiVoucherTxnDetailsEntity=new ErupiVoucherTxnDetailsEntity();
				
				erupiVoucherInitiateDetailsEntity=erupiVoucherInitiateDetailsDao.getCreationDetailsByTransactionId(request.getMerchantTranId());
				
				
				if(erupiVoucherInitiateDetailsEntity!=null) {
					if(request.getTxnStatus().equalsIgnoreCase("SUCCESS")) {
						int updatework=erupiVoucherInitiateDetailsDao.updateWorkflowId(erupiVoucherInitiateDetailsEntity.getId(), 100007l);
					}
				}
				CopyUtility.copyProperties(erupiVoucherInitiateDetailsEntity,erupiVoucherTxnDetailsEntity);
				erupiVoucherTxnDetailsEntity.setId(null);
				erupiVoucherTxnDetailsEntity.setDetailsId(erupiVoucherInitiateDetailsEntity.getId());
				erupiVoucherTxnDetailsEntity.setResponseJson(MessageConstant.gson.toJson(request));
				
				if(request.getTxnStatus().equalsIgnoreCase("SUCCESS")) {
					erupiVoucherTxnDetailsEntity=setResponseRedemValue(request,erupiVoucherTxnDetailsEntity);
					erupiVoucherTxnDetailsEntity.setWorkFlowId(100007l);
					erupiVoucherTxnDetailsEntity=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity);
					response=MessageConstant.RESPONSE_SUCCESS;
				}else {
					erupiVoucherTxnDetailsEntity=setResponseRedemValue(request,erupiVoucherTxnDetailsEntity);
					erupiVoucherTxnDetailsEntity.setWorkFlowId(100011l);
					erupiVoucherTxnDetailsEntity=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity);
					response=MessageConstant.RESPONSE_FAILED;
				}

				
			}catch (Exception e) {
				e.printStackTrace();
				log.error("Error in erupiVoucherRedemDetails.:......"+e.getMessage());
			}
			return response;
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
			            byte[] voucherIcon=( byte[])row[3];
			            Long totalAmt =totalAmount.longValue(); 
			            totalCount=totalCount+count;
			            totAmount=totAmount+totalAmt;
			            voucherSummaryDTOList.add(new ErupiVoucherCreateSummaryDto(count,totalAmt, voucherName,voucherIcon));
			        }
//			        erupiVoucherSummaryListDto.setData(voucherSummaryDTOList);

			        
			} catch (Exception e) {
				e.printStackTrace();
			}
			return voucherSummaryDTOList;
		}
		private ErupiVoucherTxnDetailsEntity setResponseRedemValue(ErupiVoucherRedemeRequest decryptedResponse,ErupiVoucherTxnDetailsEntity erupiVoucherTxnDetailsEntity) {

			erupiVoucherTxnDetailsEntity.setUmn(decryptedResponse.getUmn());
	    	LocalDateTime eventDateTime = LocalDateTime.now();	
	    	erupiVoucherTxnDetailsEntity.setCreationDate(eventDateTime);
	    	erupiVoucherTxnDetailsEntity.setResponseCode(decryptedResponse.getResponseCode());
	    	erupiVoucherTxnDetailsEntity.setBankrrn(decryptedResponse.getBankRRN());
	    	String txnInitDate=decryptedResponse.getTxnInitDate();
	    	txnInitDate = txnInitDate.split("\\.")[0];
	    	String txnCompletionDate=decryptedResponse.getTxnCompletionDate();
	    	txnCompletionDate = txnCompletionDate.split("\\.")[0];
	    	 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");	         
	         LocalDateTime localIniDateTime = LocalDateTime.parse(txnInitDate, formatter);
	         LocalDateTime localCompleteDateTime = LocalDateTime.parse(txnCompletionDate, formatter);
	         erupiVoucherTxnDetailsEntity.setTxninitdate(localIniDateTime);
	         erupiVoucherTxnDetailsEntity.setTxncompletiondate(localCompleteDateTime);
	         erupiVoucherTxnDetailsEntity.setStatusApi(decryptedResponse.getTxnStatus());
	         erupiVoucherTxnDetailsEntity.setMerchantId(decryptedResponse.getMerchantId());
	         erupiVoucherTxnDetailsEntity.setSubMerchantId(decryptedResponse.getSubMerchantId());
	         erupiVoucherTxnDetailsEntity.setMerchanttxnId(decryptedResponse.getMerchantTranId());
	         erupiVoucherTxnDetailsEntity.setTerminalId(decryptedResponse.getTerminalId());
	         erupiVoucherTxnDetailsEntity.setPayerName(decryptedResponse.getPayerName());
	         erupiVoucherTxnDetailsEntity.setPayerMobile(decryptedResponse.getPayerMobile());
	         erupiVoucherTxnDetailsEntity.setPayerVA(decryptedResponse.getPayerVA());
	         erupiVoucherTxnDetailsEntity.setPayerAmount(decryptedResponse.getPayerAmount());
	         erupiVoucherTxnDetailsEntity.setPayeeName(decryptedResponse.getPayeeName());
	         
	         
	    	return erupiVoucherTxnDetailsEntity;
	    }



		@Override
		public List<ErupiVoucherCreateDetailsRequest> saveErupiVoucherCreateListDetails(ErupiVoucherCreateListRequest request) {
			// TODO Auto-generated method stub
			//ErupiVoucherCreateListRequest erupiVoucherCreateListRequest=new ErupiVoucherCreateListRequest();
			
			List<ErupiVoucherCreateDetailsRequest> response= new ArrayList<ErupiVoucherCreateDetailsRequest>();
			
			List<String> idList = Arrays.asList(request.getArrayofnamemobile());
			for (String namemobile : idList) {
				String[] value=namemobile.split("\\|");
				String name=value[0];
				String mobile=value[1];
				ErupiVoucherCreateDetailsRequest erupiVoucherCreateDetailsRequest=new ErupiVoucherCreateDetailsRequest();
				CopyUtility.copyProperties(request,erupiVoucherCreateDetailsRequest);
				erupiVoucherCreateDetailsRequest.setName(name);
				erupiVoucherCreateDetailsRequest.setMobile(mobile);
				erupiVoucherCreateDetailsRequest.setBeneficiaryID(mobile+"1");
				ErupiVoucherCreateDetailsRequest erupi=saveErupiVoucherInitiateDetails(erupiVoucherCreateDetailsRequest);
				response.add(erupi);
			}
			
				
				
			return response;
		}



		@Override
		public List<ErupiVoucherCreateOldDto> getErupiVoucherCreateOldList(ErupiVoucherCreatedRequest request) {
			List<ErupiVoucherCreateOldDto> voucherDTOList = new ArrayList<>();
			try {
				List<Object[]> resultList = erupiVoucherInitiateDetailsDao.getVoucherCreateNameList(request.getOrgId());
				  Long totalCount=0l;
				  Long totAmount=0l;
			        for (Object[] row : resultList) {
			            String name = (String) row[0];
			            String mobile = (String) row[1];
			            voucherDTOList.add(new ErupiVoucherCreateOldDto(name,mobile));
			        }
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			return voucherDTOList;
		}



		@Override
		public ErupiMultipleVoucherCreateRequest saveErupiMultipleVoucherCreation(
				ErupiMultipleVoucherCreateRequest erupiMultipleVoucherCreateRequest) {
			String response="";
			List<ErupiVoucherSingleCreateDetailsRequest> erupiVoucherCreateDetailsRequests=erupiMultipleVoucherCreateRequest.getList();
			for (ErupiVoucherSingleCreateDetailsRequest request : erupiVoucherCreateDetailsRequests) {
				
			
			log.info("Starting ErupiVoucherInitiateDetailsServiceImpl ... saveErupiMultipleVoucherCreation..");
			ErupiVoucherCreationDetailsEntity erupiVoucherInitiateDetailsEntity=null;
			ErupiVoucherTxnDetailsEntity erupiVoucherTxnDetailsEntity=null;
			JSONObject profileJsonRes=null;
			try {
				String merchantTranId=getMerTranId(request.getBankcode());
				response=MessageConstant.RESPONSE_FAILED;
				request.setResponse(response);	
				//request.setMandateType("04");
				//request.setPayeeVPA("invaciauat@prepaidicici");
				erupiVoucherInitiateDetailsEntity=new ErupiVoucherCreationDetailsEntity();
				erupiVoucherTxnDetailsEntity=new ErupiVoucherTxnDetailsEntity();
				CopyUtility.copyProperties(request,erupiVoucherInitiateDetailsEntity);
				CopyUtility.copyProperties(request,erupiVoucherTxnDetailsEntity);
				LocalDate eventDate = LocalDate.now();	
				erupiVoucherInitiateDetailsEntity.setCreationDate(eventDate);
				erupiVoucherInitiateDetailsEntity.setWorkFlowId(100001l);
				erupiVoucherInitiateDetailsEntity.setMerchanttxnid(merchantTranId);
				
				LocalDate newDate = request.getStartDate();
				LocalDate exitdate = newDate.plusDays(request.getValidity());
				erupiVoucherInitiateDetailsEntity.setExpDate(exitdate);
				erupiVoucherInitiateDetailsEntity=erupiVoucherInitiateDetailsDao.saveDetails(erupiVoucherInitiateDetailsEntity);
				if(erupiVoucherInitiateDetailsEntity!=null) {
				VoucherCreateRequest voucherCreateRequest=new VoucherCreateRequest();
					
				
				voucherCreateRequest.setMerchantTranId(merchantTranId);
				voucherCreateRequest.setAmount(request.getAmount().toString());
				voucherCreateRequest.setBeneficiaryID(request.getBeneficiaryID());
				voucherCreateRequest.setMobileNumber(request.getMobile());
				voucherCreateRequest.setBeneficiaryName(request.getName());
				String formattedValue = String.format("%.2f", request.getAmount());
				voucherCreateRequest.setAmount(formattedValue);
				String expdate=erupiVoucherInitiateDetailsEntity.getExpDate().toString();
				voucherCreateRequest.setExpiry(expdate);
				voucherCreateRequest.setPurposeCode(request.getPurposeCode());
				voucherCreateRequest.setMcc(request.getMcc());
				voucherCreateRequest.setVoucherRedemptionType(request.getRedemtionType());
				voucherCreateRequest.setPayerVA(request.getPayerVA());
				voucherCreateRequest.setType(request.getType());
				voucherCreateRequest.setMerchantId(request.getMerchantId());
				voucherCreateRequest.setSubMerchantId(request.getSubMerchantId());
				voucherCreateRequest.setMandateType(request.getMandateType());
				voucherCreateRequest.setPayeeVPA(request.getPayeeVPA());
				log.info("Starting voucher create request ...."+merchantTranId);	
				erupiVoucherTxnDetailsEntity=setRequestValue(voucherCreateRequest, erupiVoucherTxnDetailsEntity);
					
					String response1 = CommonUtility.userRequestWiout("", MessageConstant.gson.toJson(voucherCreateRequest),
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
							erupiVoucherTxnDetailsEntity.setDetailsId(erupiVoucherInitiateDetailsEntity.getId());
							erupiVoucherTxnDetailsEntity.setResponseJson(decryptedResponse.getApiResponse());
							erupiVoucherTxnDetailsEntity=setResponseValue(decryptedResponse,erupiVoucherTxnDetailsEntity);
							erupiVoucherTxnDetailsEntity=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity);
						}
						logger.info("erupiVoucherTxnDetailsEntity"+erupiVoucherTxnDetailsEntity);
					}else {
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
			}catch (DataIntegrityViolationException ex) {
		           
				log.error("Error in ErupiVoucherInitiateDetailsServiceImpl.DataIntegrityViolationException......"+ex.getMessage());
		        request.setResponseApi("exception DataException");
			}catch (Exception e) {
				e.printStackTrace();
				request.setResponseApi("exception:Bad request some field are missing");
				log.error("Error in ErupiVoucherInitiateDetailsServiceImpl......."+e.getMessage());
			}
			}
			return erupiMultipleVoucherCreateRequest;
		}
		public static String formatAmount(String amount) {
	        // Convert the string to a double
	        double value = Double.parseDouble(amount);

	        // Check if it's an integer (i.e., no decimal value)
	        if (value == (int) value) {
	            return String.valueOf((int) value);  // Return it as an integer
	        } else {
	            return String.format("%.2f", value);  // Return the original string if it's not a whole number
	        }
	    }
		@Override
		public ErupiVoucherCreateDetailsRequest saveErupiVoucherInitiateDetailsNew(ErupiVoucherCreateDetailsRequest request) {
			String response="";
			log.info("Starting ErupiVoucherInitiateDetailsServiceImpl ... saveErupiVoucherInitiateDetails..");
			ErupiVoucherCreationDetailsEntity erupiVoucherInitiateDetailsEntity=null;
			ErupiVoucherTxnDetailsEntity erupiVoucherTxnDetailsEntity=null;
			JSONObject profileJsonRes=null;
			
			
			try {
//				String amount= request.getAmount().toString();
//				String amt=formatAmount(amount);
				//log.info("amt ....::"+amt);
				String dataString = request.getName()+request.getMobile()+request.getStartDate()+request.getValidity()+request.getPurposeCode()+
						request.getConsent()+request.getCreatedby()+ request.getOrgId()+request.getMerchantId()+request.getSubMerchantId()+
						request.getRedemtionType()+request.getMcc()+request.getVoucherCode()+request.getVoucherDesc() +
						request.getBankcode()+request.getAccountNumber()+request.getPayerVA()+request.getMandateType()+request.getAmount()+
						request.getClientKey()+MessageConstant.SECRET_KEY;
				log.info("dataString::"+dataString);
//				String dataString = request.getOrgId()+request.getBankName()+request.getAccountHolderName()+request.getAcNumber()+request.getConirmAccNumber()+request.getAccountType()+request.getIfsc()
//		        +request.getMobile()+request.getMerchentIid()+request.getSubmurchentid()+request.getPayerva()+request.getClientKey()+MessageConstant.SECRET_KEY;
				String hash=ValidateConstants.generateHash(dataString);
				log.info("hash::"+hash);
				if(!request.getHash().equalsIgnoreCase(hash)) {
					request.setResponseApi(MessageConstant.HASH_ERROR);
					return request;
				}
				
				//Check employee exist are not
				Float amount = Float.parseFloat(request.getAmount());
				
				if(request.getBankcode().equalsIgnoreCase("INDB")) {
					
					String merchantTranId=getMerTranId(request.getBankcode());
					request.setType("CREATE");
					LocalDate stDate =request.getStartDate();
					String validity = request.getValidity();
					String[] daysArray=validity.split(" ");
					Long days=Long.valueOf(daysArray[0]);
					//erupiVoucherInitiateDetailsEntity.setExpDate(stDate);
					LocalDate etDate =stDate.plusDays(days);
					request.setExpDate(etDate);
					erupiVoucherInitiateDetailsEntity=new ErupiVoucherCreationDetailsEntity();
					erupiVoucherTxnDetailsEntity=new ErupiVoucherTxnDetailsEntity();
					CopyUtility.copyProperties(request,erupiVoucherInitiateDetailsEntity);
					erupiVoucherInitiateDetailsEntity.setAmount(amount);
					CopyUtility.copyProperties(request,erupiVoucherTxnDetailsEntity);
					
					LocalDate eventDate = LocalDate.now();	
					erupiVoucherInitiateDetailsEntity.setCreationDate(eventDate);
					erupiVoucherInitiateDetailsEntity.setWorkFlowId(100001l);
					erupiVoucherInitiateDetailsEntity.setMerchanttxnid(merchantTranId);
					 //CommonUtility.convertToLocalDate(endDate);
					erupiVoucherInitiateDetailsEntity.setExpDate(etDate);
					
					erupiVoucherInitiateDetailsEntity=erupiVoucherInitiateDetailsDao.saveDetails(erupiVoucherInitiateDetailsEntity);
					if(erupiVoucherInitiateDetailsEntity!=null) {
					VoucherCreateRequest voucherCreateRequest=new VoucherCreateRequest();
						
					
					voucherCreateRequest.setMerchantTranId(merchantTranId);
					voucherCreateRequest.setAmount(request.getAmount().toString());
					voucherCreateRequest.setBeneficiaryID(request.getBeneficiaryID());
					voucherCreateRequest.setMobileNumber(request.getMobile());
					voucherCreateRequest.setBeneficiaryName(request.getName());
					String formattedValue = String.format("%.2f", amount);
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
					voucherCreateRequest.setMandateType(request.getMandateType());
					voucherCreateRequest.setPayeeVPA(request.getPayeeVPA());
					log.info("Starting voucher create request ...."+merchantTranId);	
					erupiVoucherTxnDetailsEntity=setRequestValue(voucherCreateRequest, erupiVoucherTxnDetailsEntity);
					VoucherCreateIndianBankRequest voucherCreateIndianBankRequest=new VoucherCreateIndianBankRequest();
			        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
			        String startDate = stDate.format(formatter);
			        String endDate = etDate.format(formatter);
			        ErupiLinkAccountEntity erupi=erupiLinkAccountService.getErupiAccountDetails(request.getAccountNumber());
			        voucherCreateIndianBankRequest.setCorpid("");
			        voucherCreateIndianBankRequest.setCorpmobno("91"+erupi.getCorpmobno());
			        voucherCreateIndianBankRequest.setCorpupiID(erupi.getCorpupiId());
			        voucherCreateIndianBankRequest.setCorpaccNo(erupi.getCorpaccNo());
			        voucherCreateIndianBankRequest.setCorpaccType(erupi.getCorpaccType());
			        voucherCreateIndianBankRequest.setCorpifsc(erupi.getCorpifsc());
			        voucherCreateIndianBankRequest.setCorpname(erupi.getCorpname());
			        voucherCreateIndianBankRequest.setBenemobNo("91"+request.getMobile());
			        voucherCreateIndianBankRequest.setBenename(request.getName());
			        voucherCreateIndianBankRequest.setBenemailId("fakhruddeen.mca@gmail.com");
			        voucherCreateIndianBankRequest.setBeneIdName("DL");
			        voucherCreateIndianBankRequest.setBeneIdno("HR0443438448");
			        voucherCreateIndianBankRequest.setPurposecode(request.getPurposeCode());
			        voucherCreateIndianBankRequest.setRevocable(erupi.getRevocable());
			        voucherCreateIndianBankRequest.setAmount(formattedValue);
			        voucherCreateIndianBankRequest.setValiditystartdate(startDate);
			        voucherCreateIndianBankRequest.setValidityenddate(endDate);
			        voucherCreateIndianBankRequest.setInitiationMode(erupi.getInitiationMode());
			        voucherCreateIndianBankRequest.setMerchantid(request.getMerchantId());
			        voucherCreateIndianBankRequest.setPayeeVPA(request.getPayeeVPA());
			        voucherCreateIndianBankRequest.setPayermcc(request.getMcc());
			        voucherCreateIndianBankRequest.setPayeemcc(request.getMcc());
			        voucherCreateIndianBankRequest.setRecurrencePattern("ONETIME");
			        voucherCreateIndianBankRequest.setChecksum("");
			        
			        String response1 = CommonUtility.userRequestWiout("", MessageConstant.gson.toJson(voucherCreateIndianBankRequest),
							applicationConstantConfig.voucherServiceApiUrl+CommonUtils.sendIndianVoucherCreate);
					log.info("Ending voucher create indian bank response1 ...."+response1);
					
					profileJsonRes= new JSONObject(response1);
					
					if(profileJsonRes.getBoolean("status")) { 

						response=MessageConstant.RESPONSE_SUCCESS;
						request.setResponse(response);
						JSONObject data = profileJsonRes.getJSONObject("data");
						IndianBankVoucherCreateResponse decryptedResponse= jsonToPojoIndianBank(data.toString());
						if(decryptedResponse.getTxnStatus().equalsIgnoreCase("00")) {
						//erupiVoucherTxnDetailsEntity.setResponse(data.toString());
						request.setResponseApi(decryptedResponse.getTxnMsg());
						erupiVoucherTxnDetailsEntity.setDetailsId(erupiVoucherInitiateDetailsEntity.getId());
						int updatework=erupiVoucherInitiateDetailsDao.updateWorkflowId(erupiVoucherInitiateDetailsEntity.getId(), 100003l);
						erupiVoucherTxnDetailsEntity.setWorkFlowId(100003l);
						erupiVoucherTxnDetailsEntity.setResponseJson(data.toString());
						erupiVoucherTxnDetailsEntity=setResponseValueIndianBank(decryptedResponse,erupiVoucherTxnDetailsEntity);
						erupiVoucherTxnDetailsEntity=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity);
						}else {
							response=MessageConstant.RESPONSE_FAILED;
							request.setResponse(response);
							request.setResponseApi(decryptedResponse.getTxnMsg());
							int updatework=erupiVoucherInitiateDetailsDao.updateWorkflowId(erupiVoucherInitiateDetailsEntity.getId(), 100004l);
							erupiVoucherTxnDetailsEntity.setWorkFlowId(100004l);
							erupiVoucherTxnDetailsEntity.setDetailsId(erupiVoucherInitiateDetailsEntity.getId());
							erupiVoucherTxnDetailsEntity.setResponseJson(data.toString());
							erupiVoucherTxnDetailsEntity=setResponseValueIndianBank(decryptedResponse,erupiVoucherTxnDetailsEntity);
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
					
				}else {
				//
				String merchantTranId=getMerTranId(request.getBankcode());
				response=MessageConstant.RESPONSE_FAILED;
				request.setResponse(response);
				if(request.getMandateType()!=null && request.getMandateType().equalsIgnoreCase("04")) {
					if(request.getPayeeVPA()==null || request.getPayeeVPA().equalsIgnoreCase("")) {
						response=MessageConstant.RESPONSE_FAILED;
						request.setResponse(response);
						request.setResponseApi(MessageConstant.PAYEEVPA);
						return request;
					}
					
				}
				//request.setMandateType("04");
				//request.setPayeeVPA("invaciauat@prepaidicici");
				request.setType("CREATE");
				LocalDate stDate =request.getStartDate();
				if(request.getBulktblId()!=null && request.getBulktblId()>0) {
					
				}else {
					String validity = request.getValidity();
					String[] daysArray=validity.split(" ");
					Long days=Long.valueOf(daysArray[0]);
					LocalDate etDate =stDate.plusDays(days);
					request.setExpDate(etDate);
				}
				
				
				erupiVoucherInitiateDetailsEntity=new ErupiVoucherCreationDetailsEntity();
				erupiVoucherTxnDetailsEntity=new ErupiVoucherTxnDetailsEntity();
				CopyUtility.copyProperties(request,erupiVoucherInitiateDetailsEntity);
				erupiVoucherInitiateDetailsEntity.setAmount(amount);
				CopyUtility.copyProperties(request,erupiVoucherTxnDetailsEntity);
				LocalDate eventDate = LocalDate.now();	
				erupiVoucherInitiateDetailsEntity.setCreationDate(eventDate);
				erupiVoucherInitiateDetailsEntity.setWorkFlowId(100001l);
				erupiVoucherInitiateDetailsEntity.setMerchanttxnid(merchantTranId);
				 //CommonUtility.convertToLocalDate(endDate);
				//erupiVoucherInitiateDetailsEntity.setExpDate(etDate);
				
				erupiVoucherInitiateDetailsEntity=erupiVoucherInitiateDetailsDao.saveDetails(erupiVoucherInitiateDetailsEntity);
				if(erupiVoucherInitiateDetailsEntity!=null) {
				VoucherCreateRequest voucherCreateRequest=new VoucherCreateRequest();
					
				
				voucherCreateRequest.setMerchantTranId(merchantTranId);
				voucherCreateRequest.setAmount(request.getAmount().toString());
				voucherCreateRequest.setBeneficiaryID(request.getBeneficiaryID());
				voucherCreateRequest.setMobileNumber(request.getMobile());
				voucherCreateRequest.setBeneficiaryName(request.getName());
				String formattedValue = String.format("%.2f", amount);
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
				voucherCreateRequest.setMandateType(request.getMandateType());
				voucherCreateRequest.setPayeeVPA(request.getPayeeVPA());
				log.info("Starting voucher create request ...."+merchantTranId);	
				erupiVoucherTxnDetailsEntity=setRequestValue(voucherCreateRequest, erupiVoucherTxnDetailsEntity);
				LinkSubAccountMultipleEntity liEntity=linkSubMultipleAccountDao.getLinkMultipleAccountByAccNoOrgId(request.getAccountNumber(), request.getOrgId());
				if(liEntity!=null) {
					LinkMultipleAccountUpdate linkMultipleAccountUpdate=new LinkMultipleAccountUpdate();
					linkMultipleAccountUpdate.setId(liEntity.getId());
					linkMultipleAccountUpdate.setAmount(amount);
					linkMultipleAccountUpdate.setOrgId(liEntity.getOrgId());
					linkMultipleAccountUpdate.setMerchantId(merchantTranId);
					linkMultipleAccountUpdate.setAcNumber(request.getAccountNumber());
					linkMultipleAccountUpdate.setMobile(request.getMobile());
					LinkMultipleAccountUpdate update=linkMultipleAccountService.saveMultipleAccountOrUpdateDr(linkMultipleAccountUpdate);
					if(update!=null && !update.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
						request.setResponseApi(update.getResponse());
						return request;
					}
				}
					String response1 = CommonUtility.userRequestWiout("", MessageConstant.gson.toJson(voucherCreateRequest),
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
						if(request.getRequestId()!=null && request.getRequestId()>0) {
							ErupiVoucherCreationRequestEntity requestid=erupiVoucherRequestDao.getVoucherCreationRequestEmpById(request.getRequestId());
							requestid.setStatus(1);
							requestid.setStatusMessage("Voucher Created");
							erupiVoucherRequestDao.saveDetails(requestid);
						}
						}else {
							response=MessageConstant.RESPONSE_FAILED;
							request.setResponse(response);
							liEntity=linkSubMultipleAccountDao.getLinkMultipleAccountByAccNoOrgId(request.getAccountNumber(), request.getOrgId());
							if(liEntity!=null) {
								LinkMultipleAccountUpdate linkMultipleAccountUpdate=new LinkMultipleAccountUpdate();
								linkMultipleAccountUpdate.setId(liEntity.getId());
								linkMultipleAccountUpdate.setAmount(amount);
								linkMultipleAccountUpdate.setOrgId(liEntity.getOrgId());
								linkMultipleAccountUpdate.setMerchantId(merchantTranId);
								linkMultipleAccountUpdate.setAcNumber(request.getAccountNumber());
								linkMultipleAccountUpdate.setMobile(request.getMobile());
								LinkMultipleAccountUpdate update=linkMultipleAccountService.saveMultipleAccountOrUpdateCr(linkMultipleAccountUpdate);
//								if(update!=null && !update.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
//									request.setResponseApi(update.getResponse());
//									return request;
//								}
							}
							request.setResponseApi(decryptedResponse.getMessage());
							int updatework=erupiVoucherInitiateDetailsDao.updateWorkflowId(erupiVoucherInitiateDetailsEntity.getId(), 100004l);
							erupiVoucherTxnDetailsEntity.setWorkFlowId(100004l);
							erupiVoucherTxnDetailsEntity.setDetailsId(erupiVoucherInitiateDetailsEntity.getId());
							erupiVoucherTxnDetailsEntity.setResponseJson(decryptedResponse.getApiResponse());
							erupiVoucherTxnDetailsEntity=setResponseValue(decryptedResponse,erupiVoucherTxnDetailsEntity);
							erupiVoucherTxnDetailsEntity=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity);
						}
						logger.info("erupiVoucherTxnDetailsEntity"+erupiVoucherTxnDetailsEntity);
					}else {
						//loginservice.sendEmailVerificationCompletion(userForm);
						//request.setCreateResponse(response1);
						liEntity=linkSubMultipleAccountDao.getLinkMultipleAccountByAccNoOrgId(request.getAccountNumber(), request.getOrgId());
						if(liEntity!=null) {
							LinkMultipleAccountUpdate linkMultipleAccountUpdate=new LinkMultipleAccountUpdate();
							linkMultipleAccountUpdate.setId(liEntity.getId());
							linkMultipleAccountUpdate.setAmount(amount);
							linkMultipleAccountUpdate.setOrgId(liEntity.getOrgId());
							linkMultipleAccountUpdate.setMerchantId(merchantTranId);
							linkMultipleAccountUpdate.setAcNumber(request.getAccountNumber());
							linkMultipleAccountUpdate.setMobile(request.getMobile());
							LinkMultipleAccountUpdate update=linkMultipleAccountService.saveMultipleAccountOrUpdateCr(linkMultipleAccountUpdate);
						}
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
				}
			}catch (DataIntegrityViolationException ex) {
		            // Handle the specific exception here
		           // throw new CustomVoucherException("Voucher creation failed: " + ex.getMessage(), ex);
		        request.setResponseApi("exception DataException");
			}catch (Exception e) {
				e.printStackTrace();
				request.setResponseApi("exception:Bad request some field are missing");
				log.error("Error in ErupiVoucherInitiateDetailsServiceImpl......."+e.getMessage());
			}
			return request;
		}



		@Override
		public ErupiVoucherTotalDetailDto getErupiVoucherCreateStatus(ErupiVoucherCreatedRequest request) {
			
			ErupiVoucherTotalDetailDto erupiVoucherTotalDetailDto = new ErupiVoucherTotalDetailDto();
			
			try {
				List<Object[]> resultList = erupiVoucherInitiateDetailsDao.getVoucherCreateStatus(request.getOrgId());
				  Long totalCount=0l;
				  Long totAmount=0l;
				  Long redCount=0l;
				  Long redAmount=0l;
				  Long expRevokeCount=0l;
				  Long expRevokeAmount=0l;
				  Long activeCount=0l;
				  Long activeAmount=0l;
			        for (Object[] row : resultList) {
			            Long count = ((BigInteger) row[0]).longValue();			           
			            Float totalAmount = (Float) row[1]; // SUM(amount)
			            Long workfloid = ((BigInteger) row[2]).longValue();
			            String type=(String)row[3];
			            String status=(String)row[4];
			            Long totalAmt =totalAmount.longValue(); 
			            totalCount=totalCount+count;
			            totAmount=totAmount+totalAmt;
			           // ErupiVoucherStatusDto(count,totalAmt, voucherName,type,status);
			            if(workfloid==100007) {//redem
			            	redCount=redCount+count;
			            	redAmount=redAmount+totalAmt;
			            }
			            if(workfloid==100005 || status.equalsIgnoreCase("expire")) {//redem
			            	expRevokeCount=expRevokeCount+count;
			            	expRevokeAmount=expRevokeAmount+totalAmt;
			            }
			            if(status.equalsIgnoreCase("active")) {//active
			            	activeCount=activeCount+count;
			            	activeAmount=activeAmount+totalAmt;
			            }
			        }
			        erupiVoucherTotalDetailDto.setTotalIssueCount(totalCount.toString());
			        erupiVoucherTotalDetailDto.setTotalIssueAmount(totAmount.floatValue());
			        erupiVoucherTotalDetailDto.setRedemVCount(redCount.toString());
			        erupiVoucherTotalDetailDto.setRedemVAmount(redAmount.floatValue());
			        erupiVoucherTotalDetailDto.setExpRevokeCount(expRevokeCount.toString());
			        erupiVoucherTotalDetailDto.setExpRevokeAmount(expRevokeAmount.floatValue());
			        erupiVoucherTotalDetailDto.setActiveCount(activeCount.toString());
			        erupiVoucherTotalDetailDto.setActiveAmount(activeAmount.floatValue());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return erupiVoucherTotalDetailDto;
		}



		@Override
		public List<ErupiVoucherBankListDto> getErupiVoucherCreateBAnkList(ErupiVoucherCreatedRequest request) {
			List<ErupiVoucherBankListDto> erBankListDtos=new ArrayList<>();
			ErupiVoucherBankListDto erupiVoucherBankListDto=new ErupiVoucherBankListDto();
			erupiVoucherBankListDto.setBankName("All Bank");
			 String base64String = "iVBORw0KGgoAAAANSUhEUgAAALIAAACUCAMAAAAAoYNxAAAAZlBMVEX///8zMzP6+vqoqKguLi6ampogICAlJSUaGhqJiYkqKip6enqioqIdHR1RUVHz8/NGRkbX19cAAAAWFhbl5eVzc3NfX18QEBDs7Oy+vr6vr688PDxlZWXQ0NBBQUHd3d2SkpLHx8ebzWAiAAAEpUlEQVR4nO2b23arIBBAgyIoChq8mxjN///kURMVba6tYtoz+6nLy+pmHHAAs9sBAAAAAAAAAAA8xzC2NngTo3Cc4ldJRzHjnMXR1h4vY5ghRQ00NH9JoKtYoivSrba2eQXrRNEAPVlb+zylEgQjBUzEZwc68wgdXK/qjHrZ1l73KY/2EF1RH/o/w2O5tdkdjBoNWYzxeZcPvZAerE8cOow9Tsdedzhmu+g0NiFF1cdJZzVho7F7bgWzszu0glHrwzL6LJSRTRb+9bDvpcP4QcV5U8UpmWMrI1totsf8rPM2x6bg0PmYQOenUBmJ6dHvjslD3mSHHzOlMSLf2rUjcqhihRAvmoMlZ4ihdmwrlIxpMvoTAp0fVKeWNmXd9iBvX9bl9DQ9bR3oKJZ4KoxPbWzdNvBYNNWnxWbnpbtpTWoGBH2hjXLejW40LuoDnp8nothMOIsZ+2rMuycfS9q4Ukq/GDcZjY/bZLRfkHkWX7I17ka30hWI3GjR5RoyDN0a2cfpjQB2D968tim3jveuwWmsvSY1Tzey+KpDh1zNTHbHGfHA1CocCfueSvfcj2VfBJ3RvQsxF/qGjszjN7NYcZZJUV66mHn/UsZNTd2wPJJHIUZdXFmKjl0X8x9ci4mW4t/w8L1x4OpBRVcnY0acNj3Eo/Yx7K1eR5+D9IEBurzyjPiSDbIdFeLHLZQr16SZFT4WaOLmtA1Do/LxyR00XHM6W4on3a5X9pP2Qnpqh4TgceK314m1Mtp35iXQLXDQhtYXmGGxb5t5d/xW7pHOKi/DXNjP/3kDqdurjdws2uftP0nlK/YKxb/vPhkoRpi6plW/eBdDSwf6ayH/AO5Gl5HLqF5I/h56WDLQkXuvvLkNsRPP3/lNVfRGQ5tSabniPxfh8384hdJqV8pXU6lnsens/uUsVsBxnrx/G8P7RZS9J++729D0Gw1FqbeIsvV2WnyfcBnls3z+rxYCy2UKDsPCTBNosW2K0tTEMp0PAP4kfixWIF5z5cgXDC8OE+sqv1XRvfj+AGVQ3lyZSE7TW0UmtiUh8tZSI0tDyuVk1q1RmdnuPvOjXHxZoqMHq8qyyjrMFwSwnRSRH5UuV9qpT5kOnw4VwSTQmPWb7IY13XdgQT9RimJlC1OXcqhM4veTfCHK7k3B1bYEY6XmO6FuZXZQVyxzJQPCSc2rTmmoWr8bw1q5LuV0On0YnzNOJouCWTLuu8eTW0qpV5lNxXbV+LlIPb2lHhojpxs6WT8D16RM6ulKtjF0QDpbisiHj43Y7JaaaFXms+0kY1iSpbPJ0H5QDmbr9Sb/FOXZWvFvUJ5HmYAyKIMyKIMyKIMyKIMyKIMyKIMyKIMyKIMyKIMyKP855a0Xa4OQd8jZx2EGup6w07myvJzg4WGm7F3PhMGqyp51oZ4tfBtWTz37brOq+zPzr+7L/oy3wS95gP8UQxsLCVeuRpb5fZolqTbkMh99Wupu/8rw/1dZclsTfKHE8AtPG1v8pBV4n721CT8ZnJNUW89TSJPvG/vjh0E6wcn3O+EvVDaSkGxAmPygPMqdTfjRj7v0lZ0rlKAAAAAAAAAAAPwG/gH0+oHRkSQuBgAAAABJRU5ErkJggg==";
			byte[] decodedLogo = decodeBase64ToByteArray(base64String);
			erupiVoucherBankListDto.setBankLogo(decodedLogo);
			erBankListDtos.add(erupiVoucherBankListDto);
			try {
				List<Object[]> resultList = erupiVoucherInitiateDetailsDao.getVoucherCreateBankNameList(request.getOrgId());
				Long totalCount=0l;
				  Long totAmount=0l;
			        for (Object[] row : resultList) {
			            String bankAccount = (String) row[0];			           
			            String bankname = (String) row[1];
			            byte[] bankIcon=( byte[])row[2];
			            String accNumber=replaceExceptLastFour(bankAccount);
			            erBankListDtos.add(new ErupiVoucherBankListDto(bankAccount,accNumber,bankname, bankIcon));
			        }
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			return erBankListDtos;
		}

		public static byte[] decodeBase64ToByteArray(String base64String) {
	        return Base64.getDecoder().decode(base64String);
	    }

		@Override
		public ErupiVoucherTotalDetailDto getErupiVoucherCreateDetailByAccount(ErupiVoucherCreatedRequest request) {
			ErupiVoucherTotalDetailDto erupiVoucherTotalDetailDto = new ErupiVoucherTotalDetailDto();
			
			try {
				if(request.getAccNumber()==null || request.getAccNumber().equals("")) {
					List<Object[]> resultList = erupiVoucherInitiateDetailsDao.getVoucherCreateStatus(request.getOrgId());
					  Long totalCount=0l;
					  Long totAmount=0l;
					  Long redCount=0l;
					  Long redAmount=0l;
					  Long expRevokeCount=0l;
					  Long expRevokeAmount=0l;
					  Long activeCount=0l;
					  Long activeAmount=0l;
				        for (Object[] row : resultList) {
				            Long count = ((BigInteger) row[0]).longValue();			           
				            Float totalAmount = (Float) row[1]; // SUM(amount)
				            Long workfloid = ((BigInteger) row[2]).longValue();
				            String type=(String)row[3];
				            String status=(String)row[4];
				            Long totalAmt =totalAmount.longValue(); 
				            totalCount=totalCount+count;
				            totAmount=totAmount+totalAmt;
				           // ErupiVoucherStatusDto(count,totalAmt, voucherName,type,status);
				            if(workfloid==100007) {//redem
				            	redCount=redCount+count;
				            	redAmount=redAmount+totalAmt;
				            }
				            if(workfloid==100005 || status.equalsIgnoreCase("expire")) {//redem
				            	expRevokeCount=expRevokeCount+count;
				            	expRevokeAmount=expRevokeAmount+totalAmt;
				            }
				            if(status.equalsIgnoreCase("active")) {//active
				            	activeCount=activeCount+count;
				            	activeAmount=activeAmount+totalAmt;
				            }
				        }
				        erupiVoucherTotalDetailDto.setTotalIssueCount(totalCount.toString());
				        erupiVoucherTotalDetailDto.setTotalIssueAmount(totAmount.floatValue());
				        erupiVoucherTotalDetailDto.setRedemVCount(redCount.toString());
				        erupiVoucherTotalDetailDto.setRedemVAmount(redAmount.floatValue());
				        erupiVoucherTotalDetailDto.setExpRevokeCount(expRevokeCount.toString());
				        erupiVoucherTotalDetailDto.setExpRevokeAmount(expRevokeAmount.floatValue());
				        erupiVoucherTotalDetailDto.setActiveCount(activeCount.toString());
				        erupiVoucherTotalDetailDto.setActiveAmount(activeAmount.floatValue());
				}else {
				List<Object[]> resultList = erupiVoucherInitiateDetailsDao.getVoucherCreateSummaryWithAccNo(request.getOrgId(),request.getAccNumber());
				  Long totalCount=0l;
				  Long totAmount=0l;
				  Long redCount=0l;
				  Long redAmount=0l;
				  Long expRevokeCount=0l;
				  Long expRevokeAmount=0l;
				  Long activeCount=0l;
				  Long activeAmount=0l;
			        for (Object[] row : resultList) {
			            Long count = ((BigInteger) row[0]).longValue();			           
			            Float totalAmount = (Float) row[1]; // SUM(amount)
			            Long workfloid = ((BigInteger) row[2]).longValue();
			            String type=(String)row[3];
			            String status=(String)row[4];
			            Long totalAmt =totalAmount.longValue(); 
			            totalCount=totalCount+count;
			            totAmount=totAmount+totalAmt;
			           // ErupiVoucherStatusDto(count,totalAmt, voucherName,type,status);
			            if(workfloid==100007) {//redem
			            	redCount=redCount+count;
			            	redAmount=redAmount+totalAmt;
			            }
			            if(workfloid==100005 || status.equalsIgnoreCase("expire")) {//redem
			            	expRevokeCount=expRevokeCount+count;
			            	expRevokeAmount=expRevokeAmount+totalAmt;
			            }
			            if(status.equalsIgnoreCase("active")) {//active
			            	activeCount=activeCount+count;
			            	activeAmount=activeAmount+totalAmt;
			            }
			        }
			        erupiVoucherTotalDetailDto.setTotalIssueCount(totalCount.toString());
			        erupiVoucherTotalDetailDto.setTotalIssueAmount(totAmount.floatValue());
			        erupiVoucherTotalDetailDto.setRedemVCount(redCount.toString());
			        erupiVoucherTotalDetailDto.setRedemVAmount(redAmount.floatValue());
			        erupiVoucherTotalDetailDto.setExpRevokeCount(expRevokeCount.toString());
			        erupiVoucherTotalDetailDto.setExpRevokeAmount(expRevokeAmount.floatValue());
			        erupiVoucherTotalDetailDto.setActiveCount(activeCount.toString());
			        erupiVoucherTotalDetailDto.setActiveAmount(activeAmount.floatValue());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return erupiVoucherTotalDetailDto;
		}



		@Override
		public ErupiVoucherCreatedDto getErupiVoucherCreateDetailsById(ErupiVoucherCreatedRequest request) {
			String query="";						
			ErupiVoucherCreatedDto erupiVoucherCreatedDto= erupiVoucherInitiateDetailsDao.getVoucherCreationById(request.getId());
			if(erupiVoucherCreatedDto!=null) {
				ErupiBankMasterEntity erBankMasterEntity=bankMasterDao.getDetails(erupiVoucherCreatedDto.getBankcode());
				String accNumber=replaceExceptLastFour(erupiVoucherCreatedDto.getAccountNumber());
				erupiVoucherCreatedDto.setAccountNumber(accNumber);
				if(erBankMasterEntity==null) {
					erupiVoucherCreatedDto.setBankIcon(null);
				}else {
					erupiVoucherCreatedDto.setBankIcon(erBankMasterEntity.getBankLogo());
				}
				
			}
			return erupiVoucherCreatedDto;
		}



		@Override
		public List<ErupiVoucherCreatedDateWiseDto> getErupiVoucherCreateDetailsListDateWise(
				ErupiVoucherCreatedDateWiseRequest request) {

			String query="";
			String message="";
			LocalDate today = LocalDate.now();
			LocalDate startDate =null;
			LocalDate endDate =null;			
			System.out.println("startDate:"+startDate);
			System.out.println("endDate:"+endDate);
			startDate = request.getFromDate();
		    endDate = request.getToDate();
		    message=validateVoucherCreateDateWiseRequest(request);

			List<ErupiVoucherCreatedDateWiseDto> erupiVoucherCreatedDtos= erupiVoucherInitiateDetailsDao.getVoucherCreationListDateWise(startDate,endDate,request.getBankCode());

			return erupiVoucherCreatedDtos;
		}

		private String validateVoucherCreateDateWiseRequest(ErupiVoucherCreatedDateWiseRequest request) {
	        // Check for required fields and validate the data
	        String message="";
	               
	        
//	        message=ValidateConstants.validateOrganizationId(directorOnboardingRequest.getOrgId());
//	        if(message!=null)
//	        	return message;
//	        message=ValidateConstants.validateMandatoryName(directorOnboardingRequest.getName());
//	        if(message!=null)
//	        	return message;
//	       message=ValidateConstants.validateEmail(directorOnboardingRequest.getEmail());
//	       if(message!=null)
//	       	return message;
//	       message=ValidateConstants.validateMobile(directorOnboardingRequest.getMobile());
//	       if(message!=null)
//	       	return message;
//	       message=ValidateConstants.validateDin(directorOnboardingRequest.getDin());
//	       if(message!=null)
//	       	return message;
//	       message=ValidateConstants.validateDesignation(directorOnboardingRequest.getDesignation());
//	       if(message!=null)
//	       	return message;
//	       message=ValidateConstants.validateAddress(directorOnboardingRequest.getAddress());
//	       if(message!=null)
//	       	return message;
//	       message=ValidateConstants.validateCreatedBy(directorOnboardingRequest.getCreatedby());
	       
	        return message;
	    }

		@Override
		public boolean getSecurityCheck(String clientId, String secretId,String bankCode) {
			boolean result=false;						
			SecurityClientAndSecretEntity securityClientAndSecretEntity= secretDao.getSecurityClientAndSecret(clientId,secretId,bankCode);
			if(securityClientAndSecretEntity!=null) {
				result=true;	
			}
			return result;
		}



		@Override
		public ErupiVoucherCreateDetailsRequest saveErupiVoucherInitiateDetailsNewBulk(
				ErupiVoucherCreateDetailsRequest request) {
			String response="";
			log.info("Starting ErupiVoucherInitiateDetailsServiceImpl ... saveErupiVoucherInitiateDetails..");
			ErupiVoucherCreationDetailsEntity erupiVoucherInitiateDetailsEntity=null;
			ErupiVoucherTxnDetailsEntity erupiVoucherTxnDetailsEntity=null;
			JSONObject profileJsonRes=null;
			
			
			try {
				//String amount= request.getAmount().toString();
				//String amt=formatAmount(amount);
				//log.info("amt ....::"+amt);
//				String dataString = request.getName()+request.getMobile()+request.getStartDate()+request.getValidity()+request.getPurposeCode()+
//						request.getConsent()+request.getCreatedby()+ request.getOrgId()+request.getMerchantId()+request.getSubMerchantId()+
//						request.getRedemtionType()+request.getMcc()+request.getVoucherCode()+request.getVoucherDesc() +
//						request.getBankcode()+request.getAccountNumber()+request.getPayerVA()+request.getMandateType()+amt+
//						request.getClientKey()+MessageConstant.SECRET_KEY;
//				log.info("dataString::"+dataString);
////				String dataString = request.getOrgId()+request.getBankName()+request.getAccountHolderName()+request.getAcNumber()+request.getConirmAccNumber()+request.getAccountType()+request.getIfsc()
////		        +request.getMobile()+request.getMerchentIid()+request.getSubmurchentid()+request.getPayerva()+request.getClientKey()+MessageConstant.SECRET_KEY;
//				String hash=ValidateConstants.generateHash(dataString);
//				log.info("hash::"+hash);
//				if(!request.getHash().equalsIgnoreCase(hash)) {
//					request.setResponseApi(MessageConstant.HASH_ERROR);
//					return request;
//				}
				
				//Check employee exist are not
				Float amount = Float.parseFloat(request.getAmount());
				
				if(request.getBankcode().equalsIgnoreCase("INDB")) {
					
					String merchantTranId=getMerTranId(request.getBankcode());
					request.setType("CREATE");
					LocalDate stDate =request.getStartDate();
					String validity = request.getValidity();
					String[] daysArray=validity.split(" ");
					Long days=Long.valueOf(daysArray[0]);
					//erupiVoucherInitiateDetailsEntity.setExpDate(stDate);
					LocalDate etDate =stDate.plusDays(days);
					request.setExpDate(etDate);
					erupiVoucherInitiateDetailsEntity=new ErupiVoucherCreationDetailsEntity();
					erupiVoucherTxnDetailsEntity=new ErupiVoucherTxnDetailsEntity();
					CopyUtility.copyProperties(request,erupiVoucherInitiateDetailsEntity);
					erupiVoucherInitiateDetailsEntity.setAmount(amount);
					CopyUtility.copyProperties(request,erupiVoucherTxnDetailsEntity);
					LocalDate eventDate = LocalDate.now();	
					erupiVoucherInitiateDetailsEntity.setCreationDate(eventDate);
					erupiVoucherInitiateDetailsEntity.setWorkFlowId(100001l);
					erupiVoucherInitiateDetailsEntity.setMerchanttxnid(merchantTranId);
					 //CommonUtility.convertToLocalDate(endDate);
					erupiVoucherInitiateDetailsEntity.setExpDate(etDate);
					
					erupiVoucherInitiateDetailsEntity=erupiVoucherInitiateDetailsDao.saveDetails(erupiVoucherInitiateDetailsEntity);
					if(erupiVoucherInitiateDetailsEntity!=null) {
					VoucherCreateRequest voucherCreateRequest=new VoucherCreateRequest();
						
					
					voucherCreateRequest.setMerchantTranId(merchantTranId);
					voucherCreateRequest.setAmount(request.getAmount().toString());
					voucherCreateRequest.setBeneficiaryID(request.getBeneficiaryID());
					voucherCreateRequest.setMobileNumber(request.getMobile());
					voucherCreateRequest.setBeneficiaryName(request.getName());
					String formattedValue = String.format("%.2f", amount);
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
					voucherCreateRequest.setMandateType(request.getMandateType());
					voucherCreateRequest.setPayeeVPA(request.getPayeeVPA());
					log.info("Starting voucher create request ...."+merchantTranId);	
					erupiVoucherTxnDetailsEntity=setRequestValue(voucherCreateRequest, erupiVoucherTxnDetailsEntity);
					VoucherCreateIndianBankRequest voucherCreateIndianBankRequest=new VoucherCreateIndianBankRequest();
			        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
			        String startDate = stDate.format(formatter);
			        String endDate = etDate.format(formatter);
			        ErupiLinkAccountEntity erupi=erupiLinkAccountService.getErupiAccountDetails(request.getAccountNumber());
			        voucherCreateIndianBankRequest.setCorpid("");
			        voucherCreateIndianBankRequest.setCorpmobno("91"+erupi.getCorpmobno());
			        voucherCreateIndianBankRequest.setCorpupiID(erupi.getCorpupiId());
			        voucherCreateIndianBankRequest.setCorpaccNo(erupi.getCorpaccNo());
			        voucherCreateIndianBankRequest.setCorpaccType(erupi.getCorpaccType());
			        voucherCreateIndianBankRequest.setCorpifsc(erupi.getCorpifsc());
			        voucherCreateIndianBankRequest.setCorpname(erupi.getCorpname());
			        voucherCreateIndianBankRequest.setBenemobNo("91"+request.getMobile());
			        voucherCreateIndianBankRequest.setBenename(request.getName());
			        voucherCreateIndianBankRequest.setBenemailId("fakhruddeen.mca@gmail.com");
			        voucherCreateIndianBankRequest.setBeneIdName("DL");
			        voucherCreateIndianBankRequest.setBeneIdno("HR0443438448");
			        voucherCreateIndianBankRequest.setPurposecode(request.getPurposeCode());
			        voucherCreateIndianBankRequest.setRevocable(erupi.getRevocable());
			        voucherCreateIndianBankRequest.setAmount(formattedValue);
			        voucherCreateIndianBankRequest.setValiditystartdate(startDate);
			        voucherCreateIndianBankRequest.setValidityenddate(endDate);
			        voucherCreateIndianBankRequest.setInitiationMode(erupi.getInitiationMode());
			        voucherCreateIndianBankRequest.setMerchantid(request.getMerchantId());
			        voucherCreateIndianBankRequest.setPayeeVPA(request.getPayeeVPA());
			        voucherCreateIndianBankRequest.setPayermcc(request.getMcc());
			        voucherCreateIndianBankRequest.setPayeemcc(request.getMcc());
			        voucherCreateIndianBankRequest.setRecurrencePattern("ONETIME");
			        voucherCreateIndianBankRequest.setChecksum("");
			        
			        String response1 = CommonUtility.userRequestWiout("", MessageConstant.gson.toJson(voucherCreateIndianBankRequest),
							applicationConstantConfig.voucherServiceApiUrl+CommonUtils.sendIndianVoucherCreate);
					log.info("Ending voucher create indian bank response1 ...."+response1);
					
					profileJsonRes= new JSONObject(response1);
					
					if(profileJsonRes.getBoolean("status")) { 

						response=MessageConstant.RESPONSE_SUCCESS;
						request.setResponse(response);
						JSONObject data = profileJsonRes.getJSONObject("data");
						IndianBankVoucherCreateResponse decryptedResponse= jsonToPojoIndianBank(data.toString());
						if(decryptedResponse.getTxnStatus().equalsIgnoreCase("00")) {
						//erupiVoucherTxnDetailsEntity.setResponse(data.toString());
						request.setResponseApi(decryptedResponse.getTxnMsg());
						erupiVoucherTxnDetailsEntity.setDetailsId(erupiVoucherInitiateDetailsEntity.getId());
						int updatework=erupiVoucherInitiateDetailsDao.updateWorkflowId(erupiVoucherInitiateDetailsEntity.getId(), 100003l);
						erupiVoucherTxnDetailsEntity.setWorkFlowId(100003l);
						erupiVoucherTxnDetailsEntity.setResponseJson(data.toString());
						erupiVoucherTxnDetailsEntity=setResponseValueIndianBank(decryptedResponse,erupiVoucherTxnDetailsEntity);
						erupiVoucherTxnDetailsEntity=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity);
						}else {
							response=MessageConstant.RESPONSE_FAILED;
							request.setResponse(response);
							request.setResponseApi(decryptedResponse.getTxnMsg());
							int updatework=erupiVoucherInitiateDetailsDao.updateWorkflowId(erupiVoucherInitiateDetailsEntity.getId(), 100004l);
							erupiVoucherTxnDetailsEntity.setWorkFlowId(100004l);
							erupiVoucherTxnDetailsEntity.setDetailsId(erupiVoucherInitiateDetailsEntity.getId());
							erupiVoucherTxnDetailsEntity.setResponseJson(data.toString());
							erupiVoucherTxnDetailsEntity=setResponseValueIndianBank(decryptedResponse,erupiVoucherTxnDetailsEntity);
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
					
				}else {
				//
				String merchantTranId=getMerTranId(request.getBankcode());
				response=MessageConstant.RESPONSE_FAILED;
				request.setResponse(response);
				if(request.getMandateType()!=null && request.getMandateType().equalsIgnoreCase("04")) {
					if(request.getPayeeVPA()==null || request.getPayeeVPA().equalsIgnoreCase("")) {
						response=MessageConstant.RESPONSE_FAILED;
						request.setResponse(response);
						request.setResponseApi(MessageConstant.PAYEEVPA);
						return request;
					}
					
				}
				//request.setMandateType("04");
				//request.setPayeeVPA("invaciauat@prepaidicici");
				request.setType("CREATE");
				LocalDate stDate =request.getStartDate();
				if(request.getBulktblId()!=null && request.getBulktblId()>0) {
					
				}else {
					String validity = request.getValidity();
					String[] daysArray=validity.split(" ");
					Long days=Long.valueOf(daysArray[0]);
					LocalDate etDate =stDate.plusDays(days);
					request.setExpDate(etDate);
				}
				
				
				erupiVoucherInitiateDetailsEntity=new ErupiVoucherCreationDetailsEntity();
				erupiVoucherTxnDetailsEntity=new ErupiVoucherTxnDetailsEntity();
				CopyUtility.copyProperties(request,erupiVoucherInitiateDetailsEntity);
				erupiVoucherInitiateDetailsEntity.setAmount(amount);
				CopyUtility.copyProperties(request,erupiVoucherTxnDetailsEntity);
				LocalDate eventDate = LocalDate.now();	
				erupiVoucherInitiateDetailsEntity.setCreationDate(eventDate);
				erupiVoucherInitiateDetailsEntity.setWorkFlowId(100001l);
				erupiVoucherInitiateDetailsEntity.setMerchanttxnid(merchantTranId);
				 //CommonUtility.convertToLocalDate(endDate);
				//erupiVoucherInitiateDetailsEntity.setExpDate(etDate);
				
				erupiVoucherInitiateDetailsEntity=erupiVoucherInitiateDetailsDao.saveDetails(erupiVoucherInitiateDetailsEntity);
				if(erupiVoucherInitiateDetailsEntity!=null) {
				VoucherCreateRequest voucherCreateRequest=new VoucherCreateRequest();
					
				
				voucherCreateRequest.setMerchantTranId(merchantTranId);
				voucherCreateRequest.setAmount(request.getAmount().toString());
				voucherCreateRequest.setBeneficiaryID(request.getBeneficiaryID());
				voucherCreateRequest.setMobileNumber(request.getMobile());
				voucherCreateRequest.setBeneficiaryName(request.getName());
				String formattedValue = String.format("%.2f", amount);
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
				voucherCreateRequest.setMandateType(request.getMandateType());
				voucherCreateRequest.setPayeeVPA(request.getPayeeVPA());
				log.info("Starting voucher create request ...."+merchantTranId);	
				erupiVoucherTxnDetailsEntity=setRequestValue(voucherCreateRequest, erupiVoucherTxnDetailsEntity);
				LinkSubAccountMultipleEntity liEntity=linkSubMultipleAccountDao.getLinkMultipleAccountByAccNoOrgId(request.getAccountNumber(), request.getOrgId());
				if(liEntity!=null) {
					LinkMultipleAccountUpdate linkMultipleAccountUpdate=new LinkMultipleAccountUpdate();
					linkMultipleAccountUpdate.setId(liEntity.getId());
					linkMultipleAccountUpdate.setAmount(amount);
					linkMultipleAccountUpdate.setOrgId(liEntity.getOrgId());
					linkMultipleAccountUpdate.setMerchantId(merchantTranId);
					linkMultipleAccountUpdate.setAcNumber(request.getAccountNumber());
					linkMultipleAccountUpdate.setMobile(request.getMobile());
					LinkMultipleAccountUpdate update=linkMultipleAccountService.saveMultipleAccountOrUpdateDr(linkMultipleAccountUpdate);
					if(update!=null && !update.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
						request.setResponseApi(update.getResponse());
						return request;
					}
				}
					String response1 = CommonUtility.userRequestWiout("", MessageConstant.gson.toJson(voucherCreateRequest),
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
						if(request.getRequestId()!=null && request.getRequestId()>0) {
							ErupiVoucherCreationRequestEntity requestid=erupiVoucherRequestDao.getVoucherCreationRequestEmpById(request.getRequestId());
							requestid.setStatus(1);
							requestid.setStatusMessage("Voucher Created");
							erupiVoucherRequestDao.saveDetails(requestid);
						}
						}else {
							response=MessageConstant.RESPONSE_FAILED;
							request.setResponse(response);
							liEntity=linkSubMultipleAccountDao.getLinkMultipleAccountByAccNoOrgId(request.getAccountNumber(), request.getOrgId());
							if(liEntity!=null) {
								LinkMultipleAccountUpdate linkMultipleAccountUpdate=new LinkMultipleAccountUpdate();
								linkMultipleAccountUpdate.setId(liEntity.getId());
								linkMultipleAccountUpdate.setAmount(amount);
								linkMultipleAccountUpdate.setOrgId(liEntity.getOrgId());
								linkMultipleAccountUpdate.setMerchantId(merchantTranId);
								linkMultipleAccountUpdate.setAcNumber(request.getAccountNumber());
								linkMultipleAccountUpdate.setMobile(request.getMobile());
								LinkMultipleAccountUpdate update=linkMultipleAccountService.saveMultipleAccountOrUpdateCr(linkMultipleAccountUpdate);
//								if(update!=null && !update.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
//									request.setResponseApi(update.getResponse());
//									return request;
//								}
							}
							request.setResponseApi(decryptedResponse.getMessage());
							int updatework=erupiVoucherInitiateDetailsDao.updateWorkflowId(erupiVoucherInitiateDetailsEntity.getId(), 100004l);
							erupiVoucherTxnDetailsEntity.setWorkFlowId(100004l);
							erupiVoucherTxnDetailsEntity.setDetailsId(erupiVoucherInitiateDetailsEntity.getId());
							erupiVoucherTxnDetailsEntity.setResponseJson(decryptedResponse.getApiResponse());
							erupiVoucherTxnDetailsEntity=setResponseValue(decryptedResponse,erupiVoucherTxnDetailsEntity);
							erupiVoucherTxnDetailsEntity=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnDetailsEntity);
						}
						logger.info("erupiVoucherTxnDetailsEntity"+erupiVoucherTxnDetailsEntity);
					}else {
						//loginservice.sendEmailVerificationCompletion(userForm);
						//request.setCreateResponse(response1);
						liEntity=linkSubMultipleAccountDao.getLinkMultipleAccountByAccNoOrgId(request.getAccountNumber(), request.getOrgId());
						if(liEntity!=null) {
							LinkMultipleAccountUpdate linkMultipleAccountUpdate=new LinkMultipleAccountUpdate();
							linkMultipleAccountUpdate.setId(liEntity.getId());
							linkMultipleAccountUpdate.setAmount(amount);
							linkMultipleAccountUpdate.setOrgId(liEntity.getOrgId());
							linkMultipleAccountUpdate.setMerchantId(merchantTranId);
							linkMultipleAccountUpdate.setAcNumber(request.getAccountNumber());
							linkMultipleAccountUpdate.setMobile(request.getMobile());
							LinkMultipleAccountUpdate update=linkMultipleAccountService.saveMultipleAccountOrUpdateCr(linkMultipleAccountUpdate);
						}
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
				}
			}catch (DataIntegrityViolationException ex) {
		            // Handle the specific exception here
		           // throw new CustomVoucherException("Voucher creation failed: " + ex.getMessage(), ex);
		        request.setResponseApi("exception DataException");
			}catch (Exception e) {
				e.printStackTrace();
				request.setResponseApi("exception:Bad request some field are missing");
				log.error("Error in ErupiVoucherInitiateDetailsServiceImpl......."+e.getMessage());
			}
			return request;
		}
		
}
