package com.blackone.student.enrollment.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse {

    public enum Result {SUCCESS, ERROR}

    private Result result;
    private String message;

}
