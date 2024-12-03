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

import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreateDetailsRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedDto;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedListResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherInitiateDetailsResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherRevokeDetailsRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherRevokeDetailsResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherSummaryDto;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherSummaryListResponse;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreateSummaryDto;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreateSummaryListResponse;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherRedemeRequest;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherRevokeDetailsSingleRequest;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherRevokeSingleDetailsResponse;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.multi.datasource.SetDatabaseTenent;
import com.cotodel.hrms.auth.server.service.ErupiVoucherInitiateDetailsService;
import com.cotodel.hrms.auth.server.util.CommonUtility;
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
public class ErupiVoucherCreationDetailsController {
private static final Logger logger = LoggerFactory.getLogger(ExpenseTravelController.class);
	
	@Autowired
	ErupiVoucherInitiateDetailsService erupiVoucherInitiateDetailsService;
	
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/add/erupiVoucherInitiateDetails",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiVoucherInitiateDetails(HttpServletRequest request,@Valid @RequestBody ErupiVoucherCreateDetailsRequest erupiLinkAccountRequest) {
		 
	    logger.info("inside erupiVoucherInitiateDetails....");	    	
	    	
	    
	    	String message = "";
	    	ErupiVoucherCreateDetailsRequest response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=erupiVoucherInitiateDetailsService.saveErupiVoucherInitiateDetails(erupiLinkAccountRequest);
	    		
