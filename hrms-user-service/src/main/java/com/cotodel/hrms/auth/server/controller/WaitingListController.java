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

import com.cotodel.hrms.auth.server.dto.UserWaitingListGetResponse;
import com.cotodel.hrms.auth.server.dto.UserWaitingListRequest;
import com.cotodel.hrms.auth.server.dto.UserWaitingListResponse;
import com.cotodel.hrms.auth.server.dto.UserWaitingListUpdateRequest;
import com.cotodel.hrms.auth.server.dto.UserWaitingListUpdateResponse;
import com.cotodel.hrms.auth.server.entity.UserWaitingListEntity;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.multi.datasource.SetDatabaseTenent;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.UserWaitingService;
import com.cotodel.hrms.auth.server.util.EncriptResponse;
import com.cotodel.hrms.auth.server.util.EncryptionDecriptionUtil;
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
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
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
    public ResponseEntity<Object> saveWaitingListUsers(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
    	logger.info("inside get saveWaitingListUsers+++");
    	UserWaitingListRequest response=null;
    	String authToken = "";
    	String message=MessageConstant.RESPONSE_FAILED;
    	UserWaitingListResponse userWaitingListResponse;
    	try {	    		
    		String companyId = request.getHeader("companyId");
			SetDatabaseTenent.setDataSource(companyId);
			String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
			UserWaitingListRequest userWaitingListRequest= EncryptionDecriptionUtil.convertFromJson(decript, UserWaitingListRequest.class);
			
			response=userWaitingService.saveUserDetails(userWaitingListRequest);
    		
    	    if(response!=null && response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {   		 
    	    	userWaitingListResponse=new UserWaitingListResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken);
    	    	String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userWaitingListResponse);
    	    	EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
    	    	return ResponseEntity.ok(jsonEncriptObject);
    	    }
		
    	}catch (Exception e) {
			
    		e.printStackTrace();
    		logger.error("error in /add/saveWaitingListUsers====="+e.getMessage());
    		message=e.getMessage();
		}
    	EncriptResponse jsonEncriptObject=new EncriptResponse();
    	try {
    		userWaitingListResponse=new UserWaitingListResponse(MessageConstant.FALSE,response.getResponse(),response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken);
	    	String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userWaitingListResponse);
	    	jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
		} catch (Exception e) {
			logger.error("error in /add/saveWaitingListUsers====="+e.getMessage());
		}
	    return ResponseEntity.ok(jsonEncriptObject);
        
    }
	
	@Operation(summary = "This API will provide the Save User Details ", security = {
    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
    @RequestMapping(value = "/get/waitingListUsers",produces = {"application/json"}, 
    consumes = {"application/json","application/text"},method = RequestMethod.POST)
    public ResponseEntity<Object> waitingListUsers(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
    	logger.info("inside get waitingListUsers+++");
    	List<UserWaitingListEntity> response=null;
    	String authToken = "";
    	String message=MessageConstant.RESPONSE_FAILED;
    	UserWaitingListGetResponse userWaitingListGetResponse;
    	try {	    		
    		String companyId = request.getHeader("companyId");
			SetDatabaseTenent.setDataSource(companyId);
			String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
			UserWaitingListRequest userWaitingListRequest= EncryptionDecriptionUtil.convertFromJson(decript, UserWaitingListRequest.class);
			
			response=userWaitingService.checkUserList();
    		
    	    if(response!=null && response.size()>0) {   		 
    	    	userWaitingListGetResponse=new UserWaitingListGetResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken);
    	    	String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userWaitingListGetResponse);
    	    	EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
    	    	return ResponseEntity.ok(jsonEncriptObject);
    	    }
		
    	}catch (Exception e) {
			
    		e.printStackTrace();
    		logger.error("error in /get/waitingListUsers====="+e.getMessage());
    		message=e.getMessage();
		}
    	EncriptResponse jsonEncriptObject=new EncriptResponse();
    	try {
    		userWaitingListGetResponse=new UserWaitingListGetResponse(MessageConstant.FALSE,MessageConstant.PROFILE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken);
	    	String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userWaitingListGetResponse);
	    	jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
		} catch (Exception e) {
			logger.error("error in /get/waitingListUsers====="+e.getMessage());
		}
	    return ResponseEntity.ok(jsonEncriptObject);
        
    }

	@Operation(summary = "This API will provide the Save User Details ", security = {
    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
    @RequestMapping(value = "/update/waitListUsers",produces = {"application/json"}, 
    consumes = {"application/json","application/text"},method = RequestMethod.POST)
    public ResponseEntity<Object> waitListUsers(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
    	logger.info("inside get waitListUsers+++");
    	UserWaitingListUpdateRequest response=null;
    	String authToken = "";
    	String message=MessageConstant.RESPONSE_FAILED;
    	UserWaitingListUpdateResponse userWaitingListResponse;
    	try {	    		
    		String companyId = request.getHeader("companyId");
			SetDatabaseTenent.setDataSource(companyId);
			String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
			UserWaitingListUpdateRequest userWaitingListRequest= EncryptionDecriptionUtil.convertFromJson(decript, UserWaitingListUpdateRequest.class);
			
			response=userWaitingService.updateUserDetails(request,userWaitingListRequest);
    		
    	    if(response!=null && response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {   		 
    	    	userWaitingListResponse=new UserWaitingListUpdateResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken);
    	    	String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userWaitingListResponse);
    	    	EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
    	    	return ResponseEntity.ok(jsonEncriptObject);
    	    }
		
    	}catch (Exception e) {
			
    		e.printStackTrace();
    		logger.error("error in /add/waitListUsers====="+e.getMessage());
    		message=e.getMessage();
		}
    	EncriptResponse jsonEncriptObject=new EncriptResponse();
    	try {
    		userWaitingListResponse=new UserWaitingListUpdateResponse(MessageConstant.FALSE,response.getResponse(),response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken);
	    	String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userWaitingListResponse);
	    	jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
		} catch (Exception e) {
			logger.error("error in /add/waitListUsers====="+e.getMessage());
		}
	    return ResponseEntity.ok(jsonEncriptObject);
        
    }

}
