package com.cotodel.hrms.auth.server.util;
import java.lang.reflect.Field;

public class SQLInjectionValidator {
	private static final String[] SQL_KEYWORDS = {
            "select", "insert", "update", "delete", "drop", "create", "alter", "union", "or", "and", "from", "where", "--", ";","table", "script", "exec","join","order", "group"
    };
	
    // Method to validate fields for SQL Injection
//    public static String validateFieldsForSQLInjection(Object obj) {
//        if (obj == null) {
//            return null;  // No error if the object is null
//        }
//
//        Class<?> clazz = obj.getClass();
//        Field[] fields = clazz.getDeclaredFields();
//
//        for (Field field : fields) {
//            field.setAccessible(true);  // Allow access to private fields
//            try {
//                Object value = field.get(obj);
//
//                // If the field is a String, check for SQL injection
//                if (value instanceof String) {
//                    String stringValue = (String) value;
//                    String errorMessage = containsSQLKeywords(stringValue);
//                    if (errorMessage != null) {
//                        return "SQL Injection detected in field: " + field.getName() + " (Class: " + clazz.getName() + "). " + errorMessage;
//                    }
//                }
//
//                // If the field is an object (and not a primitive), recursively check it
//                else if (!field.getType().isPrimitive() && field.getType() != String.class) {
//                	Object nestedValue = field.get(obj);
//                    if (nestedValue != null) {
//                        String nestedError = validateFieldsForSQLInjection(nestedValue);
//                        if (nestedError != null) {
//                            return nestedError;  // If any nested object is invalid, return the error message
//                        }
//                    }
////                    String nestedError = validateFieldsForSQLInjection(value);
////                    if (nestedError != null) {
////                        return nestedError;  // If any nested object is invalid, return the error message
////                    }
//                }
//
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return null;  // Return null if no SQL injection is detected
//    }
	
	public static String validateFieldsForSQLInjection(Object obj) {
        if (obj == null) {
            return null;  // No error if the object is null
        }

        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                continue;  // Skip static fields
            }

            field.setAccessible(true);  // Allow access to private fields
            try {
                Object value = field.get(obj);

                // If the field is a String, check for SQL injection
                if (value instanceof String) {
                    String stringValue = (String) value;
                    String errorMessage = containsSQLKeywords(stringValue);
                    if (errorMessage != null) {
                        return "SQL Injection detected in field: " + field.getName() + " (Class: " + clazz.getName() + "). " + errorMessage;
                    }
                }

                // If the field is an object (and not a primitive), recursively check it
                else if (!field.getType().isPrimitive() && field.getType() != String.class) {
                    Object nestedValue = field.get(obj);
                    if (nestedValue != null) {
                        String nestedError = validateFieldsForSQLInjection(nestedValue);
                        if (nestedError != null) {
                            return nestedError;  // If any nested object is invalid, return the error message
                        }
                    } 
                    else {
                        // Handle case where the nested value is null
                        System.err.println("Nested object in field " + field.getName() + " is null.");
                    }
                }

            } catch (IllegalAccessException e) {
                // Log access issues and return a message
                System.err.println("Access issue with field: " + field.getName() + " in class: " + clazz.getName());
                e.printStackTrace();
                return "Access issue while validating field: " + field.getName();
            }
        }

        return null;  // Return null if no SQL injection is detected
    }

    // Check if the string contains SQL keywords for potential SQL injection
    private static String containsSQLKeywords(String input) {
        if (input == null) {
            return null;
        }

        // Convert the input to lowercase to make the check case-insensitive
        String lowerInput = input.toLowerCase();

        // Check if any SQL keyword is present in the input
        for (String keyword : SQL_KEYWORDS) {
            if (lowerInput.contains(keyword)) {
                return "Potential SQL injection detected: contains SQL keyword '" + keyword + "'.";
            }
        }

        return null;  // No SQL injection found
    }
}
