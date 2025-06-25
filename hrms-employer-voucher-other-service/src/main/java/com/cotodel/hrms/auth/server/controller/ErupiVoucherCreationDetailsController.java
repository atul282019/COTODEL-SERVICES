package com.cotodel.hrms.auth.server.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cotodel.hrms.auth.server.dto.AccountWiseAmountDTO;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherAmountDetailResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherAmountRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherBankDetailListResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherBankListDto;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreateDetailsRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedDateWiseDto;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedDateWiseRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedDto;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedListByPurposeCodeResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedListDateWiseResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedListResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherDetailResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherInitiateDetailsResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherPurposeCodeRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherSummaryDto;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherSummaryTotalCountResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherTotalDetailDto;
import com.cotodel.hrms.auth.server.dto.PurposeCodeAmountWithImgDto;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreateListRequest;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreateListResponse;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreateNameResponse;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreateOldDto;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreateSummaryDto;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreateSummaryListResponse;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreateTransactionRequest;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreatedRedeemDto;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreatedRedeemListResponse;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreatedRedeemTransactionImgDto;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreatedRedeemTransactionListResponse;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreationDetailsResponse;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreationDetailsSingleRequest;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherRedemeRequest;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherRevokeBulkDetailsResponse;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherRevokeDetailsBulkRequest;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherRevokeDetailsSingleRequest;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherRevokeSingleDetailsResponse;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.multi.datasource.SetDatabaseTenent;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.ErupiVoucherInitiateDetailsService;
import com.cotodel.hrms.auth.server.util.CommonUtility;
import com.cotodel.hrms.auth.server.util.EncriptResponse;
import com.cotodel.hrms.auth.server.util.EncryptionDecriptionUtil;
import com.cotodel.hrms.auth.server.util.MessageConstant;
import com.cotodel.hrms.auth.server.util.TransactionManager;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/Api")
public class ErupiVoucherCreationDetailsController {
private static final Logger logger = LoggerFactory.getLogger(ExpenseTravelController.class);
	
	@Autowired
	ErupiVoucherInitiateDetailsService erupiVoucherInitiateDetailsService;
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/add/erupiVoucherInitiateDetails",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiVoucherInitiateDetails(HttpServletRequest request,@Valid @RequestBody ErupiVoucherCreateDetailsRequest erupiLinkAccountRequest) {
		 
	    logger.info("inside erupiVoucherInitiateDetails....");	    	
	    
	    	String message = "";
	    	ErupiVoucherCreateDetailsRequest response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=erupiVoucherInitiateDetailsService.saveErupiVoucherInitiateDetails(erupiLinkAccountRequest);
	    		
				if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			return ResponseEntity.ok(new ErupiVoucherInitiateDetailsResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new ErupiVoucherInitiateDetailsResponse(MessageConstant.FALSE,response.getResponseApi(),response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in erupiVoucherInitiateDetails====="+e);
			}
	        
	        return ResponseEntity.ok(new ErupiVoucherInitiateDetailsResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/add/erupiVoucherInitiateDetailsNew",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiVoucherInitiateDetailsNew(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside erupiVoucherInitiateDetails....");	    	
	    	
	    
	    	String message = "";
	    	ErupiVoucherCreateDetailsRequest response=null;
	    	ErupiVoucherInitiateDetailsResponse erupiVoucherInitiateDetailsResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ErupiVoucherCreateDetailsRequest erupiLinkAccountRequest= EncryptionDecriptionUtil.convertFromJson(decript, ErupiVoucherCreateDetailsRequest.class);
				
				response=erupiVoucherInitiateDetailsService.saveErupiVoucherInitiateDetailsNew(erupiLinkAccountRequest);
	    		
				if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
					erupiVoucherInitiateDetailsResponse=new ErupiVoucherInitiateDetailsResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherInitiateDetailsResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			erupiVoucherInitiateDetailsResponse=new ErupiVoucherInitiateDetailsResponse(MessageConstant.FALSE,response.getResponseApi(),response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherInitiateDetailsResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in erupiVoucherInitiateDetails====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		erupiVoucherInitiateDetailsResponse=new ErupiVoucherInitiateDetailsResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
				String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherInitiateDetailsResponse);
				jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				// TODO: handle exception
			}
    	    return ResponseEntity.ok(jsonEncriptObject);
	    }

	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/erupiVoucherCreateList",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiVoucherCreateList(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside erupiVoucherCreateList....");	    	
	    	
	    
	    	String message = "";
	    	List<ErupiVoucherCreatedDto> response=null;
	    	ErupiVoucherCreatedListResponse erupiVoucherCreatedListResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ErupiVoucherCreatedRequest erupiLinkAccountRequest= EncryptionDecriptionUtil.convertFromJson(decript, ErupiVoucherCreatedRequest.class);
				response=erupiVoucherInitiateDetailsService.getErupiVoucherCreateDetailsList(erupiLinkAccountRequest);
	    		//System.out.println(response.size());
				if(response!=null && response.size()>0) {
					erupiVoucherCreatedListResponse=new ErupiVoucherCreatedListResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherCreatedListResponse);
					 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					 return ResponseEntity.ok(jsonEncriptObject);

	    		}else {
	    			erupiVoucherCreatedListResponse=new ErupiVoucherCreatedListResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherCreatedListResponse);
					 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					 return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in erupiVoucherInitiateDetails====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		erupiVoucherCreatedListResponse=new ErupiVoucherCreatedListResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
		        String jsonEncript = EncryptionDecriptionUtil.convertToJson(erupiVoucherCreatedListResponse);
	            jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in erupiVoucherInitiateDetails====="+e);
			}
    	    return ResponseEntity.ok(jsonEncriptObject);
	    }
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/erupiVoucherCreateListLimit",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiVoucherCreateListLimit(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside erupiVoucherCreateList....");	    	
	    	
	    
	    	String message = "";
	    	List<ErupiVoucherCreatedRedeemDto> response=null;
	    	ErupiVoucherCreatedRedeemListResponse erupiVoucherCreatedRedeemListResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
