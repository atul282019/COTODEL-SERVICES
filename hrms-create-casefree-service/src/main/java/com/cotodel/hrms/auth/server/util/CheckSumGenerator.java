package com.cotodel.hrms.auth.server.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class CheckSumGenerator {
    // Method to generate checksum using HMAC and SHA-512 (or other types)
    public static String generateCheckSumKey(String stcheckSumdata, String stShaKey) { 
        String chkSumKey = null; 
        String stCheckType = "SHA-512";  // Default hashing algorithm
        CheckSumGenerator hmac = new CheckSumGenerator(); // Instantiate CheckSumGenerator
        chkSumKey = hmac.Sha(stcheckSumdata, stShaKey, stCheckType); // Compute checksum
        return chkSumKey; // Return the checksum key
    }

    // Method to compute HMAC SHA checksum
    public String Sha(String checkSumdata, String key, String CheckType) { 
        Mac sha512_HMAC = null; 
        String result = null;
        String HMAC_SHA = null; 

        try {
            // Convert the key to bytes
            byte[] byteKey = key.getBytes("UTF-8"); 

            // Select the algorithm (SHA-512 or SHA-256)
            if (CheckType.equals("SHA-512")) { 
                HMAC_SHA = "HmacSHA512";
            }
            if (CheckType.equals("SHA-256")) { 
                HMAC_SHA = "HmacSHA256";
            }

            // Initialize Mac instance with the selected algorithm
            sha512_HMAC = Mac.getInstance(HMAC_SHA); 

            // Set the key for the HMAC instance
            SecretKeySpec keySpec = new SecretKeySpec(byteKey, HMAC_SHA); 
            sha512_HMAC.init(keySpec); // Initialize the HMAC instance

            // Apply the HMAC operation on the data
            byte[] mac_data = sha512_HMAC.doFinal(checkSumdata.getBytes("UTF-8")); 

            // Convert the byte result to a hex string
            result = bytesToHex(mac_data); 
            System.out.println(result); // Print the result for debugging
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions
        }

        return result; // Return the checksum in hexadecimal form
    }

    // Convert byte array to hexadecimal string
    public static String bytesToHex(byte[] bytes) {
        final char[] hexArray = "0123456789ABCDEF".toCharArray(); 
        char[] hexChars = new char[bytes.length * 2]; 
        
        // Convert each byte to a 2-character hex representation
        for (int j = 0; j < bytes.length; j++) { 
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4]; // Get the higher nibble (4 bits)
            hexChars[j * 2 + 1] = hexArray[v & 0x0F]; // Get the lower nibble (4 bits)
        }
        return new String(hexChars); // Return the hexadecimal string
    }
}

