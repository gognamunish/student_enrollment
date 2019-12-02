package com.blackone.student.enrollment.service;

import com.blackone.gognamunish.dao.StudentRepository;
import com.blackone.gognamunish.entity.Student;
import com.blackone.student.enrollment.dto.StudentDTO;
import com.blackone.student.enrollment.dto.StudentDTOList;
import com.blackone.student.enrollment.exception.APIResponse;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Collectors;

// TODO API can be improved by having Java-8 style Optional returns.
public class EnrollmentService {

    private final StudentRepository studentRepository;

    private static final Function<StudentDTO, Student> DTO_TO_ENTITY = dto -> {
        return Student.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .nationality(dto.getNationality())
                .studentClass(dto.getStudentClass())
                .build();
    };

    private static final Function<Student, StudentDTO> ENTITY_TO_DTO = entity -> {
        return StudentDTO.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .nationality(entity.getNationality())
                .studentClass(entity.getStudentClass())
                .build();
    };


    public EnrollmentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public APIResponse deleteStudentById(Integer id) {
        APIResponse apiResponse = APIResponse.builder().build();
        try {
            studentRepository.deleteById(id);
            apiResponse.setResult(APIResponse.Result.SUCCESS);
        } catch (EmptyResultDataAccessException e) {
            apiResponse.setResult(APIResponse.Result.ERROR);
            apiResponse.setMessage("No record with ID " + id + " exists");
        }

        return apiResponse;
    }

    public StudentDTO saveOrUpdateStudent(StudentDTO studentDTO) {
        Student student = studentRepository.save(DTO_TO_ENTITY.apply(studentDTO));
        return ENTITY_TO_DTO.apply(student);
    }

    public StudentDTOList fetchById(Integer id) {
        return new StudentDTOList(
                Collections.singletonList(ENTITY_TO_DTO.apply(studentRepository.getOne(id)))
        );
    }

    public StudentDTOList fetchByClass(String className) {
        return new StudentDTOList(
                studentRepository.findByStudentClass(className)
                        .stream()
                        .map(ENTITY_TO_DTO)
                        .collect(Collectors.toList()));
    }

    public StudentDTOList fetchAll() {
        return new StudentDTOList(
                studentRepository.findAll()
                        .stream()
                        .map(ENTITY_TO_DTO)
                        .collect(Collectors.toList()));
    }
}
