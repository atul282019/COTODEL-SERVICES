package com.cotodel.hrms.auth.server.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import org.springframework.stereotype.Service;

@Service
public class GstApiService {

    
    private String apiKey="622ddf9ffffbd5f70d037cfc8edaff5fcbe7a34d1ccb6462d73ac2d09591f758";  // Set this in application.properties or @Value

    private static final String CLIENT_ID = "com.cotodel"; // The clientId you mentioned

    // Create a RestTemplate bean to call the API
    RestTemplate restTemplate = new RestTemplate();

   
    public String getTaxpayerDetails(String gstin) {
        // Construct the URL
        //String url = "https://apisetu.gov.in/gstn/v2/taxpayers/" + gstin;
       String url = "https://sandbox.api-setu.in/org-collections/pan-PANCR-ABCDE1234F" ;
        
        HttpHeaders headers = new HttpHeaders();
        // Set up headers
        try {
        	
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-APISETU-APIKEY", apiKey);  // Add your API key here
            headers.set("X-APISETU-CLIENTID", CLIENT_ID);       // Add your clientId here

            // Set the headers into the HttpEntity
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // Make the GET request
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            String returnStr = response.getBody();
    		System.out.println(" response Json---"+returnStr);
    		return returnStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
    }
    public static void main(String[] args) {
    	GstApiService gstApiService=new GstApiService();
    	gstApiService.getTaxpayerDetails("07AALCC4033D1Z0");
    	
	}
   
}
