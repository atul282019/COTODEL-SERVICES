package com.cotodel.hrms.auth.server.util;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStreamReader;
import java.io.BufferedReader;
public class OAuth2Request {
    public static void main(String[] args) {
        try {
            // URL for the token request
            URL url = new URL("https://app.demohrms.stg.repute.net/oauth2/token");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            // Set HTTP request method to POST
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Authorization", "Basic YTc2Y2M2OGMtZmI5MS00ODI2LTk5NDctYmJlYmVlZjAxZTBhOnZlVEh1Y1UwOGJ1M1YzTnhFaE9MOUFkeDJFUVZWYlBT");

            // Enable input and output streams for POST data
            connection.setDoOutput(true);
            connection.setDoInput(true);

            // Form data to send in the POST request
            String urlParameters = "code=jAE4CzfMjkMyH-ZxKcefzdylC_0KiumM_ZYUbj3WKmB4GtPXwzFtVWz6WPXVVRkx43_0grHsypsarjT-8YZlF0qPAeSJvjMidKFEWQx7CFklAby8gDv7QUcOgnfbPUWm"
                    + "&grant_type=authorization_code"
                    + "&redirect_uri=http://43.205.206.102:8088/repute_marketplace/";

            // Write the data to the output stream
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = urlParameters.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Read the response
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println("Response: " + response.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
