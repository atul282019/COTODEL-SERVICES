package com.cotodel.hrms.auth.server.service.impl;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
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
import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.SignUpDao;
import com.cotodel.hrms.auth.server.dao.UserDetailsDao;
import com.cotodel.hrms.auth.server.dto.UserDto;
import com.cotodel.hrms.auth.server.dto.UserRequest;
import com.cotodel.hrms.auth.server.entity.UserEmpEntity;
import com.cotodel.hrms.auth.server.entity.UserEntity;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.UserService;
import com.cotodel.hrms.auth.server.util.CommonUtility;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;

@Repository
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	UserDetailsDao userDetailsDao;
	
	@Autowired
	SignUpDao signUpDao;
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
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
		userDetails.setRole_id(MessageConstant.SIGN_UP_ROLE);
		UserEntity UserEntity1=userDetailsDao.saveUserDetails(userDetails);
		//userEmpEntity.setUser_id(UserEntity1.getId());
		//userEmpEntity.setUserDetails(UserEntity1);
		userEmpEntity.setStatus(UserEntity1.getStatus());
		
		userEmpEntity.setCreated_date(localDate);
		//userDetailsDao.saveUserEmpEntity(userEmpEntity);
		
		
		return UserEntity1;
	}

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
	                return new PasswordAuthentication("dkawal73@gmail.com", "jaygeajbqvinwacz");
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
			
		//userDetails=userDetailsDao.checkUserMobile(user.getMobile());
		CopyUtility.copyProperties(user,userDetails);
		Date date = new Date();
		LocalDate localDate =date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		userDetails.setCreated_date(localDate);
		userDetails.setRole_id(MessageConstant.USER_ROLE);
		UserEntity UserEntity1=userDetailsDao.saveUserDetails(userDetails);
		//userEmpEntity.setUser_id(UserEntity1.getId());
		//userEmpEntity.setUserDetails(UserEntity1);
		userEmpEntity.setStatus(UserEntity1.getStatus());
		
		userEmpEntity.setCreated_date(localDate);
		//userDetailsDao.saveUserEmpEntity(userEmpEntity);
		
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
		//userEmpEntity.setUser_id(UserEntity1.getId());
		//userEmpEntity.setUserDetails(UserEntity1);
		userEmpEntity.setStatus(UserEntity1.getStatus());
		
		userEmpEntity.setCreated_date(localDate);
		userEmpEntity.setUpdated_date(localDate);
		userEmpEntity.setUpdated_by(""+UserEntity1.getEmployerid());
		//userDetailsDao.saveUserEmpEntity(userEmpEntity);
		return UserEntity1;
	}

	@Override
	public UserEntity saveUsersBulk(UserRequest user) {
		
		UserEntity userDetails= new UserEntity();
		UserEmpEntity userEmpEntity= new UserEmpEntity();
		UserEntity UserEntity1=null;
		try {
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
			
			UserEntity1=userDetailsDao.saveUserDetails(userDetails);
			//userEmpEntity.setUser_id(UserEntity1.getId());
			//userEmpEntity.setUserDetails(UserEntity1);
			userEmpEntity.setStatus(UserEntity1.getStatus());
			
			userEmpEntity.setCreated_date(localDate);
			//userDetailsDao.saveUserEmpEntity(userEmpEntity);
			if(user.isEmailStatus()) {
				CommonUtility.sendEmail(user);
			}
		} catch (Exception e) {
			// TODO: handle exception
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
		//userEmpEntity.setUser_id(UserEntity1.getId());
		//userEmpEntity.setUserDetails(UserEntity1);
		userEmpEntity.setStatus(UserEntity1.getStatus());
		
		userEmpEntity.setCreated_date(localDate);
		//userDetailsDao.saveUserEmpEntity(userEmpEntity);
		
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

	
	
}
