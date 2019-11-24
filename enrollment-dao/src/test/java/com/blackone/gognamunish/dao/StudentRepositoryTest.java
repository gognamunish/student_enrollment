package com.blackone.gognamunish.dao;

import com.blackone.gognamunish.configuration.PersistenceJPAConfig;
import com.blackone.gognamunish.entity.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@Import(PersistenceJPAConfig.class)
@Transactional
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository testObject;

    @Test
    public void save_student_and_then_retrieve_by_id() {
        // Given
        Student studentToPersist = Student.builder()
                .firstName("Munish")
                .lastName("Gogna")
                .studentClass("A")
                .build();
        assertThat(studentToPersist.getId(), nullValue());
        testObject.save(studentToPersist);
        assertThat(studentToPersist.getId(), notNullValue());

        // When
        Student persistedStudent = testObject.getOne(studentToPersist.getId());

        // Then
        assertThat(persistedStudent, equalTo(studentToPersist));
    }


    @Test
    public void delete_student_and_then_retrieve_by_id() {
        // Given
        Student studentToDelete = Student.builder()
                .firstName("Munish")
                .lastName("Gogna")
                .studentClass("A")
                .build();
        assertThat(studentToDelete.getId(), nullValue());
        testObject.save(studentToDelete);
        assertThat(studentToDelete.getId(), notNullValue());

        // When
        testObject.delete(studentToDelete);
        Student deletedStudent = testObject.findById(studentToDelete.getId()).orElse(null);

        // Then
        assertThat(deletedStudent, nullValue());
    }

    @Test
    public void find_all_students_by_class_name() {
        // Given
        String studentClass = "B";
        Student classBStudent1 = Student.builder()
                .firstName("Munish")
                .lastName("Gogna")
                .studentClass(studentClass)
                .build();

        Student classBStudent2 = Student.builder()
                .firstName("Sachin")
                .lastName("Shah")
                .studentClass(studentClass)
                .build();

        Student classBStudent3 = Student.builder()
                .firstName("Mandeep")
                .lastName("Singh")
                .studentClass(studentClass)
                .build();


        List<Student> iterableStudents = Arrays.asList(classBStudent1, classBStudent2, classBStudent3);
        testObject.saveAll(iterableStudents);

        // When
        List<Student> classBStudents = testObject.findByStudentClass(studentClass);

        // Then
        assertThat(classBStudents.size(), is(iterableStudents.size()));
    }


    @Test
    public void update_student_and_then_retrieve_by_id() {
        // Given
        Student studentToPersist = Student.builder()
                .firstName("Munish")
                .lastName("Gogna")
                .studentClass("A")
                .build();
        assertThat(studentToPersist.getId(), nullValue());
        testObject.save(studentToPersist);
        assertThat(studentToPersist.getId(), notNullValue());

        Student persistedStudent = testObject.getOne(studentToPersist.getId());
        persistedStudent.setFirstName("updated");
        persistedStudent.setLastName("updated");
        persistedStudent.setStudentClass("updated");
        testObject.save(persistedStudent);

        // When
        Student updatedStudent = testObject.getOne(persistedStudent.getId());

        // Then
        assertThat(updatedStudent.getFirstName(), equalTo(persistedStudent.getFirstName()));
        assertThat(updatedStudent.getLastName(), equalTo(persistedStudent.getLastName()));
        assertThat(updatedStudent.getStudentClass(), equalTo(persistedStudent.getStudentClass()));
    }

}
