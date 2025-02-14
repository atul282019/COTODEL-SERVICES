package com.cotodel.hrms.auth.server.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cotodel.hrms.auth.server.dto.PermissionsMasterResponse;
import com.cotodel.hrms.auth.server.dto.UserRequest;
import com.cotodel.hrms.auth.server.entity.PermissionsMaster;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.PermissionsMasterService;
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
public class PermissionsController {

	

	
	private static final Logger logger = LoggerFactory.getLogger(PermissionsController.class);
    
	@Autowired
	PermissionsMasterService permissionsMasterService;
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
	 @Operation(summary = "This API will provide the User Roles Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/Permissions",produces = {"application/json"}, consumes = {"application/json","application/text"},
	    method = RequestMethod.POST)
	    public ResponseEntity<Object> saveUserDetails(@Valid @RequestBody EncriptResponse enResponse) {
	    	logger.info("inside get Roles+++");
	    	List<PermissionsMaster> permissionsMasters=null;
	    	PermissionsMasterResponse permissionsMasterResponse;
	    	try {
	    		
	    		String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
	    		UserRequest userReq= EncryptionDecriptionUtil.convertFromJson(decript, UserRequest.class);
	    		
	    		permissionsMasters=	permissionsMasterService.getPermissionsMaster(userReq.getEmployerid());
	    		 if(permissionsMasters!=null && permissionsMasters.size()>0 ) {
	    			 permissionsMasterResponse=new PermissionsMasterResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,permissionsMasters,TransactionManager.getCurrentTimeStamp());
	    			 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(permissionsMasterResponse);
	    			 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			 return ResponseEntity.ok(jsonEncriptObject);
	    		 }else {
	    			 permissionsMasterResponse=new PermissionsMasterResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,permissionsMasters,TransactionManager.getCurrentTimeStamp());
	    			 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(permissionsMasterResponse);
	    			 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			 return ResponseEntity.ok(jsonEncriptObject);
	    		 }
	    	 
	    	}catch (Exception e) {
				
	    		logger.error("error in Roles====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		permissionsMasterResponse=new PermissionsMasterResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,permissionsMasters,TransactionManager.getCurrentTimeStamp());
   			    String jsonEncript =  EncryptionDecriptionUtil.convertToJson(permissionsMasterResponse);
   			    jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				// TODO: handle exception
			}
    	    return ResponseEntity.ok(jsonEncriptObject);
	        
	    }

	

}
