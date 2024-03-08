package com.cotodel.hrms.auth.server.service;

import com.cotodel.hrms.auth.server.dto.UserRequest;
import com.cotodel.hrms.auth.server.entity.UserEmpEntity;
import com.cotodel.hrms.auth.server.entity.UserEntity;

public interface UserService {
	
	public UserEntity getByUserName(String userName);

	public UserEntity saveUserDetails(UserRequest user);
	public UserEmpEntity saveUserEmpEntity(UserEmpEntity userEmpEntity);
	
	public UserEntity checkUserDetails(String userName);
	public UserEntity checkUserMobile(String userMobile);
	public UserEntity checkUserEmail(String userEmail);
	public void sendEmailToEmployee(UserRequest user);
	public String getToken(String compid);
	String verifyEmailUpdate(String email);
	String sendSmsOtp(String authToken,String mobile);
	String verifySmsOtp(String authToken,String mobile, String pwd);	
	String userExist(String mobile,String email);
	String sendSmsOtpNew(String mobile);
	String verifySmsOtpNew(String oderID,String mobile, String otp);
	String resendSmsOtp(String mobile,String orderId);
	public UserEntity saveUsers(UserRequest user);
}
