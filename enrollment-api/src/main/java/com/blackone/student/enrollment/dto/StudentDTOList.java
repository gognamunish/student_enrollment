package com.blackone.student.enrollment.dto;

import lombok.*;

import java.util.List;

/**
 * Wrapper Class for Fetch operations
 */
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTOList {

    private List<StudentDTO> students;

}
