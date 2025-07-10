package com.example.demo.student;

import jakarta.validation.Valid;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//Create interface file, then can execute the xml to execute query
@Mapper
public interface StudentMapper {
    Student selectStudentById(Long id);

    List<Student> selectAllStudent();

    void saveStudent(@Valid Student student);
    boolean existsById(Long id);
    Student selectStudentByEmail(String email);

    void deleteStudentById(Long studentId);

    void updateStudentById(Student student);

}
