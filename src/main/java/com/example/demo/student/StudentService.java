package com.example.demo.student;

import java.beans.Transient;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class StudentService {

	private final StudentRepository studentRepository;
	private final StudentMapper studentMapper;

	@Autowired
	public StudentService(StudentRepository studentRepository, StudentMapper studentMapper) {
		this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

	//Get JPA
	public List<Student> getStudent() {
		return studentRepository.findAll();
	}

	//Get MyBatis
	public Student getStudentByIdWithMyBatis(Long id) {
		return studentMapper.selectStudentById(id);//pass to mapper to run the query
	}
	//Get all (MyBatis)
	public List<Student> getAllStudentWithMyBatis() {
		return studentMapper.selectAllStudent();//pass to mapper to run the query
	}

	//Post JPA
	public void addNewStudent(Student student) {
		Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());

		if (studentOptional.isPresent()) {
			throw new IllegalStateException("email taken");
		}

		studentRepository.save(student);
	}

	//Post MyBatis
	public void addNewStudentMyBatis(@Valid Student student) {
		Optional<Student> studentOptional = Optional.ofNullable(studentMapper.selectStudentByEmail(student.getEmail()));//get email to do validation duplicate email

		if (studentOptional.isPresent()) {//return email error message
			throw new IllegalStateException("email taken");
		}

		studentMapper.saveStudent(student);//pass to mapper to execute sql function
	}

	//Delete JPA
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
	//Delete MyBatis
	public void delateStudentByIdWithMyBatis(Long studentId) {
		boolean exists = studentMapper.existsById(studentId);//Check the student is it exist at mapper
		if (!exists) {//if not exist, throw exception
			throw new IllegalStateException("Student not exists - ID:" + studentId);
		}
		try {
			studentMapper.deleteStudentById(studentId);//pass to mapper to execute sql delete student
		} catch (Exception e) {
			if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {//check is it have foreign key at other table, if yes, throw error
				throw new IllegalStateException("The student had enroll courses, cannot remove this student!!");
			}
			throw e;
		}
	}

	//JPA update
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

	//MyBatis Update
	@Transactional
	public void updateStudentByIdWithMyBatis(Long studentId, UpdateStudentDTO newStudent) {//get student id and dto
		Student student =  studentMapper.selectStudentById(studentId);//get the student from mapper by execute sql
		if (student == null) {//if the student not found
			throw new IllegalStateException("Student not exists - ID:" + studentId);
		}
		// if the student name is not empty, or not match the previous name(to prevent only change at other field)
		if (newStudent.getName() != null && newStudent.getName().length() > 0 && !Objects.equals(student.getName(), newStudent.getName())) {
			student.setName(newStudent.getName());//update student name
		}
		// if the student email is not empty, or not match the previous email(to prevent only change at other field)
		if (newStudent.getEmail() != null && newStudent.getEmail().length() > 0 && !Objects.equals(student.getEmail(), newStudent.getEmail())) {
			// Check email not taken
			Student emailOwner = studentMapper.selectStudentByEmail(newStudent.getEmail());//get student email from mapper
			if (emailOwner != null && !Objects.equals(emailOwner.getId(), studentId)) {//check is the email exist at other student
				throw new IllegalStateException("Email taken");
			}
			student.setEmail(newStudent.getEmail());//update email
		}
		if (newStudent.getDob() != null) {//check the DoB is not empty
			student.setDob(newStudent.getDob());//update DoB
		}
		// Call MyBatis to update the record in DB
		studentMapper.updateStudentById(student);//update the previously temporary set student to database
	}

}
