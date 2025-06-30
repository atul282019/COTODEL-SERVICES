package com.cotodel.hrms.auth.server.util;
import java.lang.reflect.Field;
import java.text.Normalizer;

public class SQLInjectionValidator {
	private static final String[] SQL_KEYWORDS = {
            "select", "insert", "update", "delete", "drop", "create", "alter", "union", "or", "and", "from", "where", "--", ";","table", "script", "exec","join","order", "group"
    };
	
    // Method to validate fields for SQL Injection
    public static String validateFieldsForSQLInjection(Object obj) {
        if (obj == null) {
            return null;  // No error if the object is null
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
                    String errorMessage = containsSQLKeywords(stringValue);
                    if (errorMessage != null) {
                        return "SQL Injection detected in field: " + field.getName() + " (Class: " + clazz.getName() + "). " + errorMessage;
                    }
                }

                // If the field is an object (and not a primitive), recursively check it
                else if (!field.getType().isPrimitive() && field.getType() != String.class) {
                    String nestedError = validateFieldsForSQLInjection(value);
                    if (nestedError != null) {
                        return nestedError;  // If any nested object is invalid, return the error message
                    }
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
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
    





        public static boolean isSQLInjectionDetected(Object obj) {
            if (obj == null) return false;

            Class<?> clazz = obj.getClass();
            for (Field field : clazz.getDeclaredFields()) {
                if (field.getType().equals(String.class)) {
                    field.setAccessible(true);
                    try {
                        String value = (String) field.get(obj);
                        if (value != null) {
                            String normalized = Normalizer.normalize(value, Normalizer.Form.NFKC).toLowerCase();
                            for (String keyword : SQL_KEYWORDS) {
                                if (normalized.contains(keyword)) {
                                    return true; // SQL injection pattern found
                                }
                            }
                        }
                    } catch (IllegalAccessException e) {
                        // Optionally log or ignore
                    }
                }
            }
            return false; // No SQL injection detected
        }
    

}
