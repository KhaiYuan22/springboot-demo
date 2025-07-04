package com.example.demo.course;


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
	public void updateCourse(Long courseId, Course newData) {
		// TODO Auto-generated method stub
		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new IllegalStateException("Course " + courseId + " not exists"));
		
		if(course.getCourseName() != null && newData.getCourseName().length() > 0 && !Objects.equals(course.getCourseName(), newData.getCourseName())) {
			Optional<Course> courseOptional = courseRepository.findCourseByCourseName(newData.getCourseName());
			if(courseOptional.isPresent()) {
				throw new IllegalStateException("duplicate course name");
			}
			course.setCourseName(newData.getCourseName());		
			}
		if(course.getDescription() != null && newData.getDescription().length() > 0 && !Objects.equals(course.getDescription(), newData.getDescription())) {
			course.setDescription(newData.getDescription());		
			}
		if( course.getCreditHours() != null && newData.getCreditHours() > 0 && !Objects.equals(course.getCreditHours(), newData.getCreditHours())) {
			course.setCreditHours(newData.getCreditHours());		
			}
	}


	

}
