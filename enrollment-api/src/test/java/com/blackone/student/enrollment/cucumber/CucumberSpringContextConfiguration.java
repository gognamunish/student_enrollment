package com.blackone.student.enrollment.cucumber;

import com.blackone.student.enrollment.StudentEnrollmentMain;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ContextConfiguration;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = StudentEnrollmentMain.class, loader = SpringBootContextLoader.class)
public class CucumberSpringContextConfiguration {

    @Autowired
    protected TestRestTemplate restTemplate;

    @Before
    public void setUp() {
        log.info("-------------- Spring Context Initialized For Executing Cucumber Tests --------------");
    }
}