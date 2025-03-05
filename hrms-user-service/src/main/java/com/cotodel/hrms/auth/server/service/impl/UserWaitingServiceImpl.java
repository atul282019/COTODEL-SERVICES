package com.cotodel.hrms.auth.server.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.UserWaitingListDao;
import com.cotodel.hrms.auth.server.dto.UserRequest;
import com.cotodel.hrms.auth.server.dto.UserWaitingListRequest;
import com.cotodel.hrms.auth.server.dto.UserWaitingListUpdateRequest;
import com.cotodel.hrms.auth.server.entity.UserEntity;
import com.cotodel.hrms.auth.server.entity.UserWaitingListEntity;
import com.cotodel.hrms.auth.server.service.UserService;
import com.cotodel.hrms.auth.server.service.UserWaitingService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;

@Repository
public class UserWaitingServiceImpl implements UserWaitingService {

	private static final Logger logger = LoggerFactory.getLogger(UserWaitingServiceImpl.class);
	@Autowired
	UserWaitingListDao userWaitingListDao;	
	
	@Autowired
	UserService userService;
	
	@Override
	public UserWaitingListRequest saveUserDetails(UserWaitingListRequest user) {
		UserWaitingListEntity userWaitingListEntity= new UserWaitingListEntity();
		user.setResponse(MessageConstant.RESPONSE_FAILED);
		try {
			CopyUtility.copyProperties(user,userWaitingListEntity);
			userWaitingListEntity.setStatus(0);
			userWaitingListEntity.setStatusRemarks("Requested");
			userWaitingListEntity.setCreatedDate(LocalDateTime.now());
			userWaitingListEntity=userWaitingListDao.saveUserDetails(userWaitingListEntity);
			user.setResponse(MessageConstant.RESPONSE_SUCCESS);
		}catch (DataIntegrityViolationException e) {
			user.setResponse(MessageConstant.USER_EMAIL);
		}
		catch (Exception e) {
			e.printStackTrace();
			user.setResponse(MessageConstant.RESPONSE_FAILED);
		}
		
				
		return user;
	}


	@Override
	public UserWaitingListEntity checkUserEmail(String userEmail) {
		// TODO Auto-generated method stub
		return userWaitingListDao.getUser(userEmail);
	}


	@Override
	public List<UserWaitingListEntity> checkUserList() {
		// TODO Auto-generated method stub
		return userWaitingListDao.getUserList();
	}


	@Override
	public UserWaitingListUpdateRequest updateUserDetails(HttpServletRequest request,UserWaitingListUpdateRequest user) {
	UserWaitingListEntity userWaitingListEntity= new UserWaitingListEntity();
		user.setResponse(MessageConstant.RESPONSE_FAILED);
		String responseToken="";
		UserEntity userEntity=new UserEntity();
		try {
			userWaitingListEntity=userWaitingListDao.getUserById(user.getId());
			if(userWaitingListEntity!=null) {
				if(user.getStatus().equalsIgnoreCase("")) {
					userWaitingListEntity.setStatus(1);
					userWaitingListEntity.setStatusRemarks("Rejected");
					userWaitingListEntity=userWaitingListDao.saveUserDetails(userWaitingListEntity);
					user.setResponse(MessageConstant.REJECTED);
				}else if(user.getStatus().equalsIgnoreCase("Rejected")) {
					userWaitingListEntity.setStatus(1);
					userWaitingListEntity.setStatusRemarks("Rejected");
					userWaitingListEntity=userWaitingListDao.saveUserDetails(userWaitingListEntity);
					user.setResponse(MessageConstant.REJECTED);
				}else if(user.getStatus().equalsIgnoreCase("Hold")) {
					userWaitingListEntity.setStatus(2);
					userWaitingListEntity.setStatusRemarks("Hold");
					userWaitingListEntity=userWaitingListDao.saveUserDetails(userWaitingListEntity);
					user.setResponse(MessageConstant.REJECTED);
				}
				else {					
				responseToken=userService.userExist(user.getContactNumber(), user.getEmail());
				if(!responseToken.equalsIgnoreCase("")) {					
					user.setResponse(MessageConstant.USER_EXIST);
					userWaitingListEntity.setStatus(3);
					userWaitingListEntity.setStatusRemarks("Approved");
					userWaitingListEntity=userWaitingListDao.saveUserDetails(userWaitingListEntity);
					user.setResponse("Approved");
				}else {					
					UserRequest userReq=new UserRequest();
					userReq.setOrg_name(user.getCompanyName());
					
					//userReq.set//user.getCompanySize();
					
					userReq.setMobile(user.getContactNumber());
					userReq.setUsername(user.getContactPersonName());
					userReq.setEmail(user.getEmail());
					userReq.setErupistatus(user.isErupistatus());
					userReq.setOrg_type(user.getIndustry());
					userEntity=	userService.saveUserDetails(request,userReq);
					if(userEntity!=null) {
						userWaitingListEntity.setStatus(3);
						userWaitingListEntity.setStatusRemarks("Approved");
						userWaitingListEntity=userWaitingListDao.saveUserDetails(userWaitingListEntity);
						user.setResponse(MessageConstant.RESPONSE_SUCCESS);
					}
				}
				
			user.setResponse(MessageConstant.RESPONSE_SUCCESS);
			}
		}
		}catch (DataIntegrityViolationException e) {
			user.setResponse(MessageConstant.USER_EMAIL);
		}
		catch (Exception e) {
			e.printStackTrace();
			user.setResponse(MessageConstant.RESPONSE_FAILED);
		}
		
				
		return user;
	}

	
}
