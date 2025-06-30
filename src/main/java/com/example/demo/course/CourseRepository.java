package com.example.demo.course;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.*;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

	@Query("SELECT c FROM Course c WHERE c.courseName = ?1 ")
	Optional<Course> findCourseByCourseName(String courseName);

}
