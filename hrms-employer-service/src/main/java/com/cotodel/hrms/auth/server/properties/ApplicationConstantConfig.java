package com.cotodel.hrms.auth.server.properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@Configuration
@PropertySource({"classpath:application.properties"})
public class ApplicationConstantConfig {
	
	@Value("${auth.token.api.url}")
	public String authTokenApiUrl;
	
	@Value("${user.service.api.url}")
	public String userServiceApiUrl;
	
	@Value("${voucher.service.api.url}")
	public String voucherServiceApiUrl;
	
	@Value("${voucher.creation.min.amount}")
	public String voucherCreationMinAmount;
	
	@Value("${voucher.creation.max.amount}")
	public String voucherCreationMaxAmount;
	
	@Value("${api.signature.keystore.public.path}")
	public String apiSignaturePublicPath;
	
	@Value("${api.signature.keystore.private.path}")
	public String apiSignaturePrivatePath;
}
