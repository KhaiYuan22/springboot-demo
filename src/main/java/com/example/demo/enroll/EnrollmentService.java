package com.example.demo.enroll;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.course.Course;


@Service
public class EnrollmentService {

	private final EnrollmentRepository enrollmentRepository;
	
	
	@Autowired
	public EnrollmentService(EnrollmentRepository enrollmentRepository) {
		super();
		this.enrollmentRepository = enrollmentRepository;
	}



	public List<Enrollment> getEnrollment() {
		// TODO Auto-generated method stub
		
		return enrollmentRepository.findAll();
	}



	public void addEnrollment(Enrollment enrollment) {
		// TODO Auto-generated method stub
		Optional<Enrollment> enrollmentOptional = enrollmentRepository.findByStudentAndCourse(enrollment.getStudent(), enrollment.getCourse());
		
	    if (enrollmentOptional.isPresent()) {
	        throw new IllegalStateException("Student already enrolled in this course.");
	    }

	    enrollmentRepository.save(enrollment);
	}



	public void deleteEnrollment(Long enrollmentId) {
		// TODO Auto-generated method stub
		enrollmentRepository.findById(enrollmentId);
		boolean exists = enrollmentRepository.existsById(enrollmentId);
		if(!exists) {
			throw new IllegalStateException("The enrollment record is not exists by ID" + enrollmentId);
		}
		enrollmentRepository.deleteById(enrollmentId);
	}



	public void updateEnrollmentCourse(Long enrollmentId, Long courseId) {
		// TODO Auto-generated method stub
		
	}

}
