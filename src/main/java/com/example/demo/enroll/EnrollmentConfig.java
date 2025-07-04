package com.example.demo.enroll;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.course.CourseRepository;
import com.example.demo.course.Course;
import com.example.demo.student.StudentRepository;
import com.example.demo.student.Student;

@Configuration
public class EnrollmentConfig {

    private final EnrollmentRepository enrollmentRepository;

    EnrollmentConfig(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }
    
	@Bean
	CommandLineRunner enrollmentCommandLineRunner(EnrollmentRepository enrollRepository, StudentRepository studentRepository, CourseRepository courseReporitory) {
		return args -> {
			
			//get from other database
			//student
			Student student1 = studentRepository.findById(1L)
					.orElseThrow(() -> new IllegalStateException("Student not found"));
			//Course
			Course course1 = courseReporitory.findById(1L)
					.orElseThrow(() -> new IllegalStateException("Course1 not found"));

			Course course2 = courseReporitory.findById(2L)
					.orElseThrow(() -> new IllegalStateException("Course2 not found"));
			
			List<Enrollment> enrollmentToSave = new java.util.ArrayList<>();
			
			
			if(enrollRepository.findByStudentAndCourse(student1, course1).isEmpty()) {
			Enrollment enroll1 = new Enrollment(student1,course1);
			enrollmentToSave.add(enroll1);
			}
			
			if(enrollRepository.findByStudentAndCourse(student1, course2).isEmpty()) {
			Enrollment enroll2 = new Enrollment(student1,course2);		
			enrollmentToSave.add(enroll2);
			}
			
			if(!enrollmentToSave.isEmpty()) {
				enrollmentRepository.saveAll(enrollmentToSave);
			}
			

	};
	}
	}
