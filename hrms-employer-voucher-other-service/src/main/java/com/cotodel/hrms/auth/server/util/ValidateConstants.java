package com.cotodel.hrms.auth.server.util;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ValidateConstants {
	private static final List<String> SQL_KEYWORDS = Arrays.asList(
	        "select", "insert", "update", "delete", "drop", "union", "create", "alter", "grant", "revoke", "--", "/*", "*/"
	    );

	public static String generateHash(String data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(data.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
	public static String validateOrganizationId(Long orgId) {
	    String message = null;
	    
	    // Check if orgId is null
	    if (orgId == null) {
	        message = "Organization ID is required";
	    } else if (orgId <= 0) {
	        // Check if orgId is less than or equal to 0
	        message = "Organization ID should be greater than 0";
	    }
	    
	    return message; // Return the error message if validation fails, else null for success
	}
	
	public static String validateMandatoryName(String name) {
	    String message = null;

	    // Check if name is null or empty
	    if (name == null || name.isEmpty()) {
	        message = "Name is required";
	    }
	    
	    // Check if the length of the name is greater than 100 characters
	    if (name != null && name.length() > 50) {
	        message = "Name should be less than or equal to 50 characters";
	    }

	    // Regex validation: Name can only contain alphabets, spaces, and hyphens
	    String nameRegex = "^[A-Za-z\\s-]+$";
	    if (name != null && !name.matches(nameRegex)) {
	        message = "Name can only contain letters, spaces, and hyphens";
	    }
	    if (containsSQLKeywords(name)) {
            message="Name contains invalid SQL keywords.";
        }
	    return message; // Return the error message if validation fails, else null for success
	}
	public static String validateName(String name) {
		String message = null;
	    // Regex validation: Name can only contain alphabets, spaces, and hyphens
	    String nameRegex = "^[A-Za-z\\s-]+$";
	    if (name != null && !name.matches(nameRegex)) {
	        message = "Name can only contain letters, spaces, and hyphens";
	    }
	    if (containsSQLKeywords(name)) {
            message="Name contains invalid SQL keywords.";
        }
	    return message; // Return the error message if validation fails, else null for success
	}
	public static String validateEmail(String email) {
	    String message = null;

	    // Check if the email is provided and validate the format
	    if (email != null && !email.isEmpty()) {
	        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
	        if (!email.matches(emailRegex)) {
	            message = "Invalid email address";  // Invalid email format
	        }
	    }
	    if (containsSQLKeywords(email)) {
            message="email contains invalid SQL keywords.";
        }
	    return message;  // Return error message if validation fails, else null for success
	}
	
	public static String validateMobile(String  mobile) {
	    String message = null;

	    // Validate mobile number
	    if (mobile == null || !mobile.matches("^[6-9][0-9]{9}$")) {
	        message = "Mobile number should be exactly 10 digits and start with 6, 7, 8, or 9";
	    }
	    if (containsSQLKeywords(mobile)) {
            message="Mobile contains invalid SQL keywords.";
        }
	    return message;  // Return error message if validation fails, else null for success
	}
	public static String validAccountNumber(String accountNumber) {
        // Check if the account number is null or empty
		String message = null;
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
        	message= "Account number should be exactly 8 to 20 digits ";
        }

        // Check if the account number is numeric
        if (!accountNumber.matches("\\d+")) {
        	message= "Account number should be exactly 8 to 20 digits ";
        }

        // Check if the account number length is valid (e.g., between 8 and 20 digits)
        if (accountNumber.length() < 8 || accountNumber.length() > 20) {
        	message= "Account number should be exactly 8 to 20 digits ";
        }

        // If all checks pass, the account number is valid
        return message;
    }
	public static String validIFSC(String ifscCode) {
        // Check if the IFSC code is null or empty
		String message = null;
        if (ifscCode == null || ifscCode.trim().isEmpty()) {
        	message= "IFSC Code should be correct 11 digits ";
        }

        // IFSC code must be exactly 11 characters long
        if (ifscCode.length() != 11) {
        	message= "IFSC Code should be correct 11 digits ";
        }

        // Check if the first four characters are alphabetic (bank code)
        if (!ifscCode.substring(0, 4).matches("[A-Za-z]{4}")) {
        	message= "IFSC Code should be correct 11 digits ";
        }

        // Check if the fifth character is '0'
        if (ifscCode.charAt(4) != '0') {
        	message= "IFSC Code should be correct 11 digits ";
        }

        // Check if the last six characters are numeric (branch code)
        if (!ifscCode.substring(5).matches("[0-9]{6}")) {
        	message= "IFSC Code should be correct 11 digits ";
        }

        // If all checks pass, the IFSC code is valid
        return message;
    }
	public static String validateDin(String din) {
	    String message = null;

	    // Validate DIN (Director Identification Number)
	    
	    if (din == null || !din.matches("^[A-Za-z0-9]{8}$")) {
	        message = "DIN should be exactly 8 alphanumeric characters";
	    }
	    if (containsSQLKeywords(din)) {
            message="DIN contains invalid SQL keywords.";
        }
	    return message;  // Return the error message if validation fails, else null for success
	}
	
	public static String validateDesignation(String designation) {
	    String message = null;

	    // Validate designation (required and only alphabetic, comma, and space allowed)
	    if (designation == null || designation.isEmpty()) {
	        message = "Designation is required";
	    } else if (designation.length() > 50) {
	        message = "Designation should be less than or equal to 50 characters";
	    } else {
	        // Ensure the designation contains only alphabets, commas, and spaces
	        String designationRegex = "^[A-Za-z,\\s]+$";
	        if (!designation.matches(designationRegex)) {
	            message = "Designation can only contain alphabetic characters, commas, and spaces";
	        }
	    }
	    if (containsSQLKeywords(designation)) {
            message="Designation contains invalid SQL keywords.";
        }
	    return message;  // Return error message if validation fails, else null for success
	}
	
	public static String validateAddress(String address) {
	    String message = null;

	    // Validate designation (required and only alphabetic, comma, and space allowed)
	    if (address == null || address.isEmpty()) {
	        message = "Designation is required";
	    } else if (address.length() > 100) {
	        message = "Address should be less than or equal to 50 characters";
	    } else {
	        // Ensure the designation contains only alphabets, commas, and spaces
	        String designationRegex = "^[A-Za-z,\\s]+$";
	        if (!address.matches(designationRegex)) {
	            message = "Address can only contain alphabetic characters, commas, and spaces";
	        }
	    }
	    if (containsSQLKeywords(address)) {
            message="Address contains invalid SQL keywords.";
        }
	    return message;  // Return error message if validation fails, else null for success
	}
	public static String validateCreatedBy(String createdBy) {
	    String message = null;

	    // Ensure the createdby contains only alphanumeric characters (letters and digits)
	    String createdByRegex = "^[A-Za-z\\s-]+$";
	    if (createdBy!=null && !createdBy.matches(createdByRegex)) {
	    	message = "CreatedBy can only contain alphanumeric characters";
	    }
	    if (containsSQLKeywords(createdBy)) {
            message="CreatedBy contains invalid SQL keywords.";
        }

	    return message;  // Return error message if validation fails, else null for success
	}
	
	public static String validateString(String createdBy) {
	    String message = null;

	    // Ensure the createdby contains only alphanumeric characters (letters and digits)
	    String createdByRegex = "^[A-Za-z\\s-]+$";
	    if (createdBy!=null && !createdBy.matches(createdByRegex)) {
	    	message = "CreatedBy can only contain alphanumeric characters";
	    }
	    if (containsSQLKeywords(createdBy)) {
            message="CreatedBy contains invalid SQL keywords.";
        }

	    return message;  // Return error message if validation fails, else null for success
	}
	
	private static boolean containsSQLKeywords(String input) {
        if (input == null) {
            return false;
        }

        // Convert the input to lowercase to make the check case-insensitive
        String lowerInput = input.toLowerCase();

        // Check if any SQL keyword is present in the input
        for (String keyword : SQL_KEYWORDS) {
            if (lowerInput.contains(keyword)) {
                return true;
            }
        }

        return false;
    }
	public boolean validateFieldsForSQLInjection() {
        Field[] fields = this.getClass().getDeclaredFields(); // Get all fields of the class
        for (Field field : fields) {
            field.setAccessible(true); // Allow access to private fields

            try {
                Object value = field.get(this); // Get the value of the field

                // Skip non-string fields, such as Long, LocalDateTime, etc.
                if (value instanceof String) {
                    String stringValue = (String) value;

                    // If the value contains SQL keywords, return false (indicating invalid input)
                    if (containsSQLKeywords(stringValue)) {
                        System.out.println("SQL Injection detected in field: " + field.getName());
                        return false;
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return true; // All fields are safe
    }
	 public static boolean validateFieldsForSQLInjection(Object obj) {
	        if (obj == null) {
	            return true;
	        }

	        Class<?> clazz = obj.getClass();
	        Field[] fields = clazz.getDeclaredFields();

	        for (Field field : fields) {
	            field.setAccessible(true);  // Allow access to private fields
	            try {
	                Object value = field.get(obj);

	                // If the field is a String, check for SQL injection
	                if (value instanceof String) {
	                    String stringValue = (String) value;
	                    if (containsSQLKeywords(stringValue)) {
	                        System.out.println("SQL Injection detected in field: " + field.getName() + " (Class: " + clazz.getName() + ")");
	                        return false;
	                    }
	                }

	                // If the field is an object (and not a primitive), recursively check it
	                else if (!field.getType().isPrimitive() && field.getType() != String.class) {
	                    if (!validateFieldsForSQLInjection(value)) {
	                        return false;  // If any nested object is invalid, return false
	                    }
	                }

	            } catch (IllegalAccessException e) {
	                e.printStackTrace();
	            }
	        }

	        return true;
	    }
	 
	 public static String validateCTC(String ctc) {
	        try {
	            // Try to parse the CTC as a double
	            double ctcValue = Double.parseDouble(ctc);
	            return null;  // Use the double validation method
	        } catch (NumberFormatException e) {
	            return "CTC must be a valid numeric value.";
	        }
	    }
	 public static String validateJoiningDate(LocalDate joiningDate) {
	        if (joiningDate == null) {
	            return "Joining Date cannot be null.";
	        }

	        // Ensure the date is not in the future
	        if (joiningDate.isAfter(LocalDate.now())) {
	            return "Joining Date cannot be in the future.";
	        }

	        // Ensure the date is within a reasonable range (e.g., not more than 100 years ago)
	        LocalDate hundredYearsAgo = LocalDate.now().minusYears(100);
	        if (joiningDate.isBefore(hundredYearsAgo)) {
	            return "Joining Date cannot be more than 100 years in the past.";
	        }

	        return null;  // If all checks pass, the joining date is valid
	    }
}
