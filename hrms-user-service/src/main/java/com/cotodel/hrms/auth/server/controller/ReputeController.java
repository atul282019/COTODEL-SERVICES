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

import com.cotodel.hrms.auth.server.dto.ReputeResponse;
import com.cotodel.hrms.auth.server.dto.ReputeTokenRequest;
import com.cotodel.hrms.auth.server.dto.ReputeTokenResponse;
import com.cotodel.hrms.auth.server.entity.ReputeTokenEntity;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.multi.datasource.SetDatabaseTenent;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.ReputeService;
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

/**
 * @author vinay
 */
@RestController
@RequestMapping("/Api")
public class ReputeController extends CotoDelBaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(ReputeController.class);
    
	@Autowired
	ReputeService reputeService;
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/add/saveRepute",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> saveUserDetails(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
	    	logger.info("inside get saveRepute+++");
	    	String authToken = "";
	    	ReputeTokenRequest response=null;
	    	ReputeResponse reputeResponse=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ReputeTokenRequest userWaitingListRequest= EncryptionDecriptionUtil.convertFromJson(decript, ReputeTokenRequest.class);
				
				response=reputeService.saveReputeDetails(userWaitingListRequest);
	    		
	    	    if(response!=null && response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {   		 
	    	    	reputeResponse=new ReputeResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken);
	    	    	String jsonEncript =  EncryptionDecriptionUtil.convertToJson(reputeResponse);
	    	    	EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    	    	return ResponseEntity.ok(jsonEncriptObject);
	    	    }
			
	    	}catch (Exception e) {
				
	    		e.printStackTrace();
	    		logger.error("error in /add/saveRepute====="+e.getMessage());
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		reputeResponse=new ReputeResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken);
    	    	String jsonEncript =  EncryptionDecriptionUtil.convertToJson(reputeResponse);
    	    	jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in /add/saveRepute====="+e.getMessage());
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
	    @RequestMapping(value = "/get/reputeToken",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> reputeToken(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
	    	logger.info("inside get reputeToken+++");
	    	String authToken = "";
	    	ReputeTokenEntity response=null;
	    	ReputeTokenResponse reputeResponse=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ReputeTokenRequest userWaitingListRequest= EncryptionDecriptionUtil.convertFromJson(decript, ReputeTokenRequest.class);
				
				response=reputeService.getReputeToken(userWaitingListRequest.getMobile());
	    		
	    	    if(response!=null) {   		 
	    	    	reputeResponse=new ReputeTokenResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken);
	    	    	String jsonEncript =  EncryptionDecriptionUtil.convertToJson(reputeResponse);
	    	    	EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    	    	return ResponseEntity.ok(jsonEncriptObject);
	    	    }
			
	    	}catch (Exception e) {
				
	    		e.printStackTrace();
	    		logger.error("error in /add/saveRepute====="+e.getMessage());
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		reputeResponse=new ReputeTokenResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken);
	    		String jsonEncript =  EncryptionDecriptionUtil.convertToJson(reputeResponse);
	    		jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in /add/saveRepute====="+e.getMessage());
			}
		    return ResponseEntity.ok(jsonEncriptObject);
	 }
}
