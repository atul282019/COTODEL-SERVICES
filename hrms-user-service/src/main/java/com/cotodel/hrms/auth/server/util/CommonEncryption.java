package com.cotodel.hrms.auth.server.util;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public class CommonEncryption {
		
	
	public static X509Certificate loadCertificate(String filePath) throws Exception {
        // Read the DER-encoded certificate file
        FileInputStream fis = new FileInputStream(filePath);
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        X509Certificate certificate = (X509Certificate) certificateFactory.generateCertificate(fis);
        fis.close();
        return certificate;
    }
	 // Method to read the public key from a PEM file
	public static RSAPublicKey readPublicKey(String fileName) throws Exception {
	    // Read the entire key content as a string
	    String key = new String(Files.readAllBytes(Paths.get(fileName)));

	    // Remove the "-----BEGIN PUBLIC KEY-----" and "-----END PUBLIC KEY-----" lines
	    key = key.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");

	    // Remove any other characters that might cause an illegal Base64 character error
	    key = key.replaceAll("\\s", ""); // Remove spaces, newlines, and other whitespace characters

	    // Now, try to decode the Base64 string
	    byte[] encoded = Base64.getDecoder().decode(key);

	    // Generate the PublicKey from the decoded bytes
	    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	     return (RSAPublicKey) keyFactory.generatePublic(new java.security.spec.X509EncodedKeySpec(encoded));
	}
		public static String decrypt(String encryptedData, PrivateKey privateKey) throws Exception {
		        // Initialize the cipher with the private key
		        //Cipher cipher = Cipher.getInstance("RSA");
		        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		        
		        cipher.init(Cipher.DECRYPT_MODE, privateKey);
		        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));  // Decrypt data
		        String decryptedString = new String(decryptedData);
		        return decryptedString;
		    }
		
		    public static String encrypt(String data, PublicKey publicKey) throws Exception {
		        // Initialize the cipher with the public key
		    	Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
				cipher.init(Cipher.ENCRYPT_MODE, publicKey);
				byte[] encryptedData = cipher.doFinal(data.getBytes());  // Encrypt data
				String encryptedBase64 = Base64.getEncoder().encodeToString(encryptedData);
		    	
		                
		        return Base64.getEncoder().encodeToString(encryptedData);
		    }
		    public static RSAPrivateKey getPrivateKey(String keyPath) throws Exception {

		    	 // Step 1: Read the content of the private key file
		         String privateKeyContent = new String(Files.readAllBytes(Paths.get(keyPath)));

		         // Step 2: Remove the "-----BEGIN RSA PRIVATE KEY-----" and "-----END RSA PRIVATE KEY-----"
		         privateKeyContent = privateKeyContent.replace("-----BEGIN RSA PRIVATE KEY-----", "");
		         privateKeyContent = privateKeyContent.replace("-----END RSA PRIVATE KEY-----", "");
		         privateKeyContent = privateKeyContent.replaceAll("\\s", ""); // Remove any whitespace

		         // Step 3: Base64 decode the private key content
		         byte[] decodedKey = Base64.getDecoder().decode(privateKeyContent);

		         // Step 4: Use KeyFactory to generate the RSAPrivateKey from PKCS#1 encoded data
		         KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		         // Using RSAPrivateKeySpec
		         RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec(
		                 new java.math.BigInteger(1, decodedKey), // modulus
		                 new java.math.BigInteger(1, decodedKey)  // private exponent (you will need to extract the correct part)
		         );

		         return (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);
		    }

		    
}
