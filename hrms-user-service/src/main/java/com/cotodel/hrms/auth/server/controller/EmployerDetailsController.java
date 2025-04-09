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

import com.cotodel.hrms.auth.server.dto.EmployerAddressRequest;
import com.cotodel.hrms.auth.server.dto.EmployerAddressResponse;
import com.cotodel.hrms.auth.server.dto.EmployerDetailsByEmpIdResponse;
import com.cotodel.hrms.auth.server.dto.EmployerDetailsGetRequest;
import com.cotodel.hrms.auth.server.dto.EmployerDetailsRequest;
import com.cotodel.hrms.auth.server.dto.EmployerDetailsResponse;
import com.cotodel.hrms.auth.server.dto.EmployerProfileAddress;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedRequest;
import com.cotodel.hrms.auth.server.entity.EmployerDetailsEntity;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.multi.datasource.SetDatabaseTenent;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.EmployerDetailsService;
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
public class EmployerDetailsController extends CotoDelBaseController{
	

	
	private static final Logger logger = LoggerFactory.getLogger(EmployerDetailsController.class);
    
	@Autowired
	EmployerDetailsService employerDetailsService;
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/add/saveEmployerDetails",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> saveEmployerDetails(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
	    	logger.info("inside get saveEmployerDetails+++");
	    	EmployerDetailsRequest userEntity=null;
	    	String responseToken="";
	    	String authToken = "";
	    	EmployerDetailsResponse employerDetailsResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				EmployerDetailsRequest employerDetailsRequest= EncryptionDecriptionUtil.convertFromJson(decript, EmployerDetailsRequest.class);
				
	    	    userEntity=	employerDetailsService.saveEmployerDetails(employerDetailsRequest);
	    	    
	    	    logger.info("after get saveEmployerDetails+++");
	    	    
