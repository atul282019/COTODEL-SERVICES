package com.cotodel.hrms.auth.server.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.cotodel.hrms.auth.server.entity.OtpLogEntity;
import com.cotodel.hrms.auth.server.dao.OtpLogDao;
import com.cotodel.hrms.auth.server.repository.OtpLogRepository;


@Repository
public class OtpLogDaoImpl implements OtpLogDao {

	@Autowired
	OtpLogRepository otpLogRepository;

	@Override
	public OtpLogEntity saveUserDetails(OtpLogEntity otp) {
		
		return otpLogRepository.saveAndFlush(otp);
	}

		

	
}