//				EncriptResponse enResponse
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ErupiVoucherCreatedRequest erupiLinkAccountRequest= EncryptionDecriptionUtil.convertFromJson(decript, ErupiVoucherCreatedRequest.class);
				response=erupiVoucherInitiateDetailsService.getErupiVoucherCreateDetailsListLimit(erupiLinkAccountRequest);
	    		//System.out.println(response.size());
				if(response!=null && response.size()>0) {
					erupiVoucherCreatedRedeemListResponse=new ErupiVoucherCreatedRedeemListResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherCreatedRedeemListResponse);
					 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					 return ResponseEntity.ok(jsonEncriptObject);

	    		}else {
	    			erupiVoucherCreatedRedeemListResponse=new ErupiVoucherCreatedRedeemListResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherCreatedRedeemListResponse);
					 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					 return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in erupiVoucherInitiateDetails====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		erupiVoucherCreatedRedeemListResponse=new ErupiVoucherCreatedRedeemListResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
		        String jsonEncript = EncryptionDecriptionUtil.convertToJson(erupiVoucherCreatedRedeemListResponse);
	            jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in erupiVoucherInitiateDetails====="+e);
			}
 	    return ResponseEntity.ok(jsonEncriptObject);
	    }
	 
	 
	 
//	 @Operation(summary = "This API will provide the Save User Details ", security = {
//	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
//	    @ApiResponses(value = {
//	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
//	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
//	    @RequestMapping(value = "/add/erupiVoucherRevoke",produces = {"application/json"}, 
//	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
//	    public ResponseEntity<Object> erupiVoucherRevoke(HttpServletRequest request,@Valid @RequestBody ErupiVoucherRevokeDetailsRequest erupiVoucherRevokeDetailsRequest) {
//		 
//	    logger.info("inside erupiVoucherRevoke....");	    	
//	    	
//	    
//	    	String message = "";
//	    	ErupiVoucherRevokeDetailsRequest response=null;
//	    	try {
//	    		
//	    		String companyId = request.getHeader("companyId");
//				SetDatabaseTenent.setDataSource(companyId);
//				
//				response=erupiVoucherInitiateDetailsService.erupiVoucherRevokeDetails(erupiVoucherRevokeDetailsRequest);
//	    		
//				if(response!=null && response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
//	    			return ResponseEntity.ok(new ErupiVoucherRevokeDetailsResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
//	    		}else {
//	    			return ResponseEntity.ok(new ErupiVoucherRevokeDetailsResponse(MessageConstant.FALSE,response.getResponse(),response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
//	    		}
//	    	}catch (Exception e) {				
//	    		logger.error("error in erupiVoucherRevokeDetails====="+e);
//			}
//	        
//	        return ResponseEntity.ok(new ErupiVoucherRevokeDetailsResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
//	    }
	 
