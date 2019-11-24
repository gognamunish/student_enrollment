package com.blackone.student.enrollment.cucumber;

import com.blackone.student.enrollment.dto.StudentDTO;
import com.blackone.student.enrollment.dto.StudentDTOList;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@Slf4j
public class StudentEnrollmentStepDefs extends AbstractSteps {

    @Given("A Student with following attributes")
    public void a_Student_with_following_attributes(Map<String, String> studentAttribute) {
        log.info("{}", studentAttribute);

        studentDTORequest = StudentDTO.builder()
                .firstName(studentAttribute.get("firstName"))
                .lastName(studentAttribute.get("lastName"))
                .studentClass(studentAttribute.get("class"))
                .nationality(studentAttribute.get("nationality"))
                .build();

        String id = studentAttribute.get("id");
        if (!StringUtils.isEmpty(id)) {
            studentDTORequest.setId(Integer.parseInt(id));
        }

        log.info("Student : {} ", studentDTORequest);

    }

    @When("I {string} student JSON to {string} endpoint")
    public void i_student_JSON_to_endpoint(String httpMethod, String endpointURL) {
        studentDTOResponse =
                restTemplate.exchange(
                        baseUrl() + endpointURL,
                        HttpMethod.valueOf(httpMethod),
                        new HttpEntity<>(studentDTORequest, new HttpHeaders()),
                        StudentDTO.class).getBody();

    }

    @Then("Student should be enrolled in System")
    public void student_should_be_enrolled_in_System() {
        assertThat(studentDTOResponse.getId(), notNullValue());
    }

    @Then("I get {int} student from fetch students API")
    public void iGetStudentFromFetchStudentsAPI(Integer expectedCount) {
        Assert.assertThat(fetchSize, is(expectedCount));
    }

    @When("I fetch students for class {string}")
    public void iFetchStudentsForClass(String classs) {
        String baseURL = baseUrl() + "/enrollment/fetchStudents?class=" + classs;
        StudentDTOList studentDTOList = restTemplate.getForObject(baseURL, StudentDTOList.class);
        fetchSize = studentDTOList.getStudents().size();
    }
}
