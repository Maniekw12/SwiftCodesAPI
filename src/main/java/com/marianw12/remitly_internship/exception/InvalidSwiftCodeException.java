package com.marianw12.remitly_internship.exception;


import jakarta.validation.ValidationException;

public class InvalidSwiftCodeException extends ValidationException {
    public InvalidSwiftCodeException(String message) {
        super(message);
    }

}