//	 @Operation(summary = "This API will provide the Save User Details ", security = {
//	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
//	    @ApiResponses(value = {
//	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
//	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
//	    @RequestMapping(value = "/get/voucherSummaryList",produces = {"application/json"}, 
//	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
//	    public ResponseEntity<Object> erupiVoucherSummaryList(HttpServletRequest request,@Valid @RequestBody ErupiVoucherCreatedRequest erupiLinkAccountRequest) {
//		 
//	    logger.info("inside voucherSummaryList....");	    	
//	    	
//	    
//	    	String message = "";
//	    	Long count=0l;
//	    	Long amount=0l;
//	    	List<ErupiVoucherSummaryDto> response=null;
//	    	try {	    		
//	    		String companyId = request.getHeader("companyId");
//				SetDatabaseTenent.setDataSource(companyId);
//				
//				response=erupiVoucherInitiateDetailsService.getErupiVoucherSummaryList(erupiLinkAccountRequest);
//	    		
//				if(response!=null ) {
//					count=CommonUtility.getCount(response);
//					amount=CommonUtility.getAmount(response);
//	    			return ResponseEntity.ok(new ErupiVoucherSummaryListResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,count,amount,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
//	    		}else {
//	    			return ResponseEntity.ok(new ErupiVoucherSummaryListResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,count,amount,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
//	    		}
//	    	}catch (Exception e) {				
//	    		logger.error("error in voucherSummaryList====="+e);
//			}
//	        
//	        return ResponseEntity.ok(new ErupiVoucherSummaryListResponse(MessageConstant.FALSE,message,count,amount,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
//	    }
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/update/erupiVoucherRevokeSingle",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiVoucherRevokeSingle(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside erupiVoucherRevoke....");	    	
	    	
	    
	    	String message = "";
	    	ErupiVoucherRevokeDetailsSingleRequest response=null;
	    	ErupiVoucherRevokeSingleDetailsResponse erupiVoucherRevokeSingleDetailsResponse;
	    	try {
	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ErupiVoucherRevokeDetailsSingleRequest erupiVoucherRevokeSingleDetailsRequest= EncryptionDecriptionUtil.convertFromJson(decript, ErupiVoucherRevokeDetailsSingleRequest.class);
				
				response=erupiVoucherInitiateDetailsService.erupiVoucherRevokeSingleDetails(erupiVoucherRevokeSingleDetailsRequest);
	    		
				if(response!=null && response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
					erupiVoucherRevokeSingleDetailsResponse=new ErupiVoucherRevokeSingleDetailsResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherRevokeSingleDetailsResponse);
					 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			erupiVoucherRevokeSingleDetailsResponse=new ErupiVoucherRevokeSingleDetailsResponse(MessageConstant.FALSE,response.getResponseApi(),response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherRevokeSingleDetailsResponse);
					 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in erupiVoucherRevoke====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		erupiVoucherRevokeSingleDetailsResponse=new ErupiVoucherRevokeSingleDetailsResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
		        String jsonEncript = EncryptionDecriptionUtil.convertToJson(erupiVoucherRevokeSingleDetailsResponse);
	            jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in erupiVoucherRevoke====="+e);
			}
    	    return ResponseEntity.ok(jsonEncriptObject);
	    }
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/update/erupiVoucherRevokeBulk",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiVoucherRevokeBulk(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside erupiVoucherRevokeBulk....");	    	
	    	
	    
	    	String message = "";
	    	List<ErupiVoucherRevokeDetailsSingleRequest> response=null;
	    	ErupiVoucherRevokeBulkDetailsResponse erupiVoucherRevokeBulkDetailsResponse;
	    	try {
	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ErupiVoucherRevokeDetailsBulkRequest erupiVoucherRevokeBulkDetailsRequest= EncryptionDecriptionUtil.convertFromJson(decript, ErupiVoucherRevokeDetailsBulkRequest.class);
				
				response=erupiVoucherInitiateDetailsService.erupiVoucherRevokeBulkDetails(erupiVoucherRevokeBulkDetailsRequest);
	    		
				if(response!=null && response.size()>0) {
					erupiVoucherRevokeBulkDetailsResponse=new ErupiVoucherRevokeBulkDetailsResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherRevokeBulkDetailsResponse);
					 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			erupiVoucherRevokeBulkDetailsResponse=new ErupiVoucherRevokeBulkDetailsResponse(MessageConstant.FALSE,MessageConstant.PROFILE_FAILED_REVOKE,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherRevokeBulkDetailsResponse);
					 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in erupiVoucherRevokeBulk====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		erupiVoucherRevokeBulkDetailsResponse=new ErupiVoucherRevokeBulkDetailsResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
		        String jsonEncript = EncryptionDecriptionUtil.convertToJson(erupiVoucherRevokeBulkDetailsResponse);
	            jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in erupiVoucherRevokeBulk====="+e);
			}
 	    return ResponseEntity.ok(jsonEncriptObject);
	    }
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/update/erupiVoucherRedem",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiVoucherRedem(HttpServletRequest request,@Valid @RequestBody ErupiVoucherRedemeRequest erupiVoucherRedemeRequest) {
		 
	    logger.info("inside erupiVoucherRedem....");	    	
	    	
	    
	    	String message = "";
	    	ErupiVoucherRedemeRequest response=null;
	    	try {
	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				message=erupiVoucherInitiateDetailsService.erupiVoucherRedemDetails(erupiVoucherRedemeRequest);
	    		//return null;
	    	}catch (Exception e) {				
	    		logger.error("error in erupiVoucherRedem====="+e);
			}

			return ResponseEntity.ok(message);
	    }
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/voucherCreateSummaryList",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> voucherCreateSummaryList(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside voucherSummaryList....");
	    	String message = "";
	    	Long count=0l;
	    	Long amount=0l;
	    	List<ErupiVoucherCreateSummaryDto> response=null;
	    	ErupiVoucherCreateSummaryListResponse erupiVoucherCreateSummaryListResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ErupiVoucherCreatedRequest erupiLinkAccountRequest= EncryptionDecriptionUtil.convertFromJson(decript, ErupiVoucherCreatedRequest.class);
				response=erupiVoucherInitiateDetailsService.getErupiVoucherCreateSummaryList(erupiLinkAccountRequest);
	    		
				if(response!=null ) {
					erupiVoucherCreateSummaryListResponse=new ErupiVoucherCreateSummaryListResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherCreateSummaryListResponse);
					 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					 return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			erupiVoucherCreateSummaryListResponse=new ErupiVoucherCreateSummaryListResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherCreateSummaryListResponse);
					 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					 return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in voucherSummaryList====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		erupiVoucherCreateSummaryListResponse=new ErupiVoucherCreateSummaryListResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
				 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherCreateSummaryListResponse);
				 jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				// TODO: handle exception
			}
    	    return ResponseEntity.ok(jsonEncriptObject);
	    }
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/add/erupiVoucherCreateList",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> adderupiVoucherCreateList(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    //logger.info("inside erupiVoucherCreateList...."+erupiVoucherCreateListRequest);	    	
	    	
	    
	    	String message = "";
	    	List<ErupiVoucherCreateDetailsRequest> response=null;
	    	ErupiVoucherCreateListResponse erupiVoucherDetailResponse;
	    	try {
	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ErupiVoucherCreateListRequest erupiVoucherCreateListRequest= EncryptionDecriptionUtil.convertFromJson(decript, ErupiVoucherCreateListRequest.class);
				
				response=erupiVoucherInitiateDetailsService.saveErupiVoucherCreateListDetails(erupiVoucherCreateListRequest);
	    		
				if(response!=null) {
					erupiVoucherDetailResponse=new ErupiVoucherCreateListResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherDetailResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			erupiVoucherDetailResponse=new ErupiVoucherCreateListResponse(MessageConstant.FALSE,MessageConstant.PROFILE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherDetailResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in erupiVoucherInitiateDetails====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		erupiVoucherDetailResponse=new ErupiVoucherCreateListResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
				String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherDetailResponse);
				jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				// TODO: handle exception
			}
    	    return ResponseEntity.ok(jsonEncriptObject);
	    }
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/erupiVoucherOldList",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiVoucherOldList(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside erupiVoucherOldList....");	    	
	    	
	    
	    	String message = "";
	    	List<ErupiVoucherCreateOldDto> response=null;
	    	ErupiVoucherCreateNameResponse erupiVoucherDetailResponse;
	    	
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		    	ErupiVoucherCreatedRequest erupiVoucherCreateListRequest= EncryptionDecriptionUtil.convertFromJson(decript, ErupiVoucherCreatedRequest.class);
				response=erupiVoucherInitiateDetailsService.getErupiVoucherCreateOldList(erupiVoucherCreateListRequest);
	    		
				if(response!=null) {
					erupiVoucherDetailResponse=new ErupiVoucherCreateNameResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherDetailResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			erupiVoucherDetailResponse=new ErupiVoucherCreateNameResponse(MessageConstant.FALSE,MessageConstant.PROFILE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherDetailResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in erupiVoucherOldList====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		erupiVoucherDetailResponse=new ErupiVoucherCreateNameResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
				String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherDetailResponse);
				jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				// TODO: handle exception
			}
    	    return ResponseEntity.ok(jsonEncriptObject);
	    }
	 
