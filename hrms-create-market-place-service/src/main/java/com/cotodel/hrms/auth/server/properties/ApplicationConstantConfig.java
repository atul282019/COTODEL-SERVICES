package com.cotodel.hrms.auth.server.properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@Configuration
@PropertySource({"classpath:application.properties"})
public class ApplicationConstantConfig {
	
	@Value("${repute.token.redirect.url}")
	public String tokenRedirectUrl;
	
	@Value("${auth.token.api.url}")
	public String authTokenApiUrl;
	
	@Value("${user.service.api.url}")
	public String userServiceApiUrl;
	
	@Value("${api.signature.keystore.public.path}")
	public String apiSignaturePublicPath;
	
	@Value("${api.signature.keystore.private.path}")
	public String apiSignaturePrivatePath;
}
