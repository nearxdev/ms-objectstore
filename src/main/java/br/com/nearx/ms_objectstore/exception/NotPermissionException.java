package br.com.nearx.ms_objectstore.exception;

public class NotPermissionException extends RuntimeException {
    
    public NotPermissionException(String message) {
        super(message);
    }
    
    public NotPermissionException(String message, Throwable cause) {
        super(message, cause);
    }
} 