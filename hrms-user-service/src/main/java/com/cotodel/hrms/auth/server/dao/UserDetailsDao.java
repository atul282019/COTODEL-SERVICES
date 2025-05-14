package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.UserManagerDto;
import com.cotodel.hrms.auth.server.entity.EmployerDetailsEntity;
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
	
	public EmployerDetailsEntity getOrgExist(Long id);
	UserEntity getUserDetails(String mobile, String email);
	
	List<UserEntity> getUserList(int employerid,String mobile);
	
	List<UserEntity> getSearchUser(int orgId, String userName,String mobile);
	
	public int updateMapperFlag(String mobile,String mapperFlag);
	
	List<UserManagerDto> getUserManagerList(int orgId);
	public UserEntity checkUserEligible(String mobile);
	
	List<UserEntity> getSearchUserWithOutMobile(int orgId, String userName);
	
	UserEntity getUserDetailsByMobileAndOrgId(int orgId,String mobile);
	
	public UserEntity getByCompAndHrms(String companyId,String hrmsId);
	
	public int updateActiveDeactive(String mobile,int status);
	
}
