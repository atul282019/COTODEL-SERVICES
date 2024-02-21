package com.cotodel.hrms.auth.server.security;

/**
 * @author vinay
 */
import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.cotodel.hrms.auth.server.error.Error;
import com.cotodel.hrms.auth.server.error.ErrorResponse;
import com.cotodel.hrms.auth.server.exception.ApiExceptions;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class HrmsSecurityFilter implements Filter{

	
	private static final Logger logger = LoggerFactory.getLogger(HrmsSecurityFilter.class);
	
	@Value(value ="${cross.origin.request.validation}")
	private String crossValidation;
	
	@Value(value ="${auth.token.authentication.validation}")
	private String authTokenValidation;
	
	@Value(value ="${excluded.validation.api}")
	private String excludedValidationApi;
	
	@Autowired
	JwtUtilsService jwtUtilsService;
	
	FilterConfig filterConfig = null;

	public void init(FilterConfig filterConfig)
	{
		this.filterConfig = filterConfig;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		 
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse httpServletresponse = (HttpServletResponse)response;
		
		String requestURI= httpServletRequest.getRequestURI();
		String ip= httpServletRequest.getRemoteAddr();
		String companyId=httpServletRequest.getHeader("companyId");
		String authToken=httpServletRequest.getHeader("Authorization");
		String txnId=httpServletRequest.getHeader("txnId");
		
		
		logger.info("Filter Loggin Request {} : {}",httpServletRequest.getMethod(),httpServletRequest.getRequestURI());
		String method=	httpServletRequest.getMethod();
		boolean authCheck=false;
		try {
			if(crossValidation.equals("Y")) {
				if(!method.equals("OPTIONS")) {
					authCheck=	tokenAuthenticate(requestURI,ip,companyId,authToken,txnId);
				}
				
			}else {
				authCheck=tokenAuthenticate(requestURI,ip,companyId,authToken,txnId);
			}
			if(authCheck) {
			chain.doFilter(request, response);
			}else {
				ErrorResponse errResp= new ErrorResponse(new Date(), null, null, 
						new Error(String.valueOf("500"),"Unhandled Exceptions",null));
				
				httpServletresponse.setStatus(200);
				httpServletresponse.getWriter().write(convertObjectToJson(errResp));
			}
			
		}catch (ApiExceptions e) {
			// TODO: handle exception
		
		ErrorResponse errResp= new ErrorResponse(new Date(), null, null, 
				new Error(String.valueOf(e.getCode()),e.getDescription(),e.getStackTrace().toString()));
		
		httpServletresponse.setStatus(200);
		httpServletresponse.getWriter().write(convertObjectToJson(errResp));
		
		}
			
		
	}
	
	private boolean tokenAuthenticate(String requestURI, String ip, String companyId, String authToken, String txnId) {
		boolean authAction=false;
		
		if(authTokenValidation.equals("Y") && !requestURI.contains("swagger") && !requestURI.contains("api-docs")) {
		logger.info("Token Authentication is applicable. Validating Bearer Token.");
		
		if(requestURI!=null && excludedValidationApi.contains(requestURI))
			authAction=true;
		else
			authAction=jwtUtilsService.validateJwtToken(authToken,companyId);
			
		}else {
			logger.info("Token Authentication is skipped");
			authAction=true;
		}
		
		return authAction;
	}

	public String convertObjectToJson(Object obj)throws JsonProcessingException{
		if(obj==null) {
			return null;
		}
		ObjectMapper mapper= new ObjectMapper();
		
		return mapper.writeValueAsString(obj);
	}
	
}
