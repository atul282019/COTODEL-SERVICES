package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.UserRoleMapperDao;
import com.cotodel.hrms.auth.server.dto.RoleDto;
import com.cotodel.hrms.auth.server.dto.UserRoleMapperDto;
import com.cotodel.hrms.auth.server.entity.UserRoleMapperEntity;
import com.cotodel.hrms.auth.server.repository.UserRoleMapperRepository;

@Repository
public class UserRoleMapperDaoImpl implements UserRoleMapperDao {

	@Autowired
	UserRoleMapperRepository userRoleMapperRepository;

	@Override
	public UserRoleMapperEntity saveUserRoleDetails(UserRoleMapperEntity userRoleMapperEntity) {
		// TODO Auto-generated method stub
		return userRoleMapperRepository.saveAndFlush(userRoleMapperEntity);
	}

	@Override
	public List<UserRoleMapperDto> getUserRoleList(String  mobile) {
		// TODO Auto-generated method stub
		return userRoleMapperRepository.getByMobile(mobile);
	}

	@Override
	public UserRoleMapperEntity getUserRoleMapper(String mobile, Long orgId, int roleId) {
		// TODO Auto-generated method stub
		return userRoleMapperRepository.getMapperMobileValue(mobile,orgId,roleId);
	}

	@Override
	public void deleteUserRoleMapper(String mobile, Long orgId) {
		// TODO Auto-generated method stub
		userRoleMapperRepository.getMapperMobilDelete(mobile,orgId);
	}

	@Override
	public UserRoleMapperEntity getUserRoleMapperById(Long id) {
		// TODO Auto-generated method stub
		return userRoleMapperRepository.getById(id);
	}

	@Override
	public List<UserRoleMapperEntity> getUserRoleMapperList(String mobile, Long orgId) {
		// TODO Auto-generated method stub
		return userRoleMapperRepository.getMapperMobileandOrgValue(mobile, orgId);
	}

	@Override
	public List<RoleDto> getUserRoleMaster(String mobile) {
		// TODO Auto-generated method stub
		return userRoleMapperRepository.getByMobileRoleMaster(mobile);
	}
	
	
	
	
}
