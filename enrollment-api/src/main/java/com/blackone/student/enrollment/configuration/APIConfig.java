package com.blackone.student.enrollment.configuration;

import com.blackone.gognamunish.configuration.PersistenceJPAConfig;
import com.blackone.gognamunish.dao.StudentRepository;
import com.blackone.student.enrollment.service.EnrollmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Slf4j
@Configuration
@Import(PersistenceJPAConfig.class)
public class APIConfig {

    @Bean
    public EnrollmentService enrollmentService(@Autowired StudentRepository studentRepository) {
        EnrollmentService enrollmentService = new EnrollmentService(studentRepository);
        log.info("EnrollmentService service configured...");
        return enrollmentService;
    }

}
