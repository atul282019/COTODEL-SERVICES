package com.cotodel.hrms.auth.server.util;

import java.security.PrivateKey;

import javax.crypto.Cipher;

public class EncryptionUtil1 {
	 public static byte[] encrypt(PrivateKey privateKey, String data) throws Exception {
	        Cipher cipher = Cipher.getInstance("RSA");
	        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
	        return cipher.doFinal(data.getBytes());
	    }
}
