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

import com.cotodel.hrms.auth.server.dto.ExpenseBandListResponse;
import com.cotodel.hrms.auth.server.dto.ExpenseCategoryBandListResponse;
import com.cotodel.hrms.auth.server.dto.ExpenseCategoryBandRequest;
import com.cotodel.hrms.auth.server.dto.ExpenseCategoryBandResponse;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.model.ExpenseBandNumberEntity;
import com.cotodel.hrms.auth.server.multi.datasource.SetDatabaseTenent;
import com.cotodel.hrms.auth.server.service.ExpenseCategoryBandService;
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
public class ExpenseCategoryBandController {
	
	private static final Logger logger = LoggerFactory.getLogger(ExpenseCategoryBandController.class);
	
	@Autowired
	ExpenseCategoryBandService expenseCategoryBandService;
	
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/add/expenseCategoryBandDetails",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> expenseCategoryBandDetails(HttpServletRequest request,@Valid @RequestBody ExpenseCategoryBandRequest empolyeeRequest) {
		 
	    logger.info("inside expenseCategoryBandService");	    	
	    	
	    
	    	String message = "";
	    	ExpenseCategoryBandRequest response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=expenseCategoryBandService.saveExpenseCategoryBandDetails(empolyeeRequest);
	    		if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			return ResponseEntity.ok(new ExpenseCategoryBandResponse(true,MessageConstant.PROFILE_SUCCESS,empolyeeRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new ExpenseCategoryBandResponse(false,MessageConstant.PROFILE_FAILED,empolyeeRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		e.printStackTrace();
	    		logger.error("error in expenseCategoryBandDetails====="+e);
	    		message=e.getMessage();
			}
	        
	        return ResponseEntity.ok(new ExpenseCategoryBandResponse(false,message,empolyeeRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/expenseCategoryBandDetails",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> getExpenseCategoryBandDetails(HttpServletRequest request,@Valid @RequestBody ExpenseCategoryBandRequest empolyeeRequest) {
		 
	    logger.info("inside expenseCategoryBandService");	    	
	    	
	    
	    	String message = "";
	    	ExpenseCategoryBandRequest response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=expenseCategoryBandService.getCompEmployeeBandDetails();
	    		if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			return ResponseEntity.ok(new ExpenseCategoryBandResponse(true,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new ExpenseCategoryBandResponse(false,MessageConstant.PROFILE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		e.printStackTrace();
	    		logger.error("error in expenseCategoryBandDetails====="+e);
	    		message=e.getMessage();
			}
	        
	        return ResponseEntity.ok(new ExpenseCategoryBandResponse(false,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/expenseCategoryBandDetailsId",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> expenseCategoryBandDetailsId(HttpServletRequest request,@Valid @RequestBody ExpenseCategoryBandRequest empolyeeRequest) {
		 
	    logger.info("inside expenseCategoryBandDetailsId");	    	
	    	
	    
	    	String message = "";
	    	ExpenseCategoryBandRequest response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=expenseCategoryBandService.getCompEmployeeBandDetailsId(empolyeeRequest.getId(),empolyeeRequest.getEmployerId());
	    		if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			return ResponseEntity.ok(new ExpenseCategoryBandResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new ExpenseCategoryBandResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		e.printStackTrace();
	    		logger.error("error in expenseCategoryBandDetails====="+e);
	    		message=e.getMessage();
			}
	        
	        return ResponseEntity.ok(new ExpenseCategoryBandResponse(false,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/expenseCategoryBandDetailsList",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> getExpenseCategoryBandDetailsList(HttpServletRequest request,@Valid @RequestBody ExpenseCategoryBandRequest empolyeeRequest) {
		 
	    logger.info("inside getExpenseCategoryBandDetailsList");	    	
	    	
	    
	    	String message = "";
	    	List<ExpenseCategoryBandRequest> response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=expenseCategoryBandService.getCompEmployeeBandDetailsList(empolyeeRequest.getEmployerId());
	    		if(response!=null && response.size()>0) {
	    			return ResponseEntity.ok(new ExpenseCategoryBandListResponse(true,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new ExpenseCategoryBandListResponse(false,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		e.printStackTrace();
	    		logger.error("error in expenseCategoryBandDetails====="+e);
	    		message=e.getMessage();
			}
	        
	        return ResponseEntity.ok(new ExpenseCategoryBandListResponse(false,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/delete/deleteExpenseCategoryDetails",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> deleteExpenseCategoryDetails(HttpServletRequest request,@Valid @RequestBody ExpenseCategoryBandRequest empolyeeRequest) {
		 
	    logger.info("inside expenseCategoryBandService");	    	
	    	
	    
	    	String message = "";
	    	ExpenseCategoryBandRequest response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=expenseCategoryBandService.deleteExpenseCategoryBandDetails(empolyeeRequest);
	    		if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			return ResponseEntity.ok(new ExpenseCategoryBandResponse(MessageConstant.TRUE,MessageConstant.PROFILE_DELETE,empolyeeRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new ExpenseCategoryBandResponse(MessageConstant.FALSE,MessageConstant.PROFILE_DELETE_FAILED,empolyeeRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		e.printStackTrace();
	    		logger.error("error in expenseCategoryBandDetails====="+e);
	    		message=e.getMessage();
			}
	        
	        return ResponseEntity.ok(new ExpenseCategoryBandResponse(MessageConstant.FALSE,message,empolyeeRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/getExpenseBandList",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> getExpenseBandList(HttpServletRequest request,@Valid @RequestBody ExpenseCategoryBandRequest empolyeeRequest) {
		 
	    logger.info("inside getExpenseBandList");	    	
	    	
	    
	    	String message = "";
	    	List<ExpenseBandNumberEntity> response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=expenseCategoryBandService.getExpenseBandList(empolyeeRequest.getEmployerId());
	    		if(response!=null) {
	    			return ResponseEntity.ok(new ExpenseBandListResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new ExpenseBandListResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		e.printStackTrace();
	    		logger.error("error in ExpenseBandListResponse====="+e);
	    		message=e.getMessage();
			}
	        
	        return ResponseEntity.ok(new ExpenseBandListResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	}