//	 @Operation(summary = "This API will provide the Save User Details ", security = {
//	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
//	    @ApiResponses(value = {
//	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
//	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
//	    @RequestMapping(value = "/add/erupiMultipleVoucherCreation",produces = {"application/json"}, 
//	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
//	    public ResponseEntity<Object> erupiMultipleVoucherCreation(HttpServletRequest request,@Valid @RequestBody ErupiMultipleVoucherCreateRequest erupiMultipleVoucherCreateRequest) {
//		 
//	    logger.info("inside erupiMultipleVoucherCreation....");	    	
//	    	
//	    	String message = "";
//	    	ErupiMultipleVoucherCreateRequest response=null;
//	    	try {	    		
//	    		String companyId = request.getHeader("companyId");
//				SetDatabaseTenent.setDataSource(companyId);
//				
//				response=erupiVoucherInitiateDetailsService.saveErupiMultipleVoucherCreation(erupiMultipleVoucherCreateRequest);
//	    		
//				if(response!=null) {
//	    			return ResponseEntity.ok(new ErupiMultipleVoucherCreationResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
//	    		}else {
//	    			return ResponseEntity.ok(new ErupiMultipleVoucherCreationResponse(MessageConstant.FALSE,MessageConstant.PROFILE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
//	    		}
//	    	}catch (Exception e) {				
//	    		logger.error("error in erupiMultipleVoucherCreation====="+e);
//			}
//	        
//	        return ResponseEntity.ok(new ErupiMultipleVoucherCreationResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
//	    }
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/voucherSummaryTotalCount",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> voucherSummaryTotalCount(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside voucherSummaryTotalCount....");	    	
	    	
	    
	    	String message = "";
	    	Long count=0l;
	    	Long amount=0l;
	    	List<ErupiVoucherSummaryDto> response=null;
	    	ErupiVoucherSummaryTotalCountResponse erupiVoucherSummaryTotalCountResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ErupiVoucherCreatedRequest erupiLinkAccountRequest= EncryptionDecriptionUtil.convertFromJson(decript, ErupiVoucherCreatedRequest.class);
				response=erupiVoucherInitiateDetailsService.getErupiVoucherSummaryList(erupiLinkAccountRequest);
	    		
				if(response!=null ) {
					count=CommonUtility.getCount(response);
					amount=CommonUtility.getAmount(response);
					erupiVoucherSummaryTotalCountResponse=new ErupiVoucherSummaryTotalCountResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,count,amount,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherSummaryTotalCountResponse);
					 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					 return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			erupiVoucherSummaryTotalCountResponse=new ErupiVoucherSummaryTotalCountResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,count,amount,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherSummaryTotalCountResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in voucherSummaryTotalCount====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		erupiVoucherSummaryTotalCountResponse=new ErupiVoucherSummaryTotalCountResponse(MessageConstant.FALSE,message,count,amount,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
		        String jsonEncript = EncryptionDecriptionUtil.convertToJson(erupiVoucherSummaryTotalCountResponse);
	            jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in voucherSummaryTotalCount====="+e);
			}
    	    return ResponseEntity.ok(jsonEncriptObject);
	    }
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/voucherCreateBankList",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> voucherCreateBankList(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside voucherCreateBankList....");	    	
	    	
	    
	    	String message = "";
	    	Long count=0l;
	    	Long amount=0l;
	    	List<ErupiVoucherBankListDto> response=null;
	    	ErupiVoucherTotalDetailDto response1=null;
	    	ErupiVoucherBankDetailListResponse erupiVoucherBankDetailListResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ErupiVoucherCreatedRequest erupiLinkAccountRequest= EncryptionDecriptionUtil.convertFromJson(decript, ErupiVoucherCreatedRequest.class);
				
				response=erupiVoucherInitiateDetailsService.getErupiVoucherCreateBAnkList(erupiLinkAccountRequest);
				if(response!=null && response.size()>0) {
					response1=erupiVoucherInitiateDetailsService.getErupiVoucherCreateStatus(erupiLinkAccountRequest);
				}
				if(response!=null ) {
					//count=CommonUtility.getCreateCount(response);
					//amount=CommonUtility.getCreateAmount(response);
					erupiVoucherBankDetailListResponse=new ErupiVoucherBankDetailListResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,response1,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherBankDetailListResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			erupiVoucherBankDetailListResponse=new ErupiVoucherBankDetailListResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,response1,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherBankDetailListResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in voucherCreateBankList====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		erupiVoucherBankDetailListResponse=new ErupiVoucherBankDetailListResponse(MessageConstant.FALSE,message,response,response1,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
				String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherBankDetailListResponse);
				jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in voucherCreateBankList====="+e);
			}
    	    return ResponseEntity.ok(jsonEncriptObject);
	    }
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/voucherCreateSummaryDetailByAccount",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> voucherCreateSummaryDetailByAccount(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside voucherCreateSummaryDetailByAccount....");
	    
	    	String message = "";
	    	ErupiVoucherTotalDetailDto response=null;
	    	ErupiVoucherDetailResponse erupiVoucherDetailResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ErupiVoucherCreatedRequest erupiLinkAccountRequest= EncryptionDecriptionUtil.convertFromJson(decript, ErupiVoucherCreatedRequest.class);
				
				response=erupiVoucherInitiateDetailsService.getErupiVoucherCreateDetailByAccount(erupiLinkAccountRequest);
				
				if(response!=null ) {
					erupiVoucherDetailResponse=new ErupiVoucherDetailResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
		    		 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherDetailResponse);
			         EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			    	 return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			erupiVoucherDetailResponse=new ErupiVoucherDetailResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherDetailResponse);
			        EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			    	return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in voucherCreateSummaryDetailByAccount====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		erupiVoucherDetailResponse=new ErupiVoucherDetailResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
		        String jsonEncript = EncryptionDecriptionUtil.convertToJson(erupiVoucherDetailResponse);
	            jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				// TODO: handle exception
			}
    	    return ResponseEntity.ok(jsonEncriptObject);
	 }
