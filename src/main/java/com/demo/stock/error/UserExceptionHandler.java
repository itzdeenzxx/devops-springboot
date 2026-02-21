package com.demo.stock.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.demo.stock.error.MissingParameterException;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(MissingParameterException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(
            MissingParameterException ex) {

        ErrorResponse error = new ErrorResponse(
            "MISSING_PARAMETERS",
            ex.getMessage()
        );

        return ResponseEntity.status(400).body(error);
    }
}
