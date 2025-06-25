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

import com.cotodel.hrms.auth.server.dto.ExpenseBandListResponse;
import com.cotodel.hrms.auth.server.dto.ExpenseBillReader;
import com.cotodel.hrms.auth.server.dto.ExpenseBillReaderRequest;
import com.cotodel.hrms.auth.server.dto.ExpenseBillReaderResponse;
import com.cotodel.hrms.auth.server.dto.ExpenseCategoryBandLimitResponse;
import com.cotodel.hrms.auth.server.dto.ExpenseCategoryBandListResponse;
import com.cotodel.hrms.auth.server.dto.ExpenseCategoryBandRequest;
import com.cotodel.hrms.auth.server.dto.ExpenseCategoryBandResponse;
import com.cotodel.hrms.auth.server.dto.ExpenseTravelAdvanceRequest;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.model.ExpenseBandNumberEntity;
import com.cotodel.hrms.auth.server.multi.datasource.SetDatabaseTenent;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.ExpenseCategoryBandService;
import com.cotodel.hrms.auth.server.service.OcrReaderService;
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
public class OCRReaderController {
	
	private static final Logger logger = LoggerFactory.getLogger(OCRReaderController.class);
	
	@Autowired
	OcrReaderService ocrReaderService;
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/ocrDetails",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> ocrDetails(HttpServletRequest request,@Valid @RequestBody ExpenseBillReaderRequest eBillReaderRequest) {
		 
	    logger.info("inside ocrDetails+++");	    	
	    	
	    
	    	ExpenseBillReader expenseBillReader =null;
	    	ExpenseCategoryBandRequest response=null;
	    	ExpenseBillReaderRequest empolyeeRequest=null;
	    	ExpenseBillReaderResponse expenseBillReaderResponse;
	    	//ExpenseBillReader expenseBillReader=new ExpenseBillReader();
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
//				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
//				empolyeeRequest= EncryptionDecriptionUtil.convertFromJson(decript, ExpenseBillReaderRequest.class);
				
				expenseBillReader=ocrReaderService.ocrDetail(eBillReaderRequest);
				
	    		if(expenseBillReader.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			expenseBillReaderResponse=new ExpenseBillReaderResponse(true,MessageConstant.DATA_FOUND,expenseBillReader,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(expenseBillReaderResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(expenseBillReaderResponse);
	    		}else {
	    			expenseBillReaderResponse=new ExpenseBillReaderResponse(false,MessageConstant.DATA_NOT_FOUND,expenseBillReader,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
	    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(expenseBillReaderResponse);
	    			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    			return ResponseEntity.ok(expenseBillReaderResponse);
	    		}
	    	}catch (Exception e) {				
	    		//e.printStackTrace();
	    		logger.error("error in expenseCategoryBandDetails====="+e);
	    		//message=e.getMessage();
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		expenseBillReaderResponse=new ExpenseBillReaderResponse(false,MessageConstant.DATA_NOT_FOUND,expenseBillReader,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
    			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(expenseBillReaderResponse);
    			jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in expenseCategoryBandDetails====="+e);
			}
    	    return ResponseEntity.ok(jsonEncriptObject);
	    }

	}
