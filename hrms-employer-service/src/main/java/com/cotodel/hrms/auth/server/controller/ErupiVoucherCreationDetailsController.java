package com.cotodel.hrms.auth.server.controller;

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

import com.cotodel.hrms.auth.server.dto.ErupiMultipleVoucherCreateRequest;
import com.cotodel.hrms.auth.server.dto.ErupiMultipleVoucherCreationResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherBankDetailListResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherBankListDto;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreateDetailsRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedByIdResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedDateWiseDto;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedDateWiseRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedDto;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedListDateWiseResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedListResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherDetailResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherInitiateDetailsResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherRevokeDetailsRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherRevokeDetailsResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherSummaryDto;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherSummaryListResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherSummaryTotalCountResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherTotalDetailDto;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreateListRequest;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreateListResponse;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreateNameResponse;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreateOldDto;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreateSummaryDto;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreateSummaryListResponse;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherRedemeRequest;
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
	    @RequestMapping(value = "/add/erupiVoucherRevoke",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiVoucherRevoke(HttpServletRequest request,@Valid @RequestBody ErupiVoucherRevokeDetailsRequest erupiVoucherRevokeDetailsRequest) {
		 
	    logger.info("inside erupiVoucherRevoke....");	    	
	    	
	    
	    	String message = "";
	    	ErupiVoucherRevokeDetailsRequest response=null;
	    	try {
	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=erupiVoucherInitiateDetailsService.erupiVoucherRevokeDetails(erupiVoucherRevokeDetailsRequest);
	    		
				if(response!=null && response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			return ResponseEntity.ok(new ErupiVoucherRevokeDetailsResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new ErupiVoucherRevokeDetailsResponse(MessageConstant.FALSE,response.getResponse(),response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in erupiVoucherRevokeDetails====="+e);
			}
	        
	        return ResponseEntity.ok(new ErupiVoucherRevokeDetailsResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/voucherSummaryList",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiVoucherSummaryList(HttpServletRequest request,@Valid @RequestBody ErupiVoucherCreatedRequest erupiLinkAccountRequest) {
		 
	    logger.info("inside voucherSummaryList....");	    	
	    	
	    
	    	String message = "";
	    	Long count=0l;
	    	Long amount=0l;
	    	List<ErupiVoucherSummaryDto> response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=erupiVoucherInitiateDetailsService.getErupiVoucherSummaryList(erupiLinkAccountRequest);
	    		
				if(response!=null ) {
					count=CommonUtility.getCount(response);
					amount=CommonUtility.getAmount(response);
	    			return ResponseEntity.ok(new ErupiVoucherSummaryListResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,count,amount,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new ErupiVoucherSummaryListResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,count,amount,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in voucherSummaryList====="+e);
			}
	        
	        return ResponseEntity.ok(new ErupiVoucherSummaryListResponse(MessageConstant.FALSE,message,count,amount,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
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
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/add/erupiMultipleVoucherCreation",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiMultipleVoucherCreation(HttpServletRequest request,@Valid @RequestBody ErupiMultipleVoucherCreateRequest erupiMultipleVoucherCreateRequest) {
		 
	    logger.info("inside erupiMultipleVoucherCreation....");	    	
	    	
	    	String message = "";
	    	ErupiMultipleVoucherCreateRequest response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=erupiVoucherInitiateDetailsService.saveErupiMultipleVoucherCreation(erupiMultipleVoucherCreateRequest);
	    		
				if(response!=null) {
	    			return ResponseEntity.ok(new ErupiMultipleVoucherCreationResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new ErupiMultipleVoucherCreationResponse(MessageConstant.FALSE,MessageConstant.PROFILE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in erupiMultipleVoucherCreation====="+e);
			}
	        
	        return ResponseEntity.ok(new ErupiMultipleVoucherCreationResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
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
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/erupiVoucherCreateById",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiVoucherCreateById(HttpServletRequest request,@Valid @RequestBody ErupiVoucherCreatedRequest erupiLinkAccountRequest) {
		 
	    logger.info("inside erupiVoucherCreateById....");	    	
	    	
	    
	    	String message = "";
	    	ErupiVoucherCreatedDto response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=erupiVoucherInitiateDetailsService.getErupiVoucherCreateDetailsById(erupiLinkAccountRequest);
	    		//System.out.println(response.size());
				if(response!=null) {
	    			return ResponseEntity.ok(new ErupiVoucherCreatedByIdResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new ErupiVoucherCreatedByIdResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in erupiVoucherCreateById====="+e);
			}
	        
	        return ResponseEntity.ok(new ErupiVoucherCreatedByIdResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
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
//	 public static void main(String[] args) {
//		 ErupiVoucherCreatedDateWiseRequest erupiVoucherCreatedDateWiseRequest=new ErupiVoucherCreatedDateWiseRequest();
//		 String dateString = "2025-03-02";
//		 String enddateString = "2025-04-02";
//	        LocalDate localDate = LocalDate.parse(dateString);
//	        LocalDate endlocalDate = LocalDate.parse(enddateString);
//	        erupiVoucherCreatedDateWiseRequest.setFromDate(localDate);
//	        erupiVoucherCreatedDateWiseRequest.setToDate(endlocalDate);
//	        try {
//	        	 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherCreatedDateWiseRequest);
//	        	 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, "/opt/cotodel/key/publicKeyForApplication.txt");
//	        	 String jsonEncript1 =  EncryptionDecriptionUtil.convertToJson(jsonEncriptObject);
//	        	 System.out.println(jsonEncript1);
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//	       
//	}
}
