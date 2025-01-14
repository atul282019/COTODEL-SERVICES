package com.cotodel.hrms.auth.server.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cotodel.hrms.auth.server.dto.ExistUserResponse;
import com.cotodel.hrms.auth.server.dto.ExistUserRoleRequest;
import com.cotodel.hrms.auth.server.dto.UserDto;
import com.cotodel.hrms.auth.server.dto.UserManagerDto;
import com.cotodel.hrms.auth.server.dto.UserManagerResponse;
import com.cotodel.hrms.auth.server.dto.UserRequest;
import com.cotodel.hrms.auth.server.dto.UserResponse;
import com.cotodel.hrms.auth.server.dto.UserRoleEditListResponse;
import com.cotodel.hrms.auth.server.dto.UserRoleEditResponse;
import com.cotodel.hrms.auth.server.dto.UserRoleRequest;
import com.cotodel.hrms.auth.server.dto.UserRoleResponse;
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
public class UserSignUpController extends CotoDelBaseController{
	

	
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
				System.out.println("companyId:signup::"+companyId);
				responseToken=userService.userExist(userReq.getMobile(), userReq.getEmail());
				if(!responseToken.equalsIgnoreCase("")) {
					return ResponseEntity.ok(new UserSignUpResponse(MessageConstant.FALSE,MessageConstant.USER_EXIST,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
				}else {
	    	    userEntity=	userService.saveUserDetails(request,userReq);
	    		
	    	    if(userEntity!=null && userEntity.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {	    
	    		 userService.sendEmailToEmployee(userReq);

	    		 return ResponseEntity.ok(new UserSignUpResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	    	    }else {
	    	    	return ResponseEntity.ok(new UserSignUpResponse(MessageConstant.FALSE,userEntity.getResponse(),userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
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
	        
	        return ResponseEntity.ok(new UserSignUpResponse(MessageConstant.FALSE,userEntity.getResponse(),userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	          
	        
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
	    	String response=MessageConstant.PROFILE_FAILED;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				//responseToken=userService.userExist(userReq.getMobile(), userReq.getEmail());
				
				userEntity=	userService.saveUsersBulk(userReq);	    	    
	    	    if(userEntity!=null && userEntity.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {	 
	    	    	return ResponseEntity.ok(new UserSignUpResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	    	    }else {
	    	    	return ResponseEntity.ok(new UserSignUpResponse(MessageConstant.FALSE,userEntity.getResponse(),userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
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
	    	}
	    	catch (Exception e) {
	    		response=MessageConstant.RESPONSE_FAILED;
	    		e.printStackTrace();
	    		logger.error("error in saveUserDetails====="+e);
			}
	        
	        return ResponseEntity.ok(new UserSignUpResponse(MessageConstant.FALSE,response,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	          
	        
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
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/orgExist",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> orgExist(HttpServletRequest request,@Valid @RequestBody UserRequest userReq) {
	    	logger.info("inside get orgExist");
	    	UserEntity userEntity=null;
	    	String responseToken="";
	    	String authToken = "";
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				userEntity=userService.checkOrgExist(userReq.getId());
				
	    		
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
	    @RequestMapping(value = "/get/userDetails",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> userDetails(HttpServletRequest request,@Valid @RequestBody UserRequest userReq) {
	    	logger.info("inside get userDetails");
	    	UserEntity userEntity=null;
	    	String responseToken="";
	    	String authToken = "";
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				userEntity=userService.userDetails(userReq.getMobile(),userReq.getEmail());
				
	    		
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
	    @RequestMapping(value = "/get/userListWithRole",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> userListWithRole(HttpServletRequest request,@Valid @RequestBody UserRoleRequest userRoleRequest) {
	    	logger.info("inside get userListWithRole+++");
	    	List<ExistUserResponse>  list=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				list=userService.getUsersListWithRole(userRoleRequest.getOrgId(),userRoleRequest.getMobile());
				//System.out.println(list);
				if(list!=null && list.size()>0) {
					return ResponseEntity.ok(new UserRoleResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,list,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
				}else {
					return ResponseEntity.ok(new UserRoleResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,list,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp())); 		
	    	   
			}
	    	}catch (Exception e) {
				
	    		e.printStackTrace();
	    		logger.error("error in userListWithRole====="+e);
			}
	        
	        return ResponseEntity.ok(new UserRoleResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,list,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	          
	        
	    }
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/update/editUserRole",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> editUserRole(HttpServletRequest request,@Valid @RequestBody ExistUserRoleRequest existUserRoleRequest) {
	    	logger.info("inside add editUserRole+++");
	    	ExistUserRoleRequest existResponse=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				existResponse=userService.updateUsersRoleList(existUserRoleRequest);
				
				if(existResponse!=null && existResponse.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
					return ResponseEntity.ok(new UserRoleEditListResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,existResponse,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
				}else {
					return ResponseEntity.ok(new UserRoleEditListResponse(MessageConstant.FALSE,existResponse.getResponse(),existResponse,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp())); 		
	    	   
			}
	    	}catch (Exception e) {
				
	    		e.printStackTrace();
	    		logger.error("error in editUserRole====="+e);
			}
	        
	        return ResponseEntity.ok(new UserRoleEditListResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,existResponse,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	          
	        
	    }
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/add/saveUserRole",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> saveUserRole(HttpServletRequest request,@Valid @RequestBody ExistUserResponse existUserResponse) {
	    	logger.info("inside add saveUserRole+++");
	    	ExistUserResponse existResponse=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				existResponse=userService.saveUsersRole(existUserResponse);
				
				if(existResponse!=null) {
					return ResponseEntity.ok(new UserRoleEditResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,existResponse,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
				}else {
					return ResponseEntity.ok(new UserRoleEditResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,existResponse,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp())); 		
	    	   
			}
	    	}catch (Exception e) {
				
	    		e.printStackTrace();
	    		logger.error("error in userListWithRole====="+e);
			}
	        
	        return ResponseEntity.ok(new UserRoleEditResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,existResponse,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	          
	        
	    }
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/delete/deleteUserRole",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> deleteUserRole(HttpServletRequest request,@Valid @RequestBody ExistUserResponse existUserResponse) {
	    	logger.info("inside add deleteUserRole+++");
	    	ExistUserResponse existResponse=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				existResponse=userService.deleteUsersRole(existUserResponse);
				
				if(existResponse!=null && existResponse.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
					return ResponseEntity.ok(new UserRoleEditResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,existResponse,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
				}else {
					return ResponseEntity.ok(new UserRoleEditResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,existResponse,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp())); 		
	    	   
			}
	    	}catch (Exception e) {
				
	    		e.printStackTrace();
	    		logger.error("error in deleteUserRole====="+e);
			}
	        
	        return ResponseEntity.ok(new UserRoleEditResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,existResponse,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	          
	        
	    }
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/userSearch",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> userSearch(HttpServletRequest request,@Valid @RequestBody UserRoleRequest userRoleRequest) {
	    	logger.info("inside get userSearch+++");
	    	List<ExistUserResponse>  list=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				list=userService.searchUsers(userRoleRequest.getOrgId(),userRoleRequest.getUserName(),userRoleRequest.getMobile());
				System.out.println(list);
				if(list!=null && list.size()>0) {
					return ResponseEntity.ok(new UserRoleResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,list,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
				}else {
					return ResponseEntity.ok(new UserRoleResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,list,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp())); 		
	    	   
			}
	    	}catch (Exception e) {
				
	    		e.printStackTrace();
	    		logger.error("error in userSearch====="+e);
			}
	        
	        return ResponseEntity.ok(new UserRoleResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,list,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	          
	        
	    }
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/userManagerList",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> userManagerList(HttpServletRequest request,@Valid @RequestBody UserRoleRequest userRoleRequest) {
	    	logger.info("inside get userManagerList+++");
	    	List<UserManagerDto>  list=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				list=userService.userManagerList(userRoleRequest.getOrgId());
				//System.out.println(list);
				if(list!=null && list.size()>0) {
					return ResponseEntity.ok(new UserManagerResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND,list,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
				}else {
					return ResponseEntity.ok(new UserManagerResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,list,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp())); 		
	    	   
			}
	    	}catch (Exception e) {
				
	    		e.printStackTrace();
	    		logger.error("error in userManagerList====="+e);
			}
	        
	        return ResponseEntity.ok(new UserManagerResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND,list,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	          
	        
	    }
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/userSearchWithMobile",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> userSearchWithMobile(HttpServletRequest request,@Valid @RequestBody UserRoleRequest userRoleRequest) {
	    	logger.info("inside get userSearchWithMobile+++");
	    	List<UserManagerDto>  list=null;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				list=userService.searchUsersWithOutMobile(userRoleRequest.getOrgId(),userRoleRequest.getUserName());
				System.out.println(list);
				if(list!=null && list.size()>0) {
					return ResponseEntity.ok(new UserManagerResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,list,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
				}else {
					return ResponseEntity.ok(new UserManagerResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,list,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp())); 		
	    	   
			}
	    	}catch (Exception e) {
				
	    		e.printStackTrace();
	    		logger.error("error in userSearchWithMobile====="+e);
			}
	        
	        return ResponseEntity.ok(new UserManagerResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,list,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	          
	        
	    }
	 
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/userDetailsWithMobile",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> userDetailsWithMobile(HttpServletRequest request,@Valid @RequestBody UserRequest userReq) {
	    	logger.info("inside get userDetailsWithMobile++");
	    	UserEntity userEntity=null;
	    	String responseToken="";
	    	String authToken = "";
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				userEntity=userService.checkUserMobile(userReq.getMobile());
				
	    		
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
}
