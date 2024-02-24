package com.cotodel.hrms.auth.server.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author vinay
 */

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.model.TokenAuthRequest;
import com.cotodel.hrms.auth.server.model.TokenAuthResponse;
import com.cotodel.hrms.auth.server.security.JwtUtilsService;
import com.cotodel.hrms.auth.server.util.TransactionManager;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/Api")
public class VerifyTokenController {

	
	
	private static final Logger logger = LoggerFactory.getLogger(VerifyTokenController.class);
    
    @Autowired
    JwtUtilsService jwtUtilsService;
    
    
    @Operation(summary = "This API will verify the access token", security = {
    		@SecurityRequirement(name = "task_auth")}, tags = {"Verify Token APIs"})
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
    @RequestMapping(value = "/verify/access-token",produces = {"application/json"},
    consumes = {"application/json"},method = RequestMethod.POST)
    public ResponseEntity<Object> tokenVerify(@RequestBody TokenAuthRequest request) {
    	
    	  logger.info("inside token authentication");
    	  boolean verifyStatus=false;
    	  try {
    		  
    		  String authToken=jwtUtilsService.parseJwt(request.getAuthToken());
	    
    		  verifyStatus=jwtUtilsService.validateJwtToken(authToken, request.getCompanyCode());
    	 if(verifyStatus)
    		 return   ResponseEntity.ok(new TokenAuthResponse(true,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
    	 
    	  }catch (Exception e) {
			logger.info("Token Authentication Failed :::"+e.getMessage());
		} 
    	  
 		 return   ResponseEntity.ok(new TokenAuthResponse(false,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
 	    
        
    }

    
   



}
