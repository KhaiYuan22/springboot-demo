	package com.example.demo.course;
	
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
	
	import com.example.demo.student.StudentService;
	
	@RestController
	@RequestMapping(path = "api/v1/courses")
	public class CourseController {
	
		private final CourseService courseService;
	
		@Autowired
		public CourseController(CourseService courseService) {
			super();
			this.courseService = courseService;
		}
	
		@GetMapping
		public List<Course> getCourse() {
			return courseService.getCourse();
		}
		
		@PostMapping
		public void createNewCourse(@RequestBody Course course) {
			courseService.addNewCourse(course);
		}
	
		@DeleteMapping(path = "{courseId}")
		public void deteleCourse(@PathVariable("courseId") Long courseId) {
			courseService.deleteCourse(courseId);
		}
	
		@PutMapping(path = "/{courseId}")
		public void editCourse(
				@PathVariable("courseId") Long courseId, 
				@RequestBody Course course) {
			
		courseService.updateCourse(courseId,course);	
		}
		
		
	}
