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

import com.cotodel.hrms.auth.server.dto.AdvanceTravelAllRequest;
import com.cotodel.hrms.auth.server.dto.AdvanceTravelAllResponse;
import com.cotodel.hrms.auth.server.dto.AdvanceTravelAllUpdateRequest;
import com.cotodel.hrms.auth.server.dto.AdvanceTravelAllUpdateResponse;
import com.cotodel.hrms.auth.server.dto.AdvanceTravelApprovalResponse;
import com.cotodel.hrms.auth.server.dto.AdvanceTravelByIdResponse;
import com.cotodel.hrms.auth.server.dto.AdvanceTravelCashRequest;
import com.cotodel.hrms.auth.server.dto.AdvanceTravelCashResponse;
import com.cotodel.hrms.auth.server.dto.AdvanceTravelDeleteResponse;
import com.cotodel.hrms.auth.server.dto.AdvanceTravelDto;
import com.cotodel.hrms.auth.server.dto.AdvanceTravelListDtoResponse;
import com.cotodel.hrms.auth.server.dto.AdvanceTravelListResponse;
import com.cotodel.hrms.auth.server.dto.AdvanceTravelRequest;
import com.cotodel.hrms.auth.server.dto.AdvanceTravelResponse;
import com.cotodel.hrms.auth.server.dto.ApprovalTravelReimbursement;
import com.cotodel.hrms.auth.server.dto.ApprovalTravelReimbursementById;
import com.cotodel.hrms.auth.server.dto.ExpanceTravelAdvance;
import com.cotodel.hrms.auth.server.dto.ExpenseTravelAdvanceListResponse;
import com.cotodel.hrms.auth.server.dto.ExpenseTravelAdvanceNameListResponse;
import com.cotodel.hrms.auth.server.dto.ExpenseTravelAdvanceRequest;
import com.cotodel.hrms.auth.server.dto.ExpenseTravelAdvanceResponse;
import com.cotodel.hrms.auth.server.dto.TravelGetReimbursement;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.model.AdvanceRequestSettingEntity;
import com.cotodel.hrms.auth.server.model.AdvanceTravelRequestEntity;
import com.cotodel.hrms.auth.server.multi.datasource.SetDatabaseTenent;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.ExpenseTravelAdvanceService;
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
public class ExpenseTravelController {
	
	private static final Logger logger = LoggerFactory.getLogger(ExpenseTravelController.class);
	
