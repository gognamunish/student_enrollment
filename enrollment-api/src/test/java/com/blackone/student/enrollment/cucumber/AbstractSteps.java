package com.blackone.student.enrollment.cucumber;

import com.blackone.student.enrollment.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;


public class AbstractSteps {

    protected StudentDTO studentDTORequest;
    protected StudentDTO studentDTOResponse;
    protected Integer fetchSize;
    @Autowired
    protected TestRestTemplate restTemplate;
    @LocalServerPort
    private int port;

    protected String baseUrl() {
        return "http://localhost:" + port;
    }
}
