package com.cotodel.hrms.auth.server.util;
import java.io.FileReader;
import java.security.PrivateKey;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
public class Testing {
	public static PrivateKey loadPrivateKey(String certFilePath) throws Exception {
        // Add BouncyCastle provider
        Security.addProvider(new BouncyCastleProvider());
        
        // Open the PEM file
        PEMParser pemParser = new PEMParser(new FileReader(certFilePath));
        
        // Read the private key
        Object object = pemParser.readObject();
        pemParser.close();
        
        if (object instanceof RSAPrivateKey) {
            return (RSAPrivateKey) object;
        } else {
            throw new IllegalArgumentException("The file does not contain a valid RSA private key.");
        }
    }
	 public static void main(String[] args) {
	        try {
	            String pemFilePath = "D:/opt/cotodel/key/cotoRSA_pvt.pem"; // Path to your PEM private key file
	            PrivateKey privateKey = loadPrivateKey(pemFilePath);

	            // Output the private key for verification (or you can use it for decryption, etc.)
	            System.out.println("Private Key: " + privateKey);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}
