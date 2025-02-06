package com.cotodel.hrms.auth.server.util;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public class RSAEncryptionDecryption {

    public static void main(String[] args) throws Exception {
        // Public and Private Key (Base64 encoded)
        String publicKeyBase64 = "MIICITANBgkqhkiG9w0BAQEFAAOCAg4AMIICCQKCAgByczkUtiVTjoRL3oewfLhj"
        		+ "zgFwLp0AGXtOyDKndVb7QCHw9ZzWblRA/CEun+PdvLq5qngBuXDBbuZKBXaejr25"
        		+ "0RzJfS7mg8Ggtfo+uq03r1Pn6N4TmK0x78unKzHdSZd07dFKDoENOERAB00liNov"
        		+ "ppRKO5g3x+30Q6XNs4GaffVJJtlceX54Th6SORI0T6w0MNZJXycglF44IuysTyhq"
        		+ "XgGMiLxaNP0mLiOmlFa7K9PFcnb2k3a0g5y3fWp7c7Mh9iPdC7VQZkhPQNLNQiJz"
        		+ "ApE/mBU73n/n9saKgG1EDgsbGCba9BQT0trfn6A0q1ZBFBxIryMcGACttO85W2rw"
        		+ "yTef91ey7Dnw6KVQ5TgQ3UtQtSFTt8W+SQ8bt2qvNcoQuX/O97aPatLa11Pr3Rx1"
        		+ "RbsfLrHozCv7YWsCgfXJ1NhhSdnLiMJTnF6VQCn+lSjPEbbFF8hMCrYzu+szy53M"
        		+ "vNnNeXnxCFF5D0M2zx7gFz8YmxmLcJAgZ23WW0RHnOuQk/BL0ozkEO4gI3NxmtC/"
        		+ "owivKFmpA+42LOZypLGd2+NoqJizkYd2cQ+eVbBBZ4EaYHPHjbKB1b0OvdWBArgG"
        		+ "86C3TMg9ClIs1c/99q7CNoLEUpysxdRLul+bDIw7yxL4X+lEDu9hFJ7bFwL+FloI"
        		+ "1scvYy2pYLmPgJq2InsI7wIDAQAB";
        String privateKeyBase64 = "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\r\n"
        		+ "IaxvtqyOKkduTCVBwyNrQpt2S++y92SvXNMS1tfkO8jVlsnykf6yVgLi";

        // Convert Base64 encoded keys to PublicKey and PrivateKey
        PublicKey publicKey = getPublicKeyFromBase64(publicKeyBase64);
        PrivateKey privateKey = getPrivateKeyFromBase64(privateKeyBase64);

        // Original data to encrypt
        String data = "Hello, RSA Encryption!";

        // Encrypt the data using the public key
        byte[] encryptedData = encryptData(data.getBytes(), publicKey);

        // Encode encrypted data in Base64 for easy display or storage
        String encryptedBase64 = Base64.getEncoder().encodeToString(encryptedData);
        System.out.println("Encrypted Data: " + encryptedBase64);

        // Decrypt the data using the private key
        byte[] decryptedData = decryptData(Base64.getDecoder().decode(encryptedBase64), privateKey);

        // Convert the decrypted data to a string and print it
        String decryptedMessage = new String(decryptedData);
        System.out.println("Decrypted Data: " + decryptedMessage);
    }

    // Method to convert Base64 encoded public key string to PublicKey object
    public static PublicKey getPublicKeyFromBase64(String base64Key) throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    // Method to convert Base64 encoded private key string to PrivateKey object
    public static PrivateKey getPrivateKeyFromBase64(String base64Key) throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    // Method to encrypt data using the public key
    public static byte[] encryptData(byte[] data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    // Method to decrypt data using the private key
    public static byte[] decryptData(byte[] encryptedData, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(encryptedData);
    }
}
