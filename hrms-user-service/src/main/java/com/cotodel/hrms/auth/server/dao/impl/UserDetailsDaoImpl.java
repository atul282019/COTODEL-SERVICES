package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.UserDetailsDao;
import com.cotodel.hrms.auth.server.dto.UserManagerDto;
import com.cotodel.hrms.auth.server.entity.EmployerDetailsEntity;
import com.cotodel.hrms.auth.server.entity.UserEmpEntity;
import com.cotodel.hrms.auth.server.entity.UserEntity;
import com.cotodel.hrms.auth.server.repository.EmployerDetailsRepository;
import com.cotodel.hrms.auth.server.repository.UserEmpRepository;
import com.cotodel.hrms.auth.server.repository.UserRepository;

@Repository
public class UserDetailsDaoImpl implements UserDetailsDao {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserEmpRepository userEmpRepository;
	
	@Autowired
	EmployerDetailsRepository employerDetailsRepository;
	
	@Override
	public UserEntity saveUserDetails(UserEntity user) {
		// TODO Auto-generated method stub
		return userRepository.saveAndFlush(user);
	}

	@Override
	public UserEmpEntity saveUserEmpEntity(UserEmpEntity userEmpEntity) {
		// TODO Auto-generated method stub
		return userEmpRepository.saveAndFlush(userEmpEntity);
	}

	@Override
	public UserEntity checkUserDetails(String userName) {
		// TODO Auto-generated method stub
		return userRepository.checkUserDetails(userName);
	}

	@Override
	public UserEntity checkUserMobile(String userMobile) {
		// TODO Auto-generated method stub
		return userRepository.checkUserMobile(userMobile);
	}

	@Override
	public UserEntity checkUserEmail(String userEmail) {
		// TODO Auto-generated method stub
		return userRepository.checkUserEmail(userEmail);
	}

	@Override
	public UserEntity getByUserName(String userName) {
		// TODO Auto-generated method stub
		return userRepository.getByUser(userName);
	}

	@Override
	public List<UserEntity> getUser(String mobile, String email) {
		// TODO Auto-generated method stub
		return userRepository.findByMobileAndEmailAndStatus(mobile, email, mobile);
	}

	@Override
	public UserEntity getUserNew(String mobile, String email) {
		// TODO Auto-generated method stub
		return userRepository.findByMobileAndEmail(mobile, email, email);
	}

	@Override
	public EmployerDetailsEntity getOrgExist(Long id) {
		// TODO Auto-generated method stub
		return employerDetailsRepository.getEmployerDetailsById(id);
	}

	@Override
	public UserEntity getUserDetails(String mobile, String email) {
		// TODO Auto-generated method stub
		return userRepository.findUserByMobileAndEmail(mobile, email);
	}

	@Override
	public List<UserEntity> getUserList(int employerid,String mobile) {
		return userRepository.findByUserList(employerid,mobile);
	}

	@Override
	public List<UserEntity> getSearchUser(int orgId, String userName,String mobile) {
		// TODO Auto-generated method stub
		return userRepository.findByUserSearchList(orgId, userName,mobile);
	}

	@Override
	public int updateMapperFlag(String mobile, String mapperFlag) {
		// TODO Auto-generated method stub
		return userRepository.updateMapperFlag(mobile, mapperFlag);
	}

	@Override
	public List<UserManagerDto> getUserManagerList(int orgId,Long employeeId) {
		// TODO Auto-generated method stub
		return userRepository.getUserManagerList(orgId,employeeId);
	}
	@Override
	public List<UserManagerDto> getUserManagerList(int orgId) {
		// TODO Auto-generated method stub
		return userRepository.getUserManagerList(orgId);
	}

	@Override
	public UserEntity checkUserEligible(String mobile) {
		// TODO Auto-generated method stub
		return userRepository.userEligible(mobile);
	}

	@Override
	public List<UserEntity> getSearchUserWithOutMobile(int orgId, String userName) {
		// TODO Auto-generated method stub
		return userRepository.findByUserSearchWithoutMobile(orgId, userName);
	}

	@Override
	public UserEntity getUserDetailsByMobileAndOrgId(int orgId, String mobile) {
		// TODO Auto-generated method stub
		return userRepository.checkUserMobileAndOrgId(orgId,mobile);
	}

	@Override
	public UserEntity getByCompAndHrms(String companyId, String hrmsId) {
		// TODO Auto-generated method stub
		return userRepository.checkUserCompAndHrmsId(companyId,hrmsId);
	}

	@Override
	public int updateActiveDeactive(String mobile,int status) {
		// TODO Auto-generated method stub
		return userRepository.updateActiveDeactive(mobile, status);
	}

	@Override
	public UserEntity checkUserId(Long id) {
		// TODO Auto-generated method stub
		return userRepository.getById(id);
	}
	
}
