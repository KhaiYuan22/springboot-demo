package com.example.demo.student;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

	@GetMapping
	public List<Student> getStudent() {
		return studentService.getStudent();
	}

	@PostMapping
	public void registerNewStudent(@RequestBody @Valid Student student) {
		studentService.addNewStudent(student);
	}

	@DeleteMapping(path = "{studentId}")
	public void deleteStudent(@PathVariable("studentId") Long studentId) {
		try {
		studentService.deleteStudent(studentId);
		}catch (IllegalStateException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PutMapping(path = "/{studentId}")
	public void editStudent(@PathVariable("studentId") Long studentId, @RequestBody @Valid Student student) {
		// TODO: process PUT request
		studentService.updateStudent(studentId, student);

	}

}
