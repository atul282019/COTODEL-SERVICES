package com.cotodel.hrms.auth.server.properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@Configuration
@PropertySource({"classpath:application.properties"})
public class ApplicationConstantConfig {
	
	
	@Value("${erupi.signature.keystore.public.path}")
	public String getSignaturePublicPath;
	
	@Value("${erupi.signature.keystore.private.path}")
	public String getSignaturePrivatePath;
	
	@Value("${emp.service.api.url}")
	public String empServiceApiUrl;
	
	@Value("${auth.token.api.url}")
	public String authTokenApiUrl;
	
	@Value("${erupi.create.vouchers.url}")
	public String getCreateVouchersUrl;
	
	@Value("${erupi.create.vouchers.apikey}")
	public String getCreateVouchersToken;
	
//	@Value("${erupi.create.vouchers.mid}")
//	public String getCreateVouchersMid;
	
	
//	@Value("${api.key}")
//    private String apiKey;
//
//    @Value("#{'${whitelisted.ips}'.split(',')}")
//    private Set<String> whitelistedIps;
//
//    public boolean isValidApiKey(String key) {
//        return apiKey.equals(key);
//    }
//
//    public boolean isIpWhitelisted(HttpServletRequest request) {
//        String clientIp = request.getRemoteAddr();
//        return whitelistedIps.contains(clientIp);
//    }
    
    @Value("${erupi.voucher.status.url}")
	public String getVoucherStatusUrl;
    
    @Value("${erupi.voucher.resend.url}")
	public String getVoucherSmsUrl;
}
