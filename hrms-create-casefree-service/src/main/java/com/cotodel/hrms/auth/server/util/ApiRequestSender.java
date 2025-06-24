package com.cotodel.hrms.auth.server.util;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.Base64;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cotodel.hrms.auth.server.dto.EncryptedRequest;
import com.cotodel.hrms.auth.server.dto.EncryptedResponse;
import com.google.gson.Gson;
public class ApiRequestSender {
	private static final Logger logger = LoggerFactory.getLogger(ApiRequestSender.class);
	 public static String createRequest(String request,String url,String pubPath,String apiKey,String privatePath) throws Exception {
	        // Generate a 16-byte random session key (RANDOMNO)
		 	logger.info("In request creation ApiRequestSender:createRequest:");
	        byte[] sessionKey = new byte[16]; // 16 bytes for the session key
	        SecureRandom random = new SecureRandom();
	        random.nextBytes(sessionKey);

	        // Load the public key from the certificate
	        PublicKey publicKey = loadPublicKey(pubPath);
	        logger.info("createRequest :publicKey::"+publicKey);
	        // Encrypt the session key using RSA
	        byte[] encryptedKey = encryptSessionKey(sessionKey, publicKey);
	        String encrKey = Base64.getEncoder().encodeToString(encryptedKey);
	        logger.info("createRequest :encrKey:"+encrKey);
	        // Encrypt the request payload using AES

	        String stri=request;
	        logger.info("createRequest :request:"+stri);
	       //System.out.println(stri);
	        byte[] responsePayload = stri.getBytes(); // Example payload	        
	        byte[][] encryptedDataResult = encryptAES(responsePayload, sessionKey);
	        
	        // Option A: Send Base64 Encoded IV in "iv" tag
	        String ivBase64 = Base64.getEncoder().encodeToString(encryptedDataResult[0]);
	        String encryptedDataBase64 = Base64.getEncoder().encodeToString(encryptedDataResult[1]);
	        logger.info("createRequest :ivBase64:"+ivBase64);
	        logger.info("createRequest :encryptedDataBase64:"+encryptedDataBase64);
//	        // Create the JSON request
	        
	        String requestId = UUID.randomUUID().toString();
	        
	        // Step 5: Prepare the complete request
	        EncryptedRequest encryptedRequest = new EncryptedRequest();
	        encryptedRequest.setRequestId(requestId);
	        encryptedRequest.setService("AccountCreation");
	        encryptedRequest.setEncryptedKey(encrKey);
	        encryptedRequest.setIv(ivBase64); // Encode IV to Base64
	        encryptedRequest.setEncryptedData(encryptedDataBase64);
	        encryptedRequest.setOaepHashingAlgorithm("NONE");
	        encryptedRequest.setClientInfo(""); // Add if needed
	        encryptedRequest.setOptionalParam(""); // Add if needed
	        
	        String jsonString=encriptRequest(encryptedRequest);
	        //System.out.println(jsonString);
	        logger.info("createRequest :jsonString:"+jsonString);

	        logger.info("api request :url:"+url);
	        String message=sendRequest(jsonString, url,apiKey,privatePath); // Replace with your API URL
	        logger.info("api request message :message:"+message);
	        return message;
	    }

	    private static PublicKey loadPublicKey(String filename) throws Exception {
	        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
	        try (FileInputStream fis = new FileInputStream(filename)) {
	            return certFactory.generateCertificate(fis).getPublicKey();
	        }
	    }

	    private static byte[] encryptSessionKey(byte[] sessionKey, PublicKey publicKey) throws Exception {
	        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
	        return cipher.doFinal(sessionKey);
	    }

	    private static byte[][] encryptAES(byte[] data, byte[] sessionKey) throws Exception {
	        // Generate a random IV
	        byte[] iv = new byte[16];
	        SecureRandom random = new SecureRandom();
	        random.nextBytes(iv);

	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        SecretKeySpec secretKey = new SecretKeySpec(sessionKey, "AES");
	        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));

	        // Encrypt the data
	        byte[] encryptedData = cipher.doFinal(data);

	        // Return both the IV and encrypted data
	        return new byte[][]{iv, encryptedData};
	    }

	    private static String sendRequest(String jsonRequest, String apiUrl,String apiKey,String privatePath) throws Exception {
	        // Create a URL object
	        URL url = new URL(apiUrl);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        
	        // Set the request method and headers
	        connection.setRequestMethod("POST");
	        connection.setRequestProperty("Content-Type", "application/json");
	        connection.setRequestProperty("apikey", apiKey); // Replace with your API key
	        connection.setDoOutput(true);

	        // Write the JSON request to the output stream
	        try (OutputStream os = connection.getOutputStream()) {
	            os.write(jsonRequest.getBytes());
	            os.flush();
	        }

	        // Read the response (optional)
	        int responseCode = connection.getResponseCode();
	        logger.info("Response Code: " + responseCode);
	        String message="";
	        if (responseCode == HttpURLConnection.HTTP_OK) {
	            // Read response logic here (if needed)
	        	 try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
	                 StringBuilder response = new StringBuilder();
	                 String inputLine;

	                 while ((inputLine = in.readLine()) != null) {
	                     response.append(inputLine);
	                 }
	                 Gson gson = new Gson();
	                 EncryptedResponse apiResponse = gson.fromJson(response.toString(), EncryptedResponse.class);
	                 message=ResponseDecryption.decriptResponse(apiResponse, privatePath,responseCode);
	                 // Print the response
	                 logger.info("Response: " + response.toString());
	                return message;
	             }
	         } else {
	             // Handle errors
	        	 logger.info("Error in response: " + responseCode);
	             message=String.valueOf(responseCode);
	             message="{"+"\"responseCode\""+":"+"\""+responseCode+"\""+"}";
	         }
	        return message;
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
	    

	  
}
