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

import com.cotodel.hrms.auth.server.dto.EmployeeBandRequest;
import com.cotodel.hrms.auth.server.dto.EmployeeBandResponse;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.multi.datasource.SetDatabaseTenent;
import com.cotodel.hrms.auth.server.service.EmployeeBandService;
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
public class EmployeeBandController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeBandController.class);
	
	@Autowired
	EmployeeBandService employeeBandService;
	
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/add/employeeBandDetails",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> saveEmployeeBandDetails(HttpServletRequest request,@Valid @RequestBody EmployeeBandRequest empolyeeRequest) {
		 
	    logger.info("inside employeeBandDetails");	    	
	    	
	    
	    	String message = "";
	    	EmployeeBandRequest response=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=employeeBandService.saveCompEmployeeBandDetails(empolyeeRequest);
	    		if(response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			return ResponseEntity.ok(new EmployeeBandResponse(true,MessageConstant.PROFILE_SUCCESS,empolyeeRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new EmployeeBandResponse(false,MessageConstant.PROFILE_FAILED,empolyeeRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    	}catch (Exception e) {				
	    		e.printStackTrace();
	    		logger.error("error in saveProfileDetails====="+e);
	    		message=e.getMessage();
			}
	        
	        return ResponseEntity.ok(new EmployeeBandResponse(false,message,empolyeeRequest,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));	        
	    }
	 
	}
