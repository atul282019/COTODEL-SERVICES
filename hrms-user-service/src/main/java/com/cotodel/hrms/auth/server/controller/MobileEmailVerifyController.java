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

import com.cotodel.hrms.auth.server.dao.UserDetailsDao;
import com.cotodel.hrms.auth.server.dto.UserNewOtpResponse;
import com.cotodel.hrms.auth.server.dto.UserOtpRequest;
import com.cotodel.hrms.auth.server.dto.UserOtpVerifyResponse;
import com.cotodel.hrms.auth.server.dto.UserOtpVerifyWithoutUserResponse;
import com.cotodel.hrms.auth.server.dto.UserRequest;
import com.cotodel.hrms.auth.server.dto.UserSignUpResponse;
import com.cotodel.hrms.auth.server.dto.UserVerifyResponse;
import com.cotodel.hrms.auth.server.entity.RoleMaster;
import com.cotodel.hrms.auth.server.entity.UserEntity;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.UserService;
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
public class MobileEmailVerifyController {
		

	@Autowired
	UserService userService;
	
	@Autowired
	UserDetailsDao userDetailsDao;
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
	private static final Logger logger = LoggerFactory.getLogger(MobileEmailVerifyController.class);
    
	

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
				return ResponseEntity.ok(new UserSignUpResponse(true,"",userForm,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),""));
	    		
