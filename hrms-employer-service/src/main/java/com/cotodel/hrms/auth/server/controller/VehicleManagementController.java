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

import com.cotodel.hrms.auth.server.dto.vehicle.VehicleManagementByIdResponse;
import com.cotodel.hrms.auth.server.dto.vehicle.VehicleManagementGetDto;
import com.cotodel.hrms.auth.server.dto.vehicle.VehicleManagementRequest;
import com.cotodel.hrms.auth.server.dto.vehicle.VehicleManagementResponse;
import com.cotodel.hrms.auth.server.dto.vehicle.VehicleManagementSaveRequest;
import com.cotodel.hrms.auth.server.dto.vehicle.VehicleManagementSaveResponse;
import com.cotodel.hrms.auth.server.dto.vehicle.VehicleManagementTripRequest;
import com.cotodel.hrms.auth.server.dto.vehicle.VehicleManagementTripResponse;
import com.cotodel.hrms.auth.server.dto.vehicle.VehicleTripDto;
import com.cotodel.hrms.auth.server.dto.vehicle.VehicleTripListResponse;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.model.vehicle.VehicleManagementEntity;
import com.cotodel.hrms.auth.server.multi.datasource.SetDatabaseTenent;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.VehicleManagementService;
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
public class VehicleManagementController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeOnboardingController.class);
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
	@Autowired
	VehicleManagementService vehicleManagementService;
	
	@Operation(summary = "This API will provide the Save User Details ", security = {
    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
    @RequestMapping(value = "/add/VehicleManagement",produces = {"application/json"}, 
    consumes = {"application/json","application/text"},method = RequestMethod.POST)
    public ResponseEntity<Object> addVehicleManagement(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
    logger.info("inside addVehicleManagement+++");    	
    
    	String message = "";
    	String message1 = "";
    	VehicleManagementSaveRequest response=null;
    	VehicleManagementSaveRequest vehicleManagementSaveRequest=null;
    	VehicleManagementSaveResponse vehicleManagementSaveResponse;
    	try {	    		
    		String companyId = request.getHeader("companyId");
			SetDatabaseTenent.setDataSource(companyId);
			
			String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
			vehicleManagementSaveRequest= EncryptionDecriptionUtil.convertFromJson(decript, VehicleManagementSaveRequest.class);
			response=vehicleManagementService.saveVehicleManagement(vehicleManagementSaveRequest);
    		if(response!=null && response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
    			vehicleManagementSaveResponse=new VehicleManagementSaveResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(vehicleManagementSaveResponse);
    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
    			return ResponseEntity.ok(jsonEncriptObject);
    		}else {
    			vehicleManagementSaveResponse=new VehicleManagementSaveResponse(MessageConstant.FALSE,response.getResponse(),response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(vehicleManagementSaveResponse);
    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
    			return ResponseEntity.ok(jsonEncriptObject);
    		}
    	}catch (Exception e) {				
    		//e.printStackTrace();
    		logger.error("error in add/VehicleManagement====="+e);
    		//message=e.getMessage();
		}
    	EncriptResponse jsonEncriptObject=new EncriptResponse();
    	try {
    		vehicleManagementSaveResponse=new VehicleManagementSaveResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(vehicleManagementSaveResponse);
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
    @RequestMapping(value = "/get/getVehicleManagement",produces = {"application/json"}, 
    consumes = {"application/json","application/text"},method = RequestMethod.POST)
    public ResponseEntity<Object> getVehicleManagement(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
    logger.info("inside getVehicleManagement+++");    	
    
    	String message = "";
    	String message1 = "";
    	List<VehicleManagementGetDto> response=null;
    	VehicleManagementRequest vehicleManagementRequest=null;
    	VehicleManagementResponse vehicleManagementResponse;
    	try {	    		
    		String companyId = request.getHeader("companyId");
			SetDatabaseTenent.setDataSource(companyId);
			
			String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
			vehicleManagementRequest= EncryptionDecriptionUtil.convertFromJson(decript, VehicleManagementRequest.class);
			response=vehicleManagementService.getVehicleManagement(vehicleManagementRequest.getOrgId());
    		if(response!=null && response.size()>0) {
    			vehicleManagementResponse=new VehicleManagementResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(vehicleManagementResponse);
    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
    			return ResponseEntity.ok(jsonEncriptObject);
    		}else {
    			vehicleManagementResponse=new VehicleManagementResponse(MessageConstant.FALSE,message1,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(vehicleManagementResponse);
    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
    			return ResponseEntity.ok(jsonEncriptObject);
    		}
    	}catch (Exception e) {				
    		//e.printStackTrace();
    		logger.error("error in getVehicleManagement====="+e);
    		//message=e.getMessage();
		}
    	EncriptResponse jsonEncriptObject=new EncriptResponse();
    	try {
    		vehicleManagementResponse=new VehicleManagementResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(vehicleManagementResponse);
			jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
		} catch (Exception e) {
			// TODO: handle exception
		}
	    return ResponseEntity.ok(jsonEncriptObject);
    }
//	@Operation(summary = "This API will provide the Save User Details ", security = {
//    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
//    @ApiResponses(value = {
//    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
//    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
//    @RequestMapping(value = "/add/tripManagement",produces = {"application/json"}, 
//    consumes = {"application/json","application/text"},method = RequestMethod.POST)
//    public ResponseEntity<Object> tripManagement(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
//    logger.info("inside tripManagement+++");    	
//    
//    	String message = "";
//    	VehicleManagementTripRequest response=null;
//    	VehicleManagementTripRequest vehicleManagementSaveRequest=null;
//    	VehicleManagementTripResponse vehicleManagementSaveResponse;
//    	try {	    		
//    		String companyId = request.getHeader("companyId");
//			SetDatabaseTenent.setDataSource(companyId);
//			
//			String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
//			vehicleManagementSaveRequest= EncryptionDecriptionUtil.convertFromJson(decript, VehicleManagementTripRequest.class);
//			response=vehicleManagementService.saveTripManagement(vehicleManagementSaveRequest);
//    		if(response!=null) {
//    			vehicleManagementSaveResponse=new VehicleManagementTripResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
//    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(vehicleManagementSaveResponse);
//    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
//    			return ResponseEntity.ok(jsonEncriptObject);
//    		}else {
//    			vehicleManagementSaveResponse=new VehicleManagementTripResponse(MessageConstant.FALSE,response.getResponse(),response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
//    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(vehicleManagementSaveResponse);
//    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
//    			return ResponseEntity.ok(jsonEncriptObject);
//    		}
//    	}catch (Exception e) {				
//    		//e.printStackTrace();
//    		logger.error("error in add/tripManagement====="+e);
//    		//message=e.getMessage();
//		}
//    	EncriptResponse jsonEncriptObject=new EncriptResponse();
//    	try {
//    		vehicleManagementSaveResponse=new VehicleManagementTripResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
//			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(vehicleManagementSaveResponse);
//			jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//	    return ResponseEntity.ok(jsonEncriptObject);
//    }
	@Operation(summary = "This API will provide the Save User Details ", security = {
    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
    @RequestMapping(value = "/get/getTripHistoryList",produces = {"application/json"}, 
    consumes = {"application/json","application/text"},method = RequestMethod.POST)
    public ResponseEntity<Object> getTripManagementList(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
    logger.info("inside getTripManagementList+++");    	
    
    	String message = "";
    	List<VehicleTripDto> response=null;
    	VehicleManagementRequest vehicleManagementSaveRequest=null;
    	VehicleTripListResponse vehicleTripListResponse;
    	try {	    		
    		String companyId = request.getHeader("companyId");
			SetDatabaseTenent.setDataSource(companyId);
			
			String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
			vehicleManagementSaveRequest= EncryptionDecriptionUtil.convertFromJson(decript, VehicleManagementRequest.class);
			response=vehicleManagementService.getVehicleTripManagement(vehicleManagementSaveRequest.getVehicleSequenceId());
    		if(response!=null && response.size()>0) {
    			vehicleTripListResponse=new VehicleTripListResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(vehicleTripListResponse);
    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
    			return ResponseEntity.ok(jsonEncriptObject);
    		}else {
    			vehicleTripListResponse=new VehicleTripListResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(vehicleTripListResponse);
    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
    			return ResponseEntity.ok(jsonEncriptObject);
    		}
    	}catch (Exception e) {				
    		//e.printStackTrace();
    		logger.error("error in get/getTripManagementList====="+e);
    		//message=e.getMessage();
		}
    	EncriptResponse jsonEncriptObject=new EncriptResponse();
    	try {
    		vehicleTripListResponse=new VehicleTripListResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(vehicleTripListResponse);
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
    @RequestMapping(value = "/get/getVehicleManagementById",produces = {"application/json"}, 
    consumes = {"application/json","application/text"},method = RequestMethod.POST)
    public ResponseEntity<Object> getVehicleManagementById(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
    logger.info("inside getVehicleManagementById+++");    	
    
    	String message = "";
    	String message1 = "";
    	VehicleManagementEntity response=null;
    	VehicleManagementRequest vehicleManagementRequest=null;
    	VehicleManagementByIdResponse vehicleManagementByIdResponse;
    	try {	    		
    		String companyId = request.getHeader("companyId");
			SetDatabaseTenent.setDataSource(companyId);
			
			String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
			vehicleManagementRequest= EncryptionDecriptionUtil.convertFromJson(decript, VehicleManagementRequest.class);
			response=vehicleManagementService.getVehicleManagementById(vehicleManagementRequest.getVehicleSequenceId());
    		if(response!=null ) {
    			vehicleManagementByIdResponse=new VehicleManagementByIdResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(vehicleManagementByIdResponse);
    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
    			return ResponseEntity.ok(jsonEncriptObject);
    		}else {
    			vehicleManagementByIdResponse=new VehicleManagementByIdResponse(MessageConstant.FALSE,message1,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(vehicleManagementByIdResponse);
    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
    			return ResponseEntity.ok(jsonEncriptObject);
    		}
    	}catch (Exception e) {				
    		//e.printStackTrace();
    		logger.error("error in getVehicleManagementById====="+e);
    		//message=e.getMessage();
		}
    	EncriptResponse jsonEncriptObject=new EncriptResponse();
    	try {
    		vehicleManagementByIdResponse=new VehicleManagementByIdResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(vehicleManagementByIdResponse);
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
    @RequestMapping(value = "/add/tripVehicleDetails",produces = {"application/json"}, 
    consumes = {"application/json","application/text"},method = RequestMethod.POST)
    public ResponseEntity<Object> updateVehicleManagement(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
    logger.info("inside tripVehicleDetails+++");    	
    
    	String message = "";
    	VehicleManagementTripRequest response=null;
    	VehicleManagementTripRequest vehicleManagementSaveRequest=null;
    	VehicleManagementTripResponse vehicleManagementSaveResponse;
    	try {	    		
    		String companyId = request.getHeader("companyId");
			SetDatabaseTenent.setDataSource(companyId);
			
			String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
			vehicleManagementSaveRequest= EncryptionDecriptionUtil.convertFromJson(decript, VehicleManagementTripRequest.class);
			response=vehicleManagementService.tripVehicleDetails(vehicleManagementSaveRequest);
    		if(response!=null && response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
    			vehicleManagementSaveResponse=new VehicleManagementTripResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(vehicleManagementSaveResponse);
    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
    			return ResponseEntity.ok(jsonEncriptObject);
    		}else {
    			vehicleManagementSaveResponse=new VehicleManagementTripResponse(MessageConstant.FALSE,response.getResponse(),response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(vehicleManagementSaveResponse);
    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
    			return ResponseEntity.ok(jsonEncriptObject);
    		}
    	}catch (Exception e) {				
    		//e.printStackTrace();
    		logger.error("error in add/tripVehicleDetails====="+e);
    		//message=e.getMessage();
		}
    	EncriptResponse jsonEncriptObject=new EncriptResponse();
    	try {
    		vehicleManagementSaveResponse=new VehicleManagementTripResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(vehicleManagementSaveResponse);
			jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
		} catch (Exception e) {
			// TODO: handle exception
		}
	    return ResponseEntity.ok(jsonEncriptObject);
    }
}
