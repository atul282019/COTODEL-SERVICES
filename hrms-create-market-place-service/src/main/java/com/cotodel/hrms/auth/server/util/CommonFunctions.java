package com.cotodel.hrms.auth.server.util;

import org.springframework.util.StringUtils;

public class CommonFunctions {
	
	
	public static String parseJwt(String requestToken) {

	    if (StringUtils.hasText(requestToken) && requestToken.startsWith("Bearer ")) {
	      return requestToken.substring(7, requestToken.length());
	    }
	    return requestToken;
	  }
  

}
