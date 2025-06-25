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

import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingRequest;
import com.cotodel.hrms.auth.server.dto.bulk.EmployeeBulkCreateRequest;
import com.cotodel.hrms.auth.server.dto.bulk.EmployeeBulkCreateResponse;
import com.cotodel.hrms.auth.server.dto.bulk.EmployeeBulkUploadRequest;
import com.cotodel.hrms.auth.server.dto.bulk.EmployeeBulkUploadResponse;
import com.cotodel.hrms.auth.server.dto.bulk.EmployeeBulkUploadSFListResponse;
import com.cotodel.hrms.auth.server.dto.bulk.ErupiVoucherBulkCreateResponse;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.multi.datasource.SetDatabaseTenent;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.EmployeeCreationBulkService;
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
public class EmployeeCreationBulkController {
private static final Logger logger = LoggerFactory.getLogger(ExpenseTravelController.class);
	
	@Autowired
	EmployeeCreationBulkService employeeCreationBulkService;
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
	
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/add/employeeBulkVoucherUpload",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiVoucherBulkVoucherUploadNew(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside employeeBulkVoucherUpload....");	    	
	    	
	    
	    	String message = "";
	    	EmployeeBulkUploadSFListResponse response=null;
	    	EmployeeBulkUploadResponse employeeBulkUploadResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				EmployeeBulkUploadRequest employeeBulkUploadRequest= EncryptionDecriptionUtil.convertFromJson(decript, EmployeeBulkUploadRequest.class);
				
	            response=employeeCreationBulkService.saveEmployeeBulkFileUpload(employeeBulkUploadRequest);
	    		
				if(response!=null && response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
					employeeBulkUploadResponse=new EmployeeBulkUploadResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeBulkUploadResponse);
					 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			employeeBulkUploadResponse=new EmployeeBulkUploadResponse(MessageConstant.FALSE,response.getResponse(),response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeBulkUploadResponse);
					 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in employeeBulkVoucherUpload====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		employeeBulkUploadResponse=new EmployeeBulkUploadResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
				 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeBulkUploadResponse);
				 jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in employeeBulkVoucherUpload====="+e);
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
	    @RequestMapping(value = "/add/employeeBulkVoucherCreate",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> employeeBulkVoucherCreate(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside employeeBulkVoucherCreate....");	    	
	    	
	    
	    	String message = "";
	    	List<EmployeeOnboardingRequest> response=null;
	    	EmployeeBulkCreateResponse employeeBulkCreateResponse;
	    	EmployeeBulkCreateRequest res;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");	    		
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				EmployeeBulkCreateRequest erCreateRequest= EncryptionDecriptionUtil.convertFromJson(decript, EmployeeBulkCreateRequest.class);
				res=employeeCreationBulkService.createErupiVoucherBulkFileHash(erCreateRequest);
				if(res.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
					response=employeeCreationBulkService.createErupiEmployeeBulkFile(erCreateRequest);
				}else {
					employeeBulkCreateResponse=new EmployeeBulkCreateResponse(MessageConstant.FALSE,res.getResponse(),response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeBulkCreateResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
				}
				
				
				if(response!=null) {
					employeeBulkCreateResponse=new EmployeeBulkCreateResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeBulkCreateResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			employeeBulkCreateResponse=new EmployeeBulkCreateResponse(MessageConstant.FALSE,MessageConstant.PROFILE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeBulkCreateResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in employeeBulkVoucherCreate====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		employeeBulkCreateResponse=new EmployeeBulkCreateResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
				String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeBulkCreateResponse);
				jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				// TODO: handle exception
			}
    	    return ResponseEntity.ok(jsonEncriptObject);
    	
	 }
	 	 
	
}
