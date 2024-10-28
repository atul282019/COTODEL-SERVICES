package com.cotodel.hrms.auth.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cotodel.hrms.auth.server.util.MessageConstant;



@RestController
@RequestMapping("/Api")
public class ErupiVoucherCallBackController extends CotoDelBaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(ErupiVoucherCallBackController.class);

	@RequestMapping(value = "/create/txnId/{txnid}",produces = {"application/json"}, 
		    consumes = {"application/json","application/text"},method = RequestMethod.POST)
		    public ResponseEntity<Object> erupiCreateTxn(@PathVariable("txnid") String txnid,@RequestBody String respString) {
			 
		    logger.info("inside erupiCreateTxn...txnid."+txnid);	    	
		    logger.info("inside erupiCreateTxn...respString."+respString);	
		    
		try {
			return ResponseEntity.ok(MessageConstant.SUCCESS);	 
		} catch (Exception e) {
			e.printStackTrace();
			
			return ResponseEntity.ok(MessageConstant.RESPONSE_FAILED);	 
		}
		
	}
	
	
	@RequestMapping(value = "/create/txnId",produces = {"application/json"},method = RequestMethod.GET)
		    public ResponseEntity<Object> erupiCreateTxnGet() {
			 	    	
		    logger.info("inside erupiCreateTxn...respString.");	
		    
		try {
			return ResponseEntity.ok(MessageConstant.SUCCESS);	 
		} catch (Exception e) {
			e.printStackTrace();
			
			return ResponseEntity.ok(MessageConstant.RESPONSE_FAILED);	 
		}
		
	}
}
