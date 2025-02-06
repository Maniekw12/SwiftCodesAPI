package com.marianw12.remitly_internship.exception;

import com.marianw12.remitly_internship.request.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NonExistingSwiftCodeException.class)
    public ResponseEntity<BaseResponse> handleNonExistingSwiftCode(HttpServletRequest req, Exception e) throws Exception {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse(e.getMessage()));
    }

    @ExceptionHandler({InvalidCountryException.class, InvalidSwiftCodeException.class, InconsistentHeadquarterFlagException.class})
    public ResponseEntity<BaseResponse> handleValidation(HttpServletRequest req, Exception e) throws Exception {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse(e.getMessage()));
    }

    @ExceptionHandler(DuplicatedSwiftCodeException.class)
    public ResponseEntity<BaseResponse> handleDuplicatedSwiftCode(HttpServletRequest req, Exception e) throws Exception {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new BaseResponse(e.getMessage()));
    }

}
