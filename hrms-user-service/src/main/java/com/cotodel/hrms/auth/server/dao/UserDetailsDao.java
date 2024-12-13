package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.UserEmpEntity;
import com.cotodel.hrms.auth.server.entity.UserEntity;

public interface UserDetailsDao {
	
	public UserEntity saveUserDetails(UserEntity user);
	public UserEmpEntity saveUserEmpEntity(UserEmpEntity userEmpEntity);
	
	public UserEntity checkUserDetails(String userName);
	public UserEntity checkUserMobile(String userMobile);
	public UserEntity checkUserEmail(String userEmail);
	
	public UserEntity getByUserName(String userName);
	
	List<UserEntity> getUser(String mobile, String email);
	UserEntity getUserNew(String mobile, String email);
	
	public UserEntity getOrgExist(Long id);
	UserEntity getUserDetails(String mobile, String email);
	
	List<UserEntity> getUserList(int employerid);
}