//	 @Operation(summary = "This API will provide the Save User Details ", security = {
//	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
//	    @ApiResponses(value = {
//	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
//	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
//	    @RequestMapping(value = "/get/erupiVoucherCreateById",produces = {"application/json"}, 
//	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
//	    public ResponseEntity<Object> erupiVoucherCreateById(HttpServletRequest request,@Valid @RequestBody ErupiVoucherCreatedRequest erupiLinkAccountRequest) {
//		 
//	    logger.info("inside erupiVoucherCreateById....");	    	
//	    	
//	    
//	    	String message = "";
//	    	ErupiVoucherCreatedDto response=null;
//	    	try {	    		
//	    		String companyId = request.getHeader("companyId");
//				SetDatabaseTenent.setDataSource(companyId);
//				
//				response=erupiVoucherInitiateDetailsService.getErupiVoucherCreateDetailsById(erupiLinkAccountRequest);
//	    		//System.out.println(response.size());
//				if(response!=null) {
//	    			return ResponseEntity.ok(new ErupiVoucherCreatedByIdResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
//	    		}else {
//	    			return ResponseEntity.ok(new ErupiVoucherCreatedByIdResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
//	    		}
//	    	}catch (Exception e) {				
//	    		logger.error("error in erupiVoucherCreateById====="+e);
//			}
//	        
//	        return ResponseEntity.ok(new ErupiVoucherCreatedByIdResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
//	    }
//	 @Operation(summary = "This API will provide the Save User Details ", security = {
//	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
//	    @ApiResponses(value = {
//	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
//	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
//	    @RequestMapping(value = "/get/erupiVoucherCreateListDateWise",produces = {"application/json"}, 
//	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
//	    public ResponseEntity<Object> erupiVoucherCreateListDateWise(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
//		 
//	    logger.info("inside erupiVoucherCreateList....");	    	
//	    	
//	    
//	    	String message = "";
//	    	List<ErupiVoucherCreatedDto> response=null;
//	    	ErupiVoucherCreatedListResponse erupiVoucherCreatedListResponse;
//	    	try {	    		
//	    		String companyId = request.getHeader("companyId");
//				SetDatabaseTenent.setDataSource(companyId);
//				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
//				ErupiVoucherCreatedDateWiseRequest erupiLinkAccountRequest= EncryptionDecriptionUtil.convertFromJson(decript, ErupiVoucherCreatedDateWiseRequest.class);
//				response=erupiVoucherInitiateDetailsService.getErupiVoucherCreateDetailsListDateWise(erupiLinkAccountRequest);
//	    		//System.out.println(response.size());
//				if(response!=null && response.size()>0) {
//					erupiVoucherCreatedListResponse=new ErupiVoucherCreatedListResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
//					 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherCreatedListResponse);
//					 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
//					 return ResponseEntity.ok(jsonEncriptObject);
//
//	    		}else {
//	    			erupiVoucherCreatedListResponse=new ErupiVoucherCreatedListResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
//					 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherCreatedListResponse);
//					 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
//					 return ResponseEntity.ok(jsonEncriptObject);
//	    		}
//	    	}catch (Exception e) {				
//	    		logger.error("error in erupiVoucherInitiateDetails====="+e);
//			}
//	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
//	    	try {
//	    		erupiVoucherCreatedListResponse=new ErupiVoucherCreatedListResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
//		        String jsonEncript = EncryptionDecriptionUtil.convertToJson(erupiVoucherCreatedListResponse);
//	            jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
//			} catch (Exception e) {
//				logger.error("error in erupiVoucherInitiateDetails====="+e);
//			}
// 	    return ResponseEntity.ok(jsonEncriptObject);
//	    }
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/erupiVoucherCreateListDateWise",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiVoucherCreateListDateWise(HttpServletRequest request,@Valid @RequestBody ErupiVoucherCreatedDateWiseRequest erupiLinkAccountRequest) {
		 
	    logger.info("inside erupiVoucherCreateListDateWise....");	    	
	    	
	    
	    	String message = "";
	    	List<ErupiVoucherCreatedDateWiseDto> response=null;
	    	ErupiVoucherCreatedListDateWiseResponse erupiVoucherCreatedListResponse=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				String clientId = request.getHeader("clientId");
				String secretId = request.getHeader("secretId");
				System.out.println(clientId);
				System.out.println(secretId);
				boolean result=erupiVoucherInitiateDetailsService.getSecurityCheck(clientId, secretId,erupiLinkAccountRequest.getBankCode());
				if(result) {
//				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
//				ErupiVoucherCreatedDateWiseRequest erupiLinkAccountRequest= EncryptionDecriptionUtil.convertFromJson(decript, ErupiVoucherCreatedDateWiseRequest.class);
				response=erupiVoucherInitiateDetailsService.getErupiVoucherCreateDetailsListDateWise(erupiLinkAccountRequest);
				if(response!=null && response.size()>0) {
					erupiVoucherCreatedListResponse=new ErupiVoucherCreatedListDateWiseResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					 //String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherCreatedListResponse);
					 //EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					 return ResponseEntity.ok(erupiVoucherCreatedListResponse);

	    		}else {
	    			erupiVoucherCreatedListResponse=new ErupiVoucherCreatedListDateWiseResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					 //String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherCreatedListResponse);
					 //EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					 return ResponseEntity.ok(erupiVoucherCreatedListResponse);
	    		}
				}else {
					erupiVoucherCreatedListResponse=new ErupiVoucherCreatedListDateWiseResponse(MessageConstant.FALSE,MessageConstant.INVALID_SECRET,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					 //String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherCreatedListResponse);
					 //EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					 return ResponseEntity.ok(erupiVoucherCreatedListResponse);
				}
	    		//System.out.println(response.size());
				
	    	}catch (Exception e) {				
	    		logger.error("error in erupiVoucherCreateListDateWise====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		erupiVoucherCreatedListResponse=new ErupiVoucherCreatedListDateWiseResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
		        //String jsonEncript = EncryptionDecriptionUtil.convertToJson(erupiVoucherCreatedListResponse);
	            //jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in erupiVoucherCreateListDateWise====="+e);
			}
	    return ResponseEntity.ok(erupiVoucherCreatedListResponse);
	    }
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/voucherAmount",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> voucherAmount(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside voucherAmount....");
	    
	    	String message = "";
	    	List<AccountWiseAmountDTO> response=null;
	    	ErupiVoucherAmountDetailResponse erupiVoucherAmountDetailResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ErupiVoucherAmountRequest erupiLinkAccountRequest= EncryptionDecriptionUtil.convertFromJson(decript, ErupiVoucherAmountRequest.class);
				
				response=erupiVoucherInitiateDetailsService.getErupiVoucherAmountDetailByAccount(erupiLinkAccountRequest);
				
				if(response!=null ) {
					erupiVoucherAmountDetailResponse=new ErupiVoucherAmountDetailResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
		    		 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherAmountDetailResponse);
			         EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			    	 return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			erupiVoucherAmountDetailResponse=new ErupiVoucherAmountDetailResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherAmountDetailResponse);
			        EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			    	return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in voucherCreateSummaryDetailByAccount====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		erupiVoucherAmountDetailResponse=new ErupiVoucherAmountDetailResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
		        String jsonEncript = EncryptionDecriptionUtil.convertToJson(erupiVoucherAmountDetailResponse);
	            jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				// TODO: handle exception
			}
 	    return ResponseEntity.ok(jsonEncriptObject);
	 }
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/erupiVoucherCreateListByPurposeCode",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiVoucherCreateListByPurposeCode(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside erupiVoucherCreateListByPurposeCode....");	    	
	    	
	    
	    	String message = "";
	    	List<PurposeCodeAmountWithImgDto> response=null;
	    	ErupiVoucherCreatedListByPurposeCodeResponse erupiVoucherCreatedListResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
