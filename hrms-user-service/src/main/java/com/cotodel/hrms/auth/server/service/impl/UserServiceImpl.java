package com.cotodel.hrms.auth.server.service.impl;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.auth.server.controller.CotoDelBaseController;
import com.cotodel.hrms.auth.server.dao.EmployerDetailsDao;
import com.cotodel.hrms.auth.server.dao.SignUpDao;
import com.cotodel.hrms.auth.server.dao.UserDetailsDao;
import com.cotodel.hrms.auth.server.dao.UserRoleMapperDao;
import com.cotodel.hrms.auth.server.dao.UserRoleMapperHistoryDao;
import com.cotodel.hrms.auth.server.dao.UserRoleMasterDao;
import com.cotodel.hrms.auth.server.dto.DeleteUserRoleRequest;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingRequest;
import com.cotodel.hrms.auth.server.dto.ExistUserResponse;
import com.cotodel.hrms.auth.server.dto.ExistUserRoleRequest;
import com.cotodel.hrms.auth.server.dto.ReputeEmployeeDetails;
import com.cotodel.hrms.auth.server.dto.RoleDto;
import com.cotodel.hrms.auth.server.dto.UpdateReputeStatusRequest;
import com.cotodel.hrms.auth.server.dto.UserDetailsDto;
import com.cotodel.hrms.auth.server.dto.UserDto;
import com.cotodel.hrms.auth.server.dto.UserManagerDto;
import com.cotodel.hrms.auth.server.dto.UserRequest;
import com.cotodel.hrms.auth.server.dto.UserRoleDto;
import com.cotodel.hrms.auth.server.dto.UserRoleMapperDto;
import com.cotodel.hrms.auth.server.entity.EmployerDetailsEntity;
import com.cotodel.hrms.auth.server.entity.RoleMasterEntity;
import com.cotodel.hrms.auth.server.entity.UserEmpEntity;
import com.cotodel.hrms.auth.server.entity.UserEntity;
import com.cotodel.hrms.auth.server.entity.UserRoleMapperEntity;
import com.cotodel.hrms.auth.server.entity.UserRoleMapperHistoryEntity;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.UserService;
import com.cotodel.hrms.auth.server.util.CaptchaSession;
import com.cotodel.hrms.auth.server.util.CommonUtility;
import com.cotodel.hrms.auth.server.util.CommonUtils;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;
import com.cotodel.hrms.auth.server.util.ValidateConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public class UserServiceImpl extends CotoDelBaseController implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	UserDetailsDao userDetailsDao;
	
	@Autowired
	SignUpDao signUpDao;
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
	@Autowired
	EntityManager entityManager;
	
	@Autowired
	UserRoleMapperDao userRoleMapperDao;
	
	@Autowired
	UserRoleMapperHistoryDao userRoleMapperHistoryDao;
	
	@Autowired
	UserRoleMasterDao userRoleMasterDao;
	
	@Autowired
	public EmployerDetailsDao employerDetailsDao;
	
	
	private Map<String, Boolean> captcaValidationMap = new HashMap<String, Boolean>();
	HashMap<String, Boolean> captchaMap = new HashMap<String, Boolean>();
	CaptchaSession csotp = new CaptchaSession();
	
	@Override
	@Transactional
	public UserEntity saveUserDetails(HttpServletRequest request,UserRequest user) {
		// TODO Auto-generated method stub
		
		//add captcha
		String captchaSecurity="";
		UserEntity userEntity=new UserEntity();
		String response=MessageConstant.RESPONSE_FAILED;
		user.setResponse(response);
		try {
//			captchaSecurity = (String) session.getAttribute("CAPTCHA");
//			if(request.getSession(true).getAttribute("CAPTCHA")!=null){
//				captchaSecurity=(String) request.getSession(true).getAttribute("CAPTCHA");
//			}
//			logger.info("Session Captcha=="+captchaSecurity);
//			logger.info("User Enter Captcha=="+user.getCaptcha());
//			
//			if (validateCaptcha(request, user.getCaptcha(),captchaSecurity)) {
				
				UserEntity userDetails= new UserEntity();
				UserEmpEntity userEmpEntity= new UserEmpEntity();
				//CopyUtility.copyProperties(userDetails, user);
				CopyUtility.copyProperties(user,userDetails);
				Date date = new Date();
				LocalDate localDate =date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				userDetails.setCreated_date(localDate);
				if(user.isErupistatus()) {
					userDetails.setRole_id(MessageConstant.ERUPI_ADMIN_ROLE);
				}else {
					userDetails.setRole_id(MessageConstant.SIGN_UP_ROLE);
				}
				//userDetails.setRole_id(MessageConstant.REPUTE_ROLE);
				userDetails.setStatus(1);
				userEntity=userDetailsDao.saveUserDetails(userDetails);
				//userEmpEntity.setUser_id(UserEntity1.getId());
				userEmpEntity.setUser_id(userEntity.getId());
				userEmpEntity.setStatus(userEntity.getStatus());
				userEmpEntity.setCreated_by(userEntity.getMobile());
				userEmpEntity.setCreated_date(localDate);
				userEmpEntity=userDetailsDao.saveUserEmpEntity(userEmpEntity);
				
//				UserRoleMapperEntity userRoleMapperEntity=new UserRoleMapperEntity();
//				userRoleMapperEntity.setMobile(UserEntity1.getMobile());
//				userRoleMapperEntity.setOrgId(UserEntity1.getId());
//				userRoleMapperEntity.setStatus(1);
//				userRoleMapperEntity.setRoleId(UserEntity1.getRole_id());
//				userRoleMapperEntity.setCreatedBy(UserEntity1.getUsername());
//				userRoleMapperEntity.setCreationDate(LocalDateTime.now());
//				userRoleMapperDao.saveUserRoleDetails(userRoleMapperEntity);
				response=MessageConstant.RESPONSE_SUCCESS;
				userEntity.setResponse(response);
//			}else {
//				response=MessageConstant.INVALID_CAPTCHA;
//				userEntity.setResponse(response);
//			}
		
		}catch (Exception e) {
			response=MessageConstant.RESPONSE_FAILED;
			userEntity.setResponse(response);
		}
		return userEntity;
	}

	
	

