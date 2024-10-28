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
	
//	@Value("${auth.token.get.url}")
//	public String getTokenUrl;	
//	
//	@Value("${email.token.verify.url}")
//	public String emailVerifyUrl;	
//	
//	@Value("${otp.token.sender.url}")
//	public String otpSenderUrl;
//	
//	@Value("${otp.templateid.sender.token}")
//	public String templateId;
//	
//	@Value("${otp.token.verify.url}")
//	public String otpVerifyUrl;
//	
//	@Value("${otp.templateid.verify.token}")
//	public String templateVerifyId;
//	
//	@Value("${otp.channel.sender.token}")
//	public String channelSenderToken;
//	
//	@Value("${otp.expiry.sender.token}")
//	public int expirySenderToken;
//	
//	@Value("${otp.length.sender.token}")
//	public int otpLengthSenderToken;
//	
//	@Value("${otp.less.sender.url}")
//	public String otpLessSenderUrl;
//	
//	@Value("${otp.less.sender.client.id}")
//	public String otpLessSenderClientId;
//	
//	@Value("${otp.less.sender.client.secret}")
//	public String otpLessSenderClientSecret;
//	
//	@Value("${otp.less.verify.url}")
//	public String otpLessVerifyUrl;
//	
//	@Value("${otp.less.resender.url}")
//	public String otpLessResenderUrl;
//	
//	@Value("${otp.less.sender.client.enable}")
//	public String otpLessSenderClientEnable;
	
	@Value("${erupi.signature.keystore.path}")
	public String getSignaturePath;
	
	@Value("${erupi.signature.keystore.password}")
	public String getSignaturePassword;
	
	
	
	@Value("${erupi.create.vouchers.url}")
	public String getCreateVouchersUrl;
	
	@Value("${erupi.create.vouchers.apikey}")
	public String getCreateVouchersToken;
	
	@Value("${erupi.create.vouchers.mid}")
	public String getCreateVouchersMid;
	
	
	@Value("${api.key}")
    private String apiKey;

    @Value("#{'${whitelisted.ips}'.split(',')}")
    private Set<String> whitelistedIps;

    public boolean isValidApiKey(String key) {
        return apiKey.equals(key);
    }

    public boolean isIpWhitelisted(HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();
        return whitelistedIps.contains(clientIp);
    }
}
