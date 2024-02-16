package com.cotodel.hrms.auth.server.properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@Configuration
@PropertySource({"classpath:application.properties"})
public class ApplicationConstantConfig {
	
	@Value("${auth.token.get.url}")
	public String getTokenUrl;	
	
	@Value("${email.token.verify.url}")
	public String emailVerifyUrl;	
	
	@Value("${otp.token.sender.url}")
	public String otpSenderUrl;
	
	@Value("${otp.templateid.sender.token}")
	public String templateId;
	
	@Value("${otp.token.verify.url}")
	public String otpVerifyUrl;
	
	@Value("${otp.templateid.verify.token}")
	public String templateVerifyId;
	
}
