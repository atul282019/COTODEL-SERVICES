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
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.VoucherTypeMasterService;
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
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("/Api")
public class VoucherTypeMasterController {
	
	
	@Autowired
	VoucherTypeMasterService voucherTypeMasterService;	
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;	
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @PostMapping(value = "/get/voucherTypeMaster",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"})
	    public ResponseEntity<Object> voucherTypeMaster(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    log.info("inside voucherTypeMaster-------");
	    	String message = "";
	    	VoucherTypeMasterEntity response=null;
	    	VoucherTypeMasterDetailResponse voucherTypeMasterDetailResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				VoucherTypeDto voucherTypeDto= EncryptionDecriptionUtil.convertFromJson(decript, VoucherTypeDto.class);
				response=voucherTypeMasterService.getVoucherTypeMasterDetail(voucherTypeDto.getVoucherCode());
				
	    		if(response!=null) {
	    			voucherTypeMasterDetailResponse=new VoucherTypeMasterDetailResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(voucherTypeMasterDetailResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			voucherTypeMasterDetailResponse=new VoucherTypeMasterDetailResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(voucherTypeMasterDetailResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		//e.printStackTrace();
	    		log.error("error in voucherTypeMaster====="+e);
	    		//message=e.getMessage();
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		voucherTypeMasterDetailResponse=new VoucherTypeMasterDetailResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(voucherTypeMasterDetailResponse);
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
				
	    		if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			return ResponseEntity.ok(new VoucherTypeMasterResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new VoucherTypeMasterResponse(MessageConstant.FALSE,response.getResponse(),response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		//e.printStackTrace();
	    		log.error("error in voucherTypeMaster====="+e);
	    		//message=e.getMessage();
			}
	        
	        return ResponseEntity.ok(new VoucherTypeMasterResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @PostMapping(value = "/update/voucherTypeMasterStatus",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"})
	    public ResponseEntity<Object> voucherTypeMasterStatus(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    log.info("inside voucherTypeMaster-------");
	    	String message = "";
	    	VoucherTypeMasterRequest response=null;
	    	VoucherTypeMasterResponse voucherTypeMasterResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				VoucherTypeMasterRequest voucherTypeMasterRequest= EncryptionDecriptionUtil.convertFromJson(decript, VoucherTypeMasterRequest.class);
				
				response=voucherTypeMasterService.updateVoucherTypeMaster(voucherTypeMasterRequest);
				
	    		if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			voucherTypeMasterResponse=new VoucherTypeMasterResponse(MessageConstant.TRUE,MessageConstant.PROFILE_UPDATE,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(voucherTypeMasterResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			voucherTypeMasterResponse=new VoucherTypeMasterResponse(MessageConstant.FALSE,response.getResponse(),response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(voucherTypeMasterResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		//e.printStackTrace();
	    		log.error("error in voucherTypeMaster====="+e);
	    		//message=e.getMessage();
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		voucherTypeMasterResponse=new VoucherTypeMasterResponse(MessageConstant.FALSE,response.getResponse(),response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(voucherTypeMasterResponse);
    			jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				// TODO: handle exception
			}
    	    return ResponseEntity.ok(jsonEncriptObject);
	    }
	}
