package com.cotodel.hrms.auth.server.sql;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class NoSqlKeywordsValidator implements ConstraintValidator<NoSqlKeywords, Object> {

    // List of forbidden SQL keywords
    private static final List<String> FORBIDDEN_KEYWORDS = Arrays.asList(
            "update", "delete", "drop", "insert", "select", "alter", "union", "table", "script", "exec","create","from","where","join",  "order", "group"
    );
    @Override
    public void initialize(NoSqlKeywords constraintAnnotation) {
        // Initialization logic (if necessary)
    }

    @Override
    public boolean isValid(Object dto, ConstraintValidatorContext context) {
        if (dto == null) {
            return true;  // Null objects are considered valid (you can adjust based on your needs)
        }

        // Iterate through all fields of the DTO class and check for forbidden keywords
        for (Field field : dto.getClass().getDeclaredFields()) {
            field.setAccessible(true);  // Access private fields
            try {
                // Get the value of the field
                Object fieldValue = field.get(dto);

                // Skip null values (optional: adjust as needed)
                if (fieldValue == null) {
                    continue;
                }

                // Only validate String fields, other types will be converted to String
                if (fieldValue instanceof String) {
                    if (containsForbiddenKeyword((String) fieldValue)) {
                        // Add a violation message for the field if a forbidden keyword is found
                        context.buildConstraintViolationWithTemplate("Field '" + field.getName() + "' contains forbidden SQL keywords.")
                                .addPropertyNode(field.getName())
                                .addConstraintViolation();
                        return false;  // Return false if a forbidden keyword is found
                    }
                } else {
                    // For non-string fields, convert to a string and check
                    String stringValue = String.valueOf(fieldValue);
                    if (containsForbiddenKeyword(stringValue)) {
                        context.buildConstraintViolationWithTemplate("Field '" + field.getName() + "' contains forbidden SQL keywords.")
                                .addPropertyNode(field.getName())
                                .addConstraintViolation();
                        return false;  // Return false if a forbidden keyword is found
                    }
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();  // Handle reflection exceptions
            }
        }

        return true;  // All fields are valid if no forbidden keywords are found
    }

    // Helper method to check for forbidden SQL keywords
    private boolean containsForbiddenKeyword(String input) {
        String lowerCaseInput = input.toLowerCase();  // Case-insensitive matching
        for (String keyword : FORBIDDEN_KEYWORDS) {
            if (lowerCaseInput.contains(keyword)) {
                return true;  // Forbidden keyword found
            }
        }
        return false;  // No forbidden keywords found
    }
}
