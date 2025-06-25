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

import com.cotodel.hrms.auth.server.dto.DirectorOnboardingGetResponse;
import com.cotodel.hrms.auth.server.dto.DirectorOnboardingRequest;
import com.cotodel.hrms.auth.server.dto.DirectorOnboardingResponse;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.model.DirectorOnboardingEntity;
import com.cotodel.hrms.auth.server.multi.datasource.SetDatabaseTenent;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.DirectorOnboardingService;
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
public class DirectorOnboardingController {
	
	private static final Logger logger = LoggerFactory.getLogger(DirectorOnboardingController.class);
	
	@Autowired
	DirectorOnboardingService employeeOnboardingService;
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
	@Operation(summary = "This API will provide the Save User Details ", security = {
    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
    @RequestMapping(value = "/add/directorOnboarding",produces = {"application/json"}, 
    consumes = {"application/json","application/text"},method = RequestMethod.POST)
    public ResponseEntity<Object> directorOnboarding(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
	 
    logger.info("inside directorOnboarding....");	    	
    	
    	DirectorOnboardingRequest response=null;
    	DirectorOnboardingResponse directorOnboardingResponse;
    	try {	    		
    		String companyId = request.getHeader("companyId");
			SetDatabaseTenent.setDataSource(companyId);
			
			String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
			DirectorOnboardingRequest directorOnboardingRequest= EncryptionDecriptionUtil.convertFromJson(decript, DirectorOnboardingRequest.class);
			
			response=employeeOnboardingService.saveDirectorDetails(directorOnboardingRequest);
    		
			if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
				directorOnboardingResponse=new DirectorOnboardingResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
				String jsonEncript =  EncryptionDecriptionUtil.convertToJson(directorOnboardingResponse);
				EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
				return ResponseEntity.ok(jsonEncriptObject);
    		}else {
    			directorOnboardingResponse=new DirectorOnboardingResponse(MessageConstant.FALSE,response.getResponse(),response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
				String jsonEncript =  EncryptionDecriptionUtil.convertToJson(directorOnboardingResponse);
				EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
				return ResponseEntity.ok(jsonEncriptObject);
    		}
    	}catch (Exception e) {				
    		logger.error("error in directorOnboarding====="+e);
		}
    	EncriptResponse jsonEncriptObject=new EncriptResponse();
    	try {
    		directorOnboardingResponse=new DirectorOnboardingResponse(MessageConstant.FALSE,MessageConstant.PROFILE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(directorOnboardingResponse);
			jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
		} catch (Exception e) {
			logger.error("error in directorOnboarding====="+e);
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
    @RequestMapping(value = "/get/directorOnboarding",produces = {"application/json"}, 
    consumes = {"application/json","application/text"},method = RequestMethod.POST)
    public ResponseEntity<Object> getDirectorOnboarding(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
	 
    logger.info("inside directorOnboarding....");	    	
    	
    	List<DirectorOnboardingEntity> response=null;
    	DirectorOnboardingGetResponse directorOnboardingResponse;
    	try {	    		
    		String companyId = request.getHeader("companyId");
			SetDatabaseTenent.setDataSource(companyId);
			
			String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
			DirectorOnboardingRequest directorOnboardingRequest= EncryptionDecriptionUtil.convertFromJson(decript, DirectorOnboardingRequest.class);
			
			response=employeeOnboardingService.getDirectorDetailsList(directorOnboardingRequest.getOrgId());
    		
			if(response!=null) {
				directorOnboardingResponse=new DirectorOnboardingGetResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
				String jsonEncript =  EncryptionDecriptionUtil.convertToJson(directorOnboardingResponse);
				EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
				return ResponseEntity.ok(jsonEncriptObject);
    		}else {
    			directorOnboardingResponse=new DirectorOnboardingGetResponse(MessageConstant.FALSE,MessageConstant.PROFILE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
				String jsonEncript =  EncryptionDecriptionUtil.convertToJson(directorOnboardingResponse);
				EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
				return ResponseEntity.ok(jsonEncriptObject);
    		}
    	}catch (Exception e) {				
    		logger.error("error in directorOnboarding====="+e);
		}
    	EncriptResponse jsonEncriptObject=new EncriptResponse();
    	try {
    		directorOnboardingResponse=new DirectorOnboardingGetResponse(MessageConstant.FALSE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(directorOnboardingResponse);
			jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
		} catch (Exception e) {
			logger.error("error in directorOnboarding====="+e);
		}
	    return ResponseEntity.ok(jsonEncriptObject);
    }
	
}
