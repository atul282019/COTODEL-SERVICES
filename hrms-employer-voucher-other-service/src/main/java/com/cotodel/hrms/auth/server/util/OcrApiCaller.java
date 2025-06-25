package com.cotodel.hrms.auth.server.util;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;

public class OcrApiCaller {

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        String flaskApiUrl = "http://localhost:5000/ocr";

        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Create body with image file
        File imageFile = new File("D:/python/Media.jpg");
        FileSystemResource imageResource = new FileSystemResource(imageFile);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", imageResource);

        // Create the request
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Send POST request
        ResponseEntity<String> response = restTemplate.postForEntity(flaskApiUrl, requestEntity, String.class);

        // Output the result
        System.out.println("Response from Flask OCR API:");
        System.out.println(response.getBody());
    }
}