//	    	 if(roleMaster!=null)
//	    		 return ResponseEntity
//	 	                .ok(roleMaster);
	    	 
	    	 
	    	}catch (Exception e) {
				
	    		// TODO: handle exception
			}
	        
	    	return ResponseEntity.ok(new UserSignUpResponse(false,"",userForm,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),""));
	          
	        
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
	    	logger.info("inside verifyLink..+++");
	    	List<RoleMaster> roleMaster=null;
	    	String response="";
	    	UserEntity userDetails= null;
	    	try {
	    		
	    		// write code here
	    		userDetails = userDetailsDao.checkUserEmail(userReq.getEmail());
	    		if(userDetails!=null && userDetails.getStatus()==1) {
	    			return ResponseEntity.ok(new UserVerifyResponse(false,MessageConstant.USER_EMAIL_ALREADY_VERIFIED,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    		response = userService.verifyEmailUpdate(userReq.getEmail());
	    		
	    		if (response.equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
	    			return ResponseEntity.ok(new UserVerifyResponse(true,MessageConstant.USER_EMAIL_VERIFIED,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}else {
	    			return ResponseEntity.ok(new UserVerifyResponse(false,MessageConstant.USER_EMAIL_NOT_VERIFIED,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	    		}
	    		}
	    	 
	    	}catch (Exception e) {
				
	    		logger.error("error in verifyLink====="+e);
			}
	        
	    	return ResponseEntity.ok(new UserVerifyResponse(false,MessageConstant.USER_EMAIL_NOT_VERIFIED,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	        
	    }

	 @Operation(summary = "This API will provide the User Mobile Verify Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/getOtpNew",produces = {"application/json"}, consumes = {"application/json","application/text"},
	    method = RequestMethod.POST)
	    public ResponseEntity<Object> getOtpNew(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
	    	logger.info("inside  getOtpNew......+++");
	    	String response="";
	    	UserEntity userEntity=null;
	    	UserNewOtpResponse userNewOtpResponse;
	    	try {    		
	    		String authToken=request.getHeader("Authorization");
	    		
	    		String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
	    		UserOtpRequest userReq= EncryptionDecriptionUtil.convertFromJson(decript, UserOtpRequest.class);
	    		
	    		userEntity=userService.checkUserMobile(userReq.getMobile());
	    		if(userEntity!=null && userEntity.getStatus()==1) {
	    			String orderId="";
	    			//if(userEntity!=null && userEntity.getStatus()==MessageConstant.ONE ) {
	    			if(applicationConstantConfig.otpLessSenderClientEnable.equalsIgnoreCase("N")) {
	    				//orderId="444444";
	    				response="{\"orderId\":\"44444\"}";
	    			}else {
	    				response=userService.sendSmsOtpNew(userReq.getMobile());
	    			}
	    			
	    			//response="{\"errCode\":\"\",\"errDes\":\"\",\"txn\":\"NHA:53029a89-ae73-4e52-bdfc-0f47d237a6fc\",\"ts\":\"2024-02-14T15:12:24.240+05:24\",\"status\":\"true\"}";
	    			if(!ObjectUtils.isEmpty(response)) {
						JSONObject demoRes= new JSONObject(response);
						
						if(demoRes.has("orderId")) {
							orderId=demoRes.isNull("orderId")?"": demoRes.getString("orderId");
							userNewOtpResponse=new UserNewOtpResponse(true,MessageConstant.OTP_SENT,orderId,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
							String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userNewOtpResponse);
							EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
							return ResponseEntity.ok(jsonEncriptObject);
							
						}else {
							orderId=demoRes.isNull("message")?"": demoRes.getString("message");
							userNewOtpResponse=new UserNewOtpResponse(false,MessageConstant.OTP_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
							String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userNewOtpResponse);
							EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
							return ResponseEntity.ok(jsonEncriptObject);
						}

					}else {
						userNewOtpResponse=new UserNewOtpResponse(false,MessageConstant.OTP_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
						String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userNewOtpResponse);
						EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
						return ResponseEntity.ok(jsonEncriptObject);
					}
	    		}else if(userEntity!=null && userEntity.getStatus()==0) {
	    			userNewOtpResponse=new UserNewOtpResponse(false,MessageConstant.USER_DEACTIVE,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userNewOtpResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}else {
	    			userNewOtpResponse=new UserNewOtpResponse(false,MessageConstant.MOBILENOTEXIST,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userNewOtpResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	 
	    	}catch (Exception e) {
				e.printStackTrace();
	    		// TODO: handle exception
	    		logger.error("error in getOtp====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		userNewOtpResponse=new UserNewOtpResponse(false,MessageConstant.MOBILENOTEXIST,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
				String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userNewOtpResponse);
				jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				// TODO: handle exception
			}
    	    return ResponseEntity.ok(jsonEncriptObject);
	    }

	 @Operation(summary = "This API will provide the User Mobile Verify Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/verifyOtpNew",produces = {"application/json"}, consumes = {"application/json","application/text"},
	    method = RequestMethod.POST)
	    public ResponseEntity<Object> verifyOtpNew(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
	    	logger.info("inside token verifyOtp+++");
	    	String response="";
	    	String message="";
	    	UserEntity userEntity=null;
	    	boolean isValid=false;
	    	UserOtpVerifyResponse userOtpVerifyResponse;
	    	try {
	    		
	    		// write code here
	    		String authToken=request.getHeader("Authorization");
	    		
	    		String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
	    		UserOtpRequest userReq= EncryptionDecriptionUtil.convertFromJson(decript, UserOtpRequest.class);
	    		
	    		userEntity=userService.checkUserMobile(userReq.getMobile());
	    		if(userEntity!=null && userEntity.getStatus()==1) {
	    			//if(userEntity!=null && userEntity.getStatus()==MessageConstant.ONE ) {
	    			if(applicationConstantConfig.otpLessSenderClientEnable.equalsIgnoreCase("N")) {
	    				response="{\"isOTPVerified\":true}";
	    				
	    			}else {
	    			response=userService.verifySmsOtpNew(userReq.getOrderId(),userReq.getMobile(),userReq.getOtp());
	    			}
	    			if(!ObjectUtils.isEmpty(response)) {
	    				if(!response.startsWith("{")) {
	    					if(response.contains("Link expired")) {
	    						response="Link expired";
	    						response=null;
	    						userOtpVerifyResponse=new UserOtpVerifyResponse(MessageConstant.FALSE,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),userEntity);
	    						String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userOtpVerifyResponse);
	    						EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    						return ResponseEntity.ok(jsonEncriptObject);
	    					}else {
	    						userOtpVerifyResponse=new UserOtpVerifyResponse(MessageConstant.FALSE,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),userEntity);
	    						String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userOtpVerifyResponse);
	    						EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    						return ResponseEntity.ok(jsonEncriptObject);
	    					}
	    				}else {
						JSONObject demoRes= new JSONObject(response);
						if(demoRes.has("isOTPVerified")) {
							isValid=demoRes.isNull("isOTPVerified")?false: demoRes.getBoolean("isOTPVerified");
							if(isValid) {
								userOtpVerifyResponse=new UserOtpVerifyResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),userEntity);
	    						String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userOtpVerifyResponse);
	    						EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    						return ResponseEntity.ok(jsonEncriptObject);
							}else {
								message=demoRes.has("reason")?demoRes.getString("reason"):demoRes.getString("reason");
								userOtpVerifyResponse=new UserOtpVerifyResponse(MessageConstant.FALSE,message,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),userEntity);
	    						String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userOtpVerifyResponse);
	    						EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    						return ResponseEntity.ok(jsonEncriptObject);
							}
						}else {
								message=demoRes.isNull("message")?"": demoRes.getString("message");
								userOtpVerifyResponse=new UserOtpVerifyResponse(MessageConstant.FALSE,message,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),userEntity);
	    						String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userOtpVerifyResponse);
	    						EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    						return ResponseEntity.ok(jsonEncriptObject);
						}
	    			}
					}else {
						userOtpVerifyResponse=new UserOtpVerifyResponse(MessageConstant.FALSE,message,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),userEntity);
						String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userOtpVerifyResponse);
						EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
						return ResponseEntity.ok(jsonEncriptObject);
					}
	    		}else if(userEntity!=null && userEntity.getStatus()==0) {
	    			userOtpVerifyResponse=new UserOtpVerifyResponse(MessageConstant.FALSE,MessageConstant.USER_DEACTIVE,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),userEntity);
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userOtpVerifyResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    		
	    	 
	    	 
	    	}catch (Exception e) {
				
	    		// TODO: handle exception
	    		logger.error("error in verifyOtp====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		userOtpVerifyResponse=new UserOtpVerifyResponse(MessageConstant.FALSE,message,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),userEntity);
				String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userOtpVerifyResponse);
				jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				// TODO: handle exception
			}
    	    return ResponseEntity.ok(jsonEncriptObject);	          
	        
	    }

	 @Operation(summary = "This API will provide the User Mobile Verify Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/getOtpResend",produces = {"application/json"}, consumes = {"application/json","application/text"},
	    method = RequestMethod.POST)
	    public ResponseEntity<Object> getOtpResend(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
	    	logger.info("inside  getOtpNew......+++");
	    	//List<RoleMaster> roleMaster=null;
	    	String response="";
	    	UserEntity userEntity=null;
	    	UserNewOtpResponse userNewOtpResponse;
	    	try {
	    		// write code here
	    		String authToken=request.getHeader("Authorization");
	    		String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
	    		UserOtpRequest userReq= EncryptionDecriptionUtil.convertFromJson(decript, UserOtpRequest.class);
	    		userEntity=userService.checkUserMobile(userReq.getMobile());
	    		if(userEntity!=null) {
	    			//if(userEntity!=null && userEntity.getStatus()==MessageConstant.ONE ) {
	    			response=userService.resendSmsOtp(userReq.getMobile(),userReq.getOrderId());
	    			String orderId="";
	    			if(!ObjectUtils.isEmpty(response)) {

						JSONObject demoRes= new JSONObject(response);
						
						if(demoRes.has("orderId")) {
							orderId=demoRes.isNull("orderId")?"": demoRes.getString("orderId");
							userNewOtpResponse=new UserNewOtpResponse(true,MessageConstant.OTP_SENT,orderId,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
							String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userNewOtpResponse);
							EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
							return ResponseEntity.ok(jsonEncriptObject);							
						}else {
							orderId=demoRes.isNull("message")?"": demoRes.getString("message");
							userNewOtpResponse=new UserNewOtpResponse(false,MessageConstant.OTP_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
							String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userNewOtpResponse);
							EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
							return ResponseEntity.ok(jsonEncriptObject);
						}

					}else {
						userNewOtpResponse=new UserNewOtpResponse(false,MessageConstant.OTP_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
						String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userNewOtpResponse);
						EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
						return ResponseEntity.ok(jsonEncriptObject);
					}
	    		}
	    	 
	    	}catch (Exception e) {
				
	    		logger.error("error in resend getOtp====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		userNewOtpResponse=new UserNewOtpResponse(false,MessageConstant.OTP_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
				String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userNewOtpResponse);
				jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				// TODO: handle exception
			}
    	    return ResponseEntity.ok(jsonEncriptObject);         
	        
	    }

	 
	 @Operation(summary = "This API will provide the User Mobile Verify Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/verifyOtpWithoutUser",produces = {"application/json"}, consumes = {"application/json","application/text"},
	    method = RequestMethod.POST)
	    public ResponseEntity<Object> verifyOtpWithoutUser(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
	    	logger.info("inside token verifyOtp+++");
	    	String response="";
	    	String message="";
	    	boolean isValid=false;
	    	UserOtpVerifyWithoutUserResponse userOtpVerifyWithoutUserResponse;
	    	try {
	    		String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
	    		UserOtpRequest userReq= EncryptionDecriptionUtil.convertFromJson(decript, UserOtpRequest.class);
	    		// write code here
	    		String authToken=request.getHeader("Authorization");
	    			if(applicationConstantConfig.otpLessSenderClientEnable.equalsIgnoreCase("N")) {
	    				response="{\"isOTPVerified\":true}";
	    				
	    			}else {
	    			response=userService.verifySmsOtpNew(userReq.getOrderId(),userReq.getMobile(),userReq.getOtp());
	    			}
	    			if(!ObjectUtils.isEmpty(response)) {

						JSONObject demoRes= new JSONObject(response);
						if(demoRes.has("isOTPVerified")) {
							isValid=demoRes.isNull("isOTPVerified")?false: demoRes.getBoolean("isOTPVerified");
							if(isValid) {
								userOtpVerifyWithoutUserResponse=new UserOtpVerifyWithoutUserResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
								String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userOtpVerifyWithoutUserResponse);
								EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
								return ResponseEntity.ok(jsonEncriptObject);
							}else {
								message=demoRes.has("reason")?demoRes.getString("reason"):demoRes.getString("reason");
								userOtpVerifyWithoutUserResponse=new UserOtpVerifyWithoutUserResponse(MessageConstant.FALSE,message,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
								String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userOtpVerifyWithoutUserResponse);
								EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
								return ResponseEntity.ok(jsonEncriptObject);
							}
						}else {
								message=demoRes.isNull("message")?"": demoRes.getString("message");
								userOtpVerifyWithoutUserResponse=new UserOtpVerifyWithoutUserResponse(MessageConstant.FALSE,message,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
								String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userOtpVerifyWithoutUserResponse);
								EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
								return ResponseEntity.ok(jsonEncriptObject);
						}

					}else {
						userOtpVerifyWithoutUserResponse=new UserOtpVerifyWithoutUserResponse(MessageConstant.FALSE,message,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
						String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userOtpVerifyWithoutUserResponse);
						EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
						return ResponseEntity.ok(jsonEncriptObject);
					}
	    	 
	    	}catch (Exception e) {
				e.printStackTrace();
	    		logger.error("error in verifyOtp====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		userOtpVerifyWithoutUserResponse=new UserOtpVerifyWithoutUserResponse(MessageConstant.FALSE,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
				String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userOtpVerifyWithoutUserResponse);
				jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in verifyOtp====="+e);
			}
    	    return ResponseEntity.ok(jsonEncriptObject);
	          
	    }
	 @Operation(summary = "This API will provide the User Mobile Verify Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/otpWithOutRegister",produces = {"application/json"}, consumes = {"application/json","application/text"},
	    method = RequestMethod.POST)
	    public ResponseEntity<Object> getOtpWithOutRegister(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
	    	logger.info("inside  getOtpWithOutRegister......+++");
	    	String response="";
	    	UserEntity userEntity=null;
	    	UserNewOtpResponse userNewOtpResponse;
	    	try {    		
	    		String authToken=request.getHeader("Authorization");
	    		
	    		String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
	    		UserOtpRequest userReq= EncryptionDecriptionUtil.convertFromJson(decript, UserOtpRequest.class);
	    		
	    		userEntity=userService.checkUserMobile(userReq.getMobile());
	    		if(userEntity==null) {
	    			String orderId="";
	    			//if(userEntity!=null && userEntity.getStatus()==MessageConstant.ONE ) {
	    			if(applicationConstantConfig.otpLessSenderClientEnable.equalsIgnoreCase("N")) {
	    				//orderId="444444";
	    				response="{\"orderId\":\"44444\"}";
	    			}else {
	    				response=userService.sendSmsOtpNew(userReq.getMobile());
	    			}
	    			
	    			//response="{\"errCode\":\"\",\"errDes\":\"\",\"txn\":\"NHA:53029a89-ae73-4e52-bdfc-0f47d237a6fc\",\"ts\":\"2024-02-14T15:12:24.240+05:24\",\"status\":\"true\"}";
	    			if(!ObjectUtils.isEmpty(response)) {
						JSONObject demoRes= new JSONObject(response);
						
						if(demoRes.has("orderId")) {
							orderId=demoRes.isNull("orderId")?"": demoRes.getString("orderId");
							userNewOtpResponse=new UserNewOtpResponse(true,MessageConstant.OTP_SENT,orderId,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
							String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userNewOtpResponse);
							EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
							return ResponseEntity.ok(jsonEncriptObject);
							
						}else {
							orderId=demoRes.isNull("message")?"": demoRes.getString("message");
							userNewOtpResponse=new UserNewOtpResponse(false,MessageConstant.OTP_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
							String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userNewOtpResponse);
							EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
							return ResponseEntity.ok(jsonEncriptObject);
						}

					}else {
						userNewOtpResponse=new UserNewOtpResponse(false,MessageConstant.OTP_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
						String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userNewOtpResponse);
						EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
						return ResponseEntity.ok(jsonEncriptObject);
					}
	    		}else if(userEntity!=null ) {
	    			userNewOtpResponse=new UserNewOtpResponse(false,MessageConstant.USER_EXIST,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userNewOtpResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    	 
	    	}catch (Exception e) {
				e.printStackTrace();
	    		// TODO: handle exception
	    		logger.error("error in getOtpWithOutRegister====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		userNewOtpResponse=new UserNewOtpResponse(false,MessageConstant.OTP_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp());
				String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userNewOtpResponse);
				jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				// TODO: handle exception
			}
 	    return ResponseEntity.ok(jsonEncriptObject);
	    }
	 @Operation(summary = "This API will provide the User Mobile Verify Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/verifyOtpWithOutRegister",produces = {"application/json"}, consumes = {"application/json","application/text"},
	    method = RequestMethod.POST)
	    public ResponseEntity<Object> verifyOtpWithOutRegister(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
	    	logger.info("inside token verifyOtpWithOutRegister+++");
	    	String response="";
	    	String message="";
	    	UserEntity userEntity=null;
	    	boolean isValid=false;
	    	UserOtpVerifyResponse userOtpVerifyResponse;
	    	try {
	    		
	    		// write code here
	    		String authToken=request.getHeader("Authorization");
	    		
	    		String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
	    		UserOtpRequest userReq= EncryptionDecriptionUtil.convertFromJson(decript, UserOtpRequest.class);
	    		
	    		userEntity=userService.checkUserMobile(userReq.getMobile());
	    		if(userEntity==null) {
	    			//if(userEntity!=null && userEntity.getStatus()==MessageConstant.ONE ) {
	    			if(applicationConstantConfig.otpLessSenderClientEnable.equalsIgnoreCase("N")) {
	    				response="{\"isOTPVerified\":true}";
	    				
	    			}else {
	    			response=userService.verifySmsOtpNew(userReq.getOrderId(),userReq.getMobile(),userReq.getOtp());
	    			}
	    			if(!ObjectUtils.isEmpty(response)) {
	    				if(!response.startsWith("{")) {
	    					if(response.contains("Link expired")) {
	    						response="Link expired";
	    						response=null;
	    						userOtpVerifyResponse=new UserOtpVerifyResponse(MessageConstant.FALSE,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),userEntity);
	    						String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userOtpVerifyResponse);
	    						EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    						return ResponseEntity.ok(jsonEncriptObject);
	    					}else {
	    						userOtpVerifyResponse=new UserOtpVerifyResponse(MessageConstant.FALSE,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),userEntity);
	    						String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userOtpVerifyResponse);
	    						EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    						return ResponseEntity.ok(jsonEncriptObject);
	    					}
	    				}else {
						JSONObject demoRes= new JSONObject(response);
						if(demoRes.has("isOTPVerified")) {
							isValid=demoRes.isNull("isOTPVerified")?false: demoRes.getBoolean("isOTPVerified");
							if(isValid) {
								userOtpVerifyResponse=new UserOtpVerifyResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),userEntity);
	    						String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userOtpVerifyResponse);
	    						EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    						return ResponseEntity.ok(jsonEncriptObject);
							}else {
								message=demoRes.has("reason")?demoRes.getString("reason"):demoRes.getString("reason");
								userOtpVerifyResponse=new UserOtpVerifyResponse(MessageConstant.FALSE,message,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),userEntity);
	    						String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userOtpVerifyResponse);
	    						EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    						return ResponseEntity.ok(jsonEncriptObject);
							}
						}else {
								message=demoRes.isNull("message")?"": demoRes.getString("message");
								userOtpVerifyResponse=new UserOtpVerifyResponse(MessageConstant.FALSE,message,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),userEntity);
	    						String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userOtpVerifyResponse);
	    						EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
	    						return ResponseEntity.ok(jsonEncriptObject);
						}
	    			}
					}else {
						userOtpVerifyResponse=new UserOtpVerifyResponse(MessageConstant.FALSE,message,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),userEntity);
						String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userOtpVerifyResponse);
						EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
						return ResponseEntity.ok(jsonEncriptObject);
					}
	    		}else if(userEntity!=null) {
	    			userOtpVerifyResponse=new UserOtpVerifyResponse(MessageConstant.FALSE,MessageConstant.USER_EXIST,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),userEntity);
					String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userOtpVerifyResponse);
					EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
					return ResponseEntity.ok(jsonEncriptObject);
	    		}
	    		
	    	 
	    	 
	    	}catch (Exception e) {
				
	    		// TODO: handle exception
	    		logger.error("error in verifyOtp====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		userOtpVerifyResponse=new UserOtpVerifyResponse(MessageConstant.FALSE,message,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),userEntity);
				String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userOtpVerifyResponse);
				jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				// TODO: handle exception
			}
 	    return ResponseEntity.ok(jsonEncriptObject);	          
	        
	    }
}
