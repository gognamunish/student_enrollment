package com.blackone.student.enrollment.rest;

import com.blackone.student.enrollment.dto.StudentDTO;
import com.blackone.student.enrollment.dto.StudentDTOList;
import com.blackone.student.enrollment.exception.APIResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EnrollmentControllerTest {

    private static final StudentDTO STUDENT1 = StudentDTO.builder().firstName("Munish").lastName("Gogna").studentClass("2C").nationality("Indian").build();
    private static final StudentDTO STUDENT2 = StudentDTO.builder().firstName("Sachin").lastName("Shah").studentClass("2C").nationality("Singaporean").build();
    private static final StudentDTO STUDENT3 = StudentDTO.builder().firstName("Mandeep").lastName("Singh").studentClass("2C").nationality("American").build();
    private static final StudentDTO STUDENT4 = StudentDTO.builder().firstName("Harry").lastName("Potter").studentClass("2A").nationality("British").build();


    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void create() {

        createStudent(STUDENT1);
        createStudent(STUDENT2);
        createStudent(STUDENT3);
    }

    @Test
    public void deleteStudent() {

        createStudent(STUDENT4);

        String baseURL = "http://localhost:" + port + "/enrollment/student";

        StudentDTOList studentDTOList = fetchStudents("class", STUDENT4.getStudentClass());

        assertThat(studentDTOList.getStudents().size(), is(1));
        studentDTOList.getStudents().forEach(studentDTO -> {
            restTemplate.exchange(
                    baseURL,
                    HttpMethod.DELETE,
                    new HttpEntity<>(studentDTO, new HttpHeaders()),
                    APIResponse.class).getBody();
        });

        studentDTOList = fetchStudents("class", STUDENT4.getStudentClass());
        assertThat(studentDTOList.getStudents().size(), is(0));

    }

    @Test
    public void fetchStudentsById() {
        StudentDTOList studentDTOList = fetchStudents("id", "1");

        assertThat(studentDTOList.getStudents().size(), is(1));
        assertThat(studentDTOList.getStudents().get(0).getFirstName(), is(STUDENT1.getFirstName()));
        assertThat(studentDTOList.getStudents().get(0).getLastName(), is(STUDENT1.getLastName()));
    }

    @Test
    public void fetchStudentsByClass() {
        StudentDTOList studentDTOList = fetchStudents("class", "2C");
        assertThat(studentDTOList.getStudents().size(), is(9));
    }

    @Test
    public void fetchStudentsByUnknownParam() {
        String baseURL = "http://localhost:" + port + "/enrollment/fetchStudents?A=B";
        APIResponse apiResponse = restTemplate.getForObject(baseURL, APIResponse.class);

        assertThat(apiResponse.getResult().toString(), is(APIResponse.Result.ERROR.toString()));
        assertThat(apiResponse.getMessage(), is("API currently supports only one of following parameters with a valid value: [id, class]"));
    }

    private StudentDTOList fetchStudents(String param, String value) {
        String baseURL = "http://localhost:" + port + "/enrollment/fetchStudents?" + param + "=" + value;
        return restTemplate.getForObject(baseURL, StudentDTOList.class);
    }


    public void createStudent(StudentDTO studentDTORequest) {
        String baseURL = "http://localhost:" + port + "/enrollment/student";
        StudentDTO studentDTOResponse =
                restTemplate.postForEntity(
                        baseURL,
                        new HttpEntity<>(studentDTORequest, new HttpHeaders()),
                        StudentDTO.class).getBody();

        assertThat(studentDTOResponse.getId(), notNullValue());
        assertThat(studentDTOResponse.getFirstName(), is(studentDTORequest.getFirstName()));
        assertThat(studentDTOResponse.getLastName(), is(studentDTORequest.getLastName()));
        assertThat(studentDTOResponse.getNationality(), is(studentDTORequest.getNationality()));
        assertThat(studentDTOResponse.getStudentClass(), is(studentDTORequest.getStudentClass()));

    }

    @Test
    public void updateStudent_when_valid_id_is_provided() {
        String baseURL = "http://localhost:" + port + "/enrollment/student";
        StudentDTOList studentDTOList = fetchStudents("id", "1");

        StudentDTO existingStudentDTO = studentDTOList.getStudents().get(0);

        // update Student
        existingStudentDTO.setNationality("updated");
        restTemplate.put(baseURL, existingStudentDTO);


        studentDTOList = fetchStudents("id", "1");
        StudentDTO updatedStudentDTO = studentDTOList.getStudents().get(0);

        assertThat(updatedStudentDTO.getNationality(), is(existingStudentDTO.getNationality()));
    }

    @Test
    public void updateStudent_when_valid_id_not_provided() {
        String baseURL = "http://localhost:" + port + "/enrollment/student";
        StudentDTOList studentDTOList = fetchStudents("id", "1");

        StudentDTO existingStudentDTO = studentDTOList.getStudents().get(0);

        // update Student
        existingStudentDTO.setId(null);
        APIResponse apiResponse = restTemplate.exchange(
                baseURL,
                HttpMethod.PUT,
                new HttpEntity<>(existingStudentDTO, new HttpHeaders()),
                APIResponse.class).getBody();

        assertThat(apiResponse.getResult().toString(), is(APIResponse.Result.ERROR.toString()));
        assertThat(apiResponse.getMessage(), is("Student Id value should be provided"));
    }

    @Test
    public void deleteStudent_when_valid_id_not_provided() {
        String baseURL = "http://localhost:" + port + "/enrollment/student";

        // Delete Student (no ID set)
        APIResponse apiResponse = restTemplate.exchange(
                baseURL,
                HttpMethod.DELETE,
                new HttpEntity<>(STUDENT1, new HttpHeaders()),
                APIResponse.class).getBody();

        assertThat(apiResponse.getResult().toString(), is(APIResponse.Result.ERROR.toString()));
        assertThat(apiResponse.getMessage(), is("Student Id value should be provided"));
    }

    @Test
    public void createStudent_when_id_is_provided() {
        String baseURL = "http://localhost:" + port + "/enrollment/student";

        // Set Student Id
        STUDENT1.setId(999);
        APIResponse apiResponse = restTemplate.exchange(
                baseURL,
                HttpMethod.POST,
                new HttpEntity<>(STUDENT1, new HttpHeaders()),
                APIResponse.class).getBody();

        assertThat(apiResponse.getResult().toString(), is(APIResponse.Result.ERROR.toString()));
        assertThat(apiResponse.getMessage(), is("Student Id value should not be provided"));
    }

}