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

import com.cotodel.hrms.auth.server.dto.ErupiMultipleVoucherCreateRequest;
import com.cotodel.hrms.auth.server.dto.ErupiMultipleVoucherCreationResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherBankDetailListResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherBankListDto;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreateDetailsRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedDto;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedListResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherDetailResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherInitiateDetailsResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherRevokeDetailsRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherRevokeDetailsResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherSummaryDto;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherSummaryListResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherSummaryTotalCountResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherTotalDetailDto;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreateListRequest;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreateListResponse;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreateNameResponse;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreateOldDto;
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
	    @RequestMapping(value = "/add/erupiVoucherInitiateDetailsNew",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiVoucherInitiateDetailsNew(HttpServletRequest request,@Valid @RequestBody ErupiVoucherCreateDetailsRequest erupiLinkAccountRequest) {
		 
	    logger.info("inside erupiVoucherInitiateDetails....");	    	
	    	
	    
	    	String message = "";
	    	ErupiVoucherCreateDetailsRequest response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=erupiVoucherInitiateDetailsService.saveErupiVoucherInitiateDetailsNew(erupiLinkAccountRequest);
	    		
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
	    		System.out.println(response.size());
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
		 
	    logger.info("inside erupiVoucherRedem....");	    	
	    	
	    
	    	String message = "";
	    	ErupiVoucherRedemeRequest response=null;
	    	try {
	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				message=erupiVoucherInitiateDetailsService.erupiVoucherRedemDetails(erupiVoucherRedemeRequest);
	    		//return null;
	    	}catch (Exception e) {				
	    		logger.error("error in erupiVoucherRedem====="+e);
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
			return ResponseEntity.ok(message);
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
					//count=CommonUtility.getCreateCount(response);
					//amount=CommonUtility.getCreateAmount(response);
	    			return ResponseEntity.ok(new ErupiVoucherCreateSummaryListResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new ErupiVoucherCreateSummaryListResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in voucherSummaryList====="+e);
			}
	        
	        return ResponseEntity.ok(new ErupiVoucherCreateSummaryListResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/add/erupiVoucherCreateList",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiVoucherCreateList(HttpServletRequest request,@Valid @RequestBody ErupiVoucherCreateListRequest erupiVoucherCreateListRequest) {
		 
	    logger.info("inside erupiVoucherCreateList...."+erupiVoucherCreateListRequest);	    	
	    	
	    
	    	String message = "";
	    	List<ErupiVoucherCreateDetailsRequest> response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=erupiVoucherInitiateDetailsService.saveErupiVoucherCreateListDetails(erupiVoucherCreateListRequest);
	    		
				if(response!=null) {
	    			return ResponseEntity.ok(new ErupiVoucherCreateListResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new ErupiVoucherCreateListResponse(MessageConstant.FALSE,MessageConstant.PROFILE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in erupiVoucherInitiateDetails====="+e);
			}
	        
	        return ResponseEntity.ok(new ErupiVoucherCreateListResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/erupiVoucherOldList",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiVoucherOldList(HttpServletRequest request,@Valid @RequestBody ErupiVoucherCreatedRequest erupiVoucherCreateListRequest) {
		 
	    logger.info("inside erupiVoucherOldList....");	    	
	    	
	    
	    	String message = "";
	    	List<ErupiVoucherCreateOldDto> response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=erupiVoucherInitiateDetailsService.getErupiVoucherCreateOldList(erupiVoucherCreateListRequest);
	    		
				if(response!=null) {
	    			return ResponseEntity.ok(new ErupiVoucherCreateNameResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new ErupiVoucherCreateNameResponse(MessageConstant.FALSE,MessageConstant.PROFILE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in erupiVoucherOldList====="+e);
			}
	        
	        return ResponseEntity.ok(new ErupiVoucherCreateNameResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/add/erupiMultipleVoucherCreation",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiMultipleVoucherCreation(HttpServletRequest request,@Valid @RequestBody ErupiMultipleVoucherCreateRequest erupiMultipleVoucherCreateRequest) {
		 
	    logger.info("inside erupiMultipleVoucherCreation....");	    	
	    	
	    	String message = "";
	    	ErupiMultipleVoucherCreateRequest response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=erupiVoucherInitiateDetailsService.saveErupiMultipleVoucherCreation(erupiMultipleVoucherCreateRequest);
	    		
				if(response!=null) {
	    			return ResponseEntity.ok(new ErupiMultipleVoucherCreationResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new ErupiMultipleVoucherCreationResponse(MessageConstant.FALSE,MessageConstant.PROFILE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in erupiMultipleVoucherCreation====="+e);
			}
	        
	        return ResponseEntity.ok(new ErupiMultipleVoucherCreationResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/voucherSummaryTotalCount",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> voucherSummaryTotalCount(HttpServletRequest request,@Valid @RequestBody ErupiVoucherCreatedRequest erupiLinkAccountRequest) {
		 
	    logger.info("inside voucherSummaryTotalCount....");	    	
	    	
	    
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
	    			return ResponseEntity.ok(new ErupiVoucherSummaryTotalCountResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,count,amount,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new ErupiVoucherSummaryTotalCountResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,count,amount,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in voucherSummaryTotalCount====="+e);
			}
	        
	        return ResponseEntity.ok(new ErupiVoucherSummaryTotalCountResponse(MessageConstant.FALSE,message,count,amount,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/voucherCreateBankList",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> voucherCreateBankList(HttpServletRequest request,@Valid @RequestBody ErupiVoucherCreatedRequest erupiLinkAccountRequest) {
		 
	    logger.info("inside voucherCreateBankList....");	    	
	    	
	    
	    	String message = "";
	    	Long count=0l;
	    	Long amount=0l;
	    	List<ErupiVoucherBankListDto> response=null;
	    	ErupiVoucherTotalDetailDto response1=null;;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=erupiVoucherInitiateDetailsService.getErupiVoucherCreateBAnkList(erupiLinkAccountRequest);
				if(response!=null && response.size()>0) {
					response1=erupiVoucherInitiateDetailsService.getErupiVoucherCreateStatus(erupiLinkAccountRequest);
				}
				if(response!=null ) {
					//count=CommonUtility.getCreateCount(response);
					//amount=CommonUtility.getCreateAmount(response);
	    			return ResponseEntity.ok(new ErupiVoucherBankDetailListResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,response1,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new ErupiVoucherBankDetailListResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,response1,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in voucherCreateBankList====="+e);
			}
	        
	        return ResponseEntity.ok(new ErupiVoucherBankDetailListResponse(MessageConstant.FALSE,message,response,response1,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	 
//	 @Operation(summary = "This API will provide the Save User Details ", security = {
//	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
//	    @ApiResponses(value = {
//	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
//	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
//	    @RequestMapping(value = "/get/voucherCreateBankList",produces = {"application/json"}, 
//	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
//	    public ResponseEntity<Object> voucherCreateBankList(HttpServletRequest request,@Valid @RequestBody ErupiVoucherCreatedRequest erupiLinkAccountRequest) {
//		 
//	    logger.info("inside voucherCreateBankList....");	    	
//	    	
//	    
//	    	String message = "";
//	    	Long count=0l;
//	    	Long amount=0l;
//	    	List<ErupiVoucherBankListDto> response=null;
//	    	ErupiVoucherTotalDetailDto response1=null;;
//	    	try {	    		
//	    		String companyId = request.getHeader("companyId");
//				SetDatabaseTenent.setDataSource(companyId);
//				
//				response=erupiVoucherInitiateDetailsService.getErupiVoucherCreateBAnkList(erupiLinkAccountRequest);
//				if(response!=null && response.size()>0) {
//					response1=erupiVoucherInitiateDetailsService.getErupiVoucherCreateStatus(erupiLinkAccountRequest);
//				}
//				if(response!=null ) {
//					//count=CommonUtility.getCreateCount(response);
//					//amount=CommonUtility.getCreateAmount(response);
//	    			return ResponseEntity.ok(new ErupiVoucherBankDetailListResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,response1,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
//	    		}else {
//	    			return ResponseEntity.ok(new ErupiVoucherBankDetailListResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,response1,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
//	    		}
//	    	}catch (Exception e) {				
//	    		logger.error("error in voucherCreateBankList====="+e);
//			}
//	        
//	        return ResponseEntity.ok(new ErupiVoucherBankDetailListResponse(MessageConstant.FALSE,message,response,response1,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
//	    }
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/voucherCreateSummaryDetailByAccount",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> voucherCreateSummaryDetailByAccount(HttpServletRequest request,@Valid @RequestBody ErupiVoucherCreatedRequest erupiLinkAccountRequest) {
		 
	    logger.info("inside voucherCreateSummaryDetailByAccount....");
	    
	    	String message = "";
	    	ErupiVoucherTotalDetailDto response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=erupiVoucherInitiateDetailsService.getErupiVoucherCreateDetailByAccount(erupiLinkAccountRequest);
				
				if(response!=null ) {					
	    			return ResponseEntity.ok(new ErupiVoucherDetailResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new ErupiVoucherDetailResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in voucherCreateSummaryDetailByAccount====="+e);
			}
	        
	        return ResponseEntity.ok(new ErupiVoucherDetailResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	 
}
