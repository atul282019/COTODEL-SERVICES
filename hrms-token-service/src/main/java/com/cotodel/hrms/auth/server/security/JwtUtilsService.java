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
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

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

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.expiration.ms}")
  private int jwtExpirationMs;
  
  @Autowired
  EmployerMasterService employerMasterService;

  public String generateJwtToken(String companyCode) {

    
    if(ObjectUtils.isEmpty(companyCode)) {
    	 return Jwts.builder()
    		    	.claim("company_code","HRMS00001")
    		        .claim("role","PRE_LOGIN_GUEST")	
    		        .setSubject("HRMS_PRE_LOGIN_TOKEN")
    		        .setIssuedAt(new Date())
    		       // .setExpiration(Date.from(Instant.now().plus(10, ChronoUnit.MINUTES)))
    		        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
    		        .signWith(key(jwtSecret), SignatureAlgorithm.HS256)
    		        .compact();
    	
    }else {
    	 EmployerMaster employerMaster=	employerMasterService.getByEmployerCode(companyCode);
		  if(!ObjectUtils.isEmpty(employerMaster)) {
			  LicenceMaster licenceMaster=	employerMaster.getLicenceMaster();
			 
			  
			  return Jwts.builder()
	    		    	.claim("company_code",employerMaster.getEmployer_code())
	    		        .claim("role","POST_LOGIN_GUEST")	
	    		        .setSubject("HRMS_POST_LOGIN_TOKEN")
	    		        .setIssuedAt(new Date())
	    		       // .setExpiration(Date.from(Instant.now().plus(10, ChronoUnit.MINUTES)))
	    		        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
	    		        .signWith(key(licenceMaster.getEnc_key()), SignatureAlgorithm.HS256)
	    		        .compact();
		  }  
    	
    	
    }
	return null;
    
   
  }
  
  private Key key(String jwtSecret) {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parserBuilder().setSigningKey(key(jwtSecret)).build()
               .parseClaimsJws(token).getBody().getSubject();
  }

  public boolean validateJwtToken(String authToken,String companyCode) {
	  try {
		  EmployerMaster employerMaster=	employerMasterService.getByEmployerCode(companyCode);
		  if(!ObjectUtils.isEmpty(employerMaster)) {
			  LicenceMaster licenceMaster=	employerMaster.getLicenceMaster();
			  Jwts.parserBuilder().setSigningKey(key(licenceMaster.getEnc_key())).build().parse(authToken);
			  return true;
		  }else {
			  
			  Jwts.parserBuilder().setSigningKey(key(jwtSecret)).build().parse(authToken);
			  return true; 
		  }
	  } catch (MalformedJwtException e) {
		  logger.error("Invalid JWT token: {}", e.getMessage());
	  } catch (ExpiredJwtException e) {
		  logger.error("JWT token is expired: {}", e.getMessage());
	  } catch (UnsupportedJwtException e) {
		  logger.error("JWT token is unsupported: {}", e.getMessage());
	  } catch (IllegalArgumentException e) {
		  logger.error("JWT claims string is empty: {}", e.getMessage());
	  }

	  return false;
  }
  
  
  
  public String parseJwt(String requestToken) {

	    if (StringUtils.hasText(requestToken) && requestToken.startsWith("Bearer ")) {
	      return requestToken.substring(7, requestToken.length());
	    }
	    return requestToken;
	  }
  
  
}

