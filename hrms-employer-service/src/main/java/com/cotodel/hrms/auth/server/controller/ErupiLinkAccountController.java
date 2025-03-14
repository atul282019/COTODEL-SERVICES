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

import com.cotodel.hrms.auth.server.dto.ErupiGetLinkAccountListResponse;
import com.cotodel.hrms.auth.server.dto.ErupiGetLinkAccountResponse;
import com.cotodel.hrms.auth.server.dto.ErupiLinkAccountDetailResponse;
import com.cotodel.hrms.auth.server.dto.ErupiLinkAccountRequest;
import com.cotodel.hrms.auth.server.dto.ErupiLinkAccountResponse;
import com.cotodel.hrms.auth.server.dto.ErupiLinkAccountUpdateResponse;
import com.cotodel.hrms.auth.server.dto.ErupiLinkAccountWithOutResponse;
import com.cotodel.hrms.auth.server.dto.ErupiPrimaryAccountDetailResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedRequest;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.model.ErupiLinkAccountEntity;
import com.cotodel.hrms.auth.server.multi.datasource.SetDatabaseTenent;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.ErupiLinkAccountService;
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
public class ErupiLinkAccountController {
private static final Logger logger = LoggerFactory.getLogger(ExpenseTravelController.class);
	
	@Autowired
	ErupiLinkAccountService erupiAccountService;
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/add/erupiLinkAccount",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiLinkAccount(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside erupiLinkAccount....");	    	
	    	
	    
	    	String message = "";
	    	ErupiLinkAccountRequest response=null;
	    	ErupiLinkAccountResponse erupiLinkAccountResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ErupiLinkAccountRequest erupiLinkAccountRequest= EncryptionDecriptionUtil.convertFromJson(decript, ErupiLinkAccountRequest.class);
				
				response=erupiAccountService.saveErupiAccountDetails(erupiLinkAccountRequest);
	    		
				if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
					erupiLinkAccountResponse=new ErupiLinkAccountResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiLinkAccountResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			erupiLinkAccountResponse=new ErupiLinkAccountResponse(MessageConstant.FALSE,response.getResponse(),response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiLinkAccountResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in ErupiLinkAccountRequest====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		erupiLinkAccountResponse=new ErupiLinkAccountResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
				String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiLinkAccountResponse);
				jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				// TODO: handle exception
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
	    @RequestMapping(value = "/get/erupiLinkAccount",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> geterupiLinkAccount(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside erupiLinkAccount....");	    	
	    	
	    
	    	String message = "";
	    	ErupiLinkAccountWithOutResponse response=null;
	    	ErupiGetLinkAccountResponse erupiGetLinkAccountResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ErupiLinkAccountRequest erupiLinkAccountRequest= EncryptionDecriptionUtil.convertFromJson(decript, ErupiLinkAccountRequest.class);
				
				response=erupiAccountService.getErupiAccountDetails(erupiLinkAccountRequest);
	    		
				if(response!=null) {
					erupiGetLinkAccountResponse=new ErupiGetLinkAccountResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiGetLinkAccountResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			erupiGetLinkAccountResponse=new ErupiGetLinkAccountResponse(MessageConstant.FALSE,MessageConstant.PROFILE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiGetLinkAccountResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in erupiLinkAccount====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		erupiGetLinkAccountResponse=new ErupiGetLinkAccountResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
				String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiGetLinkAccountResponse);
				jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in erupiLinkAccount====="+e);
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
	    @RequestMapping(value = "/get/erupiLinkAccountList",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> geterupiLinkAccountList(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside geterupiLinkAccount....");	    	
	    	
	    
	    	String message = "";
	    	List<ErupiLinkAccountWithOutResponse> response=null;
	    	ErupiGetLinkAccountListResponse erupiGetLinkAccountListResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ErupiLinkAccountRequest erupiLinkAccountRequest= EncryptionDecriptionUtil.convertFromJson(decript, ErupiLinkAccountRequest.class);
				response=erupiAccountService.getErupiAccountListDetails(erupiLinkAccountRequest);
	    		
				if(response!=null && response.size()>0) {
					erupiGetLinkAccountListResponse=new ErupiGetLinkAccountListResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiGetLinkAccountListResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			erupiGetLinkAccountListResponse=new ErupiGetLinkAccountListResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiGetLinkAccountListResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in ErupiLinkAccountRequest====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		erupiGetLinkAccountListResponse=new ErupiGetLinkAccountListResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
				String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiGetLinkAccountListResponse);
				jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				// TODO: handle exception
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
	    @RequestMapping(value = "/get/erupiLinkAccountDetails",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiLinkAccountDetails(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside geterupiLinkAccount....");	    	
	    	
	    
	    	String message = "";
	    	ErupiLinkAccountEntity response=null;
	    	ErupiLinkAccountDetailResponse erupiLinkAccountDetailResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ErupiLinkAccountRequest erupiLinkAccountRequest= EncryptionDecriptionUtil.convertFromJson(decript, ErupiLinkAccountRequest.class);
				response=erupiAccountService.getErupiAccountDetails(erupiLinkAccountRequest.getAcNumber());
	    		
				if(response!=null) {
					erupiLinkAccountDetailResponse=new ErupiLinkAccountDetailResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiLinkAccountDetailResponse);
					 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					 return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			erupiLinkAccountDetailResponse=new ErupiLinkAccountDetailResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiLinkAccountDetailResponse);
					 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					 return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in ErupiLinkAccountRequest====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		erupiLinkAccountDetailResponse=new ErupiLinkAccountDetailResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
				 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiLinkAccountDetailResponse);
				 jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in ErupiLinkAccountRequest====="+e);
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
	    @RequestMapping(value = "/update/erupiLinAccPsFlag",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiLinAccPsFlag(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside erupiLinAccPsFlag....");	    	
	    	
	    
	    	String message = "";
	    	ErupiLinkAccountRequest response=null;
	    	ErupiLinkAccountUpdateResponse erupiLinkAccountUpdateResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ErupiLinkAccountRequest erupiLinkAccountRequest= EncryptionDecriptionUtil.convertFromJson(decript, ErupiLinkAccountRequest.class);
				
				response=erupiAccountService.updateErupiAccountPSFlag(erupiLinkAccountRequest);
	    		
				if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
					erupiLinkAccountUpdateResponse=new ErupiLinkAccountUpdateResponse(MessageConstant.TRUE,MessageConstant.PROFILE_UPDATE,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiLinkAccountUpdateResponse);
					 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					 return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			erupiLinkAccountUpdateResponse=new ErupiLinkAccountUpdateResponse(MessageConstant.FALSE,MessageConstant.PROFILE_FAILED_UPDATE,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiLinkAccountUpdateResponse);
					 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					 return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in erupiLinAccPsFlag====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		erupiLinkAccountUpdateResponse=new ErupiLinkAccountUpdateResponse(MessageConstant.FALSE,message,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
		        String jsonEncript = EncryptionDecriptionUtil.convertToJson(erupiLinkAccountUpdateResponse);
	            jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in erupiLinAccPsFlag====="+e);
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
	    @RequestMapping(value = "/get/erupiPrimaryAccountDetails",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiPrimaryAccountDetails(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside geterupiLinkAccount....");	    	
	    	
	    
	    	String message = "";
	    	ErupiLinkAccountRequest response=null;
	    	ErupiPrimaryAccountDetailResponse erupiPrimaryAccountDetailResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ErupiLinkAccountRequest erupiLinkAccountRequest= EncryptionDecriptionUtil.convertFromJson(decript, ErupiLinkAccountRequest.class);
				
				response=erupiAccountService.getErupiPrimaryAccountDetails(erupiLinkAccountRequest.getOrgId());
	    		
				if(response!=null) {
					erupiPrimaryAccountDetailResponse=new ErupiPrimaryAccountDetailResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiPrimaryAccountDetailResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			erupiPrimaryAccountDetailResponse=new ErupiPrimaryAccountDetailResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiPrimaryAccountDetailResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in ErupiLinkAccountRequest====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		erupiPrimaryAccountDetailResponse=new ErupiPrimaryAccountDetailResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
				String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiPrimaryAccountDetailResponse);
				jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in ErupiLinkAccountRequest====="+e);
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
	    @RequestMapping(value = "/update/erupiLinAccDisable",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiLinAccDisable(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside erupiLinAccDisable....");	    	
	    
	    	String message = "";
	    	ErupiLinkAccountRequest response=null;
	    	ErupiLinkAccountUpdateResponse erupiLinkAccountUpdateResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ErupiLinkAccountRequest erupiLinkAccountRequest= EncryptionDecriptionUtil.convertFromJson(decript, ErupiLinkAccountRequest.class);
				
				response=erupiAccountService.updateErupiAccountDisable(erupiLinkAccountRequest);
	    		
				if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
					erupiLinkAccountUpdateResponse=new ErupiLinkAccountUpdateResponse(MessageConstant.TRUE,MessageConstant.PROFILE_UPDATE,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiLinkAccountUpdateResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			erupiLinkAccountUpdateResponse=new ErupiLinkAccountUpdateResponse(MessageConstant.FALSE,MessageConstant.PROFILE_FAILED_UPDATE,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiLinkAccountUpdateResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in erupiLinAccDisable====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		erupiLinkAccountUpdateResponse=new ErupiLinkAccountUpdateResponse(MessageConstant.FALSE,message,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
				String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiLinkAccountUpdateResponse);
				jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in erupiLinAccDisable====="+e);
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
	    @RequestMapping(value = "/update/erupiLinAccEnable",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiLinAccEnable(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside erupiLinAccEnable....");	    	
	    
	    	String message = "";
	    	ErupiLinkAccountRequest response=null;
	    	ErupiLinkAccountUpdateResponse erupiLinkAccountUpdateResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ErupiLinkAccountRequest erupiLinkAccountRequest= EncryptionDecriptionUtil.convertFromJson(decript, ErupiLinkAccountRequest.class);
				
				response=erupiAccountService.updateErupiAccountEnable(erupiLinkAccountRequest);
	    		
				if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
					erupiLinkAccountUpdateResponse=new ErupiLinkAccountUpdateResponse(MessageConstant.TRUE,MessageConstant.PROFILE_UPDATE,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiLinkAccountUpdateResponse);
					 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					 return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			erupiLinkAccountUpdateResponse=new ErupiLinkAccountUpdateResponse(MessageConstant.FALSE,MessageConstant.PROFILE_FAILED_UPDATE,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiLinkAccountUpdateResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in erupiLinAccDisable====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		erupiLinkAccountUpdateResponse=new ErupiLinkAccountUpdateResponse(MessageConstant.FALSE,message,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
		        String jsonEncript = EncryptionDecriptionUtil.convertToJson(erupiLinkAccountUpdateResponse);
	            jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in erupiLinAccDisable====="+e);
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
	    @RequestMapping(value = "/get/erupiLinkAccountListWithStatus",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiLinkAccountListWithStatus(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside geterupiLinkAccount....");	    	
	    	
	    
	    	String message = "";
	    	List<ErupiLinkAccountWithOutResponse> response=null;
	    	ErupiGetLinkAccountListResponse erupiGetLinkAccountListResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ErupiVoucherCreatedRequest erupiLinkAccountRequest= EncryptionDecriptionUtil.convertFromJson(decript, ErupiVoucherCreatedRequest.class);
				
				response=erupiAccountService.getErupiAccountListDetailsWithStatus(erupiLinkAccountRequest);
	    		
				if(response!=null && response.size()>0) {
					erupiGetLinkAccountListResponse=new ErupiGetLinkAccountListResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiGetLinkAccountListResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			erupiGetLinkAccountListResponse=new ErupiGetLinkAccountListResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiGetLinkAccountListResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in ErupiLinkAccountRequest====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		erupiGetLinkAccountListResponse=new ErupiGetLinkAccountListResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
				String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiGetLinkAccountListResponse);
				jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				// TODO: handle exception
			}
    	    return ResponseEntity.ok(jsonEncriptObject);
	    }
}
