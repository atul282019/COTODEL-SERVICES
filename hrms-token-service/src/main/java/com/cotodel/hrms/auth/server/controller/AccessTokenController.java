package com.cotodel.hrms.auth.server.controller;


import java.time.LocalDate;

/**
 * @author vinay
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.model.Credential;
import com.cotodel.hrms.auth.server.response.JwtResponse;
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
public class AccessTokenController {

	
	
	private static final Logger logger = LoggerFactory.getLogger(AccessTokenController.class);
    
    @Autowired
    JwtUtilsService jwtUtilsService;

    
    
    @Operation(summary = "This API will provide the Authentication token", security = {
    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
    @RequestMapping(value = "/get/access-token",produces = {"application/json"}, consumes = {"application/json"},method = RequestMethod.GET)
    public ResponseEntity<Object> getToken() {
    	logger.info("inside token generation");
	    
        String jwtToken=jwtUtilsService.generateJwtToken(null);
        
        return ResponseEntity
                .ok(new JwtResponse(jwtToken,"Bearer", TransactionManager.getTransactionId(),
                		"", "",TransactionManager.getCurrentTimeStamp()));
          
        
    }

    
    @Operation(summary = "This API will provide the Authentication token", security = {
    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))),		
    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
    @RequestMapping(value = "/get/access-token",produces = {"application/json"}, consumes = {"application/json"},method = RequestMethod.POST)
    public ResponseEntity<Object>  getTokenProvidingEmployer(@RequestBody Credential credential) {
        
    	String jwtToken=jwtUtilsService.generateJwtToken(credential.getEmployerCode());
      
    	return ResponseEntity
                .ok(new JwtResponse(jwtToken,"Bearer", TransactionManager.getTransactionId(),
                		"", "",TransactionManager.getCurrentTimeStamp()));
    }


}
