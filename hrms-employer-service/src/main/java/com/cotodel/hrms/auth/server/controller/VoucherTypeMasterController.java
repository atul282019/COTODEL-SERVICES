package com.cotodel.hrms.auth.server.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cotodel.hrms.auth.server.dto.VoucherTypeDto;
import com.cotodel.hrms.auth.server.dto.VoucherTypeDtoListResponse;
import com.cotodel.hrms.auth.server.dto.VoucherTypeMasterDetailResponse;
import com.cotodel.hrms.auth.server.dto.VoucherTypeMasterListResponse;
import com.cotodel.hrms.auth.server.dto.VoucherTypeMasterRequest;
import com.cotodel.hrms.auth.server.dto.VoucherTypeMasterResponse;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.model.VoucherTypeMasterEntity;
import com.cotodel.hrms.auth.server.multi.datasource.SetDatabaseTenent;
import com.cotodel.hrms.auth.server.service.VoucherTypeMasterService;
import com.cotodel.hrms.auth.server.util.MessageConstant;
import com.cotodel.hrms.auth.server.util.TransactionManager;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("/Api")
public class VoucherTypeMasterController {
	
	
	@Autowired
	VoucherTypeMasterService voucherTypeMasterService;	
	
	
	@Operation(summary = "This API will provide the Save User Details ", security = {
    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
    @GetMapping(value = "/get/voucherTypeMasterList",produces = {"application/json"}, 
    consumes = {"application/json","application/text"})
    public ResponseEntity<Object> voucherTypeMasterGetList(HttpServletRequest request) {
	 
    log.info("inside voucherTypeMasterList-------");
    	String message = "";
    	List<VoucherTypeMasterEntity> response=null;
    	try {	    		
    		String companyId = request.getHeader("companyId");
			SetDatabaseTenent.setDataSource(companyId);
			
			response=voucherTypeMasterService.getVoucherTypeMaster();
			
    		if(response!=null) {
    			return ResponseEntity.ok(new VoucherTypeMasterListResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
    		}else {
    			return ResponseEntity.ok(new VoucherTypeMasterListResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
    		}
    	}catch (Exception e) {				
    		//e.printStackTrace();
    		log.error("error in getBankMasterDetailsList====="+e);
    		//message=e.getMessage();
		}
        
        return ResponseEntity.ok(new VoucherTypeMasterListResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
    }
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @PostMapping(value = "/get/voucherTypeMasterList",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"})
	    public ResponseEntity<Object> voucherTypeMasterList(HttpServletRequest request) {
		 
	    log.info("inside voucherTypeMasterList-------");
	    	String message = "";
	    	List<VoucherTypeMasterEntity> response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=voucherTypeMasterService.getVoucherTypeMaster();
				
	    		if(response!=null) {
	    			return ResponseEntity.ok(new VoucherTypeMasterListResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new VoucherTypeMasterListResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		//e.printStackTrace();
	    		log.error("error in getBankMasterDetailsList====="+e);
	    		//message=e.getMessage();
			}
	        
	        return ResponseEntity.ok(new VoucherTypeMasterListResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @PostMapping(value = "/get/voucherTypeList",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"})
	    public ResponseEntity<Object> voucherTypeList(HttpServletRequest request) {
		 
	    log.info("inside voucherTypeList-------");
	    	String message = "";
	    	List<VoucherTypeDto> response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=voucherTypeMasterService.getVoucherTypeList();
				
	    		if(response!=null) {
	    			return ResponseEntity.ok(new VoucherTypeDtoListResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new VoucherTypeDtoListResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		//e.printStackTrace();
	    		log.error("error in voucherTypeList====="+e);
	    		//message=e.getMessage();
			}
	        
	        return ResponseEntity.ok(new VoucherTypeDtoListResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @PostMapping(value = "/get/voucherTypeMaster",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"})
	    public ResponseEntity<Object> voucherTypeMaster(HttpServletRequest request,@Valid @RequestBody VoucherTypeDto voucherTypeDto) {
		 
	    log.info("inside voucherTypeMaster-------");
	    	String message = "";
	    	VoucherTypeMasterEntity response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=voucherTypeMasterService.getVoucherTypeMasterDetail(voucherTypeDto.getVoucherCode());
				
	    		if(response!=null) {
	    			return ResponseEntity.ok(new VoucherTypeMasterDetailResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new VoucherTypeMasterDetailResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		//e.printStackTrace();
	    		log.error("error in voucherTypeMaster====="+e);
	    		//message=e.getMessage();
			}
	        
	        return ResponseEntity.ok(new VoucherTypeMasterDetailResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @PostMapping(value = "/add/voucherTypeMaster",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"})
	    public ResponseEntity<Object> voucherTypeMaster(HttpServletRequest request,@Valid @RequestBody VoucherTypeMasterRequest voucherTypeMasterRequest) {
		 
	    log.info("inside voucherTypeMaster-------");
	    	String message = "";
	    	VoucherTypeMasterRequest response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=voucherTypeMasterService.saveVoucherTypeMaster(voucherTypeMasterRequest);
				
	    		if(response!=null) {
	    			return ResponseEntity.ok(new VoucherTypeMasterResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new VoucherTypeMasterResponse(MessageConstant.FALSE,MessageConstant.PROFILE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		//e.printStackTrace();
	    		log.error("error in voucherTypeMaster====="+e);
	    		//message=e.getMessage();
			}
	        
	        return ResponseEntity.ok(new VoucherTypeMasterResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	 
	}
