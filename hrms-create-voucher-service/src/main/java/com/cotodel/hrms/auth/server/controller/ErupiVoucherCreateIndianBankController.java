package com.cotodel.hrms.auth.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cotodel.hrms.auth.server.dto.CallApiResponse;
import com.cotodel.hrms.auth.server.dto.CallApiSmsResponse;
import com.cotodel.hrms.auth.server.dto.CallApiStatusResponse;
import com.cotodel.hrms.auth.server.dto.DecryptedResponse;
import com.cotodel.hrms.auth.server.dto.DecryptedSmsResponse;
import com.cotodel.hrms.auth.server.dto.DecryptedStatusResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreateRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherSmsRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherStatusRequest;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.service.ErupiVoucherTxnService;
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
public class ErupiVoucherCreateIndianBankController extends CotoDelBaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(ErupiVoucherCreateIndianBankController.class);
	
	@Autowired
	ErupiVoucherTxnService erupiVoucherTxnService;
	
//    @Operation(summary = "This API will provide the Save User Details ", security = {
//	@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
//	@ApiResponses(value = {
//	@ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
//	@ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//	@ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//	@ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
//
//	@RequestMapping(value = "/callapi/vouchercreation",produces = {"application/json"}, 
//		    consumes = {"application/json","application/text"},method = RequestMethod.POST)
//		    public ResponseEntity<Object> voucherCreation(@RequestBody ErupiVoucherCreateRequest erupiVoucherCreateRequest) {
//		
//    	DecryptedResponse decryptedResponse=null; 
//		
//		try {
//			
//		    logger.info("inside /callapi/vouchercreation...respString."+erupiVoucherCreateRequest);
//		    
//		    decryptedResponse= erupiVoucherTxnService.calApiErupiVoucherCreateDetails(erupiVoucherCreateRequest);
//		    if(decryptedResponse!=null && decryptedResponse.getSuccess().equalsIgnoreCase("true")) {
//		    	return ResponseEntity.ok(new CallApiResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,decryptedResponse,TransactionManager.getCurrentTimeStamp()));
//		    }else {
//		    	return ResponseEntity.ok(new CallApiResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,decryptedResponse,TransactionManager.getCurrentTimeStamp()));
//		    }
//				 
//		} catch (Exception e) {
//			e.printStackTrace();
//			
//			return ResponseEntity.ok(new CallApiResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,decryptedResponse,TransactionManager.getCurrentTimeStamp()));
//		}
//		
//	}
//    
//    @Operation(summary = "This API will provide the Save User Details ", security = {
//    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
//    		@ApiResponses(value = {
//    		@ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
//    		@ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//    		@ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//    		@ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
//
//    		@RequestMapping(value = "/callapi/voucherstatus",produces = {"application/json"}, 
//    			    consumes = {"application/json","application/text"},method = RequestMethod.POST)
//    			    public ResponseEntity<Object> voucherStatus(@RequestBody ErupiVoucherStatusRequest erupiVoucherStatusRequest) {
//    			
//    	DecryptedStatusResponse decryptedResponse=null; 
//    			
//    			try {
//    				
//    			    logger.info("inside /callapi/voucherStatus...respString."+erupiVoucherStatusRequest);
//    			    
//    			    decryptedResponse= erupiVoucherTxnService.calApiErupiVoucherStatusDetails(erupiVoucherStatusRequest);
//    			    if(decryptedResponse!=null && decryptedResponse.getSuccess().equalsIgnoreCase("true")) {
//    			    	return ResponseEntity.ok(new CallApiStatusResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,decryptedResponse,TransactionManager.getCurrentTimeStamp()));
//    			    }else {
//    			    	return ResponseEntity.ok(new CallApiStatusResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,decryptedResponse,TransactionManager.getCurrentTimeStamp()));
//    			    }
//    					 
//    			} catch (Exception e) {
//    				e.printStackTrace();
//    				
//    				return ResponseEntity.ok(new CallApiStatusResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,decryptedResponse,TransactionManager.getCurrentTimeStamp()));
//    			}
//    			
//    		}
//    
//    		@Operation(summary = "This API will provide the Save User Details ", security = {
//    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
//    		@ApiResponses(value = {
//    		@ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
//    		@ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//    		@ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//    		@ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
//
//    		@RequestMapping(value = "/callapi/voucherSms",produces = {"application/json"}, 
//    			    consumes = {"application/json","application/text"},method = RequestMethod.POST)
//    			    public ResponseEntity<Object> voucherSms(@RequestBody ErupiVoucherSmsRequest erupiVoucherSmsRequest) {
//    			
//    			DecryptedSmsResponse decryptedResponse=null; 
//    			
//    			try {
//    				
//    			    logger.info("inside /callapi/voucherSms...voucherSms."+erupiVoucherSmsRequest);
//    			    
//    			    decryptedResponse= erupiVoucherTxnService.calApiErupiVoucherSmsDetails(erupiVoucherSmsRequest);
//    			    if(decryptedResponse!=null && decryptedResponse.getResponse_Status().equalsIgnoreCase("Success")) {
//    			    	return ResponseEntity.ok(new CallApiSmsResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,decryptedResponse,TransactionManager.getCurrentTimeStamp()));
//    			    }else {
//    			    	return ResponseEntity.ok(new CallApiSmsResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,decryptedResponse,TransactionManager.getCurrentTimeStamp()));
//    			    }
//    					 
//    			} catch (Exception e) {
//    				e.printStackTrace();
//    				
//    				return ResponseEntity.ok(new CallApiSmsResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,decryptedResponse,TransactionManager.getCurrentTimeStamp()));
//    			}
//    			
//    		}
	
}
