package com.example.netty.repository.validation;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Validator to validate entity objects based on custom annotations
 */
public class EntityValidator {
    
    /**
     * Validate an object and return list of validation errors
     * @param obj Object to validate
     * @return List of validation error messages (empty if valid)
     */
    public static List<String> validate(Object obj) {
        List<String> errors = new ArrayList<>();
        
        if (obj == null) {
            errors.add("Object cannot be null");
            return errors;
        }
        
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        
        for (Field field : fields) {
            if (field.isAnnotationPresent(NonNegative.class)) {
                errors.addAll(validateNonNegative(obj, field));
            }
        }
        
        return errors;
    }
    
    /**
     * Validate @NonNegative annotation
     */
    private static List<String> validateNonNegative(Object obj, Field field) {
        List<String> errors = new ArrayList<>();
        NonNegative annotation = field.getAnnotation(NonNegative.class);
        
        try {
            field.setAccessible(true);
            Object value = field.get(obj);
            
            if (value == null) {
                return errors; // null is allowed, add @NotNull if needed
            }
            
            boolean isNegative = false;
            
            if (value instanceof BigDecimal) {
                isNegative = ((BigDecimal) value).compareTo(BigDecimal.ZERO) < 0;
            } else if (value instanceof Number) {
                isNegative = ((Number) value).doubleValue() < 0;
            } else {
                errors.add("Field " + field.getName() + " must be a numeric type for @NonNegative validation");
                return errors;
            }
            
            if (isNegative) {
                errors.add(field.getName() + ": " + annotation.message());
            }
            
        } catch (IllegalAccessException e) {
            errors.add("Cannot access field " + field.getName() + ": " + e.getMessage());
        }
        
        return errors;
    }
    
    /**
     * Validate and throw exception if validation fails
     * @param obj Object to validate
     * @throws ValidationException if validation fails
     */
    public static void validateAndThrow(Object obj) throws ValidationException {
        List<String> errors = validate(obj);
        if (!errors.isEmpty()) {
            throw new ValidationException(String.join(", ", errors));
        }
    }
}
