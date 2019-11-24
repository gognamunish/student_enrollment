package com.blackone.student.enrollment.rest;


import com.blackone.student.enrollment.dto.StudentDTO;
import com.blackone.student.enrollment.dto.StudentDTOList;
import com.blackone.student.enrollment.exception.APIResponse;
import com.blackone.student.enrollment.exception.InvalidEnrollmentRequest;
import com.blackone.student.enrollment.service.EnrollmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping(value = "/enrollment", produces = MediaType.APPLICATION_JSON_VALUE)
public class EnrollmentController {

    private static final List<String> SUPPORTED_PARAMS = Arrays.asList("id", "class");

    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping("fetchStudents")
    public StudentDTOList fetchStudents(@RequestParam Map<String, String> allParams) {
        log.info("Fetch Students Params: {}", allParams);

        if (allParams.size() == 1) {

            String param = allParams.keySet().iterator().next();
            String value = allParams.get(param);
            if (StringUtils.isEmpty(param) || StringUtils.isEmpty(value) || !SUPPORTED_PARAMS.contains(param)) {
                throw new InvalidEnrollmentRequest("API currently supports only one of following parameters with a valid value: " + SUPPORTED_PARAMS);
            }

            log.info("Request Parameters: {} - Value: {} ", param, value);

            switch (param) {
                case "id":
                    return enrollmentService.fetchById(Integer.parseInt(value));
                case "class":
                    return enrollmentService.fetchByClass(value);
                default:
                    // TODO this method can be refactored to be more clean than it is right now

            }
        }

        return StudentDTOList.builder().students(new ArrayList<>()).build();
    }

    @PostMapping("student")
    public StudentDTO createStudent(@RequestBody StudentDTO studentDTO) {
        log.info("Create Request: {}", studentDTO);

        if (Objects.nonNull(studentDTO.getId())) {
            throw new InvalidEnrollmentRequest("Student Id value should not be provided");
        }

        return saveOrUpdate(studentDTO);
    }

    @PutMapping("student")
    public StudentDTO updateStudent(@RequestBody StudentDTO studentDTO) {
        log.info("Update Request: {}", studentDTO);

        if (Objects.isNull(studentDTO.getId())) {
            throw new InvalidEnrollmentRequest("Student Id value should be provided");
        }

        return saveOrUpdate(studentDTO);
    }

    @DeleteMapping("student")
    public APIResponse deleteStudent(@RequestBody StudentDTO studentDTO) {
        log.info("Delete Request: {}", studentDTO);

        if (Objects.isNull(studentDTO.getId())) {
            throw new InvalidEnrollmentRequest("Student Id value should be provided");
        }

        return enrollmentService.deleteStudentById(studentDTO.getId());
    }

    private StudentDTO saveOrUpdate(StudentDTO studentDTO) {
        return enrollmentService.saveOrUpdateStudent(studentDTO);
    }

    private boolean isInteger(String idValue) {
        try {
            Integer.parseInt(idValue);
            return true;
        } catch (Exception e) {
            // intentional
        }
        return false;
    }


}
