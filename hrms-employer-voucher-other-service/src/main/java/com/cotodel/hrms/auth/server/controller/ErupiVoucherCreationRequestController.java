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

import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingManagerRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreateRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherGetRequestResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherRequestResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherUpdateRequestResponse;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.model.ErupiVoucherCreationRequestEntity;
import com.cotodel.hrms.auth.server.multi.datasource.SetDatabaseTenent;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.ErupiVoucherRequestService;
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
public class ErupiVoucherCreationRequestController {
private static final Logger logger = LoggerFactory.getLogger(ExpenseTravelController.class);
	
	@Autowired
	ErupiVoucherRequestService erupiVoucherRequestService;
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
	
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/add/erupiVoucherRequest",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiVoucherRequest(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside erupiVoucherRequest....");	    	
	    	
	    	ErupiVoucherCreateRequest response=null;
	    	ErupiVoucherRequestResponse erupiVoucherRequestResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ErupiVoucherCreateRequest erupiVoucherCreateRequest= EncryptionDecriptionUtil.convertFromJson(decript, ErupiVoucherCreateRequest.class);
				
				response=erupiVoucherRequestService.saveErupiVoucherRequest(erupiVoucherCreateRequest);
	    		
				if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
					erupiVoucherRequestResponse=new ErupiVoucherRequestResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherRequestResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			erupiVoucherRequestResponse=new ErupiVoucherRequestResponse(MessageConstant.FALSE,response.getResponse(),response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherRequestResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in erupiVoucherRequest====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		erupiVoucherRequestResponse=new ErupiVoucherRequestResponse(MessageConstant.FALSE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
				String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherRequestResponse);
				jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in erupiVoucherRequest====="+e);
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
	    @RequestMapping(value = "/get/erupiVoucherRequest",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> getErupiVoucherRequest(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside getErupiVoucherRequest....");	    	
	    	
	    	List<ErupiVoucherCreationRequestEntity> response=null;
	    	ErupiVoucherGetRequestResponse erupiVoucherGetRequestResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ErupiVoucherCreateRequest erupiVoucherCreateRequest= EncryptionDecriptionUtil.convertFromJson(decript, ErupiVoucherCreateRequest.class);
				
				response=erupiVoucherRequestService.getErupiVoucherRequestEmployerId(erupiVoucherCreateRequest.getEmployerId(),erupiVoucherCreateRequest.getEmployeeId());
	    		
				if(response!=null) {
					erupiVoucherGetRequestResponse=new ErupiVoucherGetRequestResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherGetRequestResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			erupiVoucherGetRequestResponse=new ErupiVoucherGetRequestResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherGetRequestResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {		
	    		e.printStackTrace();
	    		logger.error("error in erupiVoucherRequest====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		erupiVoucherGetRequestResponse=new ErupiVoucherGetRequestResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
				String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherGetRequestResponse);
				jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in erupiVoucherRequest====="+e);
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
	    @RequestMapping(value = "/get/erupiVoucherRequestByMngId",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiVoucherRequestByMngId(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside erupiVoucherRequestByMngId....");	    	
	    	
	    	List<ErupiVoucherCreationRequestEntity> response=null;
	    	ErupiVoucherGetRequestResponse erupiVoucherGetRequestResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				EmployeeOnboardingManagerRequest erupiVoucherCreateRequest= EncryptionDecriptionUtil.convertFromJson(decript, EmployeeOnboardingManagerRequest.class);
				
				response=erupiVoucherRequestService.getErupiVoucherRequestByMgrId(erupiVoucherCreateRequest.getManagerId());
	    		
				if(response!=null) {
					erupiVoucherGetRequestResponse=new ErupiVoucherGetRequestResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherGetRequestResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			erupiVoucherGetRequestResponse=new ErupiVoucherGetRequestResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherGetRequestResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {		
	    		e.printStackTrace();
	    		logger.error("error in erupiVoucherRequestByMngId====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		erupiVoucherGetRequestResponse=new ErupiVoucherGetRequestResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
				String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherGetRequestResponse);
				jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in erupiVoucherRequestByMngId====="+e);
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
	    @RequestMapping(value = "/update/erupiVoucherRequestUpdate",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiVoucherRequestUpdate(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside erupiVoucherRequestUpdate....");	    	
	    	
	    EmployeeOnboardingManagerRequest response=null;
	    ErupiVoucherUpdateRequestResponse erupiVoucherUpdateRequestResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				EmployeeOnboardingManagerRequest erupiVoucherCreateRequest= EncryptionDecriptionUtil.convertFromJson(decript, EmployeeOnboardingManagerRequest.class);
				
				response=erupiVoucherRequestService.updateErupiVoucherRequestUpdate(erupiVoucherCreateRequest);
	    		
				if(response!=null && response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
					erupiVoucherUpdateRequestResponse=new ErupiVoucherUpdateRequestResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherUpdateRequestResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			erupiVoucherUpdateRequestResponse=new ErupiVoucherUpdateRequestResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherUpdateRequestResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {		
	    		e.printStackTrace();
	    		logger.error("error in erupiVoucherRequestUpdate====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		erupiVoucherUpdateRequestResponse=new ErupiVoucherUpdateRequestResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
				String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherUpdateRequestResponse);
				jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in erupiVoucherRequestUpdate====="+e);
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
	    @RequestMapping(value = "/get/erupiVoucherRequestApproved",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiVoucherRequestApproved(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside erupiVoucherRequestApproved....");	    	
	    	
	    	List<ErupiVoucherCreationRequestEntity> response=null;
	    	ErupiVoucherGetRequestResponse erupiVoucherGetRequestResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ErupiVoucherCreateRequest erupiVoucherCreateRequest= EncryptionDecriptionUtil.convertFromJson(decript, ErupiVoucherCreateRequest.class);
				
				response=erupiVoucherRequestService.getErupiVoucherRequestApprovedEmployerId(erupiVoucherCreateRequest.getEmployerId(),erupiVoucherCreateRequest.getEmployeeId());
	    		
				if(response!=null) {
					erupiVoucherGetRequestResponse=new ErupiVoucherGetRequestResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherGetRequestResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			erupiVoucherGetRequestResponse=new ErupiVoucherGetRequestResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherGetRequestResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {		
	    		e.printStackTrace();
	    		logger.error("error in erupiVoucherRequestApproved====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		erupiVoucherGetRequestResponse=new ErupiVoucherGetRequestResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
				String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherGetRequestResponse);
				jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in erupiVoucherRequestApproved====="+e);
			}
	    return ResponseEntity.ok(jsonEncriptObject);
	    }
}
