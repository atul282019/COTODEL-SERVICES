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

import com.cotodel.hrms.auth.server.dto.ErupiVoucherTxnListResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherTxnRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherTxnResponse;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.model.ErupiVoucherTxnDetailsEntity;
import com.cotodel.hrms.auth.server.multi.datasource.SetDatabaseTenent;
import com.cotodel.hrms.auth.server.service.ErupiVoucherTxnService;
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
public class ErupiVoucherTxnController {
private static final Logger logger = LoggerFactory.getLogger(ExpenseTravelController.class);
	
	@Autowired
	ErupiVoucherTxnService erupiVoucherTxnService;
	
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/add/erupiVoucherTxn",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiVoucherTxn(HttpServletRequest request,@Valid @RequestBody ErupiVoucherTxnRequest erupiVoucherTxnRequest) {
		 
	    logger.info("inside erupiVoucherTxn.... ");	    	
	    	
	    
	    
	    	String message = "";
	    	ErupiVoucherTxnRequest response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=erupiVoucherTxnService.saveErupiVoucherTxnDetails(erupiVoucherTxnRequest);
	    		
				if(response.getResponseMsg().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			return ResponseEntity.ok(new ErupiVoucherTxnResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new ErupiVoucherTxnResponse(MessageConstant.FALSE,response.getResponseMsg(),response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in erupiVoucherTxn====="+e);
			}
	        
	        return ResponseEntity.ok(new ErupiVoucherTxnResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/erupiVoucherTxn",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.GET)
	    public ResponseEntity<Object> erupiVoucherTxn(HttpServletRequest request) {
		 
	    logger.info("inside erupiVoucherTxn.... ");	    	
	    	
	    
	    
	    	String message = "";
	    	List<ErupiVoucherTxnDetailsEntity> response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=erupiVoucherTxnService.getErupiVoucherTxnDetails();
	    		
				if(response!=null) {
	    			return ResponseEntity.ok(new ErupiVoucherTxnListResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new ErupiVoucherTxnListResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in erupiVoucherTxn====="+e);
			}
	        
	        return ResponseEntity.ok(new ErupiVoucherTxnListResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/erupiVoucherTxnList",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiVoucherTxnList(HttpServletRequest request,@Valid @RequestBody ErupiVoucherTxnRequest erupiVoucherTxnRequest) {
		 
	    logger.info("inside erupiVoucherTxnList.... ");	    	
	    	
	    
	    
	    	String message = "";
	    	List<ErupiVoucherTxnDetailsEntity> response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=erupiVoucherTxnService.getErupiVoucherTxnList(erupiVoucherTxnRequest.getOrgIdPk());
	    		
				if(response!=null) {
	    			return ResponseEntity.ok(new ErupiVoucherTxnListResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new ErupiVoucherTxnListResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in erupiVoucherTxn====="+e);
			}
	        
	        return ResponseEntity.ok(new ErupiVoucherTxnListResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	 
}
