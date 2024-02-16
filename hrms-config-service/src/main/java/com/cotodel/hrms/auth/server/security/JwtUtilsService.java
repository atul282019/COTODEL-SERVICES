package com.cotodel.hrms.auth.server.security;

/**
 * @author vinay
 */
import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.cotodel.hrms.auth.server.config.RestTemplateConfig;
import com.cotodel.hrms.auth.server.dto.TokenAuthRequest;
import com.cotodel.hrms.auth.server.dto.TokenAuthResponse;
import com.cotodel.hrms.auth.server.entity.EmployerMaster;
import com.cotodel.hrms.auth.server.entity.LicenceMaster;
import com.cotodel.hrms.auth.server.service.EmployerMasterService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtilsService {

	private static final Logger logger = LoggerFactory.getLogger(JwtUtilsService.class);

	@Autowired
	RestTemplateConfig restTemplateConfig;

	@Value("${auth.token.verify.url}")
	private String tokenAuthUrl;

	public boolean validateJwtToken(String authToken,String companyCode) {
		try {


			if(!ObjectUtils.isEmpty(authToken) ) {

				HttpHeaders header= new HttpHeaders();
				header.add("Content-Type", "application/json");
				header.add("Accept", "application/json");

				TokenAuthRequest tokenReq= new TokenAuthRequest();
				tokenReq.setAuthToken(authToken);
				tokenReq.setCompanyCode(companyCode);

				HttpEntity<?> requestEntity= new HttpEntity<Object>(tokenReq,header);

				ResponseEntity<TokenAuthResponse> responseEntity = new RestTemplate()
						.exchange(tokenAuthUrl,HttpMethod.POST,requestEntity,TokenAuthResponse.class);

				if(responseEntity.getBody().isStatus())
					return true;
			}else {
				return false; 
			}
		} catch (Exception e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		}

		return false;
	}


}

