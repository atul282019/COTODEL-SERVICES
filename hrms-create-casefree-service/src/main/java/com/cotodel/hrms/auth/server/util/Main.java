package com.cotodel.hrms.auth.server.util;

public class Main {
    public static void main(String[] args) {
        String data = "Some data to checksum";
        String key = "73127505498180881483015890950210"; // Example secret key
        
        String checksum = CheckSumGenerator.generateCheckSumKey(data, key);
        System.out.println("Generated Checksum: " + checksum); // Print the checksum
    }
}
