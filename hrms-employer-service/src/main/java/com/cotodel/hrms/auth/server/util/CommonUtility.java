package com.cotodel.hrms.auth.server.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.cotodel.hrms.auth.server.dto.ErupiVoucherSummaryDto;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreateSummaryDto;



public class CommonUtility {
	
	private static final Logger logger = LogManager.getLogger(CommonUtility.class);
	
	private static final String MOBILE_REGEX = "^(0[0-9]{10}|91[0-9]{10}|[7-9][0-9]{9})$";
	
	public static String userRequest(String sAccessToken,String requestJson,String url,String publicPath,String privatePath){
		String returnStr=null;
		String decript=null;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		try{
			logger.info(" Request Json for url"+url+"---"+requestJson);

			headers.setContentType(MediaType.APPLICATION_JSON);
			
			if(sAccessToken!=null && !sAccessToken.isEmpty()) {
				headers.setBearerAuth(sAccessToken);
			}
			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(requestJson, publicPath);
			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(jsonEncriptObject);
			HttpEntity<String> entity = new HttpEntity<String>(jsonEncript,headers);

			returnStr = restTemplate.postForObject(url, entity, String.class);
			EncriptResponse enResponse= EncryptionDecriptionUtil.convertFromJson(returnStr, EncriptResponse.class);
			decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), privatePath);
			logger.info(" response Json---"+returnStr);
			return decript;
		}catch(HttpStatusCodeException e) {
			logger.error("HttpStatusCodeException error in---"+url+"-"+e.getResponseBodyAsString());
			return e.getResponseBodyAsString();
		}catch(Exception e){
			logger.error(" error in---"+url+"-"+e);
			return null;
		}finally {
			restTemplate=null;headers=null;sAccessToken=null;requestJson=null;url=null;	
		}		
	}
	
	
	public static String userRequestWiout(String sAccessToken,String requestJson,String url){
		String returnStr=null;
		String decript=null;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		try{
			logger.info(" Request Json for url"+url+"---"+requestJson);

			headers.setContentType(MediaType.APPLICATION_JSON);
			
			if(sAccessToken!=null && !sAccessToken.isEmpty()) {
				headers.setBearerAuth(sAccessToken);
			}
			HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);

			returnStr = restTemplate.postForObject(url, entity, String.class);
			logger.info(" response Json---"+returnStr);
			return returnStr;
		}catch(HttpStatusCodeException e) {
			logger.error("HttpStatusCodeException error in---"+url+"-"+e.getResponseBodyAsString());
			return e.getResponseBodyAsString();
		}catch(Exception e){
			logger.error(" error in---"+url+"-"+e);
			return null;
		}finally {
			restTemplate=null;headers=null;sAccessToken=null;requestJson=null;url=null;	
		}		
	}
	
	public static String userRequestForRC(String token,String requestJson,String url){
		String returnStr=null;
		String decript=null;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		try{
			logger.info(" Request Json for url"+url+"---"+requestJson);

			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(token);
			

			HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);

			returnStr = restTemplate.postForObject(url, entity, String.class);
			logger.info(" response Json---"+returnStr);
			return returnStr;
		}catch(HttpStatusCodeException e) {
			logger.error("HttpStatusCodeException error in---"+url+"-"+e.getResponseBodyAsString());
			return e.getResponseBodyAsString();
		}catch(Exception e){
			logger.error(" error in---"+url+"-"+e);
			return null;
		}finally {
			restTemplate=null;headers=null;token=null;requestJson=null;url=null;	
		}		
	}
	
	
	public static String getTokenRequest(String sAccessToken,String requestJson,String companyId,String url){
		String returnStr=null;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		try{
			logger.info(" Request URL---"+url);
			logger.info(" Request header---"+companyId);
			headers.setContentType(MediaType.APPLICATION_JSON);
			//headers.set("companyId", companyId);
			if(sAccessToken!=null && !sAccessToken.isEmpty()) {
				headers.setBearerAuth(sAccessToken);
			}

			HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
	        returnStr = response.getBody();
			logger.info(" response Json---"+returnStr);
			return returnStr;
		}catch(Exception e){
			//e.printStackTrace();
			return null;
		}finally {
			restTemplate=null;headers=null;	
		}		
	}

	public static Long getCount(List<ErupiVoucherSummaryDto> list) {
		 Long totolCount=0l;
		  for(ErupiVoucherSummaryDto dto:list) {
			  Long count=dto.getCount();
			  totolCount=totolCount+count;
		  }
		 return totolCount;
	}
		 public static Long getAmount(List<ErupiVoucherSummaryDto> list) {
			 Long totolAmount=0l;
			  for(ErupiVoucherSummaryDto dto:list) {
				  Long amount=dto.getTotalAmount();
				  totolAmount=totolAmount+amount;
			  }
			 return totolAmount;
		 
	 }
		 
		 public static Long getCreateCount(List<ErupiVoucherCreateSummaryDto> list) {
			 Long totolCount=0l;
			  for(ErupiVoucherCreateSummaryDto dto:list) {
				  Long count=dto.getCount();
				  totolCount=totolCount+count;
			  }
			 return totolCount;
		}
			 public static Long getCreateAmount(List<ErupiVoucherCreateSummaryDto> list) {
				 Long totolAmount=0l;
				  for(ErupiVoucherCreateSummaryDto dto:list) {
					  Long amount=dto.getTotalAmount();
					  totolAmount=totolAmount+amount;
				  }
				 return totolAmount;
			 
		 }
			 

		public static boolean isValid(String mobile) {
			     // If the mobile number is null or empty, it's invalid
			     if (mobile == null || mobile.isEmpty()) {
			         return false;
			     }
			     return mobile.matches(MOBILE_REGEX);
			 }
		
		public static LocalDate convertToLocalDate(Date date) {
	        // Convert java.util.Date to Instant
	        Instant instant = date.toInstant();
	        
	        // Convert Instant to LocalDate
	        return instant.atZone(ZoneId.systemDefault()).toLocalDate();
	    }
		
		public static String getFileExtension(String fileName) {
	        int lastIndexOfDot = fileName.lastIndexOf(".");
	        if (lastIndexOfDot == -1) {
	            return ""; // No extension
	        }
	        return fileName.substring(lastIndexOfDot + 1);
	    }
