package com.cotodel.hrms.auth.server.service.impl;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.cotodel.hrms.auth.server.dto.ExpenseBillReader;
import com.cotodel.hrms.auth.server.dto.ExpenseBillReaderRequest;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.OcrReaderService;
import com.cotodel.hrms.auth.server.util.MessageConstant;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class OcrReaderServiceImpl implements OcrReaderService{
	private static final Logger logger = LoggerFactory.getLogger(OcrReaderServiceImpl.class);
	

	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
	@Override
	public ExpenseBillReader ocrDetail(ExpenseBillReaderRequest expenseBillReaderRequest) {
		ExpenseBillReader expenseBillReader=new ExpenseBillReader();
		String text=callApi(expenseBillReaderRequest);
		logger.info("text::"+text);
		double totalAmount=extractTotalAmount(text);
		String  order=order(text);
		String vendername=venderName(text);
		String name=name(text);
		String orderDate=orderDate(text);
		expenseBillReader.setTotalAmount(totalAmount);
		expenseBillReader.setOrder(order);
		expenseBillReader.setVenderName(vendername);
		expenseBillReader.setName(name);
		expenseBillReader.setOrderDate(orderDate);
		expenseBillReader.setResponse(MessageConstant.RESPONSE_SUCCESS);
		
		logger.info("order::"+order);
		logger.info("name::"+name);
		logger.info("totalAmount::"+totalAmount);
		return expenseBillReader;
	}

	public  String callApi(ExpenseBillReaderRequest expenseBillReaderRequest) {
		FileSystemResource fileResource =null;
		try {
			fileResource=byteArrayToFileSystemResource(expenseBillReaderRequest.getFile(), expenseBillReaderRequest.getFileName());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
        RestTemplate restTemplate = new RestTemplate();
        
        // Path to your image
             
        // Prepare the headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Prepare the body with the file
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add(expenseBillReaderRequest.getFileType(), fileResource);

        // Create the request entity
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Make the request
        String url = applicationConstantConfig.apiOcrReadFileUrl;
        
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        // Print the response
        String extractedText ="";
        try {
        	String jsontext=response.getBody();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsontext);

            extractedText = rootNode.path("extracted_text").asText();
            if(extractedText.equalsIgnoreCase("")) {
            	extractedText = rootNode.path("text").asText();
            }
		} catch (Exception e) {
			// TODO: handle exception
		}
        return extractedText;
    }
	public static FileSystemResource byteArrayToFileSystemResource(byte[] fileBytes, String originalFilename) throws IOException {
        // Save to a temp file
        File tempFile = File.createTempFile("upload_", "_" + originalFilename);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(fileBytes);
        }

        // Return as FileSystemResource
        return new FileSystemResource(tempFile);
    }
	

	public double extractTotalAmount(String input) {
        String[] lines = input.split("\\n");

        String totalLine = null;
        for (String line : input.split("\\n")) {
        	
            if (line.contains("Bill Total")) {
                totalLine = line;
                break;
            }
        }
        double total = 0.0;
        if (totalLine != null) {
            String number = totalLine.replaceAll("[^0-9.]", "");
            total = Double.parseDouble(number);
            System.out.println("Extracted Bill Total: " + total);
        } else {
            System.out.println("Bill Total line not found.");
        }
        return total; // default if not found
    }
	
	public String  order(String input) {

        String totalLine = null;
        for (String line : input.split("\\n")) {
        	
            if (line.contains("Order")) {
                totalLine = line;
                break;
            }
        }
        String order ="";
        if (totalLine != null) {
            String number = totalLine.replaceAll("[^0-9]", "");
            order =number;
            System.out.println("Extracted Bill Total: " + order);
        } else {
            System.out.println("Bill Total line not found.");
        }
        return order; // default if not found
    }
	public String  name(String input) {
		String[] lines=input.split("\\n");
        String totalLine = null;
        for (String line : input.split("\\n")) {
        	
            if (line.contains("Order")) {
                totalLine = lines[7];
                break;
            }
        }
        String order ="";
        if (totalLine != null) {
            String number = totalLine;
            order =number;
            System.out.println("Extracted Bill Total: " + order);
        } else {
            System.out.println("Bill Total line not found.");
        }
        return order; // default if not found
    }
	public String  orderDate(String input) {
		String[] lines=input.split("\\n");
        String totalLine = null;
        for (String line : input.split("\\n")) {
        	
            if (line.contains("Order delivered on")) {
            	System.out.println("line::"+line);
                totalLine = line.replaceAll("Order delivered on", "");
                totalLine = totalLine.replaceAll("y  ", "");
                totalLine = totalLine.replaceAll(" ON TIME", "");
                break;
            }
        }
        String order ="";
        if (totalLine != null) {
            String number = totalLine;
            order =number;
            System.out.println("Order delivered on: " + order);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");
            LocalDateTime dateTime = LocalDateTime.parse(order, formatter);
            System.out.println("LocalDate: " + dateTime.toLocalDate());
            System.out.println("LocalTime: " + dateTime.toLocalTime());
        } else {
            System.out.println("Order delivered on not found.");
        }
        return order; // default if not found
    }
	public String  venderName(String input) {
		String[] lines=input.split("\\n");
        String totalLine = null;
        for (String line : input.split("\\n")) {
        	
            if (line.contains("Order")) {
                totalLine = lines[4];
                break;
            }
        }
        String order ="";
        if (totalLine != null) {
            String number = totalLine;
            order =number;
            System.out.println("Extracted Bill Total: " + order);
        } else {
            System.out.println("Bill Total line not found.");
        }
        return order; // default if not found
    }
        
        
    
}
