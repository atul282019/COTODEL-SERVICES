package com.cotodel.hrms.auth.server.properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@Configuration
@PropertySource({"classpath:application.properties"})
public class ApplicationConstantConfig {
	
	@Value("${user.service.add.url}")
	public String userServiceAddUrl;
	
	@Value("${auth.token.get.url}")
	public String getTokenUrl;	
	
	@Value("${user.service.add.bulk.url}")
	public String userServiceAddBulkUrl;
	
	
}
