package com.jphaugla.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String resourceName;
    private String fieldName;
    private Object fieldValue;
    private String fieldName2;
    private Object fieldValue2;

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue,
                                     String filedName2, Object fieldValue2) {
        super(String.format("%s not found with %s : '%s' and %s : '%s'", resourceName, fieldName,
                fieldValue, filedName2, fieldValue2));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.fieldName = fieldName2;
        this.fieldValue = fieldValue2;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
    public String getFieldName2() {
        return fieldName2;
    }

    public Object getFieldValue2() {
        return fieldValue2;
    }

}
