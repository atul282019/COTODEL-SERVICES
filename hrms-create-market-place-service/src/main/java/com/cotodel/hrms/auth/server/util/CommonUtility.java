package com.cotodel.hrms.auth.server.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.cotodel.hrms.auth.server.dto.EncryptedRequest;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class CommonUtility {
	
	 @Autowired
	 private RestTemplate restTemplate;

	private static final Logger logger = LogManager.getLogger(CommonUtility.class);

	public static final String yyyy_MM_dd = "yyyy-MM-dd";
	public static final String ddMMYYYY = "ddMMyyyy";
//	private final RestTemplate restTemplate;
//    private final PublicKey publicKey; // Load your public key

//    public CommonUtility(PublicKey publicKey) {
//        this.restTemplate = new RestTemplate();
//        this.publicKey = publicKey; // Initialize the public key
//    }

	public static String userRequest(String sAccessToken,String requestJson,String url,String publicPath,String privatePath){
		String returnStr=null;
		String decript=null;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		try{
			logger.info(" Request Json for url"+url+"---"+requestJson);

			headers.setContentType(MediaType.APPLICATION_JSON);
			
			if(sAccessToken!=null && !sAccessToken.isEmpty()) {
				headers.setBearerAuth(sAccessToken);
			}
			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(requestJson, publicPath);
			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(jsonEncriptObject);
			HttpEntity<String> entity = new HttpEntity<String>(jsonEncript,headers);

			returnStr = restTemplate.postForObject(url, entity, String.class);
			EncriptResponse enResponse= EncryptionDecriptionUtil.convertFromJson(returnStr, EncriptResponse.class);
			decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), privatePath);
			logger.info(" response Json---"+returnStr);
			return decript;
		}catch(HttpStatusCodeException e) {
			logger.error("HttpStatusCodeException error in---"+url+"-"+e.getResponseBodyAsString());
			return e.getResponseBodyAsString();
		}catch(Exception e){
			logger.error(" error in---"+url+"-"+e);
			return null;
		}finally {
			restTemplate=null;headers=null;sAccessToken=null;requestJson=null;url=null;	
		}		
	}
	
	public static String userSmsRequest(String clientid,String secretid,String requestJson,String url){
		String returnStr=null;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		try{
			logger.info(" Request URL---"+url);
			logger.info(" Request Json---"+requestJson);

			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("clientId", clientid);
			headers.set("clientSecret", secretid);	 

			HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);

			returnStr = restTemplate.postForObject(url, entity, String.class);
			logger.info(" response Json---"+returnStr);
			return returnStr;
		}catch(Exception e){
			e.printStackTrace();
			
			return e.getMessage();
		}finally {
			restTemplate=null;headers=null;	
		}		
	}

	public static String getTokenRequest(String sAccessToken,String requestJson,String companyId,String url){
		String returnStr=null;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		try{
			logger.info(" Request URL---"+url);
			logger.info(" Request header---"+companyId);
			headers.setContentType(MediaType.APPLICATION_JSON);
			//headers.set("companyId", companyId);
			if(sAccessToken!=null && !sAccessToken.isEmpty()) {
				headers.setBearerAuth(sAccessToken);
			}

			HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
	        returnStr = response.getBody();
			logger.info(" response Json---"+returnStr);
			return returnStr;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally {
			restTemplate=null;headers=null;	
		}		
	}
	

	public static  String userRequestForCreateVoucher(String sAccessToken,String mid,String requestJson,String url,String filepath,String privateFilepath) throws Exception{
		String response="";
		
		String sessionKey = EncryptionUtil.generateSessionKey(16);
		
		//PublicKey publicKey = EncryptionUtil.getPublicKeyFromCer(filepath);
		
		PublicKey publicKey=  EncryptionUtil.getPublicKeyFromCerFile(filepath);
		
		String str="MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAsIwVStQi6aSMLBZu3vhafOR5NTMNp+TXPwyk/6VoaSQfDnZaSQPYhdt4a8X215KwXwpIL1eBJOH2NW8jp5AO4WauHWEwEggJvPaC8FgzZtDhjYexOk+/yaDbY7U9BofJSU76VIBxRoN7YmAknAKrpfn0ukXPPuUx5Ny/cy85nunqo5M8Acf2VVwSGZQMBZFSm3yxYOdS4laDlM+s1w+5wLDMjYSgIMm76rpVdO3hs2n2dSAYM6XMOaqNDwHdZk6n8lPgivYVXjTz7KU9eqkFnecWvn2ugRI7hgrplZxS020k0QBeYd0AH7zJZKS3Xo5VycL01UO/WYOQvB7v8lge7TiQZ3CCrnuykqcJ/r5DMLO/cKQAeZi+LQ95FQg39joO8G7bfO7+a3Gs8Re3mRW7AA8x1aEn7XZMOUu4l4IfNvwh20V4cz3xvGXdr9ZLFvgX5593MxCDBjkiaynzG8gmLVTIoaItPy+khwO/vjfWka0L3yvT3l55R4H/KRKxlHaY58HVdLbuWrUoH/4gbkYFYFC+rejBW5wbE0FJmWIkEXLKsTlXcsn6eAzi4BRxidQ/4rIEf8qWpSFzJobivBnWe4bpBA19g3N47PDpD5xS6uj7ODSBhEn22UnsiDaGV+RhsXYA/xqaJCjB6+W7CN00Lowr87sUoT4VAK8wrOk4D5sCAwEAAQ==";
		publicKey=  EncryptionUtil.stringToPublicKey(str);
		logger.info("sessionKey...."+sessionKey);
		
		//CommonUtility(publicKey);
		
		String encryptedKey = EncryptionUtil.encryptSessionKey(sessionKey, publicKey);
		logger.info("sAccessToken...."+sAccessToken);
		logger.info("requestJson...."+requestJson);
		logger.info("publicKey...."+publicKey);
		
		// Step 3: Generate a random IV for AES encryption
        byte[] iv = new byte[16]; // AES block size
        new SecureRandom().nextBytes(iv); // Generate a random IV

        // Step 4: Encrypt the request payload
        ObjectMapper objectMapper = new ObjectMapper();
       // String requestData = objectMapper.writeValueAsString(accountRequest); // Convert request to JSON
        String encryptedData = EncryptionUtil.encryptData(requestJson, sessionKey, iv);
        
        String requestId = UUID.randomUUID().toString();
        
        logger.info("requestId...."+requestId);
        // Step 5: Prepare the complete request
        EncryptedRequest encryptedRequest = new EncryptedRequest();
        encryptedRequest.setRequestId(requestId);
        encryptedRequest.setService("AccountCreation");
       // encryptedRequest.setEncryptedKey(encryptedKey);
        encryptedRequest.setIv(Base64.getEncoder().encodeToString(iv)); // Encode IV to Base64
        encryptedRequest.setEncryptedData(encryptedData);
        encryptedRequest.setOaepHashingAlgorithm("NONE");
        encryptedRequest.setClientInfo(""); // Add if needed
        encryptedRequest.setOptionalParam(""); // Add if needed
        
        String ecripotDatajson=encriptRequest(encryptedRequest);
		
		HttpHeaders headers = new HttpHeaders();
        
        headers.set("apikey", sAccessToken);
        
        logger.info("headers: " + headers);
        logger.info("url: " + url);
        logger.info("ecripotDatajson: " + ecripotDatajson);
        
        HttpEntity<String> requestEntity = new HttpEntity<>(ecripotDatajson, headers);
        
        
        RestTemplate restTemplate = new RestTemplate();
        
        try {
        	
        	response = restTemplate.postForObject(url, requestEntity, String.class);
        	
        	String result=decryptRequest(encryptedKey, response, privateFilepath);
        //	logger.info("result: " + result);
            logger.info("Response: " + response);
            return response;
        } catch (HttpClientErrorException e) {
            logger.error("Error Response: " + e.getResponseBodyAsString());
            logger.error("Status Code: " + e.getStatusCode());
            logger.error("Message Code: " + e.getMessage());
           // response=e.getMessage();
        }catch (Exception e) {
        	e.printStackTrace();
            logger.error("Message Code: " + e.getMessage());
           // response=e.getMessage();
        }finally {
        	try {
        		restTemplate=null;headers=null;sAccessToken=null;requestJson=null;url=null;	
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		}		
        return response;
	}
	
	
	 private static final String RSA = "RSA/ECB/PKCS1Padding";
	    private static final String AES = "AES/CBC/PKCS5Padding";
	    private static final int IV_SIZE = 16;
	public static String decryptRequest(String encryptedKey, String encryptedData, String filepath) throws Exception {
		
		PrivateKey privateKey = EncryptionUtil.getPrivateKey(filepath);
		
		// Step 1: Decrypt the session key using RSA
        Cipher rsaCipher = Cipher.getInstance(RSA);
        rsaCipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] sessionKeyBytes = rsaCipher.doFinal(Base64.getDecoder().decode(encryptedKey));
        SecretKeySpec sessionKey = new SecretKeySpec(sessionKeyBytes, "AES");

        // Step 2: Extract IV and encrypted response
        byte[] combined = Base64.getDecoder().decode(encryptedData);
        byte[] iv = new byte[IV_SIZE];
        System.arraycopy(combined, 0, iv, 0, iv.length);
        byte[] encryptedResponse = new byte[combined.length - iv.length];
        System.arraycopy(combined, iv.length, encryptedResponse, 0, encryptedResponse.length);

        // Step 3: Decrypt the response using AES
        IvParameterSpec ivParams = new IvParameterSpec(iv);
        Cipher aesCipher = Cipher.getInstance(AES);
        aesCipher.init(Cipher.DECRYPT_MODE, sessionKey, ivParams);
        byte[] decryptedResponse = aesCipher.doFinal(encryptedResponse);

        return new String(decryptedResponse, StandardCharsets.UTF_8);
    }
	public static String encriptRequest(EncryptedRequest req) {
		JSONObject request= new JSONObject();				
		request.put("requestId", req.getRequestId());
		request.put("service", req.getService());
		request.put("encryptedKey", req.getEncryptedKey());
		request.put("iv", req.getIv());
		request.put("encryptedData", req.getEncryptedData());
		request.put("oaepHashingAlgorithm", req.getOaepHashingAlgorithm());
		request.put("clientInfo",req.getClientInfo());
		request.put("optionalParam", req.getOptionalParam());
		
		return request.toString();
	}
	public static String userRequestForRepute(String requestJson,String url){
		String returnStr=null;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		try{
			logger.info(" Request URL---"+url);
			logger.info(" Request Json---"+requestJson);

			//headers.setContentType(MediaType.APPLICATION_JSON);

			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	        headers.set("Authorization", "Basic YTc2Y2M2OGMtZmI5MS00ODI2LTk5NDctYmJlYmVlZjAxZTBhOnZlVEh1Y1UwOGJ1M1YzTnhFaE9MOUFkeDJFUVZWYlBT");

			HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);

			returnStr = restTemplate.postForObject(url, entity, String.class);
			logger.info(" response Json---"+returnStr);
			return returnStr;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally {
			restTemplate=null;headers=null;	
		}		
	}
	public static String getAccessToken(String code,String redirectUri,String sendurl) {
		try {
            // URL for the token request
            //URL url = new URL("https://app.demohrms.stg.repute.net/oauth2/token");
			URL url = new URL(sendurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            // Set HTTP request method to POST
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Authorization", "Basic YTc2Y2M2OGMtZmI5MS00ODI2LTk5NDctYmJlYmVlZjAxZTBhOnZlVEh1Y1UwOGJ1M1YzTnhFaE9MOUFkeDJFUVZWYlBT");

            // Enable input and output streams for POST data
            connection.setDoOutput(true);
            connection.setDoInput(true);

            // Form data to send in the POST request
            String urlParameters = "code="+code
                    + "&grant_type=authorization_code"
                    + "&redirect_uri="+redirectUri;

            // Write the data to the output stream
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = urlParameters.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Read the response
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println("Response: " + response.toString());
                return response.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	public static String userRequestForReputeReputeCompDetails(String token,String url){
		String returnStr=null;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		try{
			//url = url+"/resource/apis/v1/company-details";
			System.out.println("url::"+url);
			url=url+"/resource/apis/v1/employee-list?offset=0&limit=100";
	        // Set headers
	        headers.set("Authorization", "Bearer "+token);
	        headers.set("Cookie", "SESSION=NzNkODI1MjMtMDJkOS00ZGUwLThlNWUtN2FkZjY4MjhjZjc1");
	        
	        System.out.println("headers::"+headers);
	        // Create HttpEntity with headers
	        HttpEntity<String> entity = new HttpEntity<>("", headers);

	        // Send the POST request
	        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

	        // Return the response body
	        return response.getBody();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally {
			restTemplate=null;headers=null;	
		}		
	}
	
	public static String userRequestForReputeReputeCompDetails(String token,String url,String employeeId){
		String returnStr=null;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		try{
			//url = url+"/resource/apis/v1/company-details";
			System.out.println("url::"+url);
			url=url+"/resource/apis/v2/employee-list?employee_id="+employeeId+"&offset=0&limit=100";
	        // Set headers
	        headers.set("Authorization", "Bearer "+token);
	        headers.set("Cookie", "SESSION=NzNkODI1MjMtMDJkOS00ZGUwLThlNWUtN2FkZjY4MjhjZjc1");
	        
	        System.out.println("headers::"+headers);
	        // Create HttpEntity with headers
	        HttpEntity<String> entity = new HttpEntity<>("", headers);

	        // Send the POST request
	        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

	        // Return the response body
	        return response.getBody();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally {
			restTemplate=null;headers=null;	
		}		
	}
//	public static void main(String[] args) {
//		getAccessToken("GTDx6RJMV1OPOok0olllOo3KEQGrq6YtOFADctB1NER4z_xgPEymsMD4GSYwP8z3oCVc-Nv-ORq-WFXRvoFmhqWhfFhVfUbRNORhq8qwC9uGOXIRDbp0frYB3cWuNMRX");
//	}
}
