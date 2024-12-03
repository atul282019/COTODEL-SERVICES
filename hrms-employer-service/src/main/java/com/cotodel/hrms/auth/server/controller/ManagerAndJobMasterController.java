package com.cotodel.hrms.auth.server.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cotodel.hrms.auth.server.dto.JobTitleMasterListResponse;
import com.cotodel.hrms.auth.server.dto.JobTitleMasterRequest;
import com.cotodel.hrms.auth.server.dto.JobTitleMasterResponse;
import com.cotodel.hrms.auth.server.dto.ManagerMasterListResponse;
import com.cotodel.hrms.auth.server.dto.ManagerMasterRequest;
import com.cotodel.hrms.auth.server.dto.ManagerMasterResponse;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.model.JobTitleMasterEntity;
import com.cotodel.hrms.auth.server.model.ManagerLblMasterEntity;
import com.cotodel.hrms.auth.server.multi.datasource.SetDatabaseTenent;
import com.cotodel.hrms.auth.server.service.JobTitleMasterService;
import com.cotodel.hrms.auth.server.service.ManagerMasterService;
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
public class ManagerAndJobMasterController {
	
	
	@Autowired
	ManagerMasterService managerMasterService;
	
	@Autowired
	JobTitleMasterService jobTitleMasterService;
	
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @PostMapping(value = "/add/managerMasterDetails",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"})
	    public ResponseEntity<Object> managerMasterDetails(HttpServletRequest request,@Valid @RequestBody ManagerMasterRequest managerMasterRequest) {
		 
	    log.info("inside bank managerMasterDetails-------");
	    	String message = "";
	    	ManagerMasterRequest response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=managerMasterService.saveManagerMaster(managerMasterRequest);
				
	    		if(response!=null && response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			return ResponseEntity.ok(new ManagerMasterResponse(MessageConstant.TRUE,response.getResponse(),response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new ManagerMasterResponse(MessageConstant.FALSE,response.getResponse(),response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		//e.printStackTrace();
	    		message =e.getMessage();
	    		log.error("error in managerMasterDetails====="+e);
			}
	        
	        return ResponseEntity.ok(new ManagerMasterResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @PostMapping(value = "/get/managerMasterList",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"})
	    public ResponseEntity<Object> managerMasterList(HttpServletRequest request,@Valid @RequestBody ManagerMasterRequest managerMasterRequest) {
		 
	    log.info("inside bank managerMasterList-------");
	    	String message = "";
	    	List<ManagerLblMasterEntity> response=null;
	    	
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=managerMasterService.getManagerMaster(managerMasterRequest.getOrgId());
				
	    		if(response!=null && response.size()>0) {
	    			return ResponseEntity.ok(new ManagerMasterListResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new ManagerMasterListResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		//e.printStackTrace();
	    		message =e.getMessage();
	    		log.error("error in managerMasterList====="+e);
			}
	        
	        return ResponseEntity.ok(new ManagerMasterListResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @PostMapping(value = "/add/jobTitleMasterDetails",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"})
	    public ResponseEntity<Object> jobTitleMasterDetails(HttpServletRequest request,@Valid @RequestBody JobTitleMasterRequest jobTitleMasterRequest) {
		 
	    log.info("inside bank jobTitleMasterDetails-------");
	    	String message = "";
	    	JobTitleMasterRequest response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=jobTitleMasterService.saveJobTitleMaster(jobTitleMasterRequest);
				
	    		if(response!=null && response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			return ResponseEntity.ok(new JobTitleMasterResponse(MessageConstant.TRUE,response.getResponse(),response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new JobTitleMasterResponse(MessageConstant.FALSE,response.getResponse(),response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		//e.printStackTrace();
	    		message =e.getMessage();
	    		log.error("error in managerMasterDetails====="+e);
			}
	        
	        return ResponseEntity.ok(new JobTitleMasterResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @PostMapping(value = "/get/jobTitleMasterList",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"})
	    public ResponseEntity<Object> jobTitleMasterList(HttpServletRequest request,@Valid @RequestBody JobTitleMasterRequest jobTitleMasterRequest) {
		 
	    log.info("inside bank managerMasterList-------");
	    	String message = "";
	    	List<JobTitleMasterEntity> response=null;
	    	
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=jobTitleMasterService.getJobTitleMaster(jobTitleMasterRequest.getOrgId());
				
	    		if(response!=null && response.size()>0) {
	    			return ResponseEntity.ok(new JobTitleMasterListResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new JobTitleMasterListResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		//e.printStackTrace();
	    		message =e.getMessage();
	    		log.error("error in managerMasterList====="+e);
			}
	        
	        return ResponseEntity.ok(new JobTitleMasterListResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	}
