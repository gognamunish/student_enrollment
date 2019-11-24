package com.blackone.student.enrollment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidEnrollmentRequest extends RuntimeException {

    public InvalidEnrollmentRequest(String message) {
        super(message);
    }
}
