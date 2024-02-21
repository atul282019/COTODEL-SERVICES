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

import com.cotodel.hrms.auth.server.dto.EmployeeProfileRequest;
import com.cotodel.hrms.auth.server.dto.EmployeeProfileResponse;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.model.SignUpEntity;
import com.cotodel.hrms.auth.server.multi.datasource.SetDatabaseTenent;
import com.cotodel.hrms.auth.server.service.EmployeeProfileService;
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
public class EmployeeProfileController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeProfileController.class);
	
	@Autowired
	EmployeeProfileService employeeProfileService;
	
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/saveProfileDetails",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> saveProfileDetails(HttpServletRequest request,@Valid @RequestBody EmployeeProfileRequest empolyeeProfileRequest) {
	    logger.info("inside saveProfileDetails");	    	
	    	
	    	SignUpEntity userEntity=null;
	    	String message = "";
	    	EmployeeProfileRequest response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				response=employeeProfileService.saveProfileDetails(empolyeeProfileRequest);
	    		if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			return ResponseEntity.ok(new EmployeeProfileResponse(true,MessageConstant.PROFILE_SUCCESS,empolyeeProfileRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new EmployeeProfileResponse(false,MessageConstant.PROFILE_FAILED,empolyeeProfileRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		e.printStackTrace();
	    		logger.error("error in saveProfileDetails====="+e);
	    		message=e.getMessage();
			}
	        
	        return ResponseEntity.ok(new EmployeeProfileResponse(false,message,empolyeeProfileRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/updateProfileDetails",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> updateProfileDetails(HttpServletRequest request,@Valid @RequestBody EmployeeProfileRequest empolyeeProfileRequest) {
	    logger.info("inside saveProfileDetails");	    	
	    	
	    	SignUpEntity userEntity=null;
	    	String message = "";
	    	EmployeeProfileRequest response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				response=employeeProfileService.updateProfileDetails(empolyeeProfileRequest);
	    		if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			return ResponseEntity.ok(new EmployeeProfileResponse(true,MessageConstant.PROFILE_SUCCESS,empolyeeProfileRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new EmployeeProfileResponse(false,MessageConstant.PROFILE_FAILED,empolyeeProfileRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		e.printStackTrace();
	    		logger.error("error in saveProfileDetails====="+e);
	    		message=e.getMessage();
			}
	        
	        return ResponseEntity.ok(new EmployeeProfileResponse(false,message,empolyeeProfileRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }

}
