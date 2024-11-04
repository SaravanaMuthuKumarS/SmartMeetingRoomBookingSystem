package com.i2i.smrb.exception;

/**
 * <p>
 * Custom exception class used for handling errors related to log in operations.
 * Extends RuntimeException to provide unchecked exception functionality.
 * </p>
 */
public class LoginException extends RuntimeException {
    public LoginException(String message, Throwable e) {
        super(message, e);
    }
    public LoginException(String message) {
        super(message);
    }
}
