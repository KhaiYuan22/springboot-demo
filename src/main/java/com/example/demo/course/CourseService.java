package com.example.demo.course;

import java.beans.Transient;
import java.lang.foreign.Linker.Option;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.example.demo.student.StudentRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    private final StudentRepository studentRepository;
	
	private final CourseRepository courseRepository;

	@Autowired
	public CourseService(CourseRepository courseRepository, StudentRepository studentRepository) {
		super();
		this.courseRepository = courseRepository;
		this.studentRepository = studentRepository;
	}
	
	public List<Course> getCourse(){
		return courseRepository.findAll();
	}

	public void addNewCourse(Course course) {
		// TODO Auto-generated method stub
		Optional<Course> courseOptional = courseRepository.findCourseByCourseName(course.getCourseName());
		
		if(courseOptional.isPresent()) {
			throw new IllegalStateException("Already created for this course");
		}
		
		courseRepository.save(course);
	}

	public void deleteCourse(Long courseId) {
		// TODO Auto-generated method stub
		courseRepository.findById(courseId);
		boolean exists = courseRepository.existsById(courseId);
		if(!exists) {
			throw new IllegalStateException("Course ID" + courseId + " not found");
		}
		courseRepository.deleteById(courseId);
	}

	@Transactional
	public void updateCourse(Long courseId, String courseName, String description, Integer creditHours) {
		// TODO Auto-generated method stub
		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new IllegalStateException("Course " + courseId + " not exists"));
		
		if(courseName != null && courseName.length() > 0 && !Objects.equals(course.getCourseName(), courseName)) {
			Optional<Course> courseOptional = courseRepository.findCourseByCourseName(courseName);
			if(courseOptional.isPresent()) {
				throw new IllegalStateException("duplicate course name");
			}
			course.setCourseName(courseName);		
			}
		if(description != null && description.length() > 0 && !Objects.equals(course.getDescription(), description)) {
			course.setDescription(description);		
			}
		if( creditHours != null && creditHours > 0 && !Objects.equals(course.getCreditHours(), creditHours)) {
			course.setCreditHours(creditHours);		
			}
	}
	

}
