package com.cotodel.hrms.auth.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.VehicleTypeMasterService;

@RestController
@RequestMapping("/Api")
public class VehicleTypeMasterController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeOnboardingController.class);
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
	@Autowired
	VehicleTypeMasterService vehicleTypeMasterService;
		
//	@Operation(summary = "This API will provide the Save User Details ", security = {
//    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
//    @ApiResponses(value = {
//    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
//    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
//    @RequestMapping(value = "/get/getVehicleTypeMaster",produces = {"application/json"}, 
//    consumes = {"application/json","application/text"},method = RequestMethod.POST)
//    public ResponseEntity<Object> getVehicleTypeMaster(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
//    logger.info("inside getVehicleTypeMaster+++");    	
//    
//    	String message = "";
//    	String message1 = "";
//    	List<VehicleTypeMasterEntity> response=null;
//    	VehicleManagementRequest vehicleManagementRequest=null;
//    	VehicleManagementResponse vehicleManagementResponse;
//    	try {	    		
//    		String companyId = request.getHeader("companyId");
//			SetDatabaseTenent.setDataSource(companyId);
//			
//			String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
//			vehicleManagementRequest= EncryptionDecriptionUtil.convertFromJson(decript, VehicleManagementRequest.class);
//			response=vehicleTypeMasterService.getVehicleTypeMaster();
//    		if(response!=null && response.size()>0) {
//    			vehicleManagementResponse=new VehicleManagementResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
//    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(vehicleManagementResponse);
//    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
//    			return ResponseEntity.ok(jsonEncriptObject);
//    		}else {
//    			vehicleManagementResponse=new VehicleManagementResponse(MessageConstant.FALSE,message1,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
//    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(vehicleManagementResponse);
//    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
//    			return ResponseEntity.ok(jsonEncriptObject);
//    		}
//    	}catch (Exception e) {				
//    		//e.printStackTrace();
//    		logger.error("error in getVehicleTypeMaster====="+e);
//    		//message=e.getMessage();
//		}
//    	EncriptResponse jsonEncriptObject=new EncriptResponse();
//    	try {
//    		vehicleManagementResponse=new VehicleManagementResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
//			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(vehicleManagementResponse);
//			jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//	    return ResponseEntity.ok(jsonEncriptObject);
//    }

}