//				EncriptResponse enResponse
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ErupiVoucherPurposeCodeRequest erupiLinkAccountRequest= EncryptionDecriptionUtil.convertFromJson(decript, ErupiVoucherPurposeCodeRequest.class);
				response=erupiVoucherInitiateDetailsService.getErupiVoucherCreateDetailsListByPuposeCode(erupiLinkAccountRequest);
	    		//System.out.println(response.size());
				if(response!=null && response.size()>0) {
					erupiVoucherCreatedListResponse=new ErupiVoucherCreatedListByPurposeCodeResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherCreatedListResponse);
					 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					 return ResponseEntity.ok(jsonEncriptObject);

	    		}else {
	    			erupiVoucherCreatedListResponse=new ErupiVoucherCreatedListByPurposeCodeResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherCreatedListResponse);
					 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					 return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in erupiVoucherCreateListByPurposeCode====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		erupiVoucherCreatedListResponse=new ErupiVoucherCreatedListByPurposeCodeResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
		        String jsonEncript = EncryptionDecriptionUtil.convertToJson(erupiVoucherCreatedListResponse);
	            jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in erupiVoucherCreateListByPurposeCode====="+e);
			}
 	    return ResponseEntity.ok(jsonEncriptObject);
	    }
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/erupiVoucherCreateTransactionList",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiVoucherCreateTransactionList(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside erupiVoucherCreateTransactionList....");	    	
	    	
	    
	    	String message = "";
	    	List<ErupiVoucherCreatedDto> response=null;
	    	ErupiVoucherCreatedListResponse erupiVoucherCreatedListResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
