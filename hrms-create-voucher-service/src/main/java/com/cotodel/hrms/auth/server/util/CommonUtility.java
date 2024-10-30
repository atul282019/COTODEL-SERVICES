package com.cotodel.hrms.auth.server.util;

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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.cotodel.hrms.auth.server.dto.EncryptedRequest;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class CommonUtility {

	private static final Logger logger = LogManager.getLogger(CommonUtility.class);

	public static final String yyyy_MM_dd = "yyyy-MM-dd";
	public static final String ddMMYYYY = "ddMMyyyy";
//	private final RestTemplate restTemplate;
//    private final PublicKey publicKey; // Load your public key

//    public CommonUtility(PublicKey publicKey) {
//        this.restTemplate = new RestTemplate();
//        this.publicKey = publicKey; // Initialize the public key
//    }

	public static String userRequest(String sAccessToken,String requestJson,String url){
		String returnStr=null;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		try{
			logger.info(" Request URL---"+url);
			logger.info(" Request Json---"+requestJson);

			headers.setContentType(MediaType.APPLICATION_JSON);

			if(sAccessToken!=null && !sAccessToken.isEmpty()) {
				headers.setBearerAuth(sAccessToken);
			}

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

//	public static String sendEmail(UserRequest req) {
//		// Set up mail server properties
//				req.setMobile("9911851042");
//				Properties properties = new Properties();
//				properties.put("mail.smtp.host", "smtp.gmail.com");
//				properties.put("mail.smtp.port", "587");
//				properties.put("mail.smtp.auth", "true");
//				properties.put("mail.smtp.starttls.enable", "true");
//				Session session = Session.getInstance(properties, new Authenticator() {
//					@Override
//					protected PasswordAuthentication getPasswordAuthentication() {
//						return new PasswordAuthentication("dkawal73@gmail.com", "jaygeajbqvinwacz");
//					}
//				});
//
//				Message msg = new MimeMessage(session);
//				try {
//					msg.setHeader("Content-Type", "text/plain; charset=UTF-8");
//					msg.setFrom(new InternetAddress(req.getEmail(), false));
//					msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(req.getEmail()));
//					msg.setSubject("Congratulations on Your Achievement");
//					msg.setContent("Verify Sigin", "text/html");
//					msg.setSentDate(new Date());
//					byte[] bytes = req.getMobile().getBytes(StandardCharsets.UTF_8);
//					String encoded = DatatypeConverter.printBase64Binary(bytes);
//					byte[] byt = req.getEmail().getBytes(StandardCharsets.UTF_8);
//					String emailbyt = DatatypeConverter.printBase64Binary(byt);
//
////					String confirmationUrl = applicationConstantConfig.emailVerifyUrl + "?token=" + encoded.replaceAll("==", "")
////							+ "/" + emailbyt;
////					String emailBody = "Click the link to verify your email: " + confirmationUrl;
//
////					String link = "<p></p><a href=" + applicationConstantConfig.emailVerifyUrl + "/"
////							+ emailbyt.replaceAll("==", "") + "/" + encoded.replaceAll("==", "")
////							+ "><h3>Please click here to verify....<h3></a>";
//					
//					String content=" <div style=\"max-width: 600px; margin: 0 auto;\">\r\n"
//							+ "    <h1 style=\"color: #333333;\">Congratulations on Your Achievement!</h1>\r\n"
//							+ "    <p style=\"font-size: 16px;\">Dear All,</p>\r\n"
//							+ "    <p style=\"font-size: 16px;\">I hope this email finds you well. I just wanted to take a moment to extend my heartfelt congratulations to you on your recent achievement! It's truly wonderful to see your hard work and dedication paying off.</p>\r\n"
//							+ "    <p style=\"font-size: 16px;\">Your accomplishment is a testament to your perseverance, skills, and determination. I have always admired your passion for what you do, and it's no surprise that you have achieved such success.</p>\r\n"
//							+ "    <p style=\"font-size: 16px;\">Please know that your efforts have not gone unnoticed, and I am genuinely thrilled for you. This is just the beginning of many great things to come, and I am confident that you will continue to excel in all your future endeavors.</p>\r\n"
//							+ "    <p style=\"font-size: 16px;\">Once again, congratulations on this well-deserved achievement! Wishing you continued success and happiness.</p>\r\n"
//							+ "    <p style=\"font-size: 16px;\">Warm regards,</p>\r\n"
//							+ "    <p style=\"font-size: 16px;\">Team Cotodel</p>\r\n"
//							+ "  </div>";
//					MimeBodyPart messageBodyPart = new MimeBodyPart();
//					// String password =generatePassword(8);
//					messageBodyPart.setContent(content, "text/html");
//					Multipart multipart = new MimeMultipart();
//					multipart.addBodyPart(messageBodyPart);
//					msg.setContent(multipart);
//					Transport.send(msg);
//					logger.info("verification mail sended successfully to :" + req.getEmail());
//					return req.getEmail();
//				} catch (MessagingException e) {
//					e.printStackTrace();
//				}
//				return req.getEmail();
//	}
//	public static boolean isValidEmail(String email)
//    {       
//		 String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";  
//	     //Compile regular expression to get the pattern  
//	     Pattern pattern = Pattern.compile(regex);  
//	     Matcher matcher = pattern.matcher(email);
//	     return matcher.matches();
//    }
	

	public static  String userRequestForCreateVoucher(String sAccessToken,String mid,String requestJson,String url,String filepath,String privateFilepath) throws Exception{
		String response="";
		
		String sessionKey = EncryptionUtil.generateSessionKey(16);
		
		PublicKey publicKey = EncryptionUtil.getPublicKeyFromCer(filepath);
		
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
        encryptedRequest.setEncryptedKey(encryptedKey);
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
        	
        //	String result=decryptRequest(encryptedKey, response, privateFilepath);
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
}