	    	    if(userEntity!=null && userEntity.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    	    	employerDetailsResponse=new EmployerDetailsResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken);
	    	    	String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employerDetailsResponse);
	    	    	EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    	    	return ResponseEntity.ok(jsonEncriptObject);
	    	    }else {
	    	    	employerDetailsResponse=new EmployerDetailsResponse(MessageConstant.FALSE,userEntity.getResponse(),userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken);
	    	    	String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employerDetailsResponse);
	    	    	EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    	    	return ResponseEntity.ok(jsonEncriptObject);
	    	    }
			
	    	}catch (Exception e) {
				
	    		e.printStackTrace();
	    		logger.error("error in saveUserDetails====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		employerDetailsResponse=new EmployerDetailsResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken);
    	    	String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employerDetailsResponse);
    	    	jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in saveUserDetails====="+e);
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
	    @RequestMapping(value = "/update/updateEmployerDetails",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> updateEmployerDetails(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
	    	logger.info("inside get updateEmployerDetails+++");
	    	EmployerDetailsRequest userEntity=null;
	    	String responseToken="";
	    	String authToken = "";
	    	EmployerDetailsResponse employerDetailsResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				EmployerDetailsRequest employerDetailsRequest= EncryptionDecriptionUtil.convertFromJson(decript, EmployerDetailsRequest.class);
				
	    	    userEntity=	employerDetailsService.updateEmployerDetails(employerDetailsRequest);
	    	    
	    	    logger.info("after get updateEmployerDetails+++");
	    	    
	    	    if(userEntity!=null && userEntity.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    	    	employerDetailsResponse=new EmployerDetailsResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken);
	    	    	String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employerDetailsResponse);
	    	    	EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    	    	return ResponseEntity.ok(jsonEncriptObject);
	    	    }else {
	    	    	employerDetailsResponse=new EmployerDetailsResponse(MessageConstant.FALSE,userEntity.getResponse(),userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken);
	    	    	String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employerDetailsResponse);
	    	    	EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    	    	return ResponseEntity.ok(jsonEncriptObject);
	    	    }
			
	    	}catch (Exception e) {
				
	    		e.printStackTrace();
	    		logger.error("error in updateEmployerDetails====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		employerDetailsResponse=new EmployerDetailsResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken);
 	    	String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employerDetailsResponse);
 	    	jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in updateEmployerDetails====="+e);
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
	    @RequestMapping(value = "/get/getEmployerDetails",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> getEmployerDetails(HttpServletRequest request,@Valid @RequestBody EmployerDetailsRequest employerDetailsRequest) {
	    	logger.info("inside get getEmployerDetails+++");
	    	EmployerDetailsEntity emDetailsEntity=null;
	    	String responseToken="";
	    	String authToken = "";
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				emDetailsEntity=	employerDetailsService.getEmployerDetails(employerDetailsRequest.getEmployerId());
	    	    
	    	    if(emDetailsEntity!=null) {
	    	    	return ResponseEntity.ok(new EmployerDetailsByEmpIdResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,emDetailsEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	    	    }else {
	    	    	return ResponseEntity.ok(new EmployerDetailsByEmpIdResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,emDetailsEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	    	    }
			
	    	}catch (Exception e) {
				
	    		e.printStackTrace();
	    		logger.error("error in saveUserDetails====="+e);
			}
	        
	        return ResponseEntity.ok(new EmployerDetailsByEmpIdResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,emDetailsEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	          
	        
	    }
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/getEmployerComplete",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> getEmployerComplete(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
	    	logger.info("inside get getEmployerComplete+++");
	    	EmployerDetailsEntity emDetailsEntity=null;
	    	String responseToken="";
	    	String authToken = "";
	    	EmployerDetailsByEmpIdResponse employerDetailsByEmpIdResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				EmployerDetailsGetRequest employerDetailsRequest= EncryptionDecriptionUtil.convertFromJson(decript, EmployerDetailsGetRequest.class);
				
				emDetailsEntity=	employerDetailsService.getEmployerDetails(employerDetailsRequest.getEmployerId());
	    	    
	    	    if(emDetailsEntity!=null) {
	    	    	employerDetailsByEmpIdResponse=new EmployerDetailsByEmpIdResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,emDetailsEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken);
	    	    	String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employerDetailsByEmpIdResponse);
	    	    	EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    	    	return ResponseEntity.ok(jsonEncriptObject);
	    	    }else {
	    	    	employerDetailsByEmpIdResponse=new EmployerDetailsByEmpIdResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,emDetailsEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken);
	    	    	String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employerDetailsByEmpIdResponse);
	    	    	EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    	    	return ResponseEntity.ok(jsonEncriptObject);
	    	    }
			
	    	}catch (Exception e) {
				
	    		e.printStackTrace();
	    		logger.error("error in saveUserDetails====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		employerDetailsByEmpIdResponse=new EmployerDetailsByEmpIdResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,emDetailsEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken);
    	    	String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employerDetailsByEmpIdResponse);
    	    	jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in saveUserDetails====="+e);
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
	    @RequestMapping(value = "/get/getCompaneyAddress",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> getCompaneyAddress(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
	    	logger.info("inside getCompaneyAddress+++");
	    	List<EmployerProfileAddress> response=null;
	    	String authToken = "";
	    	EmployerAddressResponse employerAddressResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ErupiVoucherCreatedRequest erupiLinkAccountRequest= EncryptionDecriptionUtil.convertFromJson(decript, ErupiVoucherCreatedRequest.class);
				
				response=	employerDetailsService.getCompaneyAddress(erupiLinkAccountRequest.getOrgId());	    	    
	    	    if(response!=null) {
	    	    	employerAddressResponse=new EmployerAddressResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken);
	    	    	String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employerAddressResponse);
	    	    	EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    	    	return ResponseEntity.ok(jsonEncriptObject);
	    	    }else {
	    	    	employerAddressResponse=new EmployerAddressResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken);
	    	    	String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employerAddressResponse);
	    	    	EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    	    	return ResponseEntity.ok(jsonEncriptObject);
	    	    }
			
	    	}catch (Exception e) {				
	    		e.printStackTrace();
	    		logger.error("error in getCompaneyAddress====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		employerAddressResponse=new EmployerAddressResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken);
    	    	String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employerAddressResponse);
    	    	jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in getCompaneyAddress====="+e);
			}
    	    return ResponseEntity.ok(jsonEncriptObject);
	          
	        
	    }
	 
}
