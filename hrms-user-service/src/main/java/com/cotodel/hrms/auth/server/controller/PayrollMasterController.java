package com.cotodel.hrms.auth.server.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cotodel.hrms.auth.server.dto.PayrollMasterResponse;
import com.cotodel.hrms.auth.server.entity.PayrollMasterEntity;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.PayrollMasterService;
import com.cotodel.hrms.auth.server.util.EncriptResponse;
import com.cotodel.hrms.auth.server.util.EncryptionDecriptionUtil;
import com.cotodel.hrms.auth.server.util.TransactionManager;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * @author vinay
 */
@RestController
@RequestMapping("/Api")
public class PayrollMasterController {
	private static final Logger logger = LoggerFactory.getLogger(PayrollMasterController.class);
	
	@Autowired
	PayrollMasterService payrollMasterService;
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
//	 @Operation(summary = "This API will provide the Authentication token", security = {
//	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
//	    @ApiResponses(value = {
//	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
//	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
//	    @RequestMapping(value = "/get/payroll-list",produces = {"application/json"}, consumes = {"application/json","application/text"},method = RequestMethod.POST)
//	    public ResponseEntity<Object> getState() {
//	    	logger.info("inside get state-list+++");
//	    	List<PayrollMasterEntity>  payrollMasterServices=null;
//	    	PayrollMasterResponse payrollMasterResponse;
//	    	try {
//	    		payrollMasterServices=payrollMasterService.getByPayrollMasterList();
//	    		
//	    		 if(payrollMasterServices!=null && payrollMasterServices.size()>0 ) {
//	    			 payrollMasterResponse=new PayrollMasterResponse(true,payrollMasterServices,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
//	    			 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(payrollMasterResponse);
//	    			 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
//	    			 return ResponseEntity.ok(jsonEncriptObject);
//	    		 }else {
//	    			 payrollMasterResponse=new PayrollMasterResponse(false,payrollMasterServices,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
//	    			 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(payrollMasterResponse);
//	    			 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
//	    			 return ResponseEntity.ok(jsonEncriptObject);
//	    		 }
//			} catch (Exception e) {
//				logger.error("error in state-list====="+e);
//			}
//	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
//	    	try {
//	    		payrollMasterResponse=new PayrollMasterResponse(false,payrollMasterServices,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
//   			 	String jsonEncript =  EncryptionDecriptionUtil.convertToJson(payrollMasterResponse);
//   			 	jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
//   			 return ResponseEntity.ok(jsonEncriptObject);
//			} catch (Exception e) {
//				logger.error("error in state-list====="+e);
//			}
//    	    return ResponseEntity.ok(jsonEncriptObject);
//   
//	    }
}
