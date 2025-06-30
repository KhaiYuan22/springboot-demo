package com.example.demo.enroll;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.course.Course;
import com.example.demo.student.Student;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

	Optional<Enrollment> findByStudentAndCourse(Student student, Course course);


	List<Enrollment> findByCourse(Course course);

	List<Enrollment> findByStudent(Student student);
}