	@Autowired
	ExpenseTravelAdvanceService expenseTravelAdvanceService;
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/add/expanceTravelAdvanceRequest",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> expanceTravelAdvanceRequest(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside expanceTravelAdvanceRequest+++");	    	
	    	
	    
	    	String message = "";
	    	ExpenseTravelAdvanceRequest response=null;
	    	ExpenseTravelAdvanceResponse expenseTravelAdvanceResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ExpenseTravelAdvanceRequest expenseTravelAdvanceRequest= EncryptionDecriptionUtil.convertFromJson(decript, ExpenseTravelAdvanceRequest.class);
				
				response=expenseTravelAdvanceService.saveExpenseTravelAdvenceDetails(expenseTravelAdvanceRequest);
	    		if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			expenseTravelAdvanceResponse=new ExpenseTravelAdvanceResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(expenseTravelAdvanceResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			expenseTravelAdvanceResponse=new ExpenseTravelAdvanceResponse(MessageConstant.FALSE,MessageConstant.PROFILE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(expenseTravelAdvanceResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		//e.printStackTrace();
	    		logger.error("error in expenseCategoryBandDetails====="+e);
	    		//message=e.getMessage();
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		expenseTravelAdvanceResponse=new ExpenseTravelAdvanceResponse(MessageConstant.FALSE,MessageConstant.PROFILE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(expenseTravelAdvanceResponse);
    			jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				// TODO: handle exception
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
//	    @RequestMapping(value = "/get/expanceTravelAdvanceList",produces = {"application/json"}, 
//	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
//	    public ResponseEntity<Object> getExpenseCategoryBandDetails(HttpServletRequest request,@Valid @RequestBody ExpenseTravelAdvanceRequest expenseTravelAdvanceRequest) {
//		 
//	    logger.info("inside expanceTravelAdvanceList");	    	
//	    	
//	    
//	    	String message = "";
//	    	AdvanceRequestSettingEntity response=null;
//	    	try {	    		
//	    		String companyId = request.getHeader("companyId");
//				SetDatabaseTenent.setDataSource(companyId);
//				
//				response=expenseTravelAdvanceService.getExpenseTravelAdvenceDetails(expenseTravelAdvanceRequest.getEmployerId());
//	    		if(response!=null) {
//	    			return ResponseEntity.ok(new ExpenseTravelAdvanceListResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
//	    		}else {
//	    			return ResponseEntity.ok(new ExpenseTravelAdvanceListResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
//	    		}
//	    	}catch (Exception e) {				
//	    		//e.printStackTrace();
//	    		logger.error("error in expenseCategoryBandDetails====="+e);
//	    		//message=e.getMessage();
//			}
//	        
//	        return ResponseEntity.ok(new ExpenseTravelAdvanceListResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
//	    }
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/expanceTravelAdvance",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> getExpanceTravelAdvance(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside expanceTravelAdvance+++");	    	
	    	
	    
	    	String message = "";
	    	ExpanceTravelAdvance response=null;
	    	ExpenseTravelAdvanceNameListResponse erAdvanceNameListResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ExpenseTravelAdvanceRequest expenseTravelAdvanceRequest= EncryptionDecriptionUtil.convertFromJson(decript, ExpenseTravelAdvanceRequest.class);
				
				response=expenseTravelAdvanceService.getExpenseTravelAdvence(expenseTravelAdvanceRequest.getEmployerId());
	    		if(response!=null) {
	    			erAdvanceNameListResponse=new ExpenseTravelAdvanceNameListResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erAdvanceNameListResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			erAdvanceNameListResponse=new ExpenseTravelAdvanceNameListResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erAdvanceNameListResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		//e.printStackTrace();
	    		logger.error("error in expenseCategoryBandDetails====="+e);
	    		//message=e.getMessage();
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		erAdvanceNameListResponse=new ExpenseTravelAdvanceNameListResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    		String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erAdvanceNameListResponse);
	    		jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in expenseCategoryBandDetails====="+e);
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
	    @RequestMapping(value = "/add/advanceTravelRequestCash",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> advanceTravelRequestCash(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside AdvanceTravelRequest+++");	    	
	    	
	    
	    	String message = "";
	    	AdvanceTravelCashRequest response=null;
	    	AdvanceTravelCashResponse advanceTravelCashResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				AdvanceTravelCashRequest advanceTravelRequest= EncryptionDecriptionUtil.convertFromJson(decript, AdvanceTravelCashRequest.class);
				
				response=expenseTravelAdvanceService.saveAdvenceTravelRequestCashDetails(advanceTravelRequest);
	    		if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			advanceTravelCashResponse=new AdvanceTravelCashResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(advanceTravelCashResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			advanceTravelCashResponse=new AdvanceTravelCashResponse(MessageConstant.FALSE,MessageConstant.PROFILE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(advanceTravelCashResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		//e.printStackTrace();
	    		logger.error("error in advanceTravelRequest====="+e);
	    		//message=e.getMessage();
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		advanceTravelCashResponse=new AdvanceTravelCashResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(advanceTravelCashResponse);
    			jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in advanceTravelRequest====="+e);
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
	    @RequestMapping(value = "/add/advanceTravelRequest",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> advanceTravelRequest(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside AdvanceTravelRequest+++");	    	
	    	
	    
	    	String message = "";
	    	AdvanceTravelRequest response=null;
	    	AdvanceTravelResponse advanceTravelResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				AdvanceTravelRequest advanceTravelRequest= EncryptionDecriptionUtil.convertFromJson(decript, AdvanceTravelRequest.class);
				
				response=expenseTravelAdvanceService.saveAdvenceTravelRequestDetails(advanceTravelRequest);
	    		if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			advanceTravelResponse=new AdvanceTravelResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(advanceTravelResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			advanceTravelResponse=new AdvanceTravelResponse(MessageConstant.FALSE,MessageConstant.PROFILE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(advanceTravelResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		//e.printStackTrace();
	    		logger.error("error in advanceTravelRequest====="+e);
	    		//message=e.getMessage();
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		advanceTravelResponse=new AdvanceTravelResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(advanceTravelResponse);
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
	    @RequestMapping(value = "/get/advanceTravelRequest",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> advanceTravelRequestByEmplrId(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside get/advanceTravelRequest+++");	    	
	    	
	    
	    	String message = "";
	    	List<AdvanceTravelDto> response=null;
	    	AdvanceTravelListDtoResponse advanceTravelListResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				AdvanceTravelCashRequest advanceTravelRequest= EncryptionDecriptionUtil.convertFromJson(decript, AdvanceTravelCashRequest.class);
				
				response=expenseTravelAdvanceService.getAdvenceTravelListByEmployerId(advanceTravelRequest.getEmployerId(),advanceTravelRequest.getEmployeeId());
	    		if(response!=null && response.size()>0) {
	    			advanceTravelListResponse=new AdvanceTravelListDtoResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(advanceTravelListResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			advanceTravelListResponse=new AdvanceTravelListDtoResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(advanceTravelListResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		//e.printStackTrace();
	    		logger.error("error in get/advanceTravelRequest====="+e);
	    		//message=e.getMessage();
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		advanceTravelListResponse=new AdvanceTravelListDtoResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(advanceTravelListResponse);
    			jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in get/advanceTravelRequest====="+e);
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
//	    @RequestMapping(value = "/get/advanceTravelRequestById",produces = {"application/json"}, 
//	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
//	    public ResponseEntity<Object> advanceTravelRequestById(HttpServletRequest request,@Valid @RequestBody AdvanceTravelRequest advanceTravelRequest) {
//		 
//	    logger.info("inside get/advanceTravelRequest+++");	    	
//	    	
//	    
//	    	String message = "";
//	    	List<AdvanceTravelRequestEntity> response=null;
//	    	try {	    		
//	    		String companyId = request.getHeader("companyId");
//				SetDatabaseTenent.setDataSource(companyId);
//				
//				response=expenseTravelAdvanceService.getAdvenceTravelListByEmployerId(advanceTravelRequest.getEmployerId(),advanceTravelRequest.getEmployeeId());
//	    		if(response!=null && response.size()>0) {
//	    			return ResponseEntity.ok(new AdvanceTravelListResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
//	    		}else {
//	    			return ResponseEntity.ok(new AdvanceTravelListResponse(MessageConstant.FALSE,MessageConstant.PROFILE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
//	    		}
//	    	}catch (Exception e) {				
//	    		//e.printStackTrace();
//	    		logger.error("error in get/advanceTravelRequest====="+e);
//	    		//message=e.getMessage();
//			}
//	        
//	        return ResponseEntity.ok(new AdvanceTravelListResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
//	    }
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/advanceTravelRequestStatus",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> advanceTravelRequestStatus(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside get/advanceTravelRequestStatus+++");	    	
	    	
	    
	    	String message = "";
	    	AdvanceTravelAllRequest response=null;
	    	AdvanceTravelAllResponse advanceTravelAllResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				AdvanceTravelCashRequest advanceTravelRequest= EncryptionDecriptionUtil.convertFromJson(decript, AdvanceTravelCashRequest.class);
				
				response=expenseTravelAdvanceService.getAdvenceTravelListByStatus(advanceTravelRequest.getEmployerId(),advanceTravelRequest.getEmployeeId(),advanceTravelRequest.getStatus());
	    		if(response!=null) {
	    			advanceTravelAllResponse=new AdvanceTravelAllResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(advanceTravelAllResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			advanceTravelAllResponse=new AdvanceTravelAllResponse(MessageConstant.FALSE,MessageConstant.PROFILE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(advanceTravelAllResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		//e.printStackTrace();
	    		logger.error("error in get/advanceTravelRequestStatus====="+e);
	    		//message=e.getMessage();
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		advanceTravelAllResponse=new AdvanceTravelAllResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(advanceTravelAllResponse);
    			jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in get/advanceTravelRequestStatus====="+e);
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
	    @RequestMapping(value = "/update/advanceTravelRequestUpdate",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> advanceTravelRequestUpdate(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside update/advanceTravelRequestUpdate+++");	    	
	    	
	    
	    	String message = "";
	    	AdvanceTravelAllUpdateRequest response=null;
	    	AdvanceTravelAllUpdateResponse advanceTravelAllUpdateResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				AdvanceTravelAllUpdateRequest advanceTravelAllUpdateRequest= EncryptionDecriptionUtil.convertFromJson(decript, AdvanceTravelAllUpdateRequest.class);
				
				response=expenseTravelAdvanceService.updateAdvenceTravelList(advanceTravelAllUpdateRequest);
	    		if(response!=null) {
	    			advanceTravelAllUpdateResponse=new AdvanceTravelAllUpdateResponse(MessageConstant.TRUE,MessageConstant.PROFILE_UPDATE,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(advanceTravelAllUpdateResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			advanceTravelAllUpdateResponse=new AdvanceTravelAllUpdateResponse(MessageConstant.FALSE,MessageConstant.PROFILE_FAILED_UPDATE,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(advanceTravelAllUpdateResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		//e.printStackTrace();
	    		logger.error("error in update/advanceTravelRequestUpdate====="+e);
	    		//message=e.getMessage();
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		advanceTravelAllUpdateResponse=new AdvanceTravelAllUpdateResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(advanceTravelAllUpdateResponse);
    			jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in update/advanceTravelRequestUpdate====="+e);
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
	    @RequestMapping(value = "/delete/advanceTravelRequestDelete",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> advanceTravelRequestDelete(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside delete/advanceTravelRequestDelete+++");	    	
	    	
	    	String message = "";
	    	String response=null;
	    	AdvanceTravelDeleteResponse advanceTravelDeleteResponse;
	    	try {	    		
	    		String companyId = request.getHeader(" ");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				TravelGetReimbursement advanceTravelRequest= EncryptionDecriptionUtil.convertFromJson(decript, TravelGetReimbursement.class);
				
				response=expenseTravelAdvanceService.deleteAdvenceTravelById(advanceTravelRequest.getId());
	    		if(response!=null && response.equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			advanceTravelDeleteResponse=new AdvanceTravelDeleteResponse(MessageConstant.TRUE,MessageConstant.PROFILE_DELETE,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(advanceTravelDeleteResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			advanceTravelDeleteResponse=new AdvanceTravelDeleteResponse(MessageConstant.FALSE,MessageConstant.PROFILE_DELETE_FAILED,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(advanceTravelDeleteResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		//e.printStackTrace();
	    		logger.error("error in advanceTravelRequestDelete====="+e);
	    		//message=e.getMessage();
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		advanceTravelDeleteResponse=new AdvanceTravelDeleteResponse(MessageConstant.FALSE,message,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(advanceTravelDeleteResponse);
    			jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in advanceTravelRequestDelete====="+e);
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
	    @RequestMapping(value = "/get/advanceTravelApprovalRequest",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> advanceTravelApprovalRequest(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside get/advanceTravelApprovalRequest+++");	    	
	    	
	    
	    	String message = "";
	    	List<AdvanceTravelDto> response=null;
	    	AdvanceTravelListDtoResponse advanceTravelListResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				AdvanceTravelCashRequest advanceTravelRequest= EncryptionDecriptionUtil.convertFromJson(decript, AdvanceTravelCashRequest.class);
				
				response=expenseTravelAdvanceService.getAdvenceTravelApprovalEmployerId(advanceTravelRequest.getEmployerId());
	    		if(response!=null && response.size()>0) {
	    			advanceTravelListResponse=new AdvanceTravelListDtoResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(advanceTravelListResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			advanceTravelListResponse=new AdvanceTravelListDtoResponse(MessageConstant.FALSE,MessageConstant.PROFILE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(advanceTravelListResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		//e.printStackTrace();
	    		logger.error("error in get/advanceTravelRequest====="+e);
	    		//message=e.getMessage();
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		advanceTravelListResponse=new AdvanceTravelListDtoResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(advanceTravelListResponse);
    			jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in get/advanceTravelRequest====="+e);
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
	    @RequestMapping(value = "/update/advanceTravelRequestApproved",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> advanceTravelRequestApproved(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside update/advanceTravelRequestApproved+++");	    	
	    	
	    
	    	String message = "";
	    	ApprovalTravelReimbursement response=null;
	    	AdvanceTravelApprovalResponse appAdvanceTravelApprovalResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ApprovalTravelReimbursement approvalTravelReimbursement= EncryptionDecriptionUtil.convertFromJson(decript, ApprovalTravelReimbursement.class);
				
				response=expenseTravelAdvanceService.ApprovalAdvenceTravel(approvalTravelReimbursement);
	    		if(response!=null) {
	    			appAdvanceTravelApprovalResponse=new AdvanceTravelApprovalResponse(MessageConstant.TRUE,MessageConstant.PROFILE_UPDATE,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(appAdvanceTravelApprovalResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			appAdvanceTravelApprovalResponse=new AdvanceTravelApprovalResponse(MessageConstant.FALSE,MessageConstant.PROFILE_FAILED_UPDATE,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(appAdvanceTravelApprovalResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		//e.printStackTrace();
	    		logger.error("error in update/AdvanceTravelApprovalResponse====="+e);
	    		//message=e.getMessage();
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		appAdvanceTravelApprovalResponse=new AdvanceTravelApprovalResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(appAdvanceTravelApprovalResponse);
    			jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in update/AdvanceTravelApprovalResponse====="+e);
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
	    @RequestMapping(value = "/get/advanceTravelById",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> advanceTravelById(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside get/advanceTravelById+++");	    	
	    	
	    	String message = "";
	    	AdvanceTravelRequestEntity response=null;
	    	AdvanceTravelByIdResponse advanceTravelByIdResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ApprovalTravelReimbursementById approvalTravelReimbursement= EncryptionDecriptionUtil.convertFromJson(decript, ApprovalTravelReimbursementById.class);
				
				response=expenseTravelAdvanceService.getAdvenceTravelListById(approvalTravelReimbursement.getId());
	    		if(response!=null ) {
	    			advanceTravelByIdResponse=new AdvanceTravelByIdResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(advanceTravelByIdResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			advanceTravelByIdResponse=new AdvanceTravelByIdResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(advanceTravelByIdResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		//e.printStackTrace();
	    		logger.error("error in get/advanceTravelById====="+e);
	    		//message=e.getMessage();
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		advanceTravelByIdResponse=new AdvanceTravelByIdResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(advanceTravelByIdResponse);
    			jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in get/advanceTravelById====="+e);
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
//	    @RequestMapping(value = "/get/advanceTravelApprovalById",produces = {"application/json"}, 
//	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
//	    public ResponseEntity<Object> advanceTravelApprovalById(HttpServletRequest request,@Valid @RequestBody AdvanceTravelRequest advanceTravelRequest) {
//		 
//	    logger.info("inside get/advanceTravelRequest+++");	    	
//	    	
//	    
//	    	String message = "";
//	    	List<AdvanceTravelRequestEntity> response=null;
//	    	try {	    		
//	    		String companyId = request.getHeader("companyId");
//				SetDatabaseTenent.setDataSource(companyId);
//				
//				response=expenseTravelAdvanceService.advenceTravelListById(advanceTravelRequest.getId());
//	    		if(response!=null && response.size()>0) {
//	    			return ResponseEntity.ok(new AdvanceTravelListResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
//	    		}else {
//	    			return ResponseEntity.ok(new AdvanceTravelListResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
//	    		}
//	    	}catch (Exception e) {				
//	    		//e.printStackTrace();
//	    		logger.error("error in get/advanceTravelRequest====="+e);
//	    		//message=e.getMessage();
//			}
//	        
//	        return ResponseEntity.ok(new AdvanceTravelListResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
//	    }
	}
