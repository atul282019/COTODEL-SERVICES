package com.cotodel.hrms.auth.server.redis.cache.manager.impl;
/**
 * @author Vinay Kushwaha
 * @Email  vinaykushwaha85@gmail.com

 */
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import com.cotodel.hrms.auth.server.dto.SmsOtpDto;
import com.cotodel.hrms.auth.server.redis.cache.NhaBisRedisUtil;
import com.cotodel.hrms.auth.server.redis.cache.manager.OtpCacheManager;

@Configuration
public class OtpCacheManagerImpl implements OtpCacheManager {
	
	public static final String TABLE_STATE = "TABLE_STATE";
    public static final String STATE = "STATE_";
    public NhaBisRedisUtil<SmsOtpDto> redisUtilState;

	
	@Autowired
     public OtpCacheManagerImpl(NhaBisRedisUtil<SmsOtpDto> redisUtilState) {
         this.redisUtilState = redisUtilState;
     }
	
     @Override
     public void cacheStateDetails(String txn,SmsOtpDto smsOtpDto){
    	 redisUtilState.putValueWithExpireTime(txn,smsOtpDto,10, TimeUnit.MINUTES);
     }
	
	@Override
	public boolean checkEmpty() {
		if(ObjectUtils.isEmpty(redisUtilState))
		return true;
		else
		return false;
	}

	@Override
	public SmsOtpDto getStateDetails(String key) {
		return redisUtilState.getValue(key);
	}

	
	@Override
	public void deleteOtp(String key, SmsOtpDto smsOtpDto) {
		redisUtilState.deleteValue(key,smsOtpDto,1, TimeUnit.NANOSECONDS);
		
	}}
