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

import com.cotodel.hrms.auth.server.dto.EmployerAddressRequest;
import com.cotodel.hrms.auth.server.dto.EmployerAddressResponse;
import com.cotodel.hrms.auth.server.dto.EmployerDetailsByEmpIdResponse;
import com.cotodel.hrms.auth.server.dto.EmployerDetailsRequest;
import com.cotodel.hrms.auth.server.dto.EmployerDetailsResponse;
import com.cotodel.hrms.auth.server.dto.EmployerProfileAddress;
import com.cotodel.hrms.auth.server.dto.UserRoleRequest;
import com.cotodel.hrms.auth.server.entity.EmployerDetailsEntity;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.multi.datasource.SetDatabaseTenent;
import com.cotodel.hrms.auth.server.service.EmployerDetailsService;
import com.cotodel.hrms.auth.server.util.MessageConstant;
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
public class EmployerDetailsController extends CotoDelBaseController{
	

	
	private static final Logger logger = LoggerFactory.getLogger(EmployerDetailsController.class);
    
	@Autowired
	EmployerDetailsService employerDetailsService;
	
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/add/saveEmployerDetails",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> saveEmployerDetails(HttpServletRequest request,@Valid @RequestBody EmployerDetailsRequest employerDetailsRequest) {
	    	logger.info("inside get saveEmployerDetails+++");
	    	EmployerDetailsRequest userEntity=null;
	    	String responseToken="";
	    	String authToken = "";
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
	    	    userEntity=	employerDetailsService.saveEmployerDetails(employerDetailsRequest);
	    	    
	    	    if(userEntity!=null) {
	    	    	return ResponseEntity.ok(new EmployerDetailsResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	    	    }else {
	    	    	return ResponseEntity.ok(new EmployerDetailsResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	    	    }
			
	    	}catch (Exception e) {
				
	    		e.printStackTrace();
	    		logger.error("error in saveUserDetails====="+e);
			}
	        
	        return ResponseEntity.ok(new EmployerDetailsResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	          
	        
	    }
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/getEmployerDetails",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> getEmployerDetails(HttpServletRequest request,@Valid @RequestBody EmployerDetailsRequest employerDetailsRequest) {
	    	logger.info("inside get getEmployerDetails+++");
	    	EmployerDetailsEntity emDetailsEntity=null;
	    	String responseToken="";
	    	String authToken = "";
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				emDetailsEntity=	employerDetailsService.getEmployerDetails(employerDetailsRequest.getEmployerId());
	    	    
	    	    if(emDetailsEntity!=null) {
	    	    	return ResponseEntity.ok(new EmployerDetailsByEmpIdResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,emDetailsEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	    	    }else {
	    	    	return ResponseEntity.ok(new EmployerDetailsByEmpIdResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,emDetailsEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	    	    }
			
	    	}catch (Exception e) {
				
	    		e.printStackTrace();
	    		logger.error("error in saveUserDetails====="+e);
			}
	        
	        return ResponseEntity.ok(new EmployerDetailsByEmpIdResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,emDetailsEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	          
	        
	    }
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/getEmployerComplete",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> getEmployerComplete(HttpServletRequest request,@Valid @RequestBody EmployerDetailsRequest employerDetailsRequest) {
	    	logger.info("inside get getEmployerComplete+++");
	    	EmployerDetailsEntity emDetailsEntity=null;
	    	String responseToken="";
	    	String authToken = "";
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				emDetailsEntity=	employerDetailsService.getEmployerDetails(employerDetailsRequest.getEmployerId());
	    	    
	    	    if(emDetailsEntity!=null) {
	    	    	return ResponseEntity.ok(new EmployerDetailsByEmpIdResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,emDetailsEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	    	    }else {
	    	    	return ResponseEntity.ok(new EmployerDetailsByEmpIdResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,emDetailsEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	    	    }
			
	    	}catch (Exception e) {
				
	    		e.printStackTrace();
	    		logger.error("error in saveUserDetails====="+e);
			}
	        
	        return ResponseEntity.ok(new EmployerDetailsByEmpIdResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,emDetailsEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	          
	        
	    }
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/getCompaneyAddress",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> getCompaneyAddress(HttpServletRequest request,@Valid @RequestBody EmployerAddressRequest employerAddressRequest) {
	    	logger.info("inside getCompaneyAddress+++");
	    	List<EmployerProfileAddress> response=null;
	    	String authToken = "";
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);				
				response=	employerDetailsService.getCompaneyAddress(employerAddressRequest.getOrgId());	    	    
	    	    if(response!=null) {
	    	    	return ResponseEntity.ok(new EmployerAddressResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	    	    }else {
	    	    	return ResponseEntity.ok(new EmployerAddressResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	    	    }
			
	    	}catch (Exception e) {
				
	    		e.printStackTrace();
	    		logger.error("error in getCompaneyAddress====="+e);
			}
	        
	        return ResponseEntity.ok(new EmployerAddressResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	          
	        
	    }
	 
}
