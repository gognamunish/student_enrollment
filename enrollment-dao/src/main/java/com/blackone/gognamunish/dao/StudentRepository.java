package com.blackone.gognamunish.dao;

import com.blackone.gognamunish.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findByStudentClass(String studentClass);


}
