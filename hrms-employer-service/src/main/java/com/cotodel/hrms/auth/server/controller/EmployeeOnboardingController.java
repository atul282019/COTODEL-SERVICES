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

import com.cotodel.hrms.auth.server.dto.EmployeeConfirmOnboardingResponse;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingDriverRequest;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingDto;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingIdResponse;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingListRequest;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingListResponse;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingManagerIdResponse;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingNewRequest;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingNewResponse;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingReputeRequest;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingReputeUpdateRequest;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingRequest;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingResponse;
import com.cotodel.hrms.auth.server.dto.UpdateEmployeeResponse;
import com.cotodel.hrms.auth.server.dto.UpdateEmployeeStatusRequest;
import com.cotodel.hrms.auth.server.dto.vehicle.EmployeeOnboardingDriverResponse;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.model.EmployeeOnboardingEntity;
import com.cotodel.hrms.auth.server.multi.datasource.SetDatabaseTenent;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.EmployeeOnboardingService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
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
public class EmployeeOnboardingController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeOnboardingController.class);
	
	@Autowired
	EmployeeOnboardingService employeeOnboardingService;
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/add/saveEmplOnboarding",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> saveEmplOnboarding(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
	    logger.info("inside saveEmplOnboarding+++");    	
	    
	    	String message = "";
	    	String message1 = "";
	    	EmployeeOnboardingRequest response=null;
	    	EmployeeOnboardingRequest employeeOnboardingRequest=null;
	    	EmployeeOnboardingResponse employeeOnboardingResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				employeeOnboardingRequest= EncryptionDecriptionUtil.convertFromJson(decript, EmployeeOnboardingRequest.class);
				response=employeeOnboardingService.saveEmployeeDetails(employeeOnboardingRequest);
				message1=response.getResponse()==null?MessageConstant.PROFILE_FAILED:response.getResponse();
	    		if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			employeeOnboardingResponse=new EmployeeOnboardingResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS_UPDATE,employeeOnboardingRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeOnboardingResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			employeeOnboardingResponse=new EmployeeOnboardingResponse(MessageConstant.FALSE,message1,employeeOnboardingRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeOnboardingResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		//e.printStackTrace();
	    		logger.error("error in saveProfileDetails====="+e);
	    		//message=e.getMessage();
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		employeeOnboardingResponse=new EmployeeOnboardingResponse(MessageConstant.FALSE,message,employeeOnboardingRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeOnboardingResponse);
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
	    @RequestMapping(value = "/get/empOnboardingList",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> empOnboardingList(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
	    logger.info("inside empOnboardingList...+++");	    	
	    	
	    
	    	String message = "";
	    	List<EmployeeOnboardingEntity> response=null;
	    	EmployeeOnboardingListResponse employeeOnboardingListResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				EmployeeOnboardingRequest employeeOnboardingRequest= EncryptionDecriptionUtil.convertFromJson(decript, EmployeeOnboardingRequest.class);
				response=employeeOnboardingService.getEmployeeDetailsList(employeeOnboardingRequest.getEmployerId());
	    		if(response!=null && response.size()>0) {
	    			employeeOnboardingListResponse=new EmployeeOnboardingListResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeOnboardingListResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			employeeOnboardingListResponse=new EmployeeOnboardingListResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeOnboardingListResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		//e.printStackTrace();
	    		logger.error("error in saveProfileDetails====="+e);
	    		//message=e.getMessage();
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		employeeOnboardingListResponse=new EmployeeOnboardingListResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeOnboardingListResponse);
    			jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in saveProfileDetails====="+e);
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
	    @RequestMapping(value = "/add/saveBulkEmplOnboarding",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> saveBulkEmplOnboarding(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
	    logger.info("inside saveBulkEmplOnboarding+++");    	
	    
	    	String message = "";
	    	String message1 = "";
	    	EmployeeOnboardingRequest response=null;
	    	EmployeeOnboardingRequest employeeOnboardingRequest=null;
	    	EmployeeOnboardingResponse employeeOnboardingResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				employeeOnboardingRequest= EncryptionDecriptionUtil.convertFromJson(decript, EmployeeOnboardingRequest.class);
				response=employeeOnboardingService.saveBulkEmployeeDetails(employeeOnboardingRequest);
				
				message1=response.getResponse()==null?MessageConstant.PROFILE_FAILED:response.getResponse();
	    		if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			employeeOnboardingResponse=new EmployeeOnboardingResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,employeeOnboardingRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeOnboardingResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			employeeOnboardingResponse=new EmployeeOnboardingResponse(MessageConstant.FALSE,message1,employeeOnboardingRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeOnboardingResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		//e.printStackTrace();
	    		logger.error("error in saveProfileDetails====="+e);
	    		//message=e.getMessage();
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		employeeOnboardingResponse=new EmployeeOnboardingResponse(MessageConstant.FALSE,message,employeeOnboardingRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeOnboardingResponse);
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
	    @RequestMapping(value = "/add/confirmBulkEmplOnboarding",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> confirmBulkEmplOnboarding(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
	    logger.info("inside confirmBulkEmplOnboarding+++");    	
	    
	    	String message = "";
	    	String message1 = "";
	    	EmployeeOnboardingListRequest response=null;
	    	EmployeeOnboardingListRequest employeeOnboardingRequest=null;
	    	EmployeeConfirmOnboardingResponse employeeConfirmOnboardingResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				employeeOnboardingRequest= EncryptionDecriptionUtil.convertFromJson(decript, EmployeeOnboardingListRequest.class);
				
				response=employeeOnboardingService.confirmBulkEmployeeDetails(employeeOnboardingRequest);
				
				employeeConfirmOnboardingResponse=new EmployeeConfirmOnboardingResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,employeeOnboardingRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
				String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeConfirmOnboardingResponse);
				EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
				return ResponseEntity.ok(jsonEncriptObject);	    		
	    	}catch (Exception e) {				
	    		//e.printStackTrace();
	    		logger.error("error in saveProfileDetails====="+e);
	    		//message=e.getMessage();
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		employeeConfirmOnboardingResponse=new EmployeeConfirmOnboardingResponse(MessageConstant.FALSE,message,employeeOnboardingRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
				String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeConfirmOnboardingResponse);
				jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in saveProfileDetails====="+e);
			}
    	    return ResponseEntity.ok(jsonEncriptObject);
	    }
//	 @Operation(summary = "This API will provide the Save User Details ", security = {
//	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
//	    @ApiResponses(value = {
//	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
//	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
//	    @RequestMapping(value = "/add/tryBulkEmplOnboarding",produces = {"application/json"}, 
//	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
//	    public ResponseEntity<Object> tryBulkEmplOnboarding(HttpServletRequest request,@Valid @RequestBody EmployeeOnboardingListRequest employeeOnboardingRequest) {
//	    logger.info("inside tryBulkEmplOnboarding");    	
//	    
//	    	String message = "";
//	    	String message1 = "";
//	    	EmployeeOnboardingListRequest response=null;
//	    	try {	    		
//	    		String companyId = request.getHeader("companyId");
//				SetDatabaseTenent.setDataSource(companyId);
//				
//				response=employeeOnboardingService.tryBulkEmployeeDetails(employeeOnboardingRequest);
//	    			return ResponseEntity.ok(new EmployeeConfirmOnboardingResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,employeeOnboardingRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
//	    		
//	    	}catch (Exception e) {				
//	    		//e.printStackTrace();
//	    		logger.error("error in tryBulkEmplOnboarding====="+e);
//	    		//message=e.getMessage();
//			}
//	        
//	        return ResponseEntity.ok(new EmployeeConfirmOnboardingResponse(MessageConstant.FALSE,message,employeeOnboardingRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
//	    }
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/empOnboardingById",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> empOnboardingById(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
	    logger.info("inside empOnboardingList...+++");	    	
	    	
	    
	    	String message = "";
	    	EmployeeOnboardingEntity response=null;
	    	EmployeeOnboardingIdResponse employeeOnboardingIdResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				EmployeeOnboardingRequest employeeOnboardingRequest= EncryptionDecriptionUtil.convertFromJson(decript, EmployeeOnboardingRequest.class);
				
				response=employeeOnboardingService.getEmployeeDetailsById(employeeOnboardingRequest.getId());
	    		if(response!=null ) {
	    			employeeOnboardingIdResponse=new EmployeeOnboardingIdResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeOnboardingIdResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			employeeOnboardingIdResponse=new EmployeeOnboardingIdResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeOnboardingIdResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		//e.printStackTrace();
	    		logger.error("error in tryBulkEmplOnboarding====="+e);
	    		//message=e.getMessage();
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		employeeOnboardingIdResponse=new EmployeeOnboardingIdResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeOnboardingIdResponse);
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
	    @RequestMapping(value = "/get/empOnboardingByUserDetailId",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> empOnboardingByUserDetailId(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
	    logger.info("inside empOnboardingByUserDetailId...+++");	    	
	    	
	    
	    	String message = "";
	    	EmployeeOnboardingEntity response=null;
	    	EmployeeOnboardingIdResponse employeeOnboardingIdResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				EmployeeOnboardingRequest employeeOnboardingRequest= EncryptionDecriptionUtil.convertFromJson(decript, EmployeeOnboardingRequest.class);
				
				response=employeeOnboardingService.getEmployeeDetailsByUserId(employeeOnboardingRequest.getUserDetailsId());
	    		if(response!=null ) {
	    			employeeOnboardingIdResponse=new EmployeeOnboardingIdResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeOnboardingIdResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			employeeOnboardingIdResponse=new EmployeeOnboardingIdResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeOnboardingIdResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		//e.printStackTrace();
	    		logger.error("error in empOnboardingByUserDetailId====="+e);
	    		//message=e.getMessage();
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		employeeOnboardingIdResponse=new EmployeeOnboardingIdResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
 			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeOnboardingIdResponse);
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
	    @RequestMapping(value = "/add/saveEmplOnboardingNew",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> saveEmplOnboardingNew(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
	    logger.info("inside saveEmplOnboardingNew+++");    	
	    
	    	String message = "";
	    	String message1 = "";
	    	EmployeeOnboardingNewRequest response=null;
	    	EmployeeOnboardingNewResponse employeeOnboardingNewResponse;
	    	EmployeeOnboardingNewRequest employeeOnboardingRequest=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				employeeOnboardingRequest= EncryptionDecriptionUtil.convertFromJson(decript, EmployeeOnboardingNewRequest.class);
				
				response=employeeOnboardingService.saveEmployeeDetailsNew(employeeOnboardingRequest);
				message1=response.getResponse()==null?MessageConstant.PROFILE_FAILED:response.getResponse();
	    		if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			employeeOnboardingNewResponse=new EmployeeOnboardingNewResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,employeeOnboardingRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeOnboardingNewResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			employeeOnboardingNewResponse=new EmployeeOnboardingNewResponse(MessageConstant.FALSE,message1,employeeOnboardingRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeOnboardingNewResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		//e.printStackTrace();
	    		logger.error("error in saveEmplOnboardingNew====="+e);
	    		//message=e.getMessage();
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		employeeOnboardingNewResponse=new EmployeeOnboardingNewResponse(MessageConstant.FALSE,message,employeeOnboardingRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeOnboardingNewResponse);
    			jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in saveEmplOnboardingNew====="+e);
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
	    @RequestMapping(value = "/update/updateEmplOnboardingNew",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> updateEmplOnboardingNew(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
	    logger.info("inside updateEmplOnboardingNew+++");    	
	    
	    	String message = "";
	    	String message1 = "";
	    	EmployeeOnboardingNewRequest response=null;
	    	EmployeeOnboardingNewResponse employeeOnboardingNewResponse;
	    	EmployeeOnboardingNewRequest employeeOnboardingRequest=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				employeeOnboardingRequest= EncryptionDecriptionUtil.convertFromJson(decript, EmployeeOnboardingNewRequest.class);
				
				response=employeeOnboardingService.updateEmployeeDetailsNew(employeeOnboardingRequest);
				message1=response.getResponse()==null?MessageConstant.PROFILE_FAILED:response.getResponse();
	    		if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			employeeOnboardingNewResponse=new EmployeeOnboardingNewResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,employeeOnboardingRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeOnboardingNewResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			employeeOnboardingNewResponse=new EmployeeOnboardingNewResponse(MessageConstant.FALSE,message1,employeeOnboardingRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeOnboardingNewResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		//e.printStackTrace();
	    		logger.error("error in updateEmplOnboardingNew====="+e);
	    		//message=e.getMessage();
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		employeeOnboardingNewResponse=new EmployeeOnboardingNewResponse(MessageConstant.FALSE,message,employeeOnboardingRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
 			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeOnboardingNewResponse);
 			jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in updateEmplOnboardingNew====="+e);
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
	    @RequestMapping(value = "/get/empOnboardingByManagerId",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> empOnboardingByManagerId(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
	    logger.info("inside empOnboardingByManagerId...+++");	    	
	    	
	    	String message = "";
	    	List<EmployeeOnboardingEntity> response=null;
	    	EmployeeOnboardingManagerIdResponse employeeOnboardingManagerIdResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				EmployeeOnboardingRequest employeeOnboardingRequest= EncryptionDecriptionUtil.convertFromJson(decript, EmployeeOnboardingRequest.class);
				
				response=employeeOnboardingService.getEmployeeDetailsByManagerId(employeeOnboardingRequest.getManagerId());
	    		if(response!=null && response.size()>0) {
	    			employeeOnboardingManagerIdResponse=new EmployeeOnboardingManagerIdResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeOnboardingManagerIdResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			employeeOnboardingManagerIdResponse=new EmployeeOnboardingManagerIdResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeOnboardingManagerIdResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		//e.printStackTrace();
	    		logger.error("error in empOnboardingByManagerId====="+e);
	    		//message=e.getMessage();
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		employeeOnboardingManagerIdResponse=new EmployeeOnboardingManagerIdResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeOnboardingManagerIdResponse);
    			jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in empOnboardingByManagerId====="+e);
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
	    @RequestMapping(value = "/update/emplOnboardingStatus",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> emplOnboardingStatus(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
	    logger.info("inside emplOnboardingStatus+++");    	
	    
	    	String message = "";
	    	String message1 = "";
	    	UpdateEmployeeStatusRequest response=null;
	    	UpdateEmployeeStatusRequest employeeOnboardingRequest=null;
	    	UpdateEmployeeResponse employeeOnboardingResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				employeeOnboardingRequest= EncryptionDecriptionUtil.convertFromJson(decript, UpdateEmployeeStatusRequest.class);
				response=employeeOnboardingService.updateEmployeeStatus(employeeOnboardingRequest);
				message1=response.getResponse()==null?MessageConstant.PROFILE_FAILED:response.getResponse();
	    		if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			employeeOnboardingResponse=new UpdateEmployeeResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS_UPDATE,employeeOnboardingRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeOnboardingResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			employeeOnboardingResponse=new UpdateEmployeeResponse(MessageConstant.FALSE,message1,employeeOnboardingRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeOnboardingResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		//e.printStackTrace();
	    		logger.error("error in saveProfileDetails====="+e);
	    		//message=e.getMessage();
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		employeeOnboardingResponse=new UpdateEmployeeResponse(MessageConstant.FALSE,message,employeeOnboardingRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
 			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeOnboardingResponse);
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
	    @RequestMapping(value = "/add/saveEmplOnboardingRepute",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> saveEmplOnboardingRepute(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
	    logger.info("inside saveEmplOnboardingRepute+++");    	
	    
	    	String message = "";
	    	String message1 = "";
	    	EmployeeOnboardingRequest response=null;
	    	EmployeeOnboardingReputeRequest employeeOnboardingRequest1=null;
	    	EmployeeOnboardingRequest employeeOnboardingRequest=new EmployeeOnboardingRequest();
	    	EmployeeOnboardingResponse employeeOnboardingResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				employeeOnboardingRequest1= EncryptionDecriptionUtil.convertFromJson(decript, EmployeeOnboardingReputeRequest.class);
				response=employeeOnboardingService.saveEmployeeDetailsRepute(employeeOnboardingRequest1);
				CopyUtility.copyProperties(employeeOnboardingRequest1, employeeOnboardingRequest);
				
				message1=response.getResponse()==null?MessageConstant.PROFILE_FAILED:response.getResponse();
	    		if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			employeeOnboardingResponse=new EmployeeOnboardingResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS_UPDATE,employeeOnboardingRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeOnboardingResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			employeeOnboardingResponse=new EmployeeOnboardingResponse(MessageConstant.FALSE,message1,employeeOnboardingRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeOnboardingResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		//e.printStackTrace();
	    		logger.error("error in saveProfileDetails====="+e);
	    		//message=e.getMessage();
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		employeeOnboardingResponse=new EmployeeOnboardingResponse(MessageConstant.FALSE,message,employeeOnboardingRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
 			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeOnboardingResponse);
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
	    @RequestMapping(value = "/update/updateEmplOnboardingRepute",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> updateEmplOnboardingRepute(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
	    logger.info("inside saveEmplOnboardingRepute+++");    	
	    
	    	String message = "";
	    	String message1 = "";
	    	EmployeeOnboardingRequest response=null;
	    	EmployeeOnboardingReputeUpdateRequest employeeOnboardingRequest1=null;
	    	EmployeeOnboardingRequest employeeOnboardingRequest=new EmployeeOnboardingRequest();
	    	EmployeeOnboardingResponse employeeOnboardingResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				employeeOnboardingRequest1= EncryptionDecriptionUtil.convertFromJson(decript, EmployeeOnboardingReputeUpdateRequest.class);
				response=employeeOnboardingService.updateEmployeeDetailsRepute(employeeOnboardingRequest1);
				CopyUtility.copyProperties(employeeOnboardingRequest1, employeeOnboardingRequest);
				
				message1=response.getResponse()==null?MessageConstant.PROFILE_FAILED:response.getResponse();
	    		if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			employeeOnboardingResponse=new EmployeeOnboardingResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS_UPDATE,employeeOnboardingRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeOnboardingResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			employeeOnboardingResponse=new EmployeeOnboardingResponse(MessageConstant.FALSE,message1,employeeOnboardingRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeOnboardingResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		//e.printStackTrace();
	    		logger.error("error in updateEmplOnboardingRepute====="+e);
	    		//message=e.getMessage();
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		employeeOnboardingResponse=new EmployeeOnboardingResponse(MessageConstant.FALSE,message,employeeOnboardingRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeOnboardingResponse);
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
	    @RequestMapping(value = "/get/getEmployeeDriver",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> getEmployeeDriver(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
	    logger.info("inside getEmployeeDriver+++");    	
	    
	    	String message = "";
	    	String message1 = "";
	    	List<EmployeeOnboardingDto> response=null;
	    	EmployeeOnboardingReputeUpdateRequest employeeOnboardingRequest1=null;
	    	EmployeeOnboardingRequest employeeOnboardingRequest=new EmployeeOnboardingRequest();
	    	EmployeeOnboardingDriverResponse employeeOnboardingResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				EmployeeOnboardingDriverRequest empDriverRequest= EncryptionDecriptionUtil.convertFromJson(decript, EmployeeOnboardingDriverRequest.class);
				response=employeeOnboardingService.getDriverEmployeeList(empDriverRequest);
				
	    		if(response!=null && response.size()>0) {
	    			employeeOnboardingResponse=new EmployeeOnboardingDriverResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS_UPDATE,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeOnboardingResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			employeeOnboardingResponse=new EmployeeOnboardingDriverResponse(MessageConstant.FALSE,message1,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeOnboardingResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		//e.printStackTrace();
	    		logger.error("error in getEmployeeDriver====="+e);
	    		//message=e.getMessage();
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		employeeOnboardingResponse=new EmployeeOnboardingDriverResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeeOnboardingResponse);
			jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				// TODO: handle exception
			}
	    return ResponseEntity.ok(jsonEncriptObject);
	    }
}
