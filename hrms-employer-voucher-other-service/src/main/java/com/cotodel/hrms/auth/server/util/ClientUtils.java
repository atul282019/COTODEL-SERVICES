package com.cotodel.hrms.auth.server.util;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

public class ClientUtils {
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generateClientId() {
        return UUID.randomUUID().toString(); // Generates a random client ID
    }
    
    public static String generateClientSecret() {
        byte[] randomBytes = new byte[32]; // 32 bytes => 256-bit secret
        secureRandom.nextBytes(randomBytes);
        return Base64.getEncoder().encodeToString(randomBytes); // Encode as Base64 string
    }
    public static void main(String[] args) {
    	String clientId = ClientUtils.generateClientId();      // Generate Client ID
        String clientSecret = ClientUtils.generateClientSecret(); 
        System.out.println("clientId::"+clientId);
        System.out.println("clientSecret::"+clientSecret);
	}
}
