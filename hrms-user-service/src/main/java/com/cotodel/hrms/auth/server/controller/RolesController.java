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

import com.cotodel.hrms.auth.server.dto.RoleMasterNewResponse;
import com.cotodel.hrms.auth.server.dto.RoleMasterResponse;
import com.cotodel.hrms.auth.server.dto.UserRequest;
import com.cotodel.hrms.auth.server.entity.RoleMaster;
import com.cotodel.hrms.auth.server.entity.RoleMasterEntity;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.RolesMasterService;
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
public class RolesController {

	

	
	private static final Logger logger = LoggerFactory.getLogger(RolesController.class);
    
	@Autowired
	RolesMasterService rolesMasterService;
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
	 @Operation(summary = "This API will provide the User Roles Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/Roles",produces = {"application/json"}, consumes = {"application/json","application/text"},
	    method = RequestMethod.POST)
	    public ResponseEntity<Object> saveUserDetails(@Valid @RequestBody EncriptResponse enResponse) {
	    	logger.info("inside get Roles+++");
	    	List<RoleMaster> roleMaster=null;
	    	RoleMasterResponse roleMasterResponse;
	    	try {
	    		String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
	    		UserRequest userReq= EncryptionDecriptionUtil.convertFromJson(decript, UserRequest.class);
	    		
	    		roleMaster=	rolesMasterService.getRolesMaster(userReq.getEmployerid());
	    		 if(roleMaster!=null && roleMaster.size()>0 ) {
	    			 roleMasterResponse=new RoleMasterResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,roleMaster,TransactionManager.getCurrentTimeStamp());
	    			 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(roleMasterResponse);
	    			 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			 return ResponseEntity.ok(jsonEncriptObject);
	    		 }else {
	    			 roleMasterResponse=new RoleMasterResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,roleMaster,TransactionManager.getCurrentTimeStamp());
	    			 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(roleMasterResponse);
	    			 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			 return ResponseEntity.ok(jsonEncriptObject);
	    		 }
	    	 
	    	}catch (Exception e) {
				
	    		logger.error("error in Roles====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		 roleMasterResponse=new RoleMasterResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,roleMaster,TransactionManager.getCurrentTimeStamp());
    			 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(roleMasterResponse);
    			 jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in Roles====="+e);
			}
    	    return ResponseEntity.ok(jsonEncriptObject);
	        
	    }

	 @Operation(summary = "This API will provide the User Roles Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/roleMaster",produces = {"application/json"}, consumes = {"application/json","application/text"},
	    method = RequestMethod.POST)
	    public ResponseEntity<Object> roleMaster(@Valid @RequestBody UserRequest userReq) {
	    	logger.info("inside get roleMaster+++");
	    	List<RoleMasterEntity> roleMaster=null;
	    	try {
	    		
	    		roleMaster=	rolesMasterService.getRoleMaster();
	    		 if(roleMaster!=null && roleMaster.size()>0 )
		    		 return ResponseEntity.ok(new RoleMasterNewResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,roleMaster,TransactionManager.getCurrentTimeStamp()));
		    	 
	    	 
	    	}catch (Exception e) {
				
	    		logger.error("Error in roleMaster====="+e);
			}
	        
	    	return ResponseEntity.ok(new RoleMasterNewResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,roleMaster,TransactionManager.getCurrentTimeStamp()));
	        
	    }

}
