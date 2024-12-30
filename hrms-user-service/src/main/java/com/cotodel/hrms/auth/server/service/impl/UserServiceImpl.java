package com.cotodel.hrms.auth.server.service.impl;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.auth.server.dao.SignUpDao;
import com.cotodel.hrms.auth.server.dao.UserDetailsDao;
import com.cotodel.hrms.auth.server.dao.UserRoleMapperDao;
import com.cotodel.hrms.auth.server.dao.UserRoleMapperHistoryDao;
import com.cotodel.hrms.auth.server.dao.UserRoleMasterDao;
import com.cotodel.hrms.auth.server.dto.ExistUserResponse;
import com.cotodel.hrms.auth.server.dto.ExistUserRoleRequest;
import com.cotodel.hrms.auth.server.dto.RoleDto;
import com.cotodel.hrms.auth.server.dto.UserDetailsDto;
import com.cotodel.hrms.auth.server.dto.UserDto;
import com.cotodel.hrms.auth.server.dto.UserManagerDto;
import com.cotodel.hrms.auth.server.dto.UserRequest;
import com.cotodel.hrms.auth.server.dto.UserRoleDto;
import com.cotodel.hrms.auth.server.dto.UserRoleMapperDto;
import com.cotodel.hrms.auth.server.entity.RoleMasterEntity;
import com.cotodel.hrms.auth.server.entity.UserEmpEntity;
import com.cotodel.hrms.auth.server.entity.UserEntity;
import com.cotodel.hrms.auth.server.entity.UserRoleMapperEntity;
import com.cotodel.hrms.auth.server.entity.UserRoleMapperHistoryEntity;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.UserService;
import com.cotodel.hrms.auth.server.util.CommonUtility;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;

@Service
public class UserServiceImpl implements UserService {

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
	
	@Override
	@Transactional
	public UserEntity saveUserDetails(UserRequest user) {
		// TODO Auto-generated method stub
		
		
		UserEntity userDetails= new UserEntity();
		UserEmpEntity userEmpEntity= new UserEmpEntity();
		//CopyUtility.copyProperties(userDetails, user);
		CopyUtility.copyProperties(user,userDetails);
		Date date = new Date();
		LocalDate localDate =date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		userDetails.setCreated_date(localDate);
		if(user.isERupiStatus()) {
			userDetails.setRole_id(MessageConstant.ERUPI_ADMIN_ROLE);
		}else {
			userDetails.setRole_id(MessageConstant.SIGN_UP_ROLE);
		}
		userDetails.setStatus(1);
		UserEntity UserEntity1=userDetailsDao.saveUserDetails(userDetails);
		//userEmpEntity.setUser_id(UserEntity1.getId());
		userEmpEntity.setUser_id(UserEntity1.getId());
		userEmpEntity.setStatus(UserEntity1.getStatus());
		userEmpEntity.setCreated_by(UserEntity1.getMobile());
		userEmpEntity.setCreated_date(localDate);
		userEmpEntity=userDetailsDao.saveUserEmpEntity(userEmpEntity);
		
//		UserRoleMapperEntity userRoleMapperEntity=new UserRoleMapperEntity();
//		userRoleMapperEntity.setMobile(UserEntity1.getMobile());
//		userRoleMapperEntity.setOrgId(UserEntity1.getId());
//		userRoleMapperEntity.setStatus(1);
//		userRoleMapperEntity.setRoleId(UserEntity1.getRole_id());
//		userRoleMapperEntity.setCreatedBy(UserEntity1.getUsername());
//		userRoleMapperEntity.setCreationDate(LocalDateTime.now());
//		userRoleMapperDao.saveUserRoleDetails(userRoleMapperEntity);
		
		return UserEntity1;
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
		// TODO Auto-generated method stub
		UserEmpEntity userEmpEntity= new UserEmpEntity();
		UserEntity userDetails=userDetailsDao.checkUserMobile(user.getMobile());
		userDetails.setStatus(MessageConstant.STATUS);
		UserEntity UserEntity1=userDetailsDao.saveUserDetails(userDetails);
		
		Date date = new Date();
		LocalDate localDate =date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		userEmpEntity.setUser_id(UserEntity1.getId());
		//userEmpEntity.setUserDetails(UserEntity1);
		userEmpEntity.setStatus(UserEntity1.getStatus());
		userEmpEntity.setEmployer_id(Long.valueOf(UserEntity1.getEmployerid()));
		userEmpEntity.setCreated_date(localDate);
		userEmpEntity.setUpdated_date(localDate);
		userEmpEntity.setUpdated_by(""+UserEntity1.getEmployerid());
		userDetailsDao.saveUserEmpEntity(userEmpEntity);
		return UserEntity1;
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
			response=MessageConstant.PROFILE_SUCCESS;
			
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
	public UserEntity checkOrgExist(long id) {
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
	public ExistUserResponse deleteUsersRole(ExistUserResponse existUserResponse) {
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
			Long orgId=existUserRoleRequest.getOrgId();
			int employerid=existUserRoleRequest.getEmployerid();
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
	public List<UserManagerDto> userManagerList(int orgId) {
		List<UserManagerDto> existUserList=new ArrayList<UserManagerDto>();
		try {
			
			existUserList=userDetailsDao.getUserManagerList(orgId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return existUserList;
		
	}
	
	
	@Override
	public List<UserManagerDto> searchUsersWithOutMobile(int orgId, String userName) {
		
		
		List<UserManagerDto> existUserList=new ArrayList<UserManagerDto>();
		try {
			
			List<UserEntity>  userEntities=userDetailsDao.getSearchUserWithOutMobile(orgId, userName);
			if(userEntities!=null) {
				for (UserEntity userEntity : userEntities) {
					UserManagerDto userManagerDto=new UserManagerDto();
					CopyUtility.copyProperties(userEntity, userManagerDto);
					//CopyUtility.copyProperties
					//List<RoleMasterEntity> roleMasterEntities=userRoleMasterDao.getUserRoleMaster();
					//List<UserRoleMapperDto> userRoleMapperDtoList=new ArrayList<UserRoleMapperDto>();
//					for (RoleMasterEntity roleMasterEntity : roleMasterEntities) {
//						UserRoleMapperDto userRoleMapperDto=new UserRoleMapperDto();
//						userRoleMapperDto.setRoleId(roleMasterEntity.getRoleId().intValue());
//						userRoleMapperDto.setRoleDesc(roleMasterEntity.getRoleDesc());
//						//CopyUtility.copyProperties(roleMasterEntity, userRoleMapperDto);
//						userRoleMapperDtoList.add(userRoleMapperDto);
//					}
					
					
					//existUserResponse.setUserRole(userRoleMapperDtoList);
					existUserList.add(userManagerDto);
				}
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return existUserList;
		
	}
	

}
