package com.blackone.student.enrollment.dto;

import lombok.Data;

import java.util.List;

@Data
public class StudentDeleteRequest {
    private List<Integer> ids;

}
