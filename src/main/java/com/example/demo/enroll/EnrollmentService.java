package com.example.demo.enroll;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.course.Course;
import com.example.demo.course.CourseRepository;
import com.example.demo.student.Student;
import com.example.demo.student.StudentRepository;



public interface EnrollmentService {
List<Enrollment> getEnrollment();
void addEnrollment(Enrollment enrollment);
void deleteEnrollment(Long enrollmentId);
void updateEnrollmentCourse(Long enrollmentId, Enrollment newEnrollment);
List<Student> getStudentByCourse(Long courseId);
List<EnrollmentReportDTO> getEnrollmentReport();
List<CourseStudentGroupDTO> getCourseStudentGroups();
}
