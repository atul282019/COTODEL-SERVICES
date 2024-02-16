package com.cotodel.hrms.auth.server.redis.cache.manager;
import com.cotodel.hrms.auth.server.dto.SmsOtpDto;

public interface OtpCacheManager {
	
	public void cacheStateDetails(String txn,SmsOtpDto smsOtpDto);
	public SmsOtpDto getStateDetails(String key);
	
	public boolean checkEmpty();
	public void deleteOtp(String key,SmsOtpDto smsOtpDto);
	
	

}