//				EncriptResponse enResponse
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ErupiVoucherCreateTransactionRequest erupiLinkAccountRequest= EncryptionDecriptionUtil.convertFromJson(decript, ErupiVoucherCreateTransactionRequest.class);
				response=erupiVoucherInitiateDetailsService.getErupiVoucherCreateDetailsTransactionList(erupiLinkAccountRequest);
				if(response!=null && response.size()>0) {
					erupiVoucherCreatedListResponse=new ErupiVoucherCreatedListResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherCreatedListResponse);
					 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					 return ResponseEntity.ok(jsonEncriptObject);

	    		}else {
	    			erupiVoucherCreatedListResponse=new ErupiVoucherCreatedListResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherCreatedListResponse);
					 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					 return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in erupiVoucherCreateTransactionList====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		erupiVoucherCreatedListResponse=new ErupiVoucherCreatedListResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
		        String jsonEncript = EncryptionDecriptionUtil.convertToJson(erupiVoucherCreatedListResponse);
	            jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in erupiVoucherCreateTransactionList====="+e);
			}
 	    return ResponseEntity.ok(jsonEncriptObject);
	    }
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/add/erupiVoucherSingleCreation",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiVoucherSingleCreation(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside erupiVoucherSingleCreation....");	    	
	    	
	    
	    	String message = "";
	    	ErupiVoucherCreationDetailsSingleRequest res;
	    	List<ErupiVoucherCreateDetailsRequest> response=null;
	    	ErupiVoucherCreationDetailsResponse erupiVoucherCreationDetailsResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ErupiVoucherCreationDetailsSingleRequest erupiVoucherCreationDetailsSingleRequest= EncryptionDecriptionUtil.convertFromJson(decript, ErupiVoucherCreationDetailsSingleRequest.class);
				res=erupiVoucherInitiateDetailsService.createErupiVoucherSingleValidate(erupiVoucherCreationDetailsSingleRequest);
				if(res.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
					response=erupiVoucherInitiateDetailsService.saveErupiVoucherSingleCreationDetails(erupiVoucherCreationDetailsSingleRequest);
				}else {
					erupiVoucherCreationDetailsResponse=new ErupiVoucherCreationDetailsResponse(MessageConstant.FALSE,res.getResponse(),response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherCreationDetailsResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
				}
				
	    		
				if(response!=null && response.size()>0) {
					erupiVoucherCreationDetailsResponse=new ErupiVoucherCreationDetailsResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherCreationDetailsResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			erupiVoucherCreationDetailsResponse=new ErupiVoucherCreationDetailsResponse(MessageConstant.FALSE,MessageConstant.PROFILE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherCreationDetailsResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in erupiVoucherSingleCreation====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		erupiVoucherCreationDetailsResponse=new ErupiVoucherCreationDetailsResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
				String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherCreationDetailsResponse);
				jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				// TODO: handle exception
			}
 	    return ResponseEntity.ok(jsonEncriptObject);
	    }
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/erupiVoucherCreateListRedeem",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiVoucherCreateListRedeem(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside erupiVoucherCreateListRedeem....");	    	
	    	
	    
	    	String message = "";
	    	List<ErupiVoucherCreatedRedeemTransactionImgDto> response=null;
	    	ErupiVoucherCreatedRedeemTransactionListResponse erupiVoucherCreatedRedeemTransactionListResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