				if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			return ResponseEntity.ok(new ErupiVoucherInitiateDetailsResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new ErupiVoucherInitiateDetailsResponse(MessageConstant.FALSE,response.getResponseApi(),response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in erupiVoucherInitiateDetails====="+e);
			}
	        
	        return ResponseEntity.ok(new ErupiVoucherInitiateDetailsResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/erupiVoucherCreateList",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiVoucherCreateList(HttpServletRequest request,@Valid @RequestBody ErupiVoucherCreatedRequest erupiLinkAccountRequest) {
		 
	    logger.info("inside erupiVoucherCreateList....");	    	
	    	
	    
	    	String message = "";
	    	List<ErupiVoucherCreatedDto> response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=erupiVoucherInitiateDetailsService.getErupiVoucherCreateDetailsList(erupiLinkAccountRequest);
	    		
				if(response!=null && response.size()>0) {
	    			return ResponseEntity.ok(new ErupiVoucherCreatedListResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new ErupiVoucherCreatedListResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in erupiVoucherInitiateDetails====="+e);
			}
	        
	        return ResponseEntity.ok(new ErupiVoucherCreatedListResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	 
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/add/erupiVoucherRevoke",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiVoucherRevoke(HttpServletRequest request,@Valid @RequestBody ErupiVoucherRevokeDetailsRequest erupiVoucherRevokeDetailsRequest) {
		 
	    logger.info("inside erupiVoucherRevoke....");	    	
	    	
	    
	    	String message = "";
	    	ErupiVoucherRevokeDetailsRequest response=null;
	    	try {
	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=erupiVoucherInitiateDetailsService.erupiVoucherRevokeDetails(erupiVoucherRevokeDetailsRequest);
	    		
				if(response!=null && response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			return ResponseEntity.ok(new ErupiVoucherRevokeDetailsResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new ErupiVoucherRevokeDetailsResponse(MessageConstant.FALSE,response.getResponse(),response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in erupiVoucherRevokeDetails====="+e);
			}
	        
	        return ResponseEntity.ok(new ErupiVoucherRevokeDetailsResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/voucherSummaryList",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiVoucherSummaryList(HttpServletRequest request,@Valid @RequestBody ErupiVoucherCreatedRequest erupiLinkAccountRequest) {
		 
	    logger.info("inside voucherSummaryList....");	    	
	    	
	    
	    	String message = "";
	    	Long count=0l;
	    	Long amount=0l;
	    	List<ErupiVoucherSummaryDto> response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=erupiVoucherInitiateDetailsService.getErupiVoucherSummaryList(erupiLinkAccountRequest);
	    		
				if(response!=null ) {
					count=CommonUtility.getCount(response);
					amount=CommonUtility.getAmount(response);
	    			return ResponseEntity.ok(new ErupiVoucherSummaryListResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,count,amount,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new ErupiVoucherSummaryListResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,count,amount,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in voucherSummaryList====="+e);
			}
	        
	        return ResponseEntity.ok(new ErupiVoucherSummaryListResponse(MessageConstant.FALSE,message,count,amount,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/update/erupiVoucherRevokeSingle",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiVoucherRevokeSingle(HttpServletRequest request,@Valid @RequestBody ErupiVoucherRevokeDetailsSingleRequest erupiVoucherRevokeSingleDetailsRequest) {
		 
	    logger.info("inside erupiVoucherRevoke....");	    	
	    	
	    
	    	String message = "";
	    	ErupiVoucherRevokeDetailsSingleRequest response=null;
	    	try {
	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=erupiVoucherInitiateDetailsService.erupiVoucherRevokeSingleDetails(erupiVoucherRevokeSingleDetailsRequest);
	    		
				if(response!=null && response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			return ResponseEntity.ok(new ErupiVoucherRevokeSingleDetailsResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new ErupiVoucherRevokeSingleDetailsResponse(MessageConstant.FALSE,response.getResponseApi(),response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in erupiVoucherRevokeDetails====="+e);
			}
	        
	        return ResponseEntity.ok(new ErupiVoucherRevokeSingleDetailsResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/update/erupiVoucherRedem",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiVoucherRedem(HttpServletRequest request,@Valid @RequestBody ErupiVoucherRedemeRequest erupiVoucherRedemeRequest) {
		 
	    logger.info("inside erupiVoucherRevoke....");	    	
	    	
	    
	    	String message = "";
	    	ErupiVoucherRedemeRequest response=null;
	    	try {
	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=erupiVoucherInitiateDetailsService.erupiVoucherRedemDetails(erupiVoucherRedemeRequest);
	    		//return null;
	    	}catch (Exception e) {				
	    		logger.error("error in erupiVoucherRevokeDetails====="+e);
			}
//				if(response!=null) {
//	    			return ResponseEntity.ok(new ErupiVoucherRevokeSingleDetailsResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
//	    		}else {
//	    			return ResponseEntity.ok(new ErupiVoucherRevokeSingleDetailsResponse(MessageConstant.FALSE,response.getResponseApi(),response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
//	    		}
//	    	}catch (Exception e) {				
//	    		logger.error("error in erupiVoucherRevokeDetails====="+e);
//			}
//	        
//	        return ResponseEntity.ok(new ErupiVoucherRevokeSingleDetailsResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
			return null;
	    }
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/voucherCreateSummaryList",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> voucherCreateSummaryList(HttpServletRequest request,@Valid @RequestBody ErupiVoucherCreatedRequest erupiLinkAccountRequest) {
		 
	    logger.info("inside voucherSummaryList....");	    	
	    	
	    
	    	String message = "";
	    	Long count=0l;
	    	Long amount=0l;
	    	List<ErupiVoucherCreateSummaryDto> response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=erupiVoucherInitiateDetailsService.getErupiVoucherCreateSummaryList(erupiLinkAccountRequest);
	    		
				if(response!=null ) {
					count=CommonUtility.getCreateCount(response);
					amount=CommonUtility.getCreateAmount(response);
	    			return ResponseEntity.ok(new ErupiVoucherCreateSummaryListResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,count,amount,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new ErupiVoucherCreateSummaryListResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,count,amount,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in voucherSummaryList====="+e);
			}
	        
	        return ResponseEntity.ok(new ErupiVoucherCreateSummaryListResponse(MessageConstant.FALSE,message,count,amount,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
}
