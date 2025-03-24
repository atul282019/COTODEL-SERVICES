package com.cotodel.hrms.auth.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cotodel.hrms.auth.server.dto.CashFreeOrderIdResponse;
import com.cotodel.hrms.auth.server.dto.CashFreeOrderResponse;
import com.cotodel.hrms.auth.server.dto.OrderIdResponse;
import com.cotodel.hrms.auth.server.dto.OrderUserRequest;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.service.CashService;
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
public class CashFreeController extends CotoDelBaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(CashFreeController.class);
	
	@Autowired
	CashService cashService;
	
	 @Operation(summary = "This API will provide the Save User Details ", security = {
				@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
				@ApiResponses(value = {
				@ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
				@ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
				@ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
				@ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
				@RequestMapping(value = "/get/cashFreeOrder",produces = {"application/json"},consumes = {"application/json","application/text"},method = RequestMethod.POST)
					    public ResponseEntity<Object> voucherCreation(@RequestBody OrderUserRequest orderUserRequest) {
					
		 			OrderUserRequest response=null;
					try {						
					    logger.info("inside /callapi/vouchercreation...respString."+orderUserRequest);
					    
					    response= cashService.callOrderApi(orderUserRequest);
					    
					    if(response!=null && response.getPayment_session_id()!=null) {
					    	return ResponseEntity.ok(new CashFreeOrderResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,response,TransactionManager.getCurrentTimeStamp()));
					    }else {
					    	return ResponseEntity.ok(new CashFreeOrderResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,response,TransactionManager.getCurrentTimeStamp()));
					    }
							 
					} catch (Exception e) {
						e.printStackTrace();
						
						return ResponseEntity.ok(new CashFreeOrderResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,response,TransactionManager.getCurrentTimeStamp()));
					}
					
				}
	 			
	 @Operation(summary = "This API will provide the Save User Details ", security = {
				@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
				@ApiResponses(value = {
				@ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
				@ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
				@ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
				@ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
				@RequestMapping(value = "/get/cashFreeOrderId",produces = {"application/json"},consumes = {"application/json","application/text"},method = RequestMethod.POST)
					    public ResponseEntity<Object> cashFreeOrderId(@RequestBody OrderUserRequest orderUserRequest) {
					
		 OrderIdResponse response=null;
					try {						
					    logger.info("inside /callapi/vouchercreation...respString."+orderUserRequest);
					    
					    response= cashService.callOrderIdApi(orderUserRequest);
					    
					    if(response!=null ) {
					    	return ResponseEntity.ok(new CashFreeOrderIdResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,response,TransactionManager.getCurrentTimeStamp()));
					    }else {
					    	return ResponseEntity.ok(new CashFreeOrderIdResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,response,TransactionManager.getCurrentTimeStamp()));
					    }
							 
					} catch (Exception e) {
						e.printStackTrace();
						
						return ResponseEntity.ok(new CashFreeOrderIdResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,response,TransactionManager.getCurrentTimeStamp()));
					}
					
				}
}
