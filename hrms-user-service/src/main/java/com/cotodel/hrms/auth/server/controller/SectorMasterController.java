package com.cotodel.hrms.auth.server.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cotodel.hrms.auth.server.dto.SectorMasterResponse;
import com.cotodel.hrms.auth.server.entity.SectorMaster;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.service.SectorMasterService;
import com.cotodel.hrms.auth.server.util.MessageConstant;
import com.cotodel.hrms.auth.server.util.TransactionManager;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * @author vinay
 */
@RestController
@RequestMapping("/Api")
public class SectorMasterController {
	private static final Logger logger = LoggerFactory.getLogger(SectorMasterController.class);
	
	@Autowired
	SectorMasterService sectorMasterService;
	
	 @Operation(summary = "This API will provide the Authentication token", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/sector-list",produces = {"application/json"}, consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> getSector() {
	    	logger.info("inside get sector-list");
	    	List<SectorMaster>  sectorMasters=null;
	    	try {
	    		sectorMasters=sectorMasterService.getBySectorList();
	    		
	    		 if(sectorMasters!=null && sectorMasters.size()>0 )
		    		 return ResponseEntity.ok(new SectorMasterResponse(MessageConstant.TRUE,sectorMasters,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
		    	 
			} catch (Exception e) {
				logger.error("error in state-list====="+e);
			}
	    	
	    	 return ResponseEntity.ok(new SectorMasterResponse(MessageConstant.FALSE,sectorMasters,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	          
	        
	    }
}
