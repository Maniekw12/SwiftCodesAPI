package com.marianw12.remitly_internship.exception;


public class NonExistingSwiftCodeException extends RuntimeException {
    public NonExistingSwiftCodeException(String message) {
        super(message);
    }
}
