package com.cotodel.hrms.auth.server.util;
import java.security.PrivateKey;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cotodel.hrms.auth.server.dto.EncryptedResponse;
public class ResponseDecryption {
	private static final Logger logger = LoggerFactory.getLogger(ResponseDecryption.class);
//	 static {
//	        // Add Bouncy Castle provider for RSA PKCS#1 decryption
//	        Security.addProvider(new BouncyCastleProvider());
//	    }

//	    public static void main(String[] args) throws Exception {
//	        // Example encrypted values (replace these with actual values)
//	        String encryptedKeyBase64 = "W1l+pdRoCoAcpmaJKvF70JBYdsC7IrxwOmymgxLU03LlAR5kjFigBp4P4vBoth61jJ66OB4Ag5ExIXx0nWAPKcXo7J9G+X4EJWzud/sRdJMYSkV0akfeITeytJdIZVVAmg2YgpHau3tbH97e8ivgDYSEn4QWUUgYO1BSzZVzWjWyXWp27Bq+BHXtZj8fwmNF5szMs3jGO+LtsWX6KTm2USw2y2iGR/MWp17R5Fih7YE+5TyWC5MRMppukUrMudHFruKFzJ0FcVP5XHfzvNJowKDcCIRJ54V8BnUWcnoLEP/OU3HNj3QnApo/b0TdPlR9dgz29iWVg7jAxkHCoRinzrW0fWd/X5hLcLLRlNmyr6gwKoIEGgSaPI8RARQLowtlsinboLlkcVDxfeLfqQXOvoENQkJUrx4Wv1/bcXXilFlIgI9MVGTgRVNG7gRW+1JgTp7s6imBHmenXPDWss2b76cThFcV/tHZlgu8e10qzQyLr834NDpUVOGJiBQNaCGIq8chvHUcRYto7WGRy2Ht6zXt7wdKuVTva3hlgHf4PPLdBfozYauBzY46XUc4MXB3XYK+EXlLLtAeMI7T9Sam3g6kacr8SNwIBEj/0YyR4yJZB3UJZd5365nvmCTBnHR0DT+Pl8iRrsJYRwHaKuNxywrizSLNG6yDBvHZfOrUc0g="; // Encrypted session key
//	        String encryptedDataBase64 = "WlcVLCgTz8prp0UCM+w+LsY4TbTFDnlrfFb+o4w3wCEG6YOCrM0OGCtcrGcucDSOoMXvR5JF15uNJV7snHFJthE29vDbvbtoSrKEFcVrfnmn8a1a5j2mdpMlgNhzeYv0/N2NTle4QR7FnzicoGZmdvoIDYijXIFS++myYLHbfsArFfJTMfQOldX+LLvpSge963bdCyuXXPx2TzPbnO9CmyvsPfz/Vu17ZZc8BByB9w/Fo3vFCdwBp4UHF1fVO4giZM60NgLsduWw6tA8vk7GNBTPo/henD8OnSyZVow7FNSXxoTUNt7qxV61avKE65hc"; // Encrypted response
//	        
//	        // Step 1: Extract the IV from the encrypted data
//	        byte[] encryptedData = Base64.getDecoder().decode(encryptedDataBase64);
//	        byte[] IV = getFirst16Bytes(encryptedData);
//	        
//	        // The rest is the encrypted response
//	        byte[] encryptedResponse = new byte[encryptedData.length - 16];
//	        System.arraycopy(encryptedData, 16, encryptedResponse, 0, encryptedResponse.length);
//	        
//	        // Step 2: Decrypt the encrypted key using RSA with the client’s private key
//	        //PrivateKey privateKey =loadPrivateKey("D:/opt/cotodel/key/cotoRSA_pvt.cert.txt"); //loadPrivateKey("ClientPrivateKey.p12", "your_password_here");
//	       // privateKey=
//	        PrivateKey privateKey = EncryptionUtil.getPrivateKey("/opt/cotodel/key/cotoRSA_pvt.cert.txt");
//	        byte[] sessionKey = decryptSessionKey(Base64.getDecoder().decode(encryptedKeyBase64), privateKey);
//	        
//	        // Step 3: Decrypt the response using AES
//	        byte[] decryptedResponse = decryptAES(encryptedResponse, sessionKey, IV);
//	        
//	        // Convert decrypted bytes to string (assuming UTF-8 encoding)
//	        String responseString = new String(decryptedResponse, "UTF-8");
//	        
//	        // Print the decrypted response
//	        System.out.println("Decrypted Response: " + responseString);
//	    }
	    
	    
	    public static String decriptResponse(EncryptedResponse encrResponse,String privatePath,int responseCode) throws Exception {
	        // Example encrypted values (replace these with actual values)
	        // encryptedKeyBase64 = "W1l+pdRoCoAcpmaJKvF70JBYdsC7IrxwOmymgxLU03LlAR5kjFigBp4P4vBoth61jJ66OB4Ag5ExIXx0nWAPKcXo7J9G+X4EJWzud/sRdJMYSkV0akfeITeytJdIZVVAmg2YgpHau3tbH97e8ivgDYSEn4QWUUgYO1BSzZVzWjWyXWp27Bq+BHXtZj8fwmNF5szMs3jGO+LtsWX6KTm2USw2y2iGR/MWp17R5Fih7YE+5TyWC5MRMppukUrMudHFruKFzJ0FcVP5XHfzvNJowKDcCIRJ54V8BnUWcnoLEP/OU3HNj3QnApo/b0TdPlR9dgz29iWVg7jAxkHCoRinzrW0fWd/X5hLcLLRlNmyr6gwKoIEGgSaPI8RARQLowtlsinboLlkcVDxfeLfqQXOvoENQkJUrx4Wv1/bcXXilFlIgI9MVGTgRVNG7gRW+1JgTp7s6imBHmenXPDWss2b76cThFcV/tHZlgu8e10qzQyLr834NDpUVOGJiBQNaCGIq8chvHUcRYto7WGRy2Ht6zXt7wdKuVTva3hlgHf4PPLdBfozYauBzY46XUc4MXB3XYK+EXlLLtAeMI7T9Sam3g6kacr8SNwIBEj/0YyR4yJZB3UJZd5365nvmCTBnHR0DT+Pl8iRrsJYRwHaKuNxywrizSLNG6yDBvHZfOrUc0g="; // Encrypted session key
	       //  encryptedDataBase64 = "WlcVLCgTz8prp0UCM+w+LsY4TbTFDnlrfFb+o4w3wCEG6YOCrM0OGCtcrGcucDSOoMXvR5JF15uNJV7snHFJthE29vDbvbtoSrKEFcVrfnmn8a1a5j2mdpMlgNhzeYv0/N2NTle4QR7FnzicoGZmdvoIDYijXIFS++myYLHbfsArFfJTMfQOldX+LLvpSge963bdCyuXXPx2TzPbnO9CmyvsPfz/Vu17ZZc8BByB9w/Fo3vFCdwBp4UHF1fVO4giZM60NgLsduWw6tA8vk7GNBTPo/henD8OnSyZVow7FNSXxoTUNt7qxV61avKE65hc"; // Encrypted response
	        
	         String encryptedKeyBase64=encrResponse.getEncryptedKey();
	         String encryptedDataBase64=encrResponse.getEncryptedData();
	         logger.info("encryptedKeyBase64:"+encryptedKeyBase64);
	         logger.info("encryptedDataBase64:"+encryptedDataBase64);
	        // Step 1: Extract the IV from the encrypted data
	        byte[] encryptedData = Base64.getDecoder().decode(encryptedDataBase64);
	        byte[] IV = getFirst16Bytes(encryptedData);
	        
	        // The rest is the encrypted response
	        byte[] encryptedResponse = new byte[encryptedData.length - 16];
	        System.arraycopy(encryptedData, 16, encryptedResponse, 0, encryptedResponse.length);
	        
	        // Step 2: Decrypt the encrypted key using RSA with the client’s private key
	        //PrivateKey privateKey =loadPrivateKey("D:/opt/cotodel/key/cotoRSA_pvt.cert.txt"); //loadPrivateKey("ClientPrivateKey.p12", "your_password_here");
	       // privateKey=
	        PrivateKey privateKey = EncryptionUtil.getPrivateKey(privatePath);
	        byte[] sessionKey = decryptSessionKey(Base64.getDecoder().decode(encryptedKeyBase64), privateKey);
	        
	        // Step 3: Decrypt the response using AES
	        byte[] decryptedResponse = decryptAES(encryptedResponse, sessionKey, IV);
	        
	        // Convert decrypted bytes to string (assuming UTF-8 encoding)
	        String responseString = new String(decryptedResponse, "UTF-8");
	        String responseCodevalue=","+"\"responseCode\""+":"+"\""+responseCode+"\""+"}";
	        responseString=responseString.replace("}", responseCodevalue);
	        // Print the decrypted response
	        logger.info("Decrypted Response: " + responseString);
	        return responseString;
	    }

