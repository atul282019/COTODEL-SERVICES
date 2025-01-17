package com.cotodel.hrms.auth.server.properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@Configuration
@PropertySource({"classpath:application.properties"})
public class ApplicationConstantConfig {
	
//	@Value("${user.service.add.url}")
//	public String userServiceAddUrl;
	
//	@Value("${auth.token.get.url}")
//	public String getTokenUrl;	
	
//	@Value("${user.service.add.bulk.url}")
//	public String userServiceAddBulkUrl;
	
//	@Value("${user.service.update.bulk.url}")
//	public String userServiceUpdateBulkUrl;
	
//	@Value("${user.service.add.new.url}")
//	public String userServiceAddNewUrl;
	
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
	
}
