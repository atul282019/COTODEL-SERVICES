package com.cotodel.hrms.auth.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.util.CommonUtility;

@Service
public class TokenGeneration {
	
	
	private static final Logger logger = LoggerFactory.getLogger(TokenGeneration.class);

	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
	@Autowired
    private EntityManager entityManager;
	
	public String getToken(String url) {
			
			logger.info("opening getInTouch");
			String responseToken="";
			String authToken = "";
	    	try {	    		
	    		String companyId = "HRMS00001";
	    		 responseToken =getToken(companyId,url);
					//String authToken = "";
					if (!ObjectUtils.isEmpty(responseToken)) {
						JSONObject getTokenRes = new JSONObject(responseToken);
						authToken = getTokenRes.getString("access_token");
					}
	    	 
	    	}catch (Exception e) {
				
	    		//e.printStackTrace();
			}
			return authToken;
		}

	public String getToken(String companyId,String url) {
		// TODO Auto-generated method stub
		//System.out.println("url:"+url);
		return CommonUtility.getTokenRequest(null,"",companyId,url);
	}
	 public static String getMerTranId(String bankcode) {
	    	bankcode=bankcode==null?"":bankcode;
	    	TokenGeneration helper=new TokenGeneration();
	    	Long value=helper.getMerchantTranId();
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHH");
	        String date = sdf.format(new Date());
	        String uniqueId=bankcode+date+value;
	        System.out.println(uniqueId);
	    	return uniqueId;
	    }
	    
	    public long getMerchantTranId() {
	        Query query = entityManager.createNativeQuery("SELECT nextval('merchanttranid')");
	        return ((Number) query.getSingleResult()).longValue();
	    }
}
