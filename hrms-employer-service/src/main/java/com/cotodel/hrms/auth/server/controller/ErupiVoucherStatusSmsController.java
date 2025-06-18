package com.cotodel.hrms.auth.server.controller;

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

import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherSmsResponse;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherStatusRedeemHistoryResponse;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherStatusRedeemResponse;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherStatusRequest;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherStatusResponse;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherStatusSmsRequest;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.multi.datasource.SetDatabaseTenent;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.ErupiVoucherStatusSmsService;
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
public class ErupiVoucherStatusSmsController {
private static final Logger logger = LoggerFactory.getLogger(ExpenseTravelController.class);
	
	@Autowired
	ErupiVoucherStatusSmsService erupiVoucherStatusSmsService;
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/erupiVoucherSms",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiVoucherSms(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside erupiVoucherSms....");    	
	    
	    	String message = "";
	    	ErupiVoucherStatusSmsRequest response=null;
	    	ErupiVoucherSmsResponse erupiVoucherSmsResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ErupiVoucherStatusSmsRequest erupiVoucherStatusSmsRequest= EncryptionDecriptionUtil.convertFromJson(decript, ErupiVoucherStatusSmsRequest.class);
				
				response=erupiVoucherStatusSmsService.erupiVoucherSmsDetails(erupiVoucherStatusSmsRequest);
	    		
				if(response!=null && response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
					erupiVoucherSmsResponse=new ErupiVoucherSmsResponse(MessageConstant.TRUE,response.getResponseApi(),response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherSmsResponse);
					 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					 return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			erupiVoucherSmsResponse=new ErupiVoucherSmsResponse(MessageConstant.FALSE,response.getResponse(),response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherSmsResponse);
					 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					 return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in erupiVoucherSms====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		erupiVoucherSmsResponse=new ErupiVoucherSmsResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
				 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherSmsResponse);
				 jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in erupiVoucherSms====="+e);
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
	    @RequestMapping(value = "/get/erupiVoucherStatus",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiVoucherStatus(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside erupiVoucherStatus....");	    	
	    	
	    
	    	String message = "";
	    	ErupiVoucherStatusRequest response=null;
	    	ErupiVoucherStatusResponse erupiVoucherStatusResponse;
	    	try {
	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ErupiVoucherStatusRequest erupiVoucherStatusRequest= EncryptionDecriptionUtil.convertFromJson(decript, ErupiVoucherStatusRequest.class);
				
				response=erupiVoucherStatusSmsService.erupiVoucherStatusDetails(erupiVoucherStatusRequest);
	    		
				if(response!=null && response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
					erupiVoucherStatusResponse=new ErupiVoucherStatusResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherStatusResponse);
					 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					 return ResponseEntity.ok(jsonEncriptObject);
				}else {
					erupiVoucherStatusResponse=new ErupiVoucherStatusResponse(MessageConstant.TRUE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherStatusResponse);
					 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					 return ResponseEntity.ok(jsonEncriptObject);
					 }
	    	}catch (Exception e) {				
	    		logger.error("error in erupiVoucherStatus====="+e);
			}
	        
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		erupiVoucherStatusResponse=new ErupiVoucherStatusResponse(MessageConstant.TRUE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
				 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherStatusResponse);
				 jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in erupiVoucherStatus====="+e);
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
	    @RequestMapping(value = "/get/erupiVoucherStatusHistory",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiVoucherStatusHistory(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside erupiVoucherStatusHistory....");	    	
	    	
	    
	    	String message = "";
	    	ErupiVoucherStatusRedeemResponse response=null;
	    	ErupiVoucherStatusRedeemHistoryResponse erupiVoucherStatusRedeemHistoryResponse;
	    	try {
	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ErupiVoucherStatusRequest erupiVoucherStatusRequest= EncryptionDecriptionUtil.convertFromJson(decript, ErupiVoucherStatusRequest.class);
				
				response=erupiVoucherStatusSmsService.erupiVoucherStatusDetailsHistory(erupiVoucherStatusRequest);
	    		
				if(response!=null && response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
					erupiVoucherStatusRedeemHistoryResponse=new ErupiVoucherStatusRedeemHistoryResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherStatusRedeemHistoryResponse);
					 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					 return ResponseEntity.ok(jsonEncriptObject);
				}else {
					erupiVoucherStatusRedeemHistoryResponse=new ErupiVoucherStatusRedeemHistoryResponse(MessageConstant.TRUE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherStatusRedeemHistoryResponse);
					 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					 return ResponseEntity.ok(jsonEncriptObject);
					 }
	    	}catch (Exception e) {				
	    		logger.error("error in erupiVoucherStatusHistory====="+e);
			}
	        
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		erupiVoucherStatusRedeemHistoryResponse=new ErupiVoucherStatusRedeemHistoryResponse(MessageConstant.TRUE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
				 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherStatusRedeemHistoryResponse);
				 jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in erupiVoucherStatusHistory====="+e);
			}
 	    return ResponseEntity.ok(jsonEncriptObject);    
	    }
	 
	 
}
