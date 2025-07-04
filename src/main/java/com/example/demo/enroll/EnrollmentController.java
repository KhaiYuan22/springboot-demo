package com.example.demo.enroll;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.course.Course;
import com.example.demo.student.Student;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/enrolls")
public class EnrollmentController {

	private final EnrollmentService enrollmentService;

	@Autowired
	public EnrollmentController(EnrollmentService enrollmentService) {
		super();
		this.enrollmentService = enrollmentService;
	}
	
	@GetMapping
	public List<Enrollment> getEnrollment(){
		return enrollmentService.getEnrollment();
	}
	@GetMapping("/course/{courseId}/students")
	public List<Student> getStudentByCourse(@PathVariable Long courseId){
		return enrollmentService.getStudentByCourse(courseId);
	}
	
	@PostMapping
	public void enrollStudent(@RequestBody @Valid Enrollment enrollment) {
		enrollmentService.addEnrollment(enrollment);
		
	}
	
	@DeleteMapping(path = "{enrollmentId}")
	public void deleteEnrollment(@PathVariable("enrollmentId") Long enrollmentId) {
		enrollmentService.deleteEnrollment(enrollmentId);
	}
	
	@PutMapping(path = "{enrollmentId}")
	public void changeEnrollment(@PathVariable("enrollmentId") Long enrollmentId, @RequestBody @Valid Enrollment enrollment) {
		enrollmentService.updateEnrollmentCourse(enrollmentId, enrollment);
	}
	
	@GetMapping("/report")
	public List<EnrollmentReportDTO> getEnrollmentReport(){
		return enrollmentService.getEnrollmentReport();
	}
	
	@GetMapping("/grouped")
	public List<CourseStudentGroupDTO> getGroupedReport() {
	    return enrollmentService.getCourseStudentGroups();
	}

}
