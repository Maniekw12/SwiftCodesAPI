package com.marianw12.remitly_internship.exception;

import jakarta.validation.ValidationException;

public class InconsistentHeadquarterFlagException extends ValidationException {
    public InconsistentHeadquarterFlagException(String message) {
        super(message);
    }
}
