// Archivo: src/main/java/com/franquicias/exception/ResourceNotFoundException.java
package com.franquicias.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}