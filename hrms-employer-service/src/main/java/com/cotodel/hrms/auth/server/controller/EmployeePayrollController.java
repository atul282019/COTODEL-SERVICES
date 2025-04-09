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

import com.cotodel.hrms.auth.server.dto.EmployeePayrollRequest;
import com.cotodel.hrms.auth.server.dto.EmployeePayrollResponse;
import com.cotodel.hrms.auth.server.dto.EmployeePayrollTaxRequest;
import com.cotodel.hrms.auth.server.dto.EmployeePayrollTaxResponse;
import com.cotodel.hrms.auth.server.dto.EmployerDetailsResponse;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.model.EmployeeProfileEntity;
import com.cotodel.hrms.auth.server.multi.datasource.SetDatabaseTenent;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.EmployeePayrollService;
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
public class EmployeePayrollController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeePayrollController.class);
	
	@Autowired
	EmployeePayrollService employeePayrollService;
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
//	 @Operation(summary = "This API will provide the Save User Details ", security = {
//	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
//	    @ApiResponses(value = {
//	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
//	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
//	    @RequestMapping(value = "/save/payrollDetails",produces = {"application/json"}, 
//	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
//	    public ResponseEntity<Object> payrollDetails(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
//	    logger.info("inside payrollDetails+++");	    	
//	    	
//	    	String message = "";
//	    	EmployeePayrollRequest response=null;
//	    	EmployeePayrollRequest empolyeeRequest=null;
//	    	EmployeePayrollResponse employeePayrollResponse=null;
//	    	try {	    		
//	    		String companyId = request.getHeader("companyId");
//				SetDatabaseTenent.setDataSource(companyId);
//				
//				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
//				empolyeeRequest= EncryptionDecriptionUtil.convertFromJson(decript, EmployeePayrollRequest.class);
//				
//				response=employeePayrollService.saveEmployeePayrollDetails(empolyeeRequest);
//	    		if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
//	    			employeePayrollResponse=new EmployeePayrollResponse(true,MessageConstant.PROFILE_SUCCESS,empolyeeRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
//	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeePayrollResponse);
//	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
//	    			return ResponseEntity.ok(jsonEncriptObject);
//	    		}else {
//	    			employeePayrollResponse=new EmployeePayrollResponse(false,MessageConstant.PROFILE_FAILED,empolyeeRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
//	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeePayrollResponse);
//	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
//	    			return ResponseEntity.ok(jsonEncriptObject);
//	    		}
//	    	}catch (Exception e) {				
//	    		//e.printStackTrace();
//	    		logger.error("error in payrollDetails====="+e);
//	    		//message=e.getMessage();
//			}
//	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
//	    	try {
//	    		employeePayrollResponse=new EmployeePayrollResponse(false,MessageConstant.PROFILE_FAILED,empolyeeRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
//    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeePayrollResponse);
//    			jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
//			} catch (Exception e) {
//				logger.error("error in payrollDetails====="+e);
//			}
//    	    return ResponseEntity.ok(jsonEncriptObject);
//	    }
	 
