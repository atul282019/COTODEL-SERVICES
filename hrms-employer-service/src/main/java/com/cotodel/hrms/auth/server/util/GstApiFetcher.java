package com.cotodel.hrms.auth.server.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GstApiFetcher {

    // Replace with your API key
    private static final String API_KEY = "622ddf9ffffbd5f70d037cfc8edaff5fcbe7a34d1ccb6462d73ac2d09591f758";

    // Method to fetch taxpayer details based on GSTIN
    public static void getTaxpayerDetails(String gstin) throws Exception {
        // Construct the URL dynamically by replacing {gstin} with the actual GSTIN
        String apiUrl = "https://apisetu.gov.in/gstn/v2/taxpayers/" + gstin;

        // Create a URL object
        URL url = new URL(apiUrl);
        // Open a connection to the URL
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set the HTTP request method to GET
        connection.setRequestMethod("GET");

        // Set the headers
        connection.setRequestProperty("X-APISETU-APIKEY", API_KEY);
        connection.setRequestProperty("clientId", "com.cotodel");
         
        // Get the response code
        int responseCode = connection.getResponseCode();
        
        // If the response is successful, read the response
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            
            System.out.println("Response: " + response.toString());
        } else {
            System.out.println("GET request failed. Response Code: " + responseCode);
        }
    }

    public static void main(String[] args) {
        try {
            // Test with COTODEL GSTIN or any valid GSTIN
            getTaxpayerDetails("07AALCC4033D1Z0");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}