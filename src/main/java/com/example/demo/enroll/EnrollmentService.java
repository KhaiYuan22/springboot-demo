package com.example.demo.enroll;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.course.Course;
import com.example.demo.course.CourseRepository;
import com.example.demo.student.Student;
import com.example.demo.student.StudentRepository;


@Service
public class EnrollmentService {

	private final EnrollmentRepository enrollmentRepository;
	private final StudentRepository studentRepository;
	private final CourseRepository courseRepository;
	
	@Autowired
	public EnrollmentService(EnrollmentRepository enrollmentRepository, StudentRepository studentRepository,
			CourseRepository courseRepository) {
		super();
		this.enrollmentRepository = enrollmentRepository;
		this.studentRepository = studentRepository;
		this.courseRepository = courseRepository;
	}



	public List<Enrollment> getEnrollment() {
		// TODO Auto-generated method stub
		
		return enrollmentRepository.findAll();
	}







	public void addEnrollment(Enrollment enrollment) {
		// TODO Auto-generated method stub
		Long studentId = enrollment.getStudent().getId();
		Long courseId = enrollment.getCourse().getCourseId();
		
		Student student = studentRepository.findById(studentId)
				.orElseThrow(()-> new IllegalStateException("Student not found"));
		Course course = courseRepository.findById(courseId)
				.orElseThrow(()-> new IllegalStateException("Course not found"));
		
		Optional<Enrollment> enrollmentOptional =
			    enrollmentRepository.findByStudentAndCourse(student, course);

		
	    if (enrollmentOptional.isPresent()) {
	        throw new IllegalStateException("Student already enrolled in this course.");
	    }

	    enrollment.setStudent(student);
	    enrollment.setCourse(course);
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



	public void updateEnrollmentCourse(Long enrollmentId, Enrollment newEnrollment) {
		// TODO Auto-generated method stub

		
		Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
				.orElseThrow(() -> new IllegalStateException("Enrollment Id not found. ID: " + enrollmentId));
		
		if(newEnrollment.getStudent() != null && newEnrollment.getStudent().getId() != null) {
			Student newStudent = studentRepository.findById(newEnrollment.getStudent().getId())
					.orElseThrow(()-> new IllegalStateException("Student ID: "+ newEnrollment.getStudent().getId()+" Not Found"));
			enrollment.setStudent(newStudent);
		}
		
		if(newEnrollment.getCourse() != null && newEnrollment.getCourse().getCourseId() != null) {
			Course newCourse = courseRepository.findById(newEnrollment.getCourse().getCourseId())
					.orElseThrow(()-> new IllegalStateException("Student ID: "+ newEnrollment.getCourse().getCourseId()+" Not Found"));
			enrollment.setCourse(newCourse);
		}
		Optional<Enrollment> enrollmentOptional = enrollmentRepository.findByStudentAndCourse(enrollment.getStudent(), enrollment.getCourse());
		
	    if (enrollmentOptional.isPresent() &&
	            !enrollmentOptional.get().getId().equals(enrollment.getId())) {
	        throw new IllegalStateException("Student already enrolled in this course.");
	    }
		
	    
		enrollmentRepository.save(enrollment);
	}



	public List<Student> getStudentByCourse(Long courseId) {
		// TODO Auto-generated method stub
		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new IllegalStateException("Course ID :" + courseId + "Not found"));
		
		List<Enrollment> enrollments = enrollmentRepository.findByCourse(course);
		return enrollments.stream()
				.map(Enrollment::getStudent)
				.toList();
				
	}

}
