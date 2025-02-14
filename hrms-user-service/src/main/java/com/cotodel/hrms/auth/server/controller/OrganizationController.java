package com.cotodel.hrms.auth.server.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cotodel.hrms.auth.server.dto.OrganizaionResponse;
import com.cotodel.hrms.auth.server.entity.OrganizationMaster;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.OrganizationMasterService;
import com.cotodel.hrms.auth.server.util.EncriptResponse;
import com.cotodel.hrms.auth.server.util.EncryptionDecriptionUtil;
import com.cotodel.hrms.auth.server.util.TransactionManager;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/Api")
public class OrganizationController {
	private static final Logger logger = LoggerFactory.getLogger(RolesController.class);
	
	@Autowired
	OrganizationMasterService organizationMasterService;
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
	 @Operation(summary = "This API will provide the User Roles Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/Organization",produces = {"application/json"}, consumes = {"application/json","application/text"},
	    method = RequestMethod.POST)
	    public ResponseEntity<Object> getOrganizationList() {
	    	logger.info("inside get Organization+++");
	    	List<OrganizationMaster> organizationMasters=null;
	    	OrganizaionResponse organizaionResponse;
	    	
	    	try {	    		
	    		
	    		organizationMasters=organizationMasterService.getOrganizationMaster();
	    		
	    	 if(organizationMasters!=null) {
	    		 organizaionResponse=new OrganizaionResponse(true,organizationMasters,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    		 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(organizaionResponse);
	    		 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    		 return ResponseEntity.ok(jsonEncriptObject);
	    	 
	    	 }else {
	    		 organizaionResponse=new OrganizaionResponse(false,organizationMasters,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    		 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(organizaionResponse);
	    		 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    		 return ResponseEntity.ok(jsonEncriptObject);
	    	 }
	    	}catch (Exception e) {
				
	    		logger.error("error in organization====="+e);
	    		
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		organizaionResponse=new OrganizaionResponse(false,organizationMasters,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    		String jsonEncript =  EncryptionDecriptionUtil.convertToJson(organizaionResponse);
	    		jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in organization====="+e);
			}
    	    return ResponseEntity.ok(jsonEncriptObject);   
	        
	    }
}
