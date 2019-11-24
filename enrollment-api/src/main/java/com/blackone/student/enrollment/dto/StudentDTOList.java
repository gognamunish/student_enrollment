package com.blackone.student.enrollment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * Wrapper Class for Fetch operations
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTOList {

    private List<StudentDTO> students;

}
