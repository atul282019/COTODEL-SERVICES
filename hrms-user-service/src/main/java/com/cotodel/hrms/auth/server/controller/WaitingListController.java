package com.cotodel.hrms.auth.server.controller;

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

import com.cotodel.hrms.auth.server.dto.UserWaitingListResponse;
import com.cotodel.hrms.auth.server.entity.UserWaitingListEntity;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.multi.datasource.SetDatabaseTenent;
import com.cotodel.hrms.auth.server.service.UserWaitingService;
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
public class WaitingListController {

	@Autowired
	UserWaitingService userWaitingService;		
	
	private static final Logger logger = LoggerFactory.getLogger(WaitingListController.class);
   
	@Operation(summary = "This API will provide the Save User Details ", security = {
    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
    @RequestMapping(value = "/add/saveWaitingListUsers",produces = {"application/json"}, 
    consumes = {"application/json","application/text"},method = RequestMethod.POST)
    public ResponseEntity<Object> saveWaitingListUsers(HttpServletRequest request,@Valid @RequestBody UserWaitingListEntity userWaitingListEntity) {
    	logger.info("inside get saveWaitingListUsers");
    	UserWaitingListEntity userEntity=null;
    	String authToken = "";
    	try {	    		
    		String companyId = request.getHeader("companyId");
			SetDatabaseTenent.setDataSource(companyId);
			
			
    	    userEntity=	userWaitingService.saveUserDetails(userWaitingListEntity);
    		
    	    if(userEntity!=null) {   		 

    		 return ResponseEntity.ok(new UserWaitingListResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
    	    }
		
    	}catch (Exception e) {
			
    		e.printStackTrace();
    		logger.error("error in saveWaitingListUsers====="+e);
		}
        
        return ResponseEntity.ok(new UserWaitingListResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
          
        
    }

    
    @Operation(summary = "This API will provide the user Authentication ", security = {
    		@SecurityRequirement(name = "task_auth")}, tags = {"user Authentication  APIs"})
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))),		
    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
    @RequestMapping(value = "/get/waitingListUser",produces = {"application/json"}, consumes = {"application/json"},method = RequestMethod.POST)
    public ResponseEntity<Object>  waitingListUser(HttpServletRequest request,@RequestBody UserWaitingListEntity userWaitingListEntity) {
        
    	String companyId=request.getHeader("companyId");
    	SetDatabaseTenent.setDataSource(companyId);
    
    	UserWaitingListEntity user=	userWaitingService.checkUserEmail(userWaitingListEntity.getEmail());
    	
    	return ResponseEntity
                .ok(user);
    }


}
