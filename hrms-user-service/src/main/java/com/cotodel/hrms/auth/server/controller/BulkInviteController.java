package com.cotodel.hrms.auth.server.controller;

import java.util.Properties;

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

import com.cotodel.hrms.auth.server.dto.BulkInviteRequest;
import com.cotodel.hrms.auth.server.dto.UserBulkUploadRequest;
import com.cotodel.hrms.auth.server.dto.UserBulkUploadResponse;
import com.cotodel.hrms.auth.server.dto.UserSignUpResponse;
import com.cotodel.hrms.auth.server.entity.UserEntity;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.multi.datasource.SetDatabaseTenent;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.BulkInviteService;
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
//import jakarta.mail.Authenticator;
//import jakarta.mail.Message;
//import jakarta.mail.MessagingException;
//import jakarta.mail.PasswordAuthentication;
//import jakarta.mail.Session;
//import jakarta.mail.Transport;
//import jakarta.mail.internet.InternetAddress;
//import jakarta.mail.internet.MimeMessage;

/**
 * @author vinay
 */
@RestController
@RequestMapping("/Api")
public class BulkInviteController {
	
	private static final Logger logger = LoggerFactory.getLogger(BulkInviteController.class);
    
	@Autowired
	BulkInviteService bulkInviteService;
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/sendBulkEmail",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> sendBulkEmail(HttpServletRequest request,@Valid @RequestBody EncriptResponse enResponse) {
	    	logger.info("inside get sendBulkEmail+++");
	    	UserEntity userEntity=null;
	    	String responseToken="";
	    	String authToken = "";
	    	UserSignUpResponse userSignUpResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				BulkInviteRequest bulkInviteRequest= EncryptionDecriptionUtil.convertFromJson(decript, BulkInviteRequest.class);
				bulkInviteService.sendEmailToEmployee(bulkInviteRequest);
				userSignUpResponse=new UserSignUpResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken);
				String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userSignUpResponse);
				EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
				return ResponseEntity.ok(jsonEncriptObject);				
	    	}catch (Exception e) {
				
	    		e.printStackTrace();
	    		logger.error("error in get/sendBulkEmail====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		userSignUpResponse=new UserSignUpResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken);
				String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userSignUpResponse);
				jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in get/sendBulkEmail====="+e);
			}
    	    return ResponseEntity.ok(jsonEncriptObject);          
	        
	    }

	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/add/userBulkUpload",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> userBulkUpload(HttpServletRequest request,@Valid @RequestBody UserBulkUploadRequest userBulkUploadRequest) {
	    	logger.info("inside get userBulkUpload+++");
	    	UserBulkUploadRequest response=null;
	    	String responseToken="";
	    	String authToken = "";
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				
				response=bulkInviteService.saveBulkUpload(userBulkUploadRequest);
				if(response!=null && response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
					return ResponseEntity.ok(new UserBulkUploadResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
				}else {
					return ResponseEntity.ok(new UserBulkUploadResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
				}
	    	}catch (Exception e) {
				
	    		e.printStackTrace();
	    		logger.error("error in userBulkUpload====="+e);
			}
	        
	        return ResponseEntity.ok(new UserBulkUploadResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,response,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken));
	          
	        
	    }
	 @Operation(summary = "This API will provide the Save User Details ", security = {
	    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	    @ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
	    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
	    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
	    @RequestMapping(value = "/get/sendBulkEmailNew",produces = {"application/json"}, 
	    consumes = {"application/json","application/text"},method = RequestMethod.POST)
	    public ResponseEntity<Object> sendBulkEmailNew(HttpServletRequest request,@Valid @RequestBody BulkInviteRequest bulkInviteRequest) {
	    	logger.info("inside get sendBulkEmailNew+++");
	    	UserEntity userEntity=null;
	    	String responseToken="";
	    	String authToken = "";
	    	UserSignUpResponse userSignUpResponse;
	    	try {	    		
	    		String companyId = request.getHeader("companyId");
				SetDatabaseTenent.setDataSource(companyId);
				//EncriptResponse enResponse
//				String decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
//				BulkInviteRequest bulkInviteRequest= EncryptionDecriptionUtil.convertFromJson(decript, BulkInviteRequest.class);
				bulkInviteService.sendEmailToEmployeeNew(bulkInviteRequest);
				userSignUpResponse=new UserSignUpResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken);
				String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userSignUpResponse);
				EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
				return ResponseEntity.ok(jsonEncriptObject);				
	    	}catch (Exception e) {
				
	    		e.printStackTrace();
	    		logger.error("error in get/sendBulkEmail====="+e);
			}
	    	EncriptResponse jsonEncriptObject=new EncriptResponse();
	    	try {
	    		userSignUpResponse=new UserSignUpResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,userEntity,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp(),authToken);
				String jsonEncript =  EncryptionDecriptionUtil.convertToJson(userSignUpResponse);
				jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(jsonEncript, applicationConstantConfig.apiSignaturePublicPath);
			} catch (Exception e) {
				logger.error("error in get/sendBulkEmail====="+e);
			}
 	    return ResponseEntity.ok(jsonEncriptObject);          
	        
	    }
//		    public static void main(String[] args) {
//		    	Properties props=new Properties();
//		        final String username = "payments@cotodel.com";
//		        final String password = "Recon@012025!"; // Use App Password if MFA is enabledProperties props = new Properties();
//		        props.put("mail.smtp.auth", "true");
//		        props.put("mail.smtp.starttls.enable", "true");
//		        props.put("mail.smtp.host", "smtp.office365.com");
//		        props.put("mail.smtp.port", "587");
//
//		        Session session = Session.getInstance(props, new Authenticator() {
//		            protected PasswordAuthentication getPasswordAuthentication() {
//		                return new PasswordAuthentication(username, password);
//		            }
//		        });
//
//		        try {
//		            Message message = new MimeMessage(session);
//		            message.setFrom(new InternetAddress(username));
//		            message.setRecipients(
//		                Message.RecipientType.TO,
//		                InternetAddress.parse("fakhruddeen.mca@gmail.com")
//		            );
//		            message.setSubject("Test Email from Java");
//		            message.setText("This is an automated email sent from a Java app using Outlook SMTP.");
//
//		            Transport.send(message);
//		            System.out.println("Email sent successfully.");
//		        } catch (MessagingException e) {
//		            e.printStackTrace();
//		        }
//		    }
		}

