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

import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingFailListResponse;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingListResponse;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingRequest;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingResponse;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.model.EmployeeOnboardingEntity;
import com.cotodel.hrms.auth.server.model.EmployeeOnboardingFailEntity;
import com.cotodel.hrms.auth.server.multi.datasource.SetDatabaseTenent;
import com.cotodel.hrms.auth.server.service.EmployeeOnboardingFailService;
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
public class EmployeeOnboardingFailController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeOnboardingFailController.class);
	
	@Autowired
	EmployeeOnboardingFailService employeeOnboardingFailService;
	
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/add/saveBulkFail",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> saveEmplOnboarding(HttpServletRequest request,@Valid @RequestBody EmployeeOnboardingRequest employeeOnboardingRequest) {
	    logger.info("inside saveBulkFail");    	
	    
	    	String message = "";
	    	String message1 = "";
	    	EmployeeOnboardingRequest response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=employeeOnboardingFailService.saveBulkFailEmployeeDetails(employeeOnboardingRequest);
				message1=response.getResponse()==null?MessageConstant.PROFILE_FAILED:response.getResponse();
	    		if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			return ResponseEntity.ok(new EmployeeOnboardingResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,employeeOnboardingRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new EmployeeOnboardingResponse(MessageConstant.FALSE,message1,employeeOnboardingRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		e.printStackTrace();
	    		logger.error("error in saveProfileDetails====="+e);
	    		message=e.getMessage();
			}
	        
	        return ResponseEntity.ok(new EmployeeOnboardingResponse(MessageConstant.FALSE,message,employeeOnboardingRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/empOnboardingFailList",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> empOnboardingFailList(HttpServletRequest request,@Valid @RequestBody EmployeeOnboardingRequest employeeOnboardingRequest) {
	    logger.info("inside empOnboardingFailList...");	    	
	    	
	    
	    	String message = "";
	    	List<EmployeeOnboardingRequest> response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=employeeOnboardingFailService.getBulkFailDetailsList(employeeOnboardingRequest.getEmployerId());
	    		if(response!=null && response.size()>0) {
	    			return ResponseEntity.ok(new EmployeeOnboardingFailListResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new EmployeeOnboardingFailListResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		e.printStackTrace();
	    		logger.error("error in saveProfileDetails====="+e);
	    		message=e.getMessage();
			}
	        
	        return ResponseEntity.ok(new EmployeeOnboardingFailListResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    
	 }
	
	
}
