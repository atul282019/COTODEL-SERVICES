package com.cotodel.hrms.auth.server.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cotodel.hrms.auth.server.dto.DeleteUserRoleRequest;
import com.cotodel.hrms.auth.server.dto.ExistUserResponse;
import com.cotodel.hrms.auth.server.dto.ExistUserRoleRequest;
import com.cotodel.hrms.auth.server.dto.ReputeEmployeeDetails;
import com.cotodel.hrms.auth.server.dto.UpdateReputeStatusRequest;
import com.cotodel.hrms.auth.server.dto.UserDto;
import com.cotodel.hrms.auth.server.dto.UserManagerDto;
import com.cotodel.hrms.auth.server.dto.UserRequest;
import com.cotodel.hrms.auth.server.entity.EmployerDetailsEntity;
import com.cotodel.hrms.auth.server.entity.UserEmpEntity;
import com.cotodel.hrms.auth.server.entity.UserEntity;

public interface UserService {
	
	public UserEntity getByUserName(String userName);

	public UserEntity saveUserDetails(HttpServletRequest request,UserRequest user);
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
	public UserEntity updateUsers(UserRequest user);
	public List<UserDto> getUsersList(int  employerid);
	public UserEntity saveUsersBulk(UserRequest user);
	public UserEntity confirmUsersBulk(UserRequest user);
	UserEntity userExistNew(String mobile,String email);
	public EmployerDetailsEntity checkOrgExist(long id);
	
	public UserEntity userDetails(String mobile,String email);
	
	public List<ExistUserResponse> getUsersListWithRole(int  employerid,String mobile);
	
	public ExistUserResponse updateUsersRole(ExistUserResponse  existUserResponse);
	
	public ExistUserResponse saveUsersRole(ExistUserResponse  existUserResponse);

	public DeleteUserRoleRequest deleteUsersRole(DeleteUserRoleRequest  existUserResponse);
	public List<ExistUserResponse> searchUsers(int  orgId,String userName,String mobile);
	public ExistUserRoleRequest updateUsersRoleList(ExistUserRoleRequest  existUserRoleRequest);
	public List<UserManagerDto> userManagerList(Long  orgId,Long employeeId);
	public List<UserManagerDto> searchUsersWithOutMobile(Long  orgId,String userName);
	//public List<ExistUserResponse> saveUsersListWithRole(ExistUserResponse  existUserResponse);
	//public List<UserDto> getUsersList(Long  orgId,String userName);
	public UserEntity searchUsersWithMobileAndOrgId(int  orgId,String mobile);
	public UserEntity saveReputeDetails(HttpServletRequest request,ReputeEmployeeDetails user);
	public UserEntity updateReputeDetails(HttpServletRequest request,UpdateReputeStatusRequest user);
}
