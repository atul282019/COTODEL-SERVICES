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
	
	@Value("${erupi.create.vouchers.url}")
	public String getCreateVouchersUrl;
	
	@Value("${erupi.create.vouchers.apikey}")
	public String getCreateVouchersToken;
	
	@Value("${erupi.create.vouchers.mid}")
	public String getCreateVouchersMid;
		
}