//	 @Operation(summary = "This API will provide the Save User Details ", security = {
//	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
//	    @ApiResponses(value = {
//	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
//	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
//	    @RequestMapping(value = "/add/savePayrollTaxDetails",produces = {"application/json"}, 
//	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
//	    public ResponseEntity<Object> savePayrollTaxDetails(HttpServletRequest request,@Valid @RequestBody EmployeePayrollTaxRequest empolyeeRequest) {
//	    logger.info("inside savePayrollTaxDetails");	    	
//	    	
//	    	String message = "";
//	    	EmployeePayrollTaxRequest response=null;
//	    	try {	    		
//	    		String companyId = request.getHeader("companyId");
//				SetDatabaseTenent.setDataSource(companyId);
//				response=employeePayrollService.saveEmployeePayrollTaxDetails(empolyeeRequest);
//	    		if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
//	    			return ResponseEntity.ok(new EmployeePayrollTaxResponse(true,MessageConstant.PROFILE_SUCCESS,empolyeeRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
//	    		}else {
//	    			return ResponseEntity.ok(new EmployeePayrollTaxResponse(false,MessageConstant.PROFILE_FAILED,empolyeeRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
//	    		}
//	    	}catch (Exception e) {				
//	    		//e.printStackTrace();
//	    		logger.error("error in savePayrollTaxDetails====="+e);
//	    		//message=e.getMessage();
//			}
//	        
//	        return ResponseEntity.ok(new EmployeePayrollTaxResponse(false,message,empolyeeRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
//	    }
//	 @Operation(summary = "This API will provide the Save User Details ", security = {
//	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
//	    @ApiResponses(value = {
//	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
//	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
//	    @RequestMapping(value = "/save/payrollDetailsNew",produces = {"application/json"}, 
//	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
//	    public ResponseEntity<Object> payrollDetailsNew(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
//	    logger.info("inside payrollDetailsNew+++");	    	
//	    	
//	    	String message = "";
//	    	EmployeePayrollRequest response=null;
//	    	EmployeePayrollRequest empolyeeRequest=null;
//	    	EmployeePayrollResponse employeePayrollResponse;
//	    	try {	    		
//	    		String companyId = request.getHeader("companyId");
//				SetDatabaseTenent.setDataSource(companyId);
//				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
//				empolyeeRequest= EncryptionDecriptionUtil.convertFromJson(decript, EmployeePayrollRequest.class);
//				
//				response=employeePayrollService.saveEmployeePayrollDetailsNew(empolyeeRequest);
//	    		if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
//	    			employeePayrollResponse=new EmployeePayrollResponse(true,MessageConstant.PROFILE_SUCCESS,empolyeeRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
//	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeePayrollResponse);
//	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
//	    			return ResponseEntity.ok(jsonEncriptObject);
//	    		}else {
//	    			employeePayrollResponse=new EmployeePayrollResponse(false,MessageConstant.PROFILE_FAILED,empolyeeRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
//	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeePayrollResponse);
//	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
//	    			return ResponseEntity.ok(jsonEncriptObject);
//	    		}
//	    	}catch (Exception e) {				
//	    		//e.printStackTrace();
//	    		logger.error("error in payrollDetailsNew====="+e);
//	    		//message=e.getMessage();
//			}
//	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
//	    	try {
//	    		employeePayrollResponse=new EmployeePayrollResponse(false,MessageConstant.PROFILE_FAILED,empolyeeRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
//    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employeePayrollResponse);
//    			jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//    	    return ResponseEntity.ok(jsonEncriptObject);
//	    }
	 
//	 @Operation(summary = "This API will provide the Save User Details ", security = {
//	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
//	    @ApiResponses(value = {
//	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
//	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
//	    @RequestMapping(value = "/get/payrollDetails",produces = {"application/json"}, 
//	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
//	    public ResponseEntity<Object> getPayrollDetails(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
//	    
//		 logger.info("inside getPayrollDetails+++");	    	
//	    	
//	    	String message = "";
//	    	EmployeeProfileEntity response=null;
//	    	EmployerDetailsResponse employerDetailsResponse;
//	    	try {	    		
//	    		String companyId = request.getHeader("companyId");
//				SetDatabaseTenent.setDataSource(companyId);
//				
//				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
//				EmployeePayrollRequest empolyeeRequest= EncryptionDecriptionUtil.convertFromJson(decript, EmployeePayrollRequest.class);
//				
//				response=employeePayrollService.getEmployerPayRollDetail(empolyeeRequest.getEmployerId());
//	    		if(response!=null) {
//	    			employerDetailsResponse=new EmployerDetailsResponse(true,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
//	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employerDetailsResponse);
//	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
//	    			return ResponseEntity.ok(jsonEncriptObject);
//	    		}else {
//	    			employerDetailsResponse=new EmployerDetailsResponse(false,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
//	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employerDetailsResponse);
//	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
//	    			return ResponseEntity.ok(jsonEncriptObject);
//	    		}
//	    	}catch (Exception e) {				
//	    		//e.printStackTrace();
//	    		logger.error("error in payrollDetails====="+e);
//	    		//message=e.getMessage();
//			}
//	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
//	    	try {
//	    		employerDetailsResponse=new EmployerDetailsResponse(false,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
//    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(employerDetailsResponse);
//    			jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
//			} catch (Exception e) {
//				logger.error("error in payrollDetails====="+e);
//			}
//    	    return ResponseEntity.ok(jsonEncriptObject);
//	    }
	
}
