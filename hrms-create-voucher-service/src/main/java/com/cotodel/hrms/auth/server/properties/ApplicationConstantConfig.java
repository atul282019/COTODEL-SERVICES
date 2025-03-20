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
	   
    @Value("${erupi.voucher.status.url}")
	public String getVoucherStatusUrl;
    
    @Value("${erupi.voucher.resend.url}")
	public String getVoucherSmsUrl;
    
    @Value("${erupi.indianbank.voucher.key}")
	public String erupiIndianbankVoucherKey;
    
    @Value("${erupi.indianbank.voucher.ende.key}")
	public String erupiIndianbankVoucherEndeKey;
    
    @Value("${erupi.indianbank.voucher.create.url}")
	public String erupiIndianbankVoucherCreateUrl;
    
    @Value("${erupi.indianbank.voucher.revoke.url}")
	public String erupiIndianbankVoucherRevokeUrl;
    
    @Value("${erupi.indianbank.voucher.inquiry.url}")
	public String erupiIndianbankVoucherInquiryUrl;
    
    
    
    
}