//		public static boolean isValidName(String name) {
//	        if (name == null || name.trim().isEmpty()) {
//	            return false;
//	        }
//
//	        // Updated regular expression to allow spaces, apostrophes, and hyphens
//	        String regex = "^[A-Za-z]+([\\s'-][A-Za-z]+)*$";  // Allows alphabets, spaces, apostrophes, hyphens
//	        Pattern pattern = Pattern.compile(regex);
//	        Matcher matcher = pattern.matcher(name);
//
//	        return matcher.matches();  // Returns true if the name matches the pattern
//	    }
		public static boolean isValidName(String name) {
		    if (name == null || name.trim().isEmpty()) {
		        return false;
		    }

		    // Regular expression that allows only alphabets and spaces
		    String regex = "^[A-Za-z]+(\\s[A-Za-z]+)*$";
		    Pattern pattern = Pattern.compile(regex);
		    Matcher matcher = pattern.matcher(name);

		    return matcher.matches();  // Returns true if the name matches the pattern
		}
		public static String generateUniqueFileName(String filename,Long orgId,String ext) {
	        // Get the current date and time
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
	        String dateString = dateFormat.format(new Date());
	        
	        // Add file extension (example: .txt, .jpg, .xlsx)
	        String fileName =filename+"_"+orgId+"_"+dateString+"."+ext;
	        
	        // Combine the date string with the file extension to create a unique file name
	        return fileName;
	    }
		
		public static String generateUniqueFileNameWithoutOrg(String filename,String ext) {
	        // Get the current date and time
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
	        String dateString = dateFormat.format(new Date());
	        
	        // Add file extension (example: .txt, .jpg, .xlsx)
	        String fileName =filename+"_"+dateString+"."+ext;
	        
	        // Combine the date string with the file extension to create a unique file name
	        return fileName;
	    }
		
//		public static boolean isValidAmount(String amount) {
//	        // Ensure it's positive
//			boolean result=false;
//			try {
//				BigDecimal amountDeci = new BigDecimal(amount);
//				result=amountDeci.compareTo(BigDecimal.TEN) > 0;
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//			
//	        return result;
//	    }
		public static boolean isValidAmount(String amount,String minAmount,String maxAmount) {
		    boolean result = false;
		    try {
		        BigDecimal amountDeci = new BigDecimal(amount);
		        BigDecimal amountMin = new BigDecimal(minAmount);
		        BigDecimal amountMax = new BigDecimal(maxAmount);
		        // Check if amount is between 1 and 10,000 inclusive
		        result = amountDeci.compareTo(amountMin) >= 0 && amountDeci.compareTo(amountMax) <= 0;
		    } catch (Exception e) {
		        // Optionally log or handle the exception here
		        // e.g., System.out.println("Invalid amount: " + e.getMessage());
		    }
		    
		    return result;
		}
		
		public static  boolean checkDate(String targetDate) {
			boolean result=false;
				try {			
				
			    LocalDate target = LocalDate.parse(targetDate);
			    LocalDate currentDate = LocalDate.now();
			    LocalDate dateAfterTwoDays = currentDate.plusDays(2);
			    LocalDate oneYearLater = currentDate.plusYears(1);
			    
				if (target.isAfter(dateAfterTwoDays) && target.isBefore(oneYearLater)) {
					result=true;
				} else {
					result=false;
				}
				
				} catch (Exception e) {
					e.printStackTrace();
				}
				return result;
			}
		public static  boolean checkStartDate(String startDate) {
			boolean result=false;
				try {			
					if (startDate == null || startDate.equalsIgnoreCase("")) {
			            return false;
			        }
			    LocalDate target = LocalDate.parse(startDate);
			    LocalDate currentDate = LocalDate.now();
			    LocalDate oneYearLater = currentDate.plusYears(1);
			    
				if (!target.isBefore(currentDate)) {
					result=true;
				} else {
					result=false;
				}
				
				} catch (Exception e) {
					e.printStackTrace();
				}
				return result;
			}
}
