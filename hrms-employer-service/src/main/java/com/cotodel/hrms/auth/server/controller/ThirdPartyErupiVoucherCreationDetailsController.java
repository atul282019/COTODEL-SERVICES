package com.cotodel.hrms.auth.server.controller;

import java.time.LocalDate;

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

import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreateDetailsRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedDateWiseRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherInitiateDetailsResponse;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.multi.datasource.SetDatabaseTenent;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.ErupiVoucherCreationDetailsService;
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
public class ThirdPartyErupiVoucherCreationDetailsController {
private static final Logger logger = LoggerFactory.getLogger(ExpenseTravelController.class);
	
	@Autowired
	ErupiVoucherCreationDetailsService erupiVoucherCreationDetailsService;
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/add/erupiCreateVoucher",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> erupiVoucherInitiateDetailsNew(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
		 
	    logger.info("inside erupiCreateVoucher....");	    	
	    	
	    
	    	String message = "";
	    	ErupiVoucherCreateDetailsRequest response=null;
	    	ErupiVoucherCreateDetailsRequest res;
	    	ErupiVoucherInitiateDetailsResponse erupiVoucherInitiateDetailsResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				ErupiVoucherCreateDetailsRequest erupiLinkAccountRequest= EncryptionDecriptionUtil.convertFromJson(decript, ErupiVoucherCreateDetailsRequest.class);
				res=erupiVoucherCreationDetailsService.checkErupiVoucherValidateDetails(erupiLinkAccountRequest);
				if(res.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
					response=erupiVoucherCreationDetailsService.saveErupiVoucherCreationDetails(erupiLinkAccountRequest);
				}else {
					erupiVoucherInitiateDetailsResponse=new ErupiVoucherInitiateDetailsResponse(MessageConstant.FALSE,res.getResponse(),response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherInitiateDetailsResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
				}
				
	    		
				if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
					erupiVoucherInitiateDetailsResponse=new ErupiVoucherInitiateDetailsResponse(MessageConstant.TRUE,MessageConstant.PROFILE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherInitiateDetailsResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			erupiVoucherInitiateDetailsResponse=new ErupiVoucherInitiateDetailsResponse(MessageConstant.FALSE,response.getResponseApi(),response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherInitiateDetailsResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	}catch (Exception e) {				
	    		logger.error("error in erupiCreateVoucher====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		erupiVoucherInitiateDetailsResponse=new ErupiVoucherInitiateDetailsResponse(MessageConstant.FALSE,message,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
				String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherInitiateDetailsResponse);
				jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				// TODO: handle exception
			}
    	    return ResponseEntity.ok(jsonEncriptObject);
	    }



	 public static void main(String[] args) {
		 ErupiVoucherCreatedDateWiseRequest erupiVoucherCreatedDateWiseRequest=new ErupiVoucherCreatedDateWiseRequest();
		 String dateString = "2025-03-02";
		 String enddateString = "2025-04-02";
	        LocalDate localDate = LocalDate.parse(dateString);
	        LocalDate endlocalDate = LocalDate.parse(enddateString);
	        erupiVoucherCreatedDateWiseRequest.setFromDate(localDate);
	        erupiVoucherCreatedDateWiseRequest.setToDate(endlocalDate);
	        try {
	        	String data="MwnvmL6GDwAq3r+BlBRp+Wt0XxtWFbZjprK8QyO/LDO4+0s/gacP8G1j/o2OH5j+pCsKY4cv9ln+k7ATtfxHy68krvJX/kORQV43pFkG7uTt6YcwozGMUCS3QMQbXPpXtizh0tFQWY9JqeOu2jsoimUjFpAhh6vLWn30kTDuhOzJ9YclD82TSyesj+eF7pgY3n605U2cQqIhYGA7uEUQjhJtSD4Cuy+Ntvf8GO5E5nZsX5Q0p1SvEL6TUI0yKD2TsvAHkOmirPibXGYVFD07ANg4UprCH7LKbnIrou70/gFrGqcWJ73ZodqV822YRlcG9WktNNop1Gx5EOt3M5vHNS1sZ5vmodJHwwSyCdn8pUHkKTpEf8eOdoL/enDh+atN/rb9VrXomOt5MiIz8jhAdImuxtkyCbfwo0DSyZ4UHlpn4hPcXJTlhYz8fOVqWtPVXxCmca1i3NkcTy2bbAifCXQeR6mDCSD5a29eIy8voA+Ty7btGD9XVV1v9ktgao6MnPHthJ7Q5AgimmJE9nhQ/g6Je4pFBQqXNUKU3iStDU8iJByJ09OWUh5Qh5z+YLy08rkMQBWHWmWi8QYbU0KsVGugFxH5TMl+hU9exs9fqMFur23TVd0QRhC+4ZQcaF772f6xhZ0G58vG4xY3e1l5mPUj7IqMvnbwIcqWIJXm4QlBx3GUj5PfKsE53yzusRKD/gcwwbNgikxOrjNz/BWDqB+AHE7ybJ62T3Mvir7CU8t1AYAVc5lrZ5rYngWMbLY2HY7dAmuzEYznwGLaQdPkshnUcAO8yB9lWjzPnktdvIB3NK1pAzWIbL5qOv9l+fi+Xd/MfedABFse0Xn1Dt3A+g\\u003d\\u003d";
	        	String encr="J048Hpu6FKk9mudtPrPRO3tiFhX1Hma6Kx13s/KC1rchz7xTTldddFzGJQJXm1XugmpPty5JXjJ+XQdNEfvlsm0d8GrUyR26Bkj36BEeyXVP+OuzraS2NwZxC0r/efFXrN/m3HKLoNPpA43dS7EFiFmUxl6xD1fWCx/h18ggcVI\\u003d";
	        	String decript=EncryptionDecriptionUtil.decriptResponse(data, encr, "/opt/cotodel/key/pvtKeyForApplication.txt");
	        	System.out.println("decript::"+decript);
	        	 String jsonEncript =  EncryptionDecriptionUtil.convertToJson(erupiVoucherCreatedDateWiseRequest);
	        	 EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, "/opt/cotodel/key/publicKeyForApplication.txt");
	        	 String jsonEncript1 =  EncryptionDecriptionUtil.convertToJson(jsonEncriptObject);
	        	 System.out.println(jsonEncript1);
			} catch (Exception e) {
				e.printStackTrace();
			}
	       
	}
}
