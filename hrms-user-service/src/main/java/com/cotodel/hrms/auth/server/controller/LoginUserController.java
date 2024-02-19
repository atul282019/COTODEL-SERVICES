package com.cotodel.hrms.auth.server.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cotodel.hrms.auth.server.dto.UserRequest;
import com.cotodel.hrms.auth.server.entity.UserEntity;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/Api")
public class LoginUserController {
	
	
private static final Logger logger = LoggerFactory.getLogger(LoginUserController.class);
    
	@Autowired
	UserService userService;
	
	
	@Operation(summary = "This API will provide the Login User Details ", security = {
    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
    @RequestMapping(value = "/get/loginUser",produces = {"application/json"}, 
    consumes = {"application/json","application/text"},method = RequestMethod.POST)
    public ResponseEntity<Object> checkEligibility(@Valid @RequestBody UserRequest userReq) {
    	logger.info("inside token generation");
    	UserEntity userEntity=null;
    	try {
    		
    		
			
    		
    	 userEntity=	userService.checkUserDetails(userReq.getUsername());
    	 
    	 // write code here 
    	  //test commit change 
    		
		  
    	 if(userEntity!=null)
    		 return ResponseEntity
 	                .ok(userEntity);
    	 
    	 
    	}catch (Exception e) {
			
    		logger.error("error in loginUser====="+e);
		}
        
        return ResponseEntity
                .ok(null);
          
        
    }

	

}
