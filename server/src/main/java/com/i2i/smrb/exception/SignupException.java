package com.i2i.smrb.exception;

/**
 * <p>
 * Custom exception class used for handling errors related to Signup operations.
 * Extends RuntimeException to provide unchecked exception functionality.
 * </p>
 */
public class SignupException extends RuntimeException {
    public SignupException(String message, Throwable e) {
        super(message, e);
    }

}
