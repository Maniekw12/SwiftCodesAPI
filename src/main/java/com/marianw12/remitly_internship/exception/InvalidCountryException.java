package com.marianw12.remitly_internship.exception;

import jakarta.validation.ValidationException;

public class InvalidCountryException extends ValidationException {
    public InvalidCountryException(String message) {
        super(message);
    }
}
