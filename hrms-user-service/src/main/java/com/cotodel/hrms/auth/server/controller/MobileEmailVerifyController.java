package com.cotodel.hrms.auth.server.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cotodel.hrms.auth.server.dto.UserOtpResponse;
import com.cotodel.hrms.auth.server.dto.UserOtpVerifyResponse;
import com.cotodel.hrms.auth.server.dto.UserRequest;
import com.cotodel.hrms.auth.server.dto.UserSignUpResponse;
import com.cotodel.hrms.auth.server.dto.UserVerifyResponse;
import com.cotodel.hrms.auth.server.entity.RoleMaster;
import com.cotodel.hrms.auth.server.entity.UserEntity;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.service.UserService;
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
public class MobileEmailVerifyController {
		

	@Autowired
	UserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(MobileEmailVerifyController.class);
    
	
	 @Operation(summary = "This API will provide the User Mobile Verify Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/getOtp",produces = {"application/json"}, consumes = {"application/json","application/text"},
	    method = RequestMethod.POST)
	    public ResponseEntity<Object> sendOtp(HttpServletRequest request,@Valid @RequestBody UserRequest userReq) {
	    	logger.info("inside  getOtp......");
	    	List<RoleMaster> roleMaster=null;
	    	String response="";
	    	UserEntity userEntity=null;
	    	try {
	    		// write code here
	    		String authToken=request.getHeader("Authorization");
	    		userEntity=userService.checkUserMobile(userReq.getMobile());
	    		if(userEntity!=null && userEntity.getStatus()==MessageConstant.ONE ) {
	    			//response=userService.sendSmsOtp(authToken,userReq.getMobile());
	    			response="{\"errCode\":\"\",\"errDes\":\"\",\"txn\":\"NHA:53029a89-ae73-4e52-bdfc-0f47d237a6fc\",\"ts\":\"2024-02-14T15:12:24.240+05:24\",\"status\":\"true\"}";
	    			if(!ObjectUtils.isEmpty(response)) {

						JSONObject demoRes= new JSONObject(response);
						if(Boolean.valueOf(demoRes.getString("status"))) {
							return ResponseEntity.ok(new UserOtpResponse(true,MessageConstant.OTP_SENT,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
							
						}else {
							return ResponseEntity.ok(new UserOtpResponse(false,MessageConstant.OTP_FAILED,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
						}

					}else {
						return ResponseEntity.ok(new UserOtpResponse(false,MessageConstant.OTP_FAILED,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
					}
	    		}
	    		
//	    	 if(roleMaster!=null)
//	    		 return ResponseEntity
//	 	                .ok(roleMaster);
	    	 
	    		
	    	 
	    	}catch (Exception e) {
				
	    		// TODO: handle exception
	    		logger.error("error in getOtp====="+e);
			}
	        
	    	return ResponseEntity.ok(new UserOtpResponse(false,MessageConstant.OTP_FAILED,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	          
	        
	    }

	

	 
	 @Operation(summary = "This API will provide the User Mobile Verify Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/verifyOtp",produces = {"application/json"}, consumes = {"application/json","application/text"},
	    method = RequestMethod.POST)
	    public ResponseEntity<Object> verifyOtp(HttpServletRequest request,@Valid @RequestBody UserRequest userReq) {
	    	logger.info("inside token verifyOtp");
	    	List<RoleMaster> roleMaster=null;
	    	String response="";
	    	UserEntity userEntity=null;
	    	try {
	    		
	    		// write code here
	    		String authToken=request.getHeader("Authorization");
	    		userEntity=userService.checkUserMobile(userReq.getMobile());
	    		if(userEntity!=null && userEntity.getStatus()==MessageConstant.ONE ) {
	    			response=userService.verifySmsOtp(authToken,userReq.getMobile(),userReq.getOtp());
	    			response="{\"errCode\":\"\",\"errDes\":\"\",\"txn\":\"NHA:53029a89-ae73-4e52-bdfc-0f47d237a6fc\",\"ts\":\"2024-02-14T15:12:24.240+05:24\",\"status\":\"true\"}";
	    			
	    			if(!ObjectUtils.isEmpty(response)) {

						JSONObject demoRes= new JSONObject(response);
						if(Boolean.valueOf(demoRes.getString("status"))) {
							return ResponseEntity.ok(new UserOtpVerifyResponse(true,MessageConstant.RESPONSE_SUCCESS,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),userEntity));
							
						}else {
							return ResponseEntity.ok(new UserOtpVerifyResponse(false,MessageConstant.RESPONSE_FAILED,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),userEntity));
						}

					}else {
						return ResponseEntity.ok(new UserOtpVerifyResponse(false,MessageConstant.RESPONSE_FAILED,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),userEntity));
					}
	    		}
	    		
	    	 
	    	 
	    	}catch (Exception e) {
				
	    		// TODO: handle exception
	    		logger.error("error in verifyOtp====="+e);
			}
	        
	    	return ResponseEntity.ok(new UserOtpVerifyResponse(false,MessageConstant.RESPONSE_FAILED,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),userEntity));
	          
	        
	    }



	 @Operation(summary = "This API will provide the User Emial Address Verify Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/sendEmailVerifyLink/{token}/{emailbyt}",produces = {"application/json"}, consumes = {"application/json","application/text"},
	    method = RequestMethod.GET)
	    public ResponseEntity<Object> sendLinkToEmail(HttpServletRequest request, @PathVariable("token") String token
				,@PathVariable("emailbyt") String emailbyt,@Valid @RequestBody UserRequest userReq) {
	    	logger.info("inside  sendEmailVerifyLink...");
	    	List<RoleMaster> roleMaster=null;
	    	UserEntity userForm = new UserEntity();
	    	try {    		
	    	
	    		// write code here
	    		//userService.sendEmailToEmployee(userReq);
	    		
	    		System.out.println("In Request Mapping"); 
				byte[] tokenBytes = Base64.getDecoder().decode(token);//parseBase64Binary(token + "==");
				String mobileno = new String(tokenBytes, StandardCharsets.UTF_8);
				byte[] emailBytes = Base64.getDecoder().decode(emailbyt);
				String emailAgain = new String(emailBytes, StandardCharsets.UTF_8);
				System.out.println(mobileno + " ------ "+emailAgain);
				
				
				userForm.setEmail(mobileno);
				userForm.setMobile(mobileno);
				//model.addAttribute("userform",userForm);
				return ResponseEntity.ok(new UserSignUpResponse(true,userForm,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),""));
	    		
//	    	 if(roleMaster!=null)
//	    		 return ResponseEntity
//	 	                .ok(roleMaster);
	    	 
	    	 
	    	}catch (Exception e) {
				
	    		// TODO: handle exception
			}
	        
	    	return ResponseEntity.ok(new UserSignUpResponse(false,userForm,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),""));
	          
	        
	    }

	 
	 @Operation(summary = "This API will provide the User Emial Verify Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/verifyLink",produces = {"application/json"}, consumes = {"application/json","application/text"},
	    method = RequestMethod.POST)
	    public ResponseEntity<Object> verifyLinkFromEmail(@Valid @RequestBody UserRequest userReq) {
	    	logger.info("inside verifyLink..");
	    	List<RoleMaster> roleMaster=null;
	    	String response="";
	    	try {
	    		
	    		// write code here
	    		response = userService.verifyEmailUpdate(userReq.getEmail());
	    		
	    		if (response.equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			return ResponseEntity.ok(new UserVerifyResponse(true,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new UserVerifyResponse(false,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    		
	    	 
	    	}catch (Exception e) {
				
	    		logger.error("error in verifyLink====="+e);
			}
	        
	    	return ResponseEntity.ok(new UserVerifyResponse(false,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	          
	        
	    }

	

}
