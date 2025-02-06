package com.cotodel.hrms.auth.server.util;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;

public class HybridEncryption {

    // Encrypt data using AES
    public static byte[] encryptAES(byte[] data, SecretKey aesKey) throws Exception {
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);
        return aesCipher.doFinal(data);
    }

    // Decrypt data using AES
    public static byte[] decryptAES(byte[] encryptedData, SecretKey aesKey) throws Exception {
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.DECRYPT_MODE, aesKey);
        return aesCipher.doFinal(encryptedData);
    }

    // Encrypt AES key using RSA
    public static byte[] encryptRSA(byte[] data, PublicKey rsaPublicKey) throws Exception {
        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");  // Use PKCS1Padding
        rsaCipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
        return rsaCipher.doFinal(data);
    }

    // Decrypt AES key using RSA
    public static byte[] decryptRSA(byte[] encryptedData, PrivateKey rsaPrivateKey) throws Exception {
        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");  // Use PKCS1Padding
        rsaCipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
        return rsaCipher.doFinal(encryptedData);
    }

    // Generate a random AES key
    public static SecretKey generateAESKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256); // 256 bits AES key
        return keyGenerator.generateKey();
    }

    public static void main(String[] args) throws Exception {
        // Generate RSA KeyPair (For demonstration, use this RSA key for both encryption and decryption)
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);  // 2048-bit RSA key
        KeyPair rsaKeyPair = keyPairGenerator.generateKeyPair();
        PublicKey rsaPublicKey=CommonEncryption.readPublicKey("/opt/cotodel/key/publicKeyForApplication.txt");
		PrivateKey rsaPrivateKey=CommonEncryption.getPrivateKey("/opt/cotodel/key/pvtKeyForApplication.txt");

		// Step 1: Generate a random AES key
        SecretKey aesKey = generateAESKey();

        // Step 2: Encrypt some data using AES
        String originalData = "This is a large piece of data that will be encrypted using AES.";
        byte[] encryptedData = encryptAES(originalData.getBytes(), aesKey);
        System.out.println("Encrypted Data (AES): " + Base64.getEncoder().encodeToString(encryptedData));

        // Step 3: Encrypt the AES key using RSA
        byte[] encryptedAESKey = encryptRSA(aesKey.getEncoded(), rsaPublicKey);
        String encryptedAESKeyBase64 = Base64.getEncoder().encodeToString(encryptedAESKey);
        System.out.println("Encrypted AES Key (RSA): " + encryptedAESKeyBase64);

        // Step 4: Decrypt the AES key using RSA (Ensure Base64 decoding)
        byte[] decodedEncryptedAESKey = Base64.getDecoder().decode(encryptedAESKeyBase64);
        byte[] decryptedAESKeyBytes = decryptRSA(decodedEncryptedAESKey, rsaPrivateKey);
        SecretKey decryptedAESKey = new SecretKeySpec(decryptedAESKeyBytes, "AES");

        // Step 5: Decrypt the data using the decrypted AES key
        byte[] decryptedData = decryptAES(encryptedData, decryptedAESKey);
        System.out.println("Decrypted Data (AES): " + new String(decryptedData));
    }
}