package com.cotodel.hrms.auth.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cotodel.hrms.auth.server.dto.DecryptedResponse;
import com.cotodel.hrms.auth.server.dto.ErupiIndianBankVoucherCreateRequest;
import com.cotodel.hrms.auth.server.dto.ErupiIndianBankVoucherInquiryRequest;
import com.cotodel.hrms.auth.server.dto.ErupiIndianBankVoucherRevokeRequest;
import com.cotodel.hrms.auth.server.dto.IndianBankApiResponse;
import com.cotodel.hrms.auth.server.dto.IndianBankVoucherCreateResponse;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.service.ErupiIndianBankVoucherService;
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
	ErupiIndianBankVoucherService erupiIndianBankVoucherService;
	
    @Operation(summary = "This API will provide the Save User Details ", security = {
	@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	@ApiResponses(value = {
	@ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	@ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	@ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	@ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})

	@RequestMapping(value = "/call/indianVoucherCreation",produces = {"application/json"}, 
		    consumes = {"application/json","application/text"},method = RequestMethod.POST)
		    public ResponseEntity<Object> indianVoucherCreation(@RequestBody ErupiIndianBankVoucherCreateRequest erupiVoucherCreateRequest) {
		
    	DecryptedResponse decryptedResponse=null; 
    	IndianBankVoucherCreateResponse response=null;
		try {
			
		    logger.info("inside call/indianVoucherCreation...respString."+erupiVoucherCreateRequest);
		    
		    response= erupiIndianBankVoucherService.calApiErupiVoucherCreateDetails(erupiVoucherCreateRequest);
		    
		    if(response!=null && response.getTxnStatus().equalsIgnoreCase("00")) {
		    	return ResponseEntity.ok(new IndianBankApiResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,response,TransactionManager.getCurrentTimeStamp()));
		    }else {
		    	return ResponseEntity.ok(new IndianBankApiResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,response,TransactionManager.getCurrentTimeStamp()));
		    }
				 
		} catch (Exception e) {
			e.printStackTrace();
			
			return ResponseEntity.ok(new IndianBankApiResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,response,TransactionManager.getCurrentTimeStamp()));
		}
		
	}
    
    @Operation(summary = "This API will provide the Save User Details ", security = {
    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
    		@ApiResponses(value = {
    		@ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
    		@ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
    		@ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
    		@ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})

    		@RequestMapping(value = "/call/indianVoucherRevoke",produces = {"application/json"}, 
    			    consumes = {"application/json","application/text"},method = RequestMethod.POST)
    			    public ResponseEntity<Object> indianVoucherRevoke(@RequestBody ErupiIndianBankVoucherRevokeRequest erupiVoucherCreateRequest) {
    			
    	    	DecryptedResponse decryptedResponse=null; 
    	    	IndianBankVoucherCreateResponse response=null;
    			try {
    				
    			    logger.info("inside call/indianVoucherCreation...respString."+erupiVoucherCreateRequest);
    			    
    			    response= erupiIndianBankVoucherService.calApiErupiVoucherRevokeDetails(erupiVoucherCreateRequest);
    			    
    			    if(response!=null && response.getTxnStatus().equalsIgnoreCase("00")) {
    			    	return ResponseEntity.ok(new IndianBankApiResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,response,TransactionManager.getCurrentTimeStamp()));
    			    }else {
    			    	return ResponseEntity.ok(new IndianBankApiResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,response,TransactionManager.getCurrentTimeStamp()));
    			    }
    					 
    			} catch (Exception e) {
    				e.printStackTrace();
    				
    				return ResponseEntity.ok(new IndianBankApiResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,response,TransactionManager.getCurrentTimeStamp()));
    			}
    			
    		}
    @Operation(summary = "This API will provide the Save User Details ", security = {
    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
    		@ApiResponses(value = {
    		@ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
    		@ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
    		@ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
    		@ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})

    		@RequestMapping(value = "/call/indianVoucherInquiry",produces = {"application/json"}, 
    			    consumes = {"application/json","application/text"},method = RequestMethod.POST)
    			    public ResponseEntity<Object> indianVoucherInquiry(@RequestBody ErupiIndianBankVoucherInquiryRequest eruBankVoucherInquiryRequest) {
    			
    	    	DecryptedResponse decryptedResponse=null; 
    	    	IndianBankVoucherCreateResponse response=null;
    			try {
    				
    			    logger.info("inside call/indianVoucherInquiry...respString."+eruBankVoucherInquiryRequest);
    			    
    			    response= erupiIndianBankVoucherService.calApiErupiVoucherInquiryDetails(eruBankVoucherInquiryRequest);
    			    
    			    if(response!=null && response.getTxnStatus().equalsIgnoreCase("00")) {
    			    	return ResponseEntity.ok(new IndianBankApiResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,response,TransactionManager.getCurrentTimeStamp()));
    			    }else {
    			    	return ResponseEntity.ok(new IndianBankApiResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,response,TransactionManager.getCurrentTimeStamp()));
    			    }
    					 
    			} catch (Exception e) {
    				e.printStackTrace();
    				
    				return ResponseEntity.ok(new IndianBankApiResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,response,TransactionManager.getCurrentTimeStamp()));
    			}
    			
    		}

	
}
