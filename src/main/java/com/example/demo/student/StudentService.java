package com.example.demo.student;

import java.beans.Transient;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class StudentService {

	private final StudentRepository studentRepository;

	@Autowired
	public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	public List<Student> getStudent() {
		return studentRepository.findAll();
	}

	public void addNewStudent(Student student) {
		Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());

		if (studentOptional.isPresent()) {
			throw new IllegalStateException("email taken");
		}

		studentRepository.save(student);
	}

	public void deleteStudent(Long studentId) {
		// TODO Auto-generated method stub
		studentRepository.findById(studentId);
		boolean exists = studentRepository.existsById(studentId);
		if (!exists) {
			throw new IllegalStateException("Student not exists - ID:" + studentId);
		}
		try {
		studentRepository.deleteById(studentId);
		} catch (Exception e) {
			if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				throw new IllegalStateException("The student had enroll courses, cannot remove this student!!");
			}
			throw e;
		}
	}

	@Transactional //for update
	public void updateStudent(Long studentId, Student newStudent) {
		// TODO Auto-generated method stub
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new IllegalStateException("student wit id" + studentId + "not exists"));

		if (newStudent.getName() != null && newStudent.getName().length() > 0 && !Objects.equals(student.getName(), newStudent.getName())) {
			student.setName(newStudent.getName());
		}

		if (newStudent.getEmail() != null && newStudent.getEmail().length() > 0 && !Objects.equals(student.getEmail(), newStudent.getEmail())) {
			Optional<Student> studentOptional = studentRepository.findStudentByEmail(newStudent.getEmail());
			if (studentOptional.isPresent()) {
				throw new IllegalStateException("email taken");
			}
			student.setEmail(newStudent.getEmail());
		}
	}
}
