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

	//Declaration of enroll, student, course, get them for testing
	//@mock is able to control them from repository
    @Mock
    private EnrollmentRepository enrollmentRepository;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private CourseRepository courseRepository;

    //Go into service to take action without hitting real database
    @InjectMocks
    private EnrollmentServiceImpl enrollmentService;

    //set up the mock, before run every test
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);//initialize each field(Mocks and InjectMocks) then ready for the test
    }

    //test the create if the student haven't have enroll a course(test without error)
    @Test
    void addEnrollment_shouldSaveEnrollment_whenNotAlreadyEnrolled() {
        // Declare object, then run the repository
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

        // run the service to do validation
        enrollmentService.addEnrollment(enrollment);

        // check is it save by repository
        verify(enrollmentRepository).save(enrollment);
    }

    //test the enrollment if have exist enrollment
    @Test
    void addEnrollment_shouldThrowException_whenAlreadyEnrolled() {
        // To test the exist enrollment
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

        // get the error message if have exist enrollment
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            enrollmentService.addEnrollment(enrollment);
        });
        assertEquals("Student already enrolled in this course.", ex.getMessage());
    }

    //test the delete, if the enrollment is exist
    @Test
    void deleteEnrollment_shouldDelete_whenExists() {
        when(enrollmentRepository.existsById(1L)).thenReturn(true);//get the enrollment(check at database is it exist)
        //delete the enrollment by id
        enrollmentService.deleteEnrollment(1L);
        //if have error, will get error message
        verify(enrollmentRepository).deleteById(1L);
    }

    //Test the delete for don't have the enrollment
    @Test
    void deleteEnrollment_shouldThrow_whenNotExists() {
        when(enrollmentRepository.existsById(1L)).thenReturn(false);//get the null enrollment(the enrollment id is not exist at database)

        //to get the validation message(throw it)
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            enrollmentService.deleteEnrollment(1L);
        });
        //return the error message
        assertTrue(ex.getMessage().contains("not exists"));
    }
}
