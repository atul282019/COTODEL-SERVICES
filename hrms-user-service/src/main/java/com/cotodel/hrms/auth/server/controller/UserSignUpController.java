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

import com.cotodel.hrms.auth.server.dto.UserDto;
import com.cotodel.hrms.auth.server.dto.UserRequest;
import com.cotodel.hrms.auth.server.dto.UserResponse;
import com.cotodel.hrms.auth.server.dto.UserSignUpResponse;
import com.cotodel.hrms.auth.server.entity.UserEntity;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.multi.datasource.SetDatabaseTenent;
import com.cotodel.hrms.auth.server.service.UserService;
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
public class UserSignUpController {
	

	
	private static final Logger logger = LoggerFactory.getLogger(UserSignUpController.class);
    
	@Autowired
	UserService userService;
	
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/saveUserDetails",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> saveUserDetails(HttpServletRequest request,@Valid @RequestBody UserRequest userReq) {
	    	logger.info("inside get saveUserDetails+++");
	    	UserEntity userEntity=null;
	    	String responseToken="";
	    	String authToken = "";
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				responseToken=userService.userExist(userReq.getMobile(), userReq.getEmail());
				if(!responseToken.equalsIgnoreCase("")) {
					return ResponseEntity.ok(new UserSignUpResponse(MessageConstant.FALSE,MessageConstant.USER_EXIST,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
				}else {
	    	    userEntity=	userService.saveUserDetails(userReq);
	    		
	    	    if(userEntity!=null) {	    
	    		 userService.sendEmailToEmployee(userReq);

	    		 return ResponseEntity.ok(new UserSignUpResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	    	    }
			}
	    	}catch (Exception e) {
				
	    		e.printStackTrace();
	    		logger.error("error in saveUserDetails====="+e);
			}
	        
	        return ResponseEntity.ok(new UserSignUpResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	          
	        
	    }

	 
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/checkUserDetails",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> checkEligibility(@Valid @RequestBody UserRequest userReq) {
	    	logger.info("inside get checkUserDetails");
	    	UserEntity userEntity=null;
	    	try {
	    		
	    		
	    		
	    	 userEntity=	userService.checkUserDetails(userReq.getUsername());
	    		
	    	 if(userEntity!=null)
	    		 return ResponseEntity
	 	                .ok(userEntity);
	    	 
	    	 
	    	}catch (Exception e) {
				
	    		// TODO: handle exception
			}
	        
	        return ResponseEntity
	                .ok(null);
	          
	        
	    }


	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/add/saveUsers",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> saveUsers(HttpServletRequest request,@Valid @RequestBody UserRequest userReq) {
	    	logger.info("inside get saveUserDetails+++");
	    	UserEntity userEntity=null;
	    	String responseToken="";
	    	String authToken = "";
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				responseToken=userService.userExist(userReq.getMobile(), userReq.getEmail());
				if(!responseToken.equalsIgnoreCase("")) {
					return ResponseEntity.ok(new UserSignUpResponse(MessageConstant.FALSE,MessageConstant.USER_EXIST,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
				}else {
	    	    userEntity=	userService.saveUsers(userReq);
	    		
	    	    if(userEntity!=null) {
	    	    	try {
	    	    		userService.sendEmailToEmployee(userReq);
					} catch (Exception e) {
						// TODO: handle exception
					}
	    		 

	    		 return ResponseEntity.ok(new UserSignUpResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	    	    }
			}
	    	}catch (Exception e) {
				
	    		e.printStackTrace();
	    		logger.error("error in saveUserDetails====="+e);
			}
	        
	        return ResponseEntity.ok(new UserSignUpResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	          
	        
	    }


	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/userList",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> userList(HttpServletRequest request,@Valid @RequestBody UserRequest userReq) {
	    	logger.info("inside get saveUserDetails+++");
	    	List<UserDto>  list=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				list=userService.getUsersList(userReq.getEmployerid());
				if(list!=null && list.size()>0) {
					return ResponseEntity.ok(new UserResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,list,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
				}else {
					return ResponseEntity.ok(new UserResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,list,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp())); 		
	    	   
			}
	    	}catch (Exception e) {
				
	    		e.printStackTrace();
	    		logger.error("error in saveUserDetails====="+e);
			}
	        
	        return ResponseEntity.ok(new UserResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,list,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	          
	        
	    }
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/update/updateUsers",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> updateUsers(HttpServletRequest request,@Valid @RequestBody UserRequest userReq) {
	    	logger.info("inside get updateUsers");
	    	UserEntity userEntity=null;
	    	String authToken = "";
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
	    	    userEntity=	userService.updateUsers(userReq);	    	    
	    	    if(userEntity!=null) {	 
	    	    	return ResponseEntity.ok(new UserSignUpResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	    	    }else {
	    	    	return ResponseEntity.ok(new UserSignUpResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	    	    }
			
	    	}catch (Exception e) {
				
	    		e.printStackTrace();
	    		logger.error("error in updateUserDetails====="+e);
			}
	        
	        return ResponseEntity.ok(new UserSignUpResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	          
	        
	    }

	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/add/saveUsersBulk",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> saveUsersBulk(HttpServletRequest request,@Valid @RequestBody UserRequest userReq) {
	    	logger.info("inside get saveUsersBulk");
	    	UserEntity userEntity=null;
	    	String responseToken="";
	    	String authToken = "";
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				//responseToken=userService.userExist(userReq.getMobile(), userReq.getEmail());
				
				userEntity=	userService.saveUsersBulk(userReq);	    	    
	    	    if(userEntity!=null) {	 
	    	    	return ResponseEntity.ok(new UserSignUpResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	    	    }else {
	    	    	return ResponseEntity.ok(new UserSignUpResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	    	    }
//				if(!responseToken.equalsIgnoreCase("")) {
//					return ResponseEntity.ok(new UserSignUpResponse(MessageConstant.FALSE,MessageConstant.USER_EXIST,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
//				}else {
//	    	    userEntity=	userService.saveUsers(userReq);
//	    	   
//
//	    		 return ResponseEntity.ok(new UserSignUpResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
//	    	    }
//			}
	    	}catch (Exception e) {
				
	    		e.printStackTrace();
	    		logger.error("error in saveUserDetails====="+e);
			}
	        
	        return ResponseEntity.ok(new UserSignUpResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	          
	        
	    }
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/add/confirmUsersBulk",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> confirmUsersBulk(HttpServletRequest request,@Valid @RequestBody UserRequest userReq) {
	    	logger.info("inside get confirmUsersBulk");
	    	UserEntity userEntity=null;
	    	String responseToken="";
	    	String authToken = "";
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				userEntity=	userService.confirmUsersBulk(userReq);	    	    
	    	    if(userEntity!=null) {	 
	    	    	return ResponseEntity.ok(new UserSignUpResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	    	    }else {
	    	    	return ResponseEntity.ok(new UserSignUpResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	    	    }

	    	}catch (Exception e) {
				
	    		e.printStackTrace();
	    		logger.error("error in saveUserDetails====="+e);
			}
	        
	        return ResponseEntity.ok(new UserSignUpResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	          
	        
	    }
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/add/saveUsersWithOutMail",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> saveUsersWithOutMail(HttpServletRequest request,@Valid @RequestBody UserRequest userReq) {
	    	logger.info("inside get saveUsersWithOutMail");
	    	UserEntity userEntity=null;
	    	String responseToken="";
	    	String authToken = "";
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				responseToken=userService.userExist(userReq.getMobile(), userReq.getEmail());
				if(!responseToken.equalsIgnoreCase("")) {
					return ResponseEntity.ok(new UserSignUpResponse(MessageConstant.FALSE,MessageConstant.USER_EXIST,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
				}else {
	    	    userEntity=	userService.saveUsers(userReq);
	    		
	    	    if(userEntity!=null) {
	    	    	return ResponseEntity.ok(new UserSignUpResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	    	    }
			}
	    	}catch (Exception e) {
				
	    		e.printStackTrace();
	    		logger.error("error in saveUserDetails====="+e);
			}
	        
	        return ResponseEntity.ok(new UserSignUpResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	          
	        
	    }
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/add/saveUsersWithOutMailNew",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> saveUsersWithOutMailNew(HttpServletRequest request,@Valid @RequestBody UserRequest userReq) {
	    	logger.info("inside get saveUsersWithOutMail");
	    	UserEntity userEntity=null;
	    	String responseToken="";
	    	String authToken = "";
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				userEntity=userService.userExistNew(userReq.getMobile(), userReq.getEmail());
				if(userEntity!=null) {
					return ResponseEntity.ok(new UserSignUpResponse(MessageConstant.TRUE,MessageConstant.USER_EXIST,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
				}else {
					userEntity=	userService.saveUsers(userReq);
	    		
	    	    if(userEntity!=null) {
	    	    	return ResponseEntity.ok(new UserSignUpResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	    	    }
			}
	    	}catch (Exception e) {
				
	    		e.printStackTrace();
	    		logger.error("error in saveUserDetails====="+e);
			}
	        
	        return ResponseEntity.ok(new UserSignUpResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	          
	        
	    }


}
