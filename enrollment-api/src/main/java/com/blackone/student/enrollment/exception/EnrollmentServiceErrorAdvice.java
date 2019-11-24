package com.blackone.student.enrollment.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class EnrollmentServiceErrorAdvice {

    @ExceptionHandler({InvalidEnrollmentRequest.class})
    public ResponseEntity<APIResponse> handleInvalidEnrollmentRequest(InvalidEnrollmentRequest e) {
        return error(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<APIResponse> handleInvalidFormatException(HttpMessageNotReadableException e) {
        return error(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<APIResponse> handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        return error(HttpStatus.NOT_FOUND, e);
    }

    @ExceptionHandler({NumberFormatException.class})
    public ResponseEntity<APIResponse> handleNumberFormatException(NumberFormatException e) {
        return error(HttpStatus.NOT_FOUND, e);
    }


    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<APIResponse> handleEmptyResultDataAccessException(EntityNotFoundException e) {
        return error(HttpStatus.NOT_FOUND, e);
    }


    private ResponseEntity<APIResponse> error(HttpStatus status, Exception e) {
        return ResponseEntity.status(status)
                .body(APIResponse.builder().result(APIResponse.Result.ERROR)
                        .message(e.getMessage())
                        .build());
    }
}