	    private static byte[] getFirst16Bytes(byte[] data) {
	        byte[] iv = new byte[16];
	        System.arraycopy(data, 0, iv, 0, iv.length);
	        return iv;
	    }

//	    private static PrivateKey loadPrivateKey(String p12FilePath, String password) throws Exception {
//	        try (FileInputStream fis = new FileInputStream(p12FilePath)) {
//	            java.security.KeyStore keystore = java.security.KeyStore.getInstance("PKCS12");
//	            keystore.load(fis, password.toCharArray());
//	            String alias = keystore.aliases().nextElement(); // Get the first alias
//	            return (PrivateKey) keystore.getKey(alias, password.toCharArray());
//	        }
//	    }
//	    public static PrivateKey getPrivateKey(String keyPath) throws Exception {
////	        try (FileInputStream fis = new FileInputStream(keyPath)) {
////	            byte[] keyBytes = fis.readAllBytes();
////	            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
////	            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
////	            return keyFactory.generatePrivate(spec);
////	        }
//	    	String privateKeyPEM = new String(Files.readAllBytes(Paths.get(keyPath)));
//
//	        // Remove the first and last lines
//	    	privateKeyPEM = privateKeyPEM.replace("-----BEGIN RSA PRIVATE KEY-----\r\n", "");
//	        privateKeyPEM = privateKeyPEM.replace("-----END RSA PRIVATE KEY-----", "");
//	        privateKeyPEM = privateKeyPEM.replaceAll("\\s", "");
//
//	        // Decode the base64 encoded string
//	        byte[] keyBytes = Base64.getDecoder().decode(privateKeyPEM);
//
//	        // Generate the private key
//	        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
//	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//	        return keyFactory.generatePrivate(spec);
//	    }
//	    private static PrivateKey loadPrivateKey(String p12FilePath) throws Exception {
//	        try (FileInputStream fis = new FileInputStream(p12FilePath)) {
//	            KeyStore keystore = KeyStore.getInstance("PKCS12");
//	            //keystore.load(fis, null); // No password
//
//	            String alias = keystore.aliases().nextElement(); // Get the first alias
//	            PrivateKey privateKey = (PrivateKey) keystore.getKey(alias, null); // No password
//
//	            if (privateKey == null) {
//	                throw new Exception("Private key not found in keystore.");
//	            }
//
//	            return privateKey;
//	        } catch (IOException e) {
//	            throw new Exception("Error reading the keystore file: " + e.getMessage(), e);
//	        } catch (GeneralSecurityException e) {
//	            throw new Exception("Error accessing the keystore: " + e.getMessage(), e);
//	        }
//	    }

	    private static byte[] decryptSessionKey(byte[] encryptedKey, PrivateKey privateKey) throws Exception {
	        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	        cipher.init(Cipher.DECRYPT_MODE, privateKey);
	        return cipher.doFinal(encryptedKey);
	    }

	    private static byte[] decryptAES(byte[] encryptedData, byte[] sessionKey, byte[] iv) throws Exception {
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        SecretKeySpec secretKey = new SecretKeySpec(sessionKey, "AES");
	        IvParameterSpec ivParams = new IvParameterSpec(iv);
	        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParams);
	        return cipher.doFinal(encryptedData);
	    }
}
