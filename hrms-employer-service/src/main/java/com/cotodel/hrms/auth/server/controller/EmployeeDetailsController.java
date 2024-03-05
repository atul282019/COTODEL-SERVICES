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

import com.cotodel.hrms.auth.server.dto.CertificateGetResponse;
import com.cotodel.hrms.auth.server.dto.CertificateRequest;
import com.cotodel.hrms.auth.server.dto.CertificateResponse;
import com.cotodel.hrms.auth.server.dto.EmployeeDetailsRequest;
import com.cotodel.hrms.auth.server.dto.EmployeeDetailsResponse;
import com.cotodel.hrms.auth.server.dto.EmployeeFamilyDetailsRequest;
import com.cotodel.hrms.auth.server.dto.EmployeeFamilyDetailsResponse;
import com.cotodel.hrms.auth.server.dto.EmployeeFamilyGetDetailsResponse;
import com.cotodel.hrms.auth.server.dto.EmployeeGetDetailsResponse;
import com.cotodel.hrms.auth.server.dto.ExperienceGetResponse;
import com.cotodel.hrms.auth.server.dto.ExperienceRequest;
import com.cotodel.hrms.auth.server.dto.ExperienceResponse;
import com.cotodel.hrms.auth.server.dto.ProjectGetResponse;
import com.cotodel.hrms.auth.server.dto.ProjectRequest;
import com.cotodel.hrms.auth.server.dto.ProjectResponse;
import com.cotodel.hrms.auth.server.dto.QualificationGetResponse;
import com.cotodel.hrms.auth.server.dto.QualificationRequest;
import com.cotodel.hrms.auth.server.dto.QualificationResponse;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.model.CertificateEntity;
import com.cotodel.hrms.auth.server.model.EmployeeDetailsEntity;
import com.cotodel.hrms.auth.server.model.EmployeeFamilyDetailEntity;
import com.cotodel.hrms.auth.server.model.ExperienceEntity;
import com.cotodel.hrms.auth.server.model.ProjectEntity;
import com.cotodel.hrms.auth.server.model.QualificationEntity;
import com.cotodel.hrms.auth.server.multi.datasource.SetDatabaseTenent;
import com.cotodel.hrms.auth.server.service.EmployeeDetailsService;
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
public class EmployeeDetailsController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeDetailsController.class);
	
	@Autowired
	EmployeeDetailsService employeeDetailsService;
	
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/add/empDetails",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> saveEmpDetails(HttpServletRequest request,@Valid @RequestBody EmployeeDetailsRequest empolyeeRequest) {
	    logger.info("inside saveDetails");	    	
	    	
	    
	    	String message = "";
	    	EmployeeDetailsRequest response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=employeeDetailsService.saveEmpDetails(empolyeeRequest);
				
	    		if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			return ResponseEntity.ok(new EmployeeDetailsResponse(true,MessageConstant.PROFILE_SUCCESS,empolyeeRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new EmployeeDetailsResponse(false,MessageConstant.PROFILE_FAILED,empolyeeRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		e.printStackTrace();
	    		logger.error("error in saveProfileDetails====="+e);
	    		message=e.getMessage();
			}
	        
	        return ResponseEntity.ok(new EmployeeDetailsResponse(false,message,empolyeeRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/empAllDetails",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> getEmpAllDetails(HttpServletRequest request,@Valid @RequestBody EmployeeDetailsRequest empolyeeRequest) {
	    logger.info("inside empDetails.....");	    	
	    	
	    
	    	String message = "";
	    	List<EmployeeDetailsEntity> response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=employeeDetailsService.getEmpDetailsList(empolyeeRequest.getEmployerId());
	    		if(response!=null && response.size()>0) {
	    			return ResponseEntity.ok(new EmployeeGetDetailsResponse(true,MessageConstant.RESPONSE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new EmployeeGetDetailsResponse(false,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		e.printStackTrace();
	    		logger.error("error in saveProfileDetails====="+e);
	    		message=e.getMessage();
			}
	        
	        return ResponseEntity.ok(new EmployeeGetDetailsResponse(false,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }

	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/add/empFamilyDetails",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> saveEmpFamilyDetails(HttpServletRequest request,@Valid @RequestBody EmployeeFamilyDetailsRequest empolyeeRequest) {
	    logger.info("inside saveDetails");	    	
	    	
	    
	    	String message = "";
	    	EmployeeFamilyDetailsRequest response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=employeeDetailsService.saveEmpFamilyDetails(empolyeeRequest);
				
	    		if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			return ResponseEntity.ok(new EmployeeFamilyDetailsResponse(true,MessageConstant.PROFILE_SUCCESS,empolyeeRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new EmployeeFamilyDetailsResponse(false,MessageConstant.PROFILE_FAILED,empolyeeRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		e.printStackTrace();
	    		logger.error("error in saveProfileDetails====="+e);
	    		message=e.getMessage();
			}
	        
	        return ResponseEntity.ok(new EmployeeFamilyDetailsResponse(false,message,empolyeeRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/empFamilyAllDetails",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> getEmpFamilyAllDetails(HttpServletRequest request,@Valid @RequestBody EmployeeFamilyDetailsRequest empolyeeRequest) {
	    logger.info("inside empDetails.....");	    	
	    	
	    
	    	String message = "";
	    	List<EmployeeFamilyDetailEntity> response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=employeeDetailsService.getEmpFamilyDetailsList(empolyeeRequest.getEmployeeId());
	    		if(response!=null && response.size()>0) {
	    			return ResponseEntity.ok(new EmployeeFamilyGetDetailsResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new EmployeeFamilyGetDetailsResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		e.printStackTrace();
	    		logger.error("error in saveProfileDetails====="+e);
	    		message=e.getMessage();
			}
	        
	        return ResponseEntity.ok(new EmployeeFamilyGetDetailsResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }

	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/add/saveQualification",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> saveQualification(HttpServletRequest request,@Valid @RequestBody QualificationRequest qualificationRequest) {
	    logger.info("inside saveQualification...");	    	
	    
	    	String message = "";
	    	QualificationRequest response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=employeeDetailsService.saveQualification(qualificationRequest);
				
	    		if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			return ResponseEntity.ok(new QualificationResponse(true,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new QualificationResponse(false,MessageConstant.PROFILE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		e.printStackTrace();
	    		logger.error("error in saveProfileDetails====="+e);
	    		message=e.getMessage();
			}
	        
	        return ResponseEntity.ok(new QualificationResponse(false,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/getQualification",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> getQualification(HttpServletRequest request,@Valid @RequestBody QualificationRequest qualificationRequest) {
	    logger.info("inside getQualification.....");	    	
	    	
	    
	    	String message = "";
	    	List<QualificationEntity> response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=employeeDetailsService.getQualificationList(qualificationRequest.getEmployeeId());
	    		if(response!=null && response.size()>0) {
	    			return ResponseEntity.ok(new QualificationGetResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new QualificationGetResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		e.printStackTrace();
	    		logger.error("error in getQualification====="+e);
	    		message=e.getMessage();
			}
	        
	        return ResponseEntity.ok(new QualificationGetResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/add/saveExperience",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> saveExperience(HttpServletRequest request,@Valid @RequestBody ExperienceRequest experienceRequest) {
	    logger.info("inside saveQualification...");	    	
	    
	    	String message = "";
	    	ExperienceRequest response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=employeeDetailsService.saveExperience(experienceRequest);
				
	    		if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			return ResponseEntity.ok(new ExperienceResponse(true,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new ExperienceResponse(false,MessageConstant.PROFILE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		e.printStackTrace();
	    		logger.error("error in saveProfileDetails====="+e);
	    		message=e.getMessage();
			}
	        
	        return ResponseEntity.ok(new ExperienceResponse(false,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/getExperience",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> getExperience(HttpServletRequest request,@Valid @RequestBody ExperienceRequest experienceRequest) {
	    logger.info("inside getQualification.....");	    	
	    	
	    
	    	String message = "";
	    	List<ExperienceEntity> response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=employeeDetailsService.getExperienceList(experienceRequest.getEmployeeId());
	    		if(response!=null && response.size()>0) {
	    			return ResponseEntity.ok(new ExperienceGetResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new ExperienceGetResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		e.printStackTrace();
	    		logger.error("error in getQualification====="+e);
	    		message=e.getMessage();
			}
	        
	        return ResponseEntity.ok(new ExperienceGetResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/add/saveCertificate",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> saveCertificate(HttpServletRequest request,@Valid @RequestBody CertificateRequest certificateRequest) {
	    logger.info("inside saveQualification...");	    	
	    
	    	String message = "";
	    	CertificateRequest response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=employeeDetailsService.saveCertificate(certificateRequest);
				
	    		if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			return ResponseEntity.ok(new CertificateResponse(true,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new CertificateResponse(false,MessageConstant.PROFILE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		e.printStackTrace();
	    		logger.error("error in saveProfileDetails====="+e);
	    		message=e.getMessage();
			}
	        
	        return ResponseEntity.ok(new CertificateResponse(false,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/getCertificate",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> getCertificate(HttpServletRequest request,@Valid @RequestBody ExperienceRequest experienceRequest) {
	    logger.info("inside getQualification.....");	    	
	    	
	    
	    	String message = "";
	    	List<CertificateEntity> response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=employeeDetailsService.getCertificateList(experienceRequest.getEmployeeId());
	    		if(response!=null && response.size()>0) {
	    			return ResponseEntity.ok(new CertificateGetResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new CertificateGetResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		e.printStackTrace();
	    		logger.error("error in getQualification====="+e);
	    		message=e.getMessage();
			}
	        
	        return ResponseEntity.ok(new CertificateGetResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/add/saveProject",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> saveProject(HttpServletRequest request,@Valid @RequestBody ProjectRequest projectRequest) {
	    logger.info("inside saveQualification...");	    	
	    
	    	String message = "";
	    	ProjectRequest response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=employeeDetailsService.saveProject(projectRequest);
				
	    		if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			return ResponseEntity.ok(new ProjectResponse(true,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new ProjectResponse(false,MessageConstant.PROFILE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		e.printStackTrace();
	    		logger.error("error in saveProfileDetails====="+e);
	    		message=e.getMessage();
			}
	        
	        return ResponseEntity.ok(new ProjectResponse(false,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/getProject",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> getProject(HttpServletRequest request,@Valid @RequestBody ProjectRequest projectRequest) {
	    logger.info("inside getProject.....");	    	
	    	
	    
	    	String message = "";
	    	List<ProjectEntity> response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=employeeDetailsService.getProjectList(projectRequest.getEmployeeId());
	    		if(response!=null && response.size()>0) {
	    			return ResponseEntity.ok(new ProjectGetResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new ProjectGetResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		e.printStackTrace();
	    		logger.error("error in getQualification====="+e);
	    		message=e.getMessage();
			}
	        
	        return ResponseEntity.ok(new ProjectGetResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }


	 
}
