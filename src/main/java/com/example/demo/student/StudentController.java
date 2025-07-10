package com.example.demo.student;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping(path = "api/v1/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {

        this.studentService = studentService;
    }

    //JPA Get all student
    @GetMapping
    public List<Student> getStudent() {
        return studentService.getStudent();
    }

    //MyBatis Get student by id
    @GetMapping("/mybatis/{studentId}")//Set the route able to access this function
    public Student getStudentByIdWithMyBatis(@PathVariable("studentId") Long studentId) {//get the student is
        Student student = studentService.getStudentByIdWithMyBatis(studentId);//pass the student id to get method at service
        if (student == null) {//if no student, then display error message
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found (MyBatis)");
        }
        return student;
    }

    //MyBatis Get all student
    @GetMapping("/mybatis")//set the route and able to use Get method to have the result
    public List<Student> getAllStudentWithMyBatis() {//return list of object(student)
        List<Student> students = studentService.getAllStudentWithMyBatis();//get list of object(student)
        if (students == null) {//if no student found then return error message
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found (MyBatis)");
        }
        return students;
    }

    //JPA Post
    @PostMapping
    public void registerNewStudent(@RequestBody @Valid Student student) {

        studentService.addNewStudent(student);
    }

    //MyBatis Post
    @PostMapping("/mybatis")
    public void registerNewStudentWithMyBatis(@RequestBody @Validated Student student) {//add to student, with validation object
        studentService.addNewStudentMyBatis(student);//pass to service method
    }

    //JPA delete
    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId) {
        try {
            studentService.deleteStudent(studentId);
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    //Delete By id MyBatis
    @DeleteMapping("/mybatis/{studentId}")//set the route, with id to delete
    public void removeStudentByIdWithMyBatis(@PathVariable("studentId") Long studentId) {//get the student id, and return nothing
        try {
            studentService.delateStudentByIdWithMyBatis(studentId);//pass to service to delete student
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());//if no student id found, return error message
        }
    }

    //JPA Update
    @PutMapping(path = "/{studentId}")
    public void editStudent(@PathVariable("studentId") Long studentId, @RequestBody @Validated Student student) {
        // TODO: process PUT request
        studentService.updateStudent(studentId, student);

    }

    //Update By id MyBatis
    @PutMapping("/mybatis/{studentId}")//Set can access the url with id for put method
    public void updateStudentByIdWithMyBatis(@PathVariable("studentId") Long studentId, @RequestBody UpdateStudentDTO student) {//get the student id and the DTO
        try {
            studentService.updateStudentByIdWithMyBatis(studentId, student);//pass to service method
        } catch (IllegalStateException e) {
            if (e.getMessage().toLowerCase().contains("email")) {//if email exist, return bad request ()
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());//if cannot find the id, return error 404
        }
    }


}