//				EncriptResponse enResponse
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ErupiVoucherCreatedRequest erupiLinkAccountRequest= EncryptionDecriptionUtil.convertFromJson(decript, ErupiVoucherCreatedRequest.class);
				response=erupiVoucherInitiateDetailsService.getErupiVoucherCreateDetailsListRedeem(erupiLinkAccountRequest);
	    		//System.out.println(response.size());
				if(response!=null && response.size()>0) {
					erupiVoucherCreatedRedeemTransactionListResponse=new ErupiVoucherCreatedRedeemTransactionListResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherCreatedRedeemTransactionListResponse);
					 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					 return ResponseEntity.ok(jsonEncriptObject);

	    		}else {
	    			erupiVoucherCreatedRedeemTransactionListResponse=new ErupiVoucherCreatedRedeemTransactionListResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherCreatedRedeemTransactionListResponse);
					 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					 return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in erupiVoucherCreateListRedeem====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		erupiVoucherCreatedRedeemTransactionListResponse=new ErupiVoucherCreatedRedeemTransactionListResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
		        String jsonEncript = EncryptionDecriptionUtil.convertToJson(erupiVoucherCreatedRedeemTransactionListResponse);
	            jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in erupiVoucherCreateListRedeem====="+e);
			}
	    return ResponseEntity.ok(jsonEncriptObject);
	    }
	 
	 public static void main(String[] args) {
		 ErupiVoucherCreatedDateWiseRequest erupiVoucherCreatedDateWiseRequest=new ErupiVoucherCreatedDateWiseRequest();
		 String dateString = "2025-03-02";
		 String enddateString = "2025-04-02";
	        LocalDate localDate = LocalDate.parse(dateString);
	        LocalDate endlocalDate = LocalDate.parse(enddateString);
	        erupiVoucherCreatedDateWiseRequest.setFromDate(localDate);
	        erupiVoucherCreatedDateWiseRequest.setToDate(endlocalDate);
	        try {
	        	LocalDateTime date=LocalDateTime.now();
	        	System.out.println(date);
	        	String dd=date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
	        	
//	        	String data="MwnvmL6GDwAq3r+BlBRp+Wt0XxtWFbZjprK8QyO/LDO4+0s/gacP8G1j/o2OH5j+pCsKY4cv9ln+k7ATtfxHy68krvJX/kORQV43pFkG7uTt6YcwozGMUCS3QMQbXPpXtizh0tFQWY9JqeOu2jsoimUjFpAhh6vLWn30kTDuhOzJ9YclD82TSyesj+eF7pgY3n605U2cQqIhYGA7uEUQjhJtSD4Cuy+Ntvf8GO5E5nZsX5Q0p1SvEL6TUI0yKD2TsvAHkOmirPibXGYVFD07ANg4UprCH7LKbnIrou70/gFrGqcWJ73ZodqV822YRlcG9WktNNop1Gx5EOt3M5vHNS1sZ5vmodJHwwSyCdn8pUHkKTpEf8eOdoL/enDh+atN/rb9VrXomOt5MiIz8jhAdImuxtkyCbfwo0DSyZ4UHlpn4hPcXJTlhYz8fOVqWtPVXxCmca1i3NkcTy2bbAifCXQeR6mDCSD5a29eIy8voA+Ty7btGD9XVV1v9ktgao6MnPHthJ7Q5AgimmJE9nhQ/g6Je4pFBQqXNUKU3iStDU8iJByJ09OWUh5Qh5z+YLy08rkMQBWHWmWi8QYbU0KsVGugFxH5TMl+hU9exs9fqMFur23TVd0QRhC+4ZQcaF772f6xhZ0G58vG4xY3e1l5mPUj7IqMvnbwIcqWIJXm4QlBx3GUj5PfKsE53yzusRKD/gcwwbNgikxOrjNz/BWDqB+AHE7ybJ62T3Mvir7CU8t1AYAVc5lrZ5rYngWMbLY2HY7dAmuzEYznwGLaQdPkshnUcAO8yB9lWjzPnktdvIB3NK1pAzWIbL5qOv9l+fi+Xd/MfedABFse0Xn1Dt3A+g\\u003d\\u003d";
//	        	String encr="J048Hpu6FKk9mudtPrPRO3tiFhX1Hma6Kx13s/KC1rchz7xTTldddFzGJQJXm1XugmpPty5JXjJ+XQdNEfvlsm0d8GrUyR26Bkj36BEeyXVP+OuzraS2NwZxC0r/efFXrN/m3HKLoNPpA43dS7EFiFmUxl6xD1fWCx/h18ggcVI\\u003d";
//	        	String decript=EncryptionDecriptionUtil.decriptResponse(data, encr, "/opt/cotodel/key/pvtKeyForApplication.txt");
//	        	System.out.println("decript::"+decript);
//	        	 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherCreatedDateWiseRequest);
//	        	 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, "/opt/cotodel/key/publicKeyForApplication.txt");
//	        	 String jsonEncript1 =  EncryptionDecriptionUtil.convertToJson(jsonEncriptObject);
//	        	 System.out.println(jsonEncript1);
			} catch (Exception e) {
				e.printStackTrace();
			}
	       
	}
}
