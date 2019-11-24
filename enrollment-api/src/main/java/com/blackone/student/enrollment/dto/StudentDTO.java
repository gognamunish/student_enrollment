package com.blackone.student.enrollment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * We don't want to expose Entity class to Caller
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private String nationality;

    // class in java is a reserved keyword
    @JsonProperty("class")
    private String studentClass;

}
