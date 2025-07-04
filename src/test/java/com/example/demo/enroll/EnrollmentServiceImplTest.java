package com.example.demo.enroll;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.demo.course.Course;
import com.example.demo.course.CourseRepository;
import com.example.demo.student.Student;
import com.example.demo.student.StudentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

class EnrollmentServiceImplTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private EnrollmentServiceImpl enrollmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addEnrollment_shouldSaveEnrollment_whenNotAlreadyEnrolled() {
        // Arrange
        Student student = new Student();
        student.setId(1L);
        Course course = new Course();
        course.setCourseId(2L);

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findById(2L)).thenReturn(Optional.of(course));
        when(enrollmentRepository.findByStudentAndCourse(student, course)).thenReturn(Optional.empty());

        // Act
        enrollmentService.addEnrollment(enrollment);

        // Assert
        verify(enrollmentRepository).save(enrollment);
    }

    @Test
    void addEnrollment_shouldThrowException_whenAlreadyEnrolled() {
        // Arrange
        Student student = new Student();
        student.setId(1L);
        Course course = new Course();
        course.setCourseId(2L);

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findById(2L)).thenReturn(Optional.of(course));
        when(enrollmentRepository.findByStudentAndCourse(student, course)).thenReturn(Optional.of(new Enrollment()));

        // Act & Assert
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            enrollmentService.addEnrollment(enrollment);
        });
        assertEquals("Student already enrolled in this course.", ex.getMessage());
    }

    @Test
    void deleteEnrollment_shouldDelete_whenExists() {
        when(enrollmentRepository.existsById(1L)).thenReturn(true);

        enrollmentService.deleteEnrollment(1L);

        verify(enrollmentRepository).deleteById(1L);
    }

    @Test
    void deleteEnrollment_shouldThrow_whenNotExists() {
        when(enrollmentRepository.existsById(1L)).thenReturn(false);

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            enrollmentService.deleteEnrollment(1L);
        });

        assertTrue(ex.getMessage().contains("not exists"));
    }
}
