package com.cotodel.hrms.auth.server.service.impl;

import java.util.Base64;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dto.ReputeCompanyDetails;
import com.cotodel.hrms.auth.server.dto.ReputeEmployee;
import com.cotodel.hrms.auth.server.dto.ReputeEmployeeRequest;
import com.cotodel.hrms.auth.server.dto.ReputeEmployeeSingleRequest;
import com.cotodel.hrms.auth.server.dto.ReputeSingleEmployee;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.ReputeService;
import com.cotodel.hrms.auth.server.util.CommonUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
@Repository
public class ReputeServiceImpl implements ReputeService{

	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
	@Override
	public String getReputeToken(String code,String url) {
		
		try {
			 // Set up the body data
			String redirectUri=applicationConstantConfig.tokenRedirectUrl;
	        String body = "code="+code+ "&grant_type=authorization_code&redirect_uri="+ redirectUri;
			//String response=CommonUtility.userRequestForRepute(body, url);
	        String response=CommonUtility.getAccessToken(code,redirectUri,url+"/oauth2/token");
	        String accessToken ="";
	        String idToken ="";
	        if(response!=null) {
	        	JSONObject jsonObject = new JSONObject(response);
	        	accessToken = jsonObject.getString("access_token");
	        	idToken = jsonObject.getString("id_token");
	        }
	        String[] jwtParts = idToken.split("\\.");
	        String payload = jwtParts[1];  // This is the middle part (payload)
	        
	        // Decode the payload from Base64
	        String value=new String(Base64.getDecoder().decode(payload));
	        System.out.println("value: " + value);

		} catch (Exception e) {
			e.printStackTrace();
		}
	    
		return null;
	}
	
	
public ReputeCompanyDetails parseJson(String json) {
    	ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, ReputeCompanyDetails.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


@Override
public List<ReputeEmployee> getReputeEmpList(ReputeEmployeeRequest reputeEmployeeRequest) {
	// TODO Auto-generated method stub
	String response=CommonUtility.userRequestForReputeReputeCompDetails(reputeEmployeeRequest.getAccessToken(), reputeEmployeeRequest.getEndpoint());
	List<ReputeEmployee> list=convertJsonToPojo(response);
	return list;
}
public List<ReputeEmployee> convertJsonToPojo(String json) {
	
    ObjectMapper objectMapper = new ObjectMapper();
    
    List<ReputeEmployee> employees =null;
    try {
    	employees = objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, ReputeEmployee.class));
	} catch (Exception e) {
		e.printStackTrace();
	}
    
    return employees;
}
public ReputeSingleEmployee convertJsonToSinglePojo(String json) {
	
	ObjectMapper objectMapper = new ObjectMapper();
	ReputeSingleEmployee response = null;
    try {
        response = objectMapper.readValue(json, ReputeSingleEmployee.class);
    } catch (Exception e) {
        e.printStackTrace();
    }
    return response;
    
}

@Override
public ReputeEmployee getReputeEmpSingle(ReputeEmployeeSingleRequest reputeEmployeeRequest) {
	// TODO Auto-generated method stub
	ReputeEmployee reputeEmployee=new ReputeEmployee();
		String response=CommonUtility.userRequestForReputeReputeCompDetails(reputeEmployeeRequest.getAccessToken(), reputeEmployeeRequest.getEndpoint(),reputeEmployeeRequest.getEmployeeId());
		ReputeSingleEmployee list=convertJsonToSinglePojo(response);
		List<ReputeEmployee> reputeList=list.getEmployeeList();
		if(reputeList!=null && reputeList.size()>0) {
			reputeEmployee=reputeList.get(0);
		}
		
		return reputeEmployee;
}

}
