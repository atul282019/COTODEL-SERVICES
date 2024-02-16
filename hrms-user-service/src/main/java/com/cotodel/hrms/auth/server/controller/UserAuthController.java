package com.cotodel.hrms.auth.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cotodel.hrms.auth.server.dto.UserAuthRequest;
import com.cotodel.hrms.auth.server.entity.UserEntity;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.multi.datasource.SetDatabaseTenent;
import com.cotodel.hrms.auth.server.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/Api")
public class UserAuthController {


	@Autowired
	UserService userService;
		
	
	private static final Logger logger = LoggerFactory.getLogger(UserAuthController.class);
   // private  HttpServletRequest request;
   
    
    @Operation(summary = "This API will provide the user Authentication ", security = {
    		@SecurityRequirement(name = "task_auth")}, tags = {"user Authentication  APIs"})
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))),		
    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
    @RequestMapping(value = "/get/user",produces = {"application/json"}, consumes = {"application/json"},method = RequestMethod.POST)
    public ResponseEntity<Object>  getUserAuth(HttpServletRequest request,@RequestBody UserAuthRequest userRequest) {
        
    	String companyId=request.getHeader("companyId");
    	SetDatabaseTenent.setDataSource(companyId)	;
    
    	UserEntity user=	userService.getByUserName(userRequest.getUserName());
    	
    	
    	return ResponseEntity
                .ok(user);
    }



}
