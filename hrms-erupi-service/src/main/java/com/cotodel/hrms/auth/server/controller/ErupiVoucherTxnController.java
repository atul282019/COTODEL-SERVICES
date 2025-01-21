package com.cotodel.hrms.auth.server.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreateRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherTxnRequest;
import com.cotodel.hrms.auth.server.service.ErupiVoucherTxnService;
import com.cotodel.hrms.auth.server.util.MessageConstant;

@RestController
@RequestMapping("/Api")
public class ErupiVoucherTxnController {
private static final Logger logger = LoggerFactory.getLogger(ErupiVoucherTxnController.class);
	
	@Autowired
	ErupiVoucherTxnService erupiVoucherTxnService;
	
//	 @Operation(summary = "This API will provide the Save User Details ", security = {
//	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
//	    @ApiResponses(value = {
//	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
//	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	 	@PostMapping("/create/voucher")
	    public ResponseEntity<Object> erupiCreateTxn(@PathVariable("txnid") String txnid,@RequestBody String respString) {
		 
	    logger.info("inside erupiCreateTxn...txnid."+txnid);	    	
	    logger.info("inside erupiCreateTxn...respString."+respString);
	    
	    	String message = "";
	    	ErupiVoucherTxnRequest response=null;
	    	try {	    		
	    		//String companyId = request.getHeader("companyId");
				//SetDatabaseTenent.setDataSource(companyId);
				
	    		
				
	    	}catch (Exception e) {				
	    		logger.error("error in erupiVoucherInitiateDetails====="+e);
			}
	        
	        return ResponseEntity.ok(MessageConstant.TRUE);	        
	    }
	 

//	 @Operation(summary = "This API will provide the Save User Details ", security = {
//	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
//	    @ApiResponses(value = {
//	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
//	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
//	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	 @RequestMapping(value = "/create/voucher",produces = {"application/json"}, 
			    consumes = {"application/json","application/text"},method = RequestMethod.POST)
			    public ResponseEntity<Object> erupiCreate(HttpServletRequest request,@Valid @RequestBody ErupiVoucherCreateRequest erupiVoucherCreateRequest) {
				 
			   
			    
			    	String message = "";
			    	ErupiVoucherTxnRequest response=null;
			    	try {	    		
			    		//String companyId = request.getHeader("companyId");
						//SetDatabaseTenent.setDataSource(companyId);
						logger.info("Hi");
			    		
						message=erupiVoucherTxnService.saveErupiVoucherTxnDetails(erupiVoucherCreateRequest);

			    	}catch (Exception e) {				
			    		logger.error("error in erupiVoucherInitiateDetails====="+e);
					}
			        
			        return ResponseEntity.ok(message);	        
			    }
	 

}
