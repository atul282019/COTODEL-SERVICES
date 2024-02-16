package com.cotodel.hrms.auth.server.service.impl;
/**
 * @author Vinay Kushwaha
 * @Email  vinaykushwaha85@gmail.com

 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.auth.server.dao.SmsOtpDao;
import com.cotodel.hrms.auth.server.model.SmsOtpEntity;
import com.cotodel.hrms.auth.server.service.SmsOtpService;

@Service
public class SmsOtpServiceImpl implements SmsOtpService {

	
	@Autowired
	public SmsOtpDao smsOtpDao;



	@Override
	public void saveSmsOtpEntity(SmsOtpEntity smsOtpEntity) {
		// TODO Auto-generated method stub
		smsOtpDao.saveSmsOtpEntity(smsOtpEntity);
	}

}