//	public UserEntity saveUserDetails1(UserRequest user) {
//		// TODO Auto-generated method stub
//		
//		
//		
//		UserEntity userDetails= new UserEntity();
//		UserEmpEntity userEmpEntity= new UserEmpEntity();
//		//CopyUtility.copyProperties(userDetails, user);
//		CopyUtility.copyProperties(user,userDetails);
//		Date date = new Date();
//		LocalDate localDate =date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//		userDetails.setCreated_date(localDate);
//		userDetails.setRole_id(MessageConstant.SIGN_UP_ROLE);
//		UserEntity UserEntity1=userDetailsDao.saveUserDetails(userDetails);
//		//userEmpEntity.setUser_id(UserEntity1.getId());
////		userEmpEntity.setUser(UserEntity1);
////		userEmpEntity.setStatus(UserEntity1.getStatus());
////		
////		userEmpEntity.setCreated_date(localDate);
////		userDetailsDao.saveUserEmpEntity(userEmpEntity);
//		
//		
//		return UserEntity1;
//	}

	@Override
	public UserEmpEntity saveUserEmpEntity(UserEmpEntity userEmpEntity) {
		// TODO Auto-generated method stub
		return userDetailsDao.saveUserEmpEntity(userEmpEntity);
	}

	@Override
	public UserEntity checkUserDetails(String userName) {
		// TODO Auto-generated method stub
		return userDetailsDao.checkUserDetails(userName);
	}

	@Override
	public UserEntity checkUserMobile(String userMobile) {
		// TODO Auto-generated method stub
		return userDetailsDao.checkUserMobile(userMobile);
	}

	@Override
	public UserEntity checkUserEmail(String userEmail) {
		// TODO Auto-generated method stub
		return userDetailsDao.checkUserEmail(userEmail);
	}
	
	
	@Override
	public UserEntity getByUserName(String userName) {
		// TODO Auto-generated method stub
		return userDetailsDao.getByUserName(userName);
	}

	
	@Override
	public void sendEmailToEmployee(UserRequest req) {
		
		 // Set up mail server properties
	        Properties properties = new Properties();
	        properties.put("mail.smtp.host", "smtp.gmail.com");
	        properties.put("mail.smtp.port", "587");
	        properties.put("mail.smtp.auth", "true");
	        properties.put("mail.smtp.starttls.enable", "true");
		  Session session = Session.getInstance(properties, new Authenticator() {
	            @Override
	            protected PasswordAuthentication getPasswordAuthentication() {
	            	return new PasswordAuthentication("cotodel.info@gmail.com", "zxvg tryo uhdh akgf");
	            	//return new PasswordAuthentication("cotodel917@gmail.com", "CotoDel@123");
	            	
	            }
	        });
			
			Message msg = new MimeMessage(session);
				try {
					msg.setHeader("Content-Type", "text/plain; charset=UTF-8");
					msg.setFrom(new InternetAddress(req.getEmail(), false));
					msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(req.getEmail()));
					msg.setSubject("User Registration Request Verification");
					msg.setContent("Verify Sigin", "text/html");
					msg.setSentDate(new Date());
					byte[] bytes = req.getMobile().getBytes(StandardCharsets.UTF_8);
				    String encoded = DatatypeConverter.printBase64Binary(bytes);
				    byte[] byt = req.getEmail().getBytes(StandardCharsets.UTF_8);
				    String emailbyt = DatatypeConverter.printBase64Binary(byt);
				    
				    String confirmationUrl = applicationConstantConfig.emailVerifyUrl+"?token="+encoded.replaceAll("==","")+"/"+emailbyt;
				    String emailBody = "Click the link to verify your email: " + confirmationUrl;
				    
					String link="<p></p><a href=" +applicationConstantConfig.emailVerifyUrl+"/"+emailbyt.replaceAll("==","")+"/"+encoded.replaceAll("==","") +"><h3>Please click here to verify....<h3></a>";
					MimeBodyPart messageBodyPart = new MimeBodyPart();
					//String password =generatePassword(8);
					messageBodyPart.setContent(link, "text/html");
					Multipart multipart = new MimeMultipart();
					multipart.addBodyPart(messageBodyPart);
//					MimeBodyPart attachPart = new MimeBodyPart();
//					attachPart.attachFile(attachement);
//					multipart.addBodyPart(attachPart);
					msg.setContent(multipart);
					Transport.send(msg);
					System.out.println("verification mail sended successfully to :"+req.getEmail());

				} catch (MessagingException e) {
					e.printStackTrace();
				}
			
			}

	@Override
	public String getToken(String comId) {
		return CommonUtility.getTokenRequest(null,"",comId,applicationConstantConfig.getTokenUrl);
		
	}

	
	
	@Override
	public String verifyEmailUpdate(String email) {
		UserEntity userDetails= null;
		try {
			//check user exist
			userDetails = userDetailsDao.checkUserEmail(email);
			if(userDetails!=null) {
				
						
			userDetails.setStatus(1);
			userDetails.setEmail_verify_status(1);
			userDetails.setEmail_verify_date(LocalDate.now());
			//logger.info("updateUserStatus object going to save------"+userDetails.toString());
			
			userDetails = userDetailsDao.saveUserDetails(userDetails);
			
			if(userDetails!=null && userDetails.getStatus()==1) {
				return MessageConstant.RESPONSE_SUCCESS;
			}else {
				return MessageConstant.RESPONSE_FAILED;	
			}		
			}
		} catch (Exception e) {
			e.printStackTrace();
			//logger.error("error in updateUserStatus----"+e);
			return MessageConstant.RESPONSE_FAILED;
		}finally {
			userDetails= null;
		}		
		return MessageConstant.RESPONSE_FAILED;
	}

	

	@Override
	public String sendSmsOtp(String authToken,String mobile) {
		return CommonUtility.userRequest(authToken,sendSmsOtpRequest(mobile),applicationConstantConfig.otpSenderUrl);
		
	}

	public  String sendSmsOtpRequest(String mobile){
		JSONObject data= new JSONObject();
		data.put("mobile", mobile);
		data.put("templateid", applicationConstantConfig.templateId);
		logger.info("send SMS OTP Request"+data);
		return data.toString();
	}


	public  String verifySmsOtp(String authToken,String mobile,String otp) {
		return  CommonUtility.userRequest(authToken,verifySmsOtpRequest(mobile,otp), applicationConstantConfig.otpVerifyUrl);
	}
	
	public  String verifySmsOtpRequest(String mobile,String otp){
		JSONObject data= new JSONObject();
		data.put("mobile", mobile);
		data.put("otp", otp);
		data.put("templateid", applicationConstantConfig.templateVerifyId);
		logger.info("Verify SMS OTP Request JSON========"+data.toString());
		return data.toString();
	}

	@Override
	public String userExist(String mobile, String email) {
		List<UserEntity> userDetail = userDetailsDao.getUser(mobile,email);
		if(userDetail!=null && userDetail.size()>0) {
			return MessageConstant.USER_EXIST;
		}
		return "";
	}

	@Override
	public String sendSmsOtpNew(String mobile) {
		// TODO Auto-generated method stub
		return  CommonUtility.userSmsRequest(applicationConstantConfig.otpLessSenderClientId,applicationConstantConfig.otpLessSenderClientSecret,smsOtpRequest(mobile),applicationConstantConfig.otpLessSenderUrl);
	}
	
	public  String smsOtpRequest(String mobile){
		JSONObject data= new JSONObject();
		data.put("phoneNumber", "+91"+mobile);
		data.put("orderId", randomNumber());
		data.put("hash", "");
		data.put("otpLength", applicationConstantConfig.otpLengthSenderToken);
		data.put("channel", applicationConstantConfig.channelSenderToken);
		data.put("expiry", applicationConstantConfig.expirySenderToken);
		logger.info("send SMS OTP Request"+data);
		return data.toString();
	}		
	
	public  int randomNumber(){
		Random random = new Random();
		int randomNumber = random.nextInt(90000) + 10000;
		return randomNumber;
	}

	@Override
	public String verifySmsOtpNew(String oderID, String mobile, String otp) {
		// TODO Auto-generated method stub
		return  CommonUtility.userSmsRequest(applicationConstantConfig.otpLessSenderClientId,applicationConstantConfig.otpLessSenderClientSecret,smsOtpVerifyRequest(oderID,mobile,otp),applicationConstantConfig.otpLessVerifyUrl);
	}
	
	public  String smsOtpVerifyRequest(String orderId,String mobile,String otp){
		JSONObject data= new JSONObject();
		data.put("phoneNumber", "+91"+mobile);
		data.put("orderId", orderId);
		data.put("otp", otp);
		logger.info("verify SMS OTP Request"+data);
		return data.toString();
	}

	@Override
	public String resendSmsOtp(String mobile, String orderId) {
		
		return CommonUtility.userSmsRequest(applicationConstantConfig.otpLessSenderClientId,applicationConstantConfig.otpLessSenderClientSecret,smsResendOtpRequest(mobile,orderId),applicationConstantConfig.otpLessResenderUrl);
	}
	
	public  String smsResendOtpRequest(String mobile,String orderId){
		JSONObject data= new JSONObject();
		data.put("phoneNumber", "+91"+mobile);
		data.put("orderId", orderId);
		data.put("hash", "");
		data.put("otpLength", applicationConstantConfig.otpLengthSenderToken);
		data.put("channel", applicationConstantConfig.channelSenderToken);
		data.put("expiry", applicationConstantConfig.expirySenderToken);
		logger.info("resend send SMS OTP Request"+data);
		return data.toString();
	}

	@Override
	public UserEntity saveUsers(UserRequest user) {
		
		UserEntity userDetails= new UserEntity();
		UserEmpEntity userEmpEntity= new UserEmpEntity();
		UserEntity UserEntity1=null;
		String response=MessageConstant.RESPONSE_FAILED;
			try {
				//userDetails=userDetailsDao.checkUserMobile(user.getMobile());
				CopyUtility.copyProperties(user,userDetails);
				Date date = new Date();
				LocalDate localDate =date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				userDetails.setCreated_date(localDate);
				userDetails.setRole_id(MessageConstant.USER_ROLE);
				//userDetails.setMapperFlag("Y");
				userDetails.setStatus(1);
				UserEntity1=userDetailsDao.saveUserDetails(userDetails);
				userEmpEntity.setUser_id(UserEntity1.getId());
				//userEmpEntity.setUser(UserEntity1);
				userEmpEntity.setStatus(UserEntity1.getStatus());
				userEmpEntity.setCreated_by(UserEntity1.getMobile());
				userEmpEntity.setCreated_date(localDate);
				userDetailsDao.saveUserEmpEntity(userEmpEntity);
				response=MessageConstant.RESPONSE_SUCCESS;
//				UserRoleMapperEntity userRoleMapperEntity=new UserRoleMapperEntity();
//				userRoleMapperEntity.setMobile(UserEntity1.getMobile());
//				long empid = UserEntity1.getEmployerid();
//				userRoleMapperEntity.setOrgId(empid);
//				userRoleMapperEntity.setStatus(1);
//				userRoleMapperEntity.setRoleId(UserEntity1.getRole_id());
//				userRoleMapperEntity.setCreatedBy(UserEntity1.getUsername());
//				userRoleMapperEntity.setCreationDate(LocalDateTime.now());
//				userRoleMapperDao.saveUserRoleDetails(userRoleMapperEntity);
			}catch (DataIntegrityViolationException e) {
				response=MessageConstant.USER_BULK_EXIST;
				UserEntity1.setResponse(response);
				logger.error("Error in saveBankMaster :DataIntegrityViolationException:"+e.getMessage());
			}
			catch (Exception e) {
				response=MessageConstant.RESPONSE_FAILED;
				UserEntity1.setResponse(response);
			}
		
		
		return UserEntity1;

	}

	@Override
	public List<UserDto> getUsersList(int employerid) {
		// TODO Auto-generated method stub
		return signUpDao.getUser(employerid);
	}

	@Override
	public UserEntity updateUsers(UserRequest user) {
		
		UserEntity userEntity1=null;
		UserEmpEntity userEmpEntity= new UserEmpEntity();
		UserEntity userDetails=userDetailsDao.checkUserId(user.getId());
		if(userDetails!=null) {
			if(user.getStatus()==1) {
				userDetails.setStatus(1);
			}else if(user.getStatus()==0) {
				userDetails.setStatus(0);
			}
		//userDetails.setStatus(MessageConstant.STATUS);
		userEntity1=userDetailsDao.saveUserDetails(userDetails);
		
		Date date = new Date();
		LocalDate localDate =date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		userEmpEntity.setUser_id(userEntity1.getId());
		//userEmpEntity.setUserDetails(UserEntity1);
		userEmpEntity.setStatus(userEntity1.getStatus());
		userEmpEntity.setEmployer_id(Long.valueOf(userEntity1.getEmployerid()));
		userEmpEntity.setCreated_date(localDate);
		userEmpEntity.setUpdated_date(localDate);
		userEmpEntity.setUpdated_by(""+userEntity1.getEmployerid());
		userDetailsDao.saveUserEmpEntity(userEmpEntity);
		
		}
		return userEntity1;
	}

	@Override
	public UserEntity saveUsersBulk(UserRequest user) {
		String response=MessageConstant.RESPONSE_FAILED;
		UserEntity userDetails= new UserEntity();
		UserEmpEntity userEmpEntity= new UserEmpEntity();
		UserEntity UserEntity1=new UserEntity();
		try {
			UserEntity1.setResponse(response);
			if(user.isUpdateStatus()) {
			userDetails=userDetailsDao.checkUserMobile(user.getMobile());
			if(userDetails!=null)
				CopyUtility.copyProperties(user,userDetails);
				else {
					userDetails= new UserEntity();
				CopyUtility.copyProperties(user,userDetails);	
				}
			}else {
				CopyUtility.copyProperties(user,userDetails);	
			}
			//CopyUtility.copyProperties(user,userDetails);
			Date date = new Date();
			LocalDate localDate =date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			userDetails.setCreated_date(localDate);
			userDetails.setRole_id(MessageConstant.USER_ROLE);
			//userDetails.setMapperFlag("Y");
			UserEntity1=userDetailsDao.saveUserDetails(userDetails);
			userEmpEntity.setUser_id(UserEntity1.getId());
			//userEmpEntity.setUserDetails(UserEntity1);
			userEmpEntity.setStatus(UserEntity1.getStatus());
			
			userEmpEntity.setCreated_date(localDate);
			userDetailsDao.saveUserEmpEntity(userEmpEntity);
			
//			UserRoleMapperEntity userRoleMapperEntity=new UserRoleMapperEntity();
//			userRoleMapperEntity.setMobile(UserEntity1.getMobile());
//			long empid = UserEntity1.getEmployerid();
//			userRoleMapperEntity.setOrgId(empid);
//			userRoleMapperEntity.setStatus(1);
//			userRoleMapperEntity.setRoleId(UserEntity1.getRole_id());
//			userRoleMapperEntity.setCreatedBy(UserEntity1.getUsername());
//			userRoleMapperEntity.setCreationDate(LocalDateTime.now());
//			userRoleMapperDao.saveUserRoleDetails(userRoleMapperEntity);
			
			if(user.isEmailStatus()) {
				CommonUtility.sendEmail(user);
			}
			response=MessageConstant.RESPONSE_SUCCESS;
			
			UserEntity1.setResponse(response);
		} catch (DataIntegrityViolationException e) {
			response=MessageConstant.USER_BULK_EXIST;
			UserEntity1.setResponse(response);
			//logger.error("Error in saveBankMaster :DataIntegrityViolationException:"+e.getMessage());
		}
		catch (Exception e) {
			response=MessageConstant.PROFILE_FAILED;
			UserEntity1.setResponse(response);
			//response=MessageConstant.RESPONSE_FAILED;
			//UserEntity1.setResponse(response);
		}
		
		return UserEntity1;
	}

	@Override
	public UserEntity confirmUsersBulk(UserRequest user) {
		UserEntity userDetails= new UserEntity();
		UserEmpEntity userEmpEntity= new UserEmpEntity();
		userDetails=userDetailsDao.checkUserMobile(user.getMobile());
		CopyUtility.copyProperties(user,userDetails);
		Date date = new Date();
		LocalDate localDate =date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		userDetails.setCreated_date(localDate);
		//userDetails.setRole_id(MessageConstant.USER_ROLE);
		userDetails.setStatus(1);
		UserEntity UserEntity1=userDetailsDao.saveUserDetails(userDetails);
		userEmpEntity.setUser_id(UserEntity1.getId());
		//userEmpEntity.setUserDetails(UserEntity1);
		userEmpEntity.setStatus(UserEntity1.getStatus());
		userEmpEntity.setCreated_by(UserEntity1.getMobile());
		userEmpEntity.setCreated_date(localDate);
		userDetailsDao.saveUserEmpEntity(userEmpEntity);
		
		return UserEntity1;

	}

	@Override
	public UserEntity userExistNew(String mobile, String email) {
		// TODO Auto-generated method stub
		UserEntity userDetail = userDetailsDao.getUserNew(mobile,email);		
			return userDetail;
		
	}

	@Override
	public EmployerDetailsEntity checkOrgExist(long id) {
		// TODO Auto-generated method stub
		return userDetailsDao.getOrgExist(id);
	}




	@Override
	public UserEntity userDetails(String mobile, String email) {
		// TODO Auto-generated method stub
		return userDetailsDao.getUserDetails(mobile, email);
	}




	@Override
	public List<ExistUserResponse> getUsersListWithRole(int employerid,String mobile) {
		List<ExistUserResponse> existUserList=new ArrayList<ExistUserResponse>();
		try {
			UserEntity uEntity=userDetailsDao.checkUserEligible(mobile);
			if(uEntity==null) {
				return existUserList;
			}
			//userEligible
			List<UserEntity>  userEntities=userDetailsDao.getUserList(employerid,mobile);
			if(userEntities!=null) {
				for (UserEntity userEntity : userEntities) {
					ExistUserResponse existUserResponse=new ExistUserResponse();
					CopyUtility.copyProperties(userEntity, existUserResponse);
					//CopyUtility.copyProperties
					List<UserRoleMapperDto> userRoleMapperEntities=userRoleMapperDao.getUserRoleList(existUserResponse.getMobile());
					List<RoleDto> userrolelist=userRoleMapperDao.getUserRoleMaster(existUserResponse.getMobile());
					if(userrolelist!=null) {
						if(userRoleMapperEntities!=null && userRoleMapperEntities.size()>0) {
							
						}else {
							userRoleMapperEntities=new ArrayList<UserRoleMapperDto>();
						}
					for (RoleDto roledto : userrolelist) {
						UserRoleMapperDto userRoleMapperDto=new UserRoleMapperDto();
						userRoleMapperDto.setRoleId(roledto.getRoleId().intValue());
						userRoleMapperDto.setRoleDesc(roledto.getRoleDesc());
						userRoleMapperEntities.add(userRoleMapperDto);
					}
					}
					existUserResponse.setUserRole(userRoleMapperEntities);
					existUserList.add(existUserResponse);
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return existUserList;
	}




	@Override
	public ExistUserResponse saveUsersRole(ExistUserResponse existUserResponses) {
		try {
				String mobile=existUserResponses.getMobile();
				String consent="";
//				Long employerid=0l;
//				if(existUserResponses.getRole_id()==1) {
//				employerid=existUserResponses.getId();
//				}else {
//					int empid=existUserResponses.getEmployerid();
//					employerid=(long)empid;
//				}
				Long orgId=existUserResponses.getOrgId();
				
				//
				List<UserRoleMapperHistoryEntity> historyEntityList=new ArrayList<UserRoleMapperHistoryEntity>();
				List<UserRoleMapperEntity> userRoleMapperList=userRoleMapperDao.getUserRoleMapperList(mobile,orgId);
				if(userRoleMapperList!=null) {
					for (UserRoleMapperEntity userRoleMapperEntity : userRoleMapperList) {
						UserRoleMapperHistoryEntity historyEntity=new UserRoleMapperHistoryEntity();
						CopyUtility.copyProperties(userRoleMapperEntity, historyEntity);
						historyEntityList.add(historyEntity);
					}					
					historyEntityList=userRoleMapperHistoryDao.saveUserRoleList(historyEntityList);
					userRoleMapperDao.deleteUserRoleMapper(mobile,orgId);

				}
				
				String[] roleList =existUserResponses.getRoleDesc();
				for (String roleDesc : roleList) {
					RoleMasterEntity roleMasterEntity=userRoleMasterDao.getUserRoleList(roleDesc);
					int role=roleMasterEntity.getRoleId().intValue();
					UserRoleMapperEntity userMapperEntity=new UserRoleMapperEntity();
					userMapperEntity=setMapper(userMapperEntity, mobile, orgId, role, mobile,consent,"");					
				}
				existUserResponses.setResponse(MessageConstant.RESPONSE_SUCCESS);
			
		} catch (Exception e) {
			e.printStackTrace();
			existUserResponses.setResponse(MessageConstant.RESPONSE_FAILED);
		}
		
		return existUserResponses;
	}	

	
	public UserRoleMapperEntity setMapper(UserRoleMapperEntity userRoleMapperEntity,String mobile,Long orgId,int role,
			String createdBy,String consent,String userMobile) {
		userRoleMapperEntity.setMobile(mobile);
		userRoleMapperEntity.setOrgId(orgId);
		userRoleMapperEntity.setRoleId(role);
		userRoleMapperEntity.setStatus(1);
		userRoleMapperEntity.setCreatedBy(createdBy);
		userRoleMapperEntity.setConsent(consent);
		userRoleMapperEntity.setCreationDate(LocalDateTime.now());
		userRoleMapperEntity.setLoginUserMobile(userMobile);		
		userRoleMapperEntity=userRoleMapperDao.saveUserRoleDetails(userRoleMapperEntity);
		return userRoleMapperEntity;
	}




	@Override
	public ExistUserResponse updateUsersRole(ExistUserResponse existUserResponses) {
		try {
			String mobile=existUserResponses.getMobile();
			//String consent=existUserResponses.getc
//			Long employerid=0l;
//			if(existUserResponses.getRole_id()==1) {
//			employerid=existUserResponses.getId();
//			}else {
//				int empid=existUserResponses.getEmployerid();
//				employerid=(long)empid;
//			}
			Long orgId=existUserResponses.getOrgId();
			
			//
			List<UserRoleMapperHistoryEntity> historyEntityList=new ArrayList<UserRoleMapperHistoryEntity>();
			List<UserRoleMapperEntity> userRoleMapperList=userRoleMapperDao.getUserRoleMapperList(mobile,orgId);
			if(userRoleMapperList!=null) {
				for (UserRoleMapperEntity userRoleMapperEntity : userRoleMapperList) {
					UserRoleMapperHistoryEntity historyEntity=new UserRoleMapperHistoryEntity();
					CopyUtility.copyProperties(userRoleMapperEntity, historyEntity);
					historyEntityList.add(historyEntity);
				}					
				historyEntityList=userRoleMapperHistoryDao.saveUserRoleList(historyEntityList);
				userRoleMapperDao.deleteUserRoleMapper(mobile,orgId);

			}
			
			String[] roleList =existUserResponses.getRoleDesc();
			for (String roleDesc : roleList) {
				RoleMasterEntity roleMasterEntity=userRoleMasterDao.getUserRoleList(roleDesc);
				int role=roleMasterEntity.getRoleId().intValue();
				UserRoleMapperEntity userMapperEntity=new UserRoleMapperEntity();
				userMapperEntity=setMapper(userMapperEntity, mobile, orgId, role, mobile,"Y","");					
			}
			if(roleList.length>0) {
				userDetailsDao.updateMapperFlag(mobile, "Y");
			}
			existUserResponses.setResponse(MessageConstant.RESPONSE_SUCCESS);
		
	} catch (Exception e) {
		e.printStackTrace();
		existUserResponses.setResponse(MessageConstant.RESPONSE_FAILED);
	}
	
	return existUserResponses;
	}




	@Override
	public DeleteUserRoleRequest deleteUsersRole(DeleteUserRoleRequest existUserResponse) {
		try {
			String mobile=existUserResponse.getMobile();
			
			Long orgId=existUserResponse.getOrgId();			
			//
			List<UserRoleMapperHistoryEntity> historyEntityList=new ArrayList<UserRoleMapperHistoryEntity>();
			List<UserRoleMapperEntity> userRoleMapperList=userRoleMapperDao.getUserRoleMapperList(mobile,orgId);
			if(userRoleMapperList!=null) {
				for (UserRoleMapperEntity userRoleMapperEntity : userRoleMapperList) {
					UserRoleMapperHistoryEntity historyEntity=new UserRoleMapperHistoryEntity();
					CopyUtility.copyProperties(userRoleMapperEntity, historyEntity);
					historyEntityList.add(historyEntity);
				}					
				historyEntityList=userRoleMapperHistoryDao.saveUserRoleList(historyEntityList);
				userRoleMapperDao.deleteUserRoleMapper(mobile,orgId);
				userDetailsDao.updateMapperFlag(mobile, "N");
			}
			
			existUserResponse.setResponse(MessageConstant.RESPONSE_SUCCESS);
		
	} catch (Exception e) {
		e.printStackTrace();
		existUserResponse.setResponse(MessageConstant.RESPONSE_FAILED);
	}
	
	return existUserResponse;
	}




	@Override
	public List<ExistUserResponse> searchUsers(int orgId, String userName,String mobile) {
		
		
		List<ExistUserResponse> existUserList=new ArrayList<ExistUserResponse>();
		try {
			
			List<UserEntity>  userEntities=userDetailsDao.getSearchUser(orgId, userName,mobile);
			if(userEntities!=null) {
				for (UserEntity userEntity : userEntities) {
					ExistUserResponse existUserResponse=new ExistUserResponse();
					CopyUtility.copyProperties(userEntity, existUserResponse);
					//CopyUtility.copyProperties
					List<RoleMasterEntity> roleMasterEntities=userRoleMasterDao.getUserRoleMaster();
					List<UserRoleMapperDto> userRoleMapperDtoList=new ArrayList<UserRoleMapperDto>();
					for (RoleMasterEntity roleMasterEntity : roleMasterEntities) {
						UserRoleMapperDto userRoleMapperDto=new UserRoleMapperDto();
						userRoleMapperDto.setRoleId(roleMasterEntity.getRoleId().intValue());
						userRoleMapperDto.setRoleDesc(roleMasterEntity.getRoleDesc());
						//CopyUtility.copyProperties(roleMasterEntity, userRoleMapperDto);
						userRoleMapperDtoList.add(userRoleMapperDto);
					}
					
					
					existUserResponse.setUserRole(userRoleMapperDtoList);
					existUserList.add(existUserResponse);
				}
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return existUserList;
		// TODO Auto-generated method stub
		
		
		
//		List<UserRoleMapperDto> userRoleMapperEntities=userRoleMapperDao.getUserRoleList(existUserResponse.getMobile());
//		
//		existUserResponse.setUserRole(userRoleMapperEntities);
//		
//		return userDetailsDao.getSearchUser(orgId, userName);
		
	}




	@Override
	public ExistUserRoleRequest updateUsersRoleList(ExistUserRoleRequest existUserRoleRequest) {
		try {
			
			StringBuilder dataBuilder = new StringBuilder();
			 
	        dataBuilder.append(existUserRoleRequest.getOrgId())
	                   .append(existUserRoleRequest.getEmployerId())
	                   .append(existUserRoleRequest.getUserMobile())
	                   .append(existUserRoleRequest.getConsent())
	                   .append(existUserRoleRequest.getCreatedBy());
 
	        for (UserDetailsDto user : existUserRoleRequest.getUserDTO()) {
	            dataBuilder.append(user.getId())
	                       .append(user.getUsername())
	                       .append(user.getEmail())
	                       .append(user.getMobile());
 
	            for (UserRoleDto role : user.getUserRole()) {
	                dataBuilder.append(role.getRoleDesc());
	            }
	        }
 
	        dataBuilder.append(existUserRoleRequest.getClientKey()).append(MessageConstant.SECRET_KEY);
 
	        String dataString = dataBuilder.toString();
	        
	        String hash=ValidateConstants.generateHash(dataString);
			if(!existUserRoleRequest.getHash().equalsIgnoreCase(hash)) {
				existUserRoleRequest.setResponse(MessageConstant.HASH_ERROR);
				return existUserRoleRequest;
			}
			
			Long orgId=existUserRoleRequest.getOrgId();
			int employerid=existUserRoleRequest.getEmployerId();
			String consent=existUserRoleRequest.getConsent();
			String createdBy=existUserRoleRequest.getCreatedBy();
			String userMobile=existUserRoleRequest.getUserMobile();
			if(consent==null || consent.equalsIgnoreCase("") || createdBy==null || createdBy.equalsIgnoreCase("") ) {				
				String response=MessageConstant.CONSENT_CREATED_BY;
				existUserRoleRequest.setResponse(response);
				return existUserRoleRequest;
			}
			
			
			List<UserDetailsDto> userDTO=existUserRoleRequest.getUserDTO();
			
			for (UserDetailsDto userDetailsDto : userDTO) {
				List<UserRoleMapperHistoryEntity> historyEntityList=new ArrayList<UserRoleMapperHistoryEntity>();
				String mobile=userDetailsDto.getMobile();
				List<UserRoleMapperEntity> userRoleMapperList=userRoleMapperDao.getUserRoleMapperList(mobile,orgId);
				if(userRoleMapperList!=null) {
					for (UserRoleMapperEntity userRoleMapperEntity : userRoleMapperList) {
						UserRoleMapperHistoryEntity historyEntity=new UserRoleMapperHistoryEntity();
						CopyUtility.copyProperties(userRoleMapperEntity, historyEntity);
						historyEntityList.add(historyEntity);
					}					
					historyEntityList=userRoleMapperHistoryDao.saveUserRoleList(historyEntityList);
					userRoleMapperDao.deleteUserRoleMapper(mobile,orgId);

				}
				List<UserRoleDto> userRole=userDetailsDto.getUserRole();
				for (UserRoleDto roleDesc : userRole) {
					RoleMasterEntity roleMasterEntity=userRoleMasterDao.getUserRoleList(roleDesc.getRoleDesc());
						if(roleMasterEntity!=null) {
						int role=roleMasterEntity.getRoleId().intValue();
						UserRoleMapperEntity userMapperEntity=new UserRoleMapperEntity();
						userMapperEntity=setMapper(userMapperEntity, mobile, orgId, role, createdBy,consent,userMobile);	
						}
				}
				if(userRole.size()>0) {
					userDetailsDao.updateMapperFlag(mobile, "Y");
				}
				
			}			
			existUserRoleRequest.setResponse(MessageConstant.RESPONSE_SUCCESS);
		
	} catch (Exception e) {
		e.printStackTrace();
		existUserRoleRequest.setResponse(MessageConstant.RESPONSE_FAILED);
	}
	
	return existUserRoleRequest;
	}




	@Override
	public List<UserManagerDto> userManagerList(Long orgId,Long employeeId) {
		List<UserManagerDto> existUserList=new ArrayList<UserManagerDto>();
		int emprid=orgId.intValue();
		try {
			employeeId=employeeId==null?0l:employeeId;
			if(employeeId==0) {
				existUserList=userDetailsDao.getUserManagerList(emprid);
			}else {
				existUserList=userDetailsDao.getUserManagerList(emprid,employeeId);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return existUserList;
		
	}
	
	
	@Override
	public List<UserManagerDto> searchUsersWithOutMobile(Long orgId, String userName) {
		
		
		List<UserManagerDto> existUserList=new ArrayList<UserManagerDto>();
		try {
			int organizationId=orgId.intValue();
			List<UserEntity>  userEntities=userDetailsDao.getSearchUserWithOutMobile(organizationId, userName);
			if(userEntities!=null) {
				for (UserEntity userEntity : userEntities) {
					UserManagerDto userManagerDto=new UserManagerDto();
					CopyUtility.copyProperties(userEntity, userManagerDto);					
					existUserList.add(userManagerDto);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return existUserList;
		
	}
	@SuppressWarnings("unchecked")
	protected boolean validateCaptcha(HttpServletRequest request, String captcha,String sessioncaptcha) throws Exception {
		String captchaId =sessioncaptcha;

		// HashMap<String, Boolean> captchaMap = new HashMap<String, Boolean>();
		captchaMap = (HashMap<String, Boolean>) request.getSession().getAttribute("capchaValidatedMap");
		logger.debug("Captcha validation done for " + captchaId);
		
			if (null == captchaMap || !captchaMap.containsKey(captchaId)) {
				if (captcha != null && !"".equals(captcha.trim())) {
					if (captcha.equals(captchaId)) {
						captcaValidationMap.put(captchaId, false);
						request.getSession().setAttribute("capchaValidatedMap", captcaValidationMap);
						logger.debug("Captcha is set in session  : :" + captchaId);
						csotp.setCaptchaValidated(true);
						return true; // invalid captcha
					} else {
						logger.error("Captcha is already set in session  : :" + captchaId);
						csotp.setCaptchaValidated(false);
						return false; // valid captcha
					}
				}
			} else {
				logger.error("Captcha is already set in session  : :: :" + captchaId);
				csotp.setCaptchaValidated(false);
				return false;
			}
			logger.error("Captcha is already set in session or captchaMap is not null : ::::::: :" + captchaId);
			csotp.setCaptchaValidated(false);
			return false;
		
	}




	@Override
	public UserEntity searchUsersWithMobileAndOrgId(int orgId, String mobile) {
		// TODO Auto-generated method stub
		return userDetailsDao.getUserDetailsByMobileAndOrgId(orgId, mobile);
	}




	@Override
	public UserEntity saveReputeDetails(HttpServletRequest request, ReputeEmployeeDetails user) {
		String captchaSecurity="";
		UserEntity userEntity=new UserEntity();
		String response=MessageConstant.RESPONSE_FAILED;
		user.setResponse(response);
		try {
			TokenGeneration token=new TokenGeneration();
				UserEntity userDetails= new UserEntity();
				UserEmpEntity userEmpEntity= new UserEmpEntity();
				logger.info("reputeUser::00");
				if(user.getRole().equalsIgnoreCase("ROLE_Employee") || user.getRole().equalsIgnoreCase("ROLE_HR_ADMIN")) {
					EmployerDetailsEntity reputeUser=employerDetailsDao.getEmployerOnboardingDetails(user.getCompanyId(), user.getHrmsId());
					if(reputeUser!=null) {
						logger.info("reputeUser::11");
						CopyUtility.copyProperties(user,userDetails);
						Date date = new Date();
						LocalDate localDate =LocalDate.now();
						userDetails.setCreated_date(localDate);
						userDetails.setMobile(user.getMobile());
						userDetails.setEmail(user.getEmail());
						userDetails.setRole(user.getRole());
						if(user.getRole().equalsIgnoreCase("ROLE_Employee")) {
						userDetails.setRole_id(MessageConstant.USER_ROLE);
						}else if(user.getRole().equalsIgnoreCase("ROLE_HR_ADMIN")) {
							userDetails.setRole_id(MessageConstant.REPUTE_ROLE);
						}
						userDetails.setStatus(1);
						userDetails.setEmployerid(reputeUser.getId().intValue());
						userDetails.setEmployeeId(user.getEmployeeId());
						userDetails.setManagerEmployeeId(user.getManagerEmployeeId());
						userEntity=userDetailsDao.saveUserDetails(userDetails);
						logger.info("reputeUser::22");
						String tokenvalue = token.getToken(applicationConstantConfig.getTokenUrl);
						logger.info("tokenvalue::"+tokenvalue);
						logger.info("reputeUser::33");
						EmployeeOnboardingRequest employeeOnboardingRequest=new EmployeeOnboardingRequest();
						employeeOnboardingRequest.setUserDetailsId(userEntity.getId());
						employeeOnboardingRequest.setEmployerId(reputeUser.getId());
						employeeOnboardingRequest.setEmpOrCont("Repute");
						employeeOnboardingRequest.setMobile(user.getMobile());
						employeeOnboardingRequest.setEmail(user.getEmail());
						employeeOnboardingRequest.setName(user.getEmployeeName());
						//logger.info("reputeUser::44");
						 ObjectMapper objectMapper = new ObjectMapper();
					     objectMapper.registerModule(new JavaTimeModule());
					     String json =objectMapper.writeValueAsString(employeeOnboardingRequest);
					    //    logger.info("json::100"+json);
						String response1 = CommonUtility.userRequest(tokenvalue, json,
								applicationConstantConfig.employerServiceBaseUrl+CommonUtils.addEmployeeRepute,applicationConstantConfig.apiSignaturePublicPath,applicationConstantConfig.apiSignaturePrivatePath);
						logger.info("response1::"+response1);
						userEmpEntity.setUser_id(userEntity.getId());
						userEmpEntity.setStatus(userEntity.getStatus());
						userEmpEntity.setCreated_by(userEntity.getMobile());
						userEmpEntity.setCreated_date(localDate);
						userEmpEntity=userDetailsDao.saveUserEmpEntity(userEmpEntity);		

						response=MessageConstant.RESPONSE_SUCCESS;
						userEntity.setResponse(response);
					}else {
						if(user.getRole().equalsIgnoreCase("ROLE_HR_ADMIN")) {
						EmployerDetailsEntity employerDetailsEntity1=new EmployerDetailsEntity();
						employerDetailsEntity1.setCompanyId(user.getCompanyId());
						employerDetailsEntity1.setHrmsId(user.getHrmsId());
						employerDetailsEntity1.setMobile(user.getMobile());
						employerDetailsEntity1.setEmail(user.getEmail());
						employerDetailsEntity1.setOrganizationName(user.getHrmsName());
						employerDetailsEntity1.setStatus(1);
						employerDetailsEntity1.setCreatedDate(LocalDate.now());
						employerDetailsEntity1.setCreatedBy(user.getEmployeeName());
						String employerCode=getEmployerNo();
						employerDetailsEntity1.setProfileComplete(2);
						employerDetailsEntity1.setEmployerCode(employerCode);
						EmployerDetailsEntity employerDetailsEntity=employerDetailsDao.saveCompanyDetails(employerDetailsEntity1);
						if(employerDetailsEntity!=null) {
						logger.info("reputeUser::11");
						CopyUtility.copyProperties(user,userDetails);
						Date date = new Date();
						LocalDate localDate =LocalDate.now();
						userDetails.setCreated_date(localDate);
						userDetails.setMobile(user.getMobile());
						userDetails.setEmail(user.getEmail());
						userDetails.setRole(user.getRole());
						if(user.getRole().equalsIgnoreCase("ROLE_Employee")) {
						userDetails.setRole_id(MessageConstant.USER_ROLE);
						}else if(user.getRole().equalsIgnoreCase("ROLE_HR_ADMIN")) {
							userDetails.setRole_id(MessageConstant.REPUTE_ROLE);
						}
						userDetails.setStatus(1);
						userDetails.setEmployerid(employerDetailsEntity.getId().intValue());
						userDetails.setEmployeeId(user.getEmployeeId());
						userDetails.setManagerEmployeeId(user.getManagerEmployeeId());
						userEntity=userDetailsDao.saveUserDetails(userDetails);
						logger.info("reputeUser::22");
						String tokenvalue = token.getToken(applicationConstantConfig.getTokenUrl);
						logger.info("tokenvalue::"+tokenvalue);
						logger.info("reputeUser::33");
						EmployeeOnboardingRequest employeeOnboardingRequest=new EmployeeOnboardingRequest();
						employeeOnboardingRequest.setUserDetailsId(userEntity.getId());
						employeeOnboardingRequest.setEmployerId(employerDetailsEntity.getId());
						employeeOnboardingRequest.setEmpOrCont("Repute");
						employeeOnboardingRequest.setMobile(user.getMobile());
						employeeOnboardingRequest.setEmail(user.getEmail());
						employeeOnboardingRequest.setName(user.getEmployeeName());
						//logger.info("reputeUser::44");
						 ObjectMapper objectMapper = new ObjectMapper();
					     objectMapper.registerModule(new JavaTimeModule());
					     String json =objectMapper.writeValueAsString(employeeOnboardingRequest);
					    //    logger.info("json::100"+json);
						String response1 = CommonUtility.userRequest(tokenvalue, json,
								applicationConstantConfig.employerServiceBaseUrl+CommonUtils.addEmployeeRepute,applicationConstantConfig.apiSignaturePublicPath,applicationConstantConfig.apiSignaturePrivatePath);
						logger.info("response1::"+response1);
						userEmpEntity.setUser_id(userEntity.getId());
						userEmpEntity.setStatus(userEntity.getStatus());
						userEmpEntity.setCreated_by(userEntity.getMobile());
						userEmpEntity.setCreated_date(localDate);
						userEmpEntity=userDetailsDao.saveUserEmpEntity(userEmpEntity);		

						response=MessageConstant.RESPONSE_SUCCESS;
						userEntity.setResponse(response);
						}else {
							response=MessageConstant.RESPONSE_FAILED;
							userEntity.setResponse(response);
						}
					}else {
						response=MessageConstant.ELIG_NOT_ORG_EXIST;
						userEntity.setResponse(response);
					}
					}
					
				}
//				else {
//				
//				
//				//CopyUtility.copyProperties(userDetails, user);
//				CopyUtility.copyProperties(user,userDetails);
//				Date date = new Date();
//				LocalDate localDate =date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//				userDetails.setCreated_date(localDate);
//				userDetails.setRole_id(MessageConstant.REPUTE_ROLE);
//				userDetails.setStatus(1);
//				userEntity=userDetailsDao.saveUserDetails(userDetails);
//				userEmpEntity.setUser_id(userEntity.getId());
//				userEmpEntity.setStatus(userEntity.getStatus());
//				userEmpEntity.setCreated_by(userEntity.getMobile());
//				userEmpEntity.setCreated_date(localDate);
//				userEmpEntity=userDetailsDao.saveUserEmpEntity(userEmpEntity);		
//
//				response=MessageConstant.RESPONSE_SUCCESS;
//				userEntity.setResponse(response);
//				}
		
		}catch (Exception e) {
			response=MessageConstant.RESPONSE_FAILED;
			userEntity.setResponse(response);
			e.printStackTrace();
		}
		return userEntity;
	}

	public long generateId() {
        Query query = entityManager.createNativeQuery("SELECT nextval('employercode')");
        return ((Number) query.getSingleResult()).longValue();
    }
	public String getEmployerNo() {
    	
    	String value=String.valueOf(generateId());
    	int len =value.length();
    	String finalValue="";
    	String employerCode="";
        switch (len) {
            case 1:
            	finalValue="0000"+value;
                break;
            case 2:
            	finalValue="000"+value;
                break;
            case 3:
            	finalValue="00"+value;
                break;
            case 4:
            	finalValue="0"+value;
                break;
            default:
            	finalValue=value;
        }
        employerCode="HRMS"+finalValue;
        System.out.println(employerCode);
    	return employerCode;
    }


	@Override
	public UserEntity updateReputeDetails(HttpServletRequest request, UpdateReputeStatusRequest user) {
		UserEntity userEntity=new UserEntity();
		String response=MessageConstant.RESPONSE_FAILED;
		user.setResponse(response);
		try {
			TokenGeneration token=new TokenGeneration();
				UserEntity userDetails= new UserEntity();
				UserEmpEntity userEmpEntity= new UserEmpEntity();
					UserEntity reputeUser=userDetailsDao.checkUserMobile(user.getMobileNumber());
					if(reputeUser!=null) {
						logger.info("reputeUser::11");
						//CopyUtility.copyProperties(user,userDetails);
						Date date = new Date();
						LocalDate localDate =date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						//userDetails.setCreated_date(localDate);
						//userDetails.setRole_id(MessageConstant.USER_ROLE);
						EmployeeOnboardingRequest employeeOnboardingRequest=new EmployeeOnboardingRequest();
						if(user.getEmploymentStatus().equalsIgnoreCase("INACTIVE")) {
							reputeUser.setStatus(0);
							employeeOnboardingRequest.setUpdateStatus(false);
						}else if(user.getEmploymentStatus().equalsIgnoreCase("ACTIVE")) {
							reputeUser.setStatus(1);
							employeeOnboardingRequest.setUpdateStatus(true);
						}
						
						//userDetails.setEmployerid(reputeUser.getId().intValue());
						userEntity=userDetailsDao.saveUserDetails(reputeUser);
						logger.info("reputeUser::22");
						String tokenvalue = token.getToken(applicationConstantConfig.getTokenUrl);
						
						employeeOnboardingRequest.setUserDetailsId(userEntity.getId());
						//employeeOnboardingRequest.setEmployerId(reputeUser.getId());
						//employeeOnboardingRequest.setEmpOrCont("Repute");
						//employeeOnboardingRequest.setMobile(user.getMobile());
						//employeeOnboardingRequest.setEmail(user.getEmail());
						//employeeOnboardingRequest.setName(user.getEmployeeName());
//						if(user.getStatus()==1) {
//							employeeOnboardingRequest.setUpdateStatus(true);
//						}else {
//							employeeOnboardingRequest.setUpdateStatus(false);							
//						}
						employeeOnboardingRequest.setDoe(user.getDoe());
						 ObjectMapper objectMapper = new ObjectMapper();
					     objectMapper.registerModule(new JavaTimeModule());
					     String json =objectMapper.writeValueAsString(employeeOnboardingRequest);
						String response1 = CommonUtility.userRequest(tokenvalue, json,
								applicationConstantConfig.employerServiceBaseUrl+CommonUtils.updateEmployeeRepute,applicationConstantConfig.apiSignaturePublicPath,applicationConstantConfig.apiSignaturePrivatePath);
						logger.info("response1::"+response1);
						userEmpEntity.setUser_id(userEntity.getId());
						userEmpEntity.setStatus(userEntity.getStatus());
						userEmpEntity.setCreated_by(userEntity.getMobile());
						userEmpEntity.setCreated_date(localDate);
						userEmpEntity=userDetailsDao.saveUserEmpEntity(userEmpEntity);		

						response=MessageConstant.RESPONSE_SUCCESS;
						userEntity.setResponse(response);
					}
					
				
		
		}catch (Exception e) {
			response=MessageConstant.RESPONSE_FAILED;
			userEntity.setResponse(response);
			e.printStackTrace();
		}
		return userEntity;
	}

}
