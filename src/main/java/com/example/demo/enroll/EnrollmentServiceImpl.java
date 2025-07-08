package com.example.demo.enroll;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.course.Course;
import com.example.demo.course.CourseRepository;
import com.example.demo.student.Student;
import com.example.demo.student.StudentRepository;


@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Autowired//Auto change the variable, no need to call setter
    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository, StudentRepository studentRepository,
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
        //Get the student and course
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("Student not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalStateException("Course not found"));
        //check the student and course is match and exist
        Optional<Enrollment> enrollmentOptional =
                enrollmentRepository.findByStudentAndCourse(student, course);
        //if match, then throw error message
        if (enrollmentOptional.isPresent()) {
            throw new IllegalStateException("Student already enrolled in this course.");
        }
        //assign the student and course into enrollment, then save it(create enrollment)
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollmentRepository.save(enrollment);
    }


    public void deleteEnrollment(Long enrollmentId) {
        // TODO Auto-generated method stub
        //Find the enrollment
        enrollmentRepository.findById(enrollmentId);
        //check the enrollment is it exist
        boolean exists = enrollmentRepository.existsById(enrollmentId);
        //if not exist, then throw error message
        if (!exists) {
            throw new IllegalStateException("The enrollment record is not exists by ID" + enrollmentId);
        }
        //delete the enrollment
        enrollmentRepository.deleteById(enrollmentId);
    }


    public void updateEnrollmentCourse(Long enrollmentId, Enrollment newEnrollment) {
        // TODO Auto-generated method stub

        //get enroll id
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new IllegalStateException("Enrollment Id not found. ID: " + enrollmentId));

        //check the student is it exist
        if (newEnrollment.getStudent() != null && newEnrollment.getStudent().getId() != null) {
            Student newStudent = studentRepository.findById(newEnrollment.getStudent().getId())
                    .orElseThrow(() -> new IllegalStateException("Student ID: " + newEnrollment.getStudent().getId() + " Not Found"));
            enrollment.setStudent(newStudent);
        }

        //check the course is it exist
        if (newEnrollment.getCourse() != null && newEnrollment.getCourse().getCourseId() != null) {
            Course newCourse = courseRepository.findById(newEnrollment.getCourse().getCourseId())
                    .orElseThrow(() -> new IllegalStateException("Student ID: " + newEnrollment.getCourse().getCourseId() + " Not Found"));
            enrollment.setCourse(newCourse);
        }
        //pass to repository to find the student and course with pair(enrollment)
        Optional<Enrollment> enrollmentOptional = enrollmentRepository.findByStudentAndCourse(enrollment.getStudent(), enrollment.getCourse());

        //if both exist, means already had this enrollment, then it will not able to assign the student to this course
        if (enrollmentOptional.isPresent() &&
                !enrollmentOptional.get().getId().equals(enrollment.getId())) {
            throw new IllegalStateException("Student already enrolled in this course.");
        }

        //update to database
        enrollmentRepository.save(enrollment);
    }


    public List<Student> getStudentByCourse(Long courseId) {
        // TODO Auto-generated method stub
        Course course = courseRepository.findById(courseId)//find course
                .orElseThrow(() -> new IllegalStateException("Course ID :" + courseId + "Not found"));

        List<Enrollment> enrollments = enrollmentRepository.findByCourse(course);
        //map the enrollment and student with course
        return enrollments.stream()
                .map(Enrollment::getStudent)
                .toList();

    }


    @Override
    public List<EnrollmentReportDTO> getEnrollmentReport() {//return to Report DTO
        // TODO Auto-generated method stub
        List<Enrollment> enrollments = enrollmentRepository.findAll();//get all enrollment
        return enrollments.stream()//pass the student name, course name, enroll date time into the DTO
                .map(e -> new EnrollmentReportDTO(
                        e.getStudent().getName(),
                        e.getCourse().getCourseName(),
                        e.getEnrollDateTime()
                )).toList();
    }


    @Override
    public List<CourseStudentGroupDTO> getCourseStudentGroups() {//return to the DTO class
        List<Enrollment> enrollments = enrollmentRepository.findAll();        //get all enrollment
        long totalStudents = enrollmentRepository.count();//get the total number of enrollment(student)

        Map<String, List<String>> grouped = enrollments.stream()
                .collect(Collectors.groupingBy( //combine the course and student, make it become a <List>
                        e -> e.getCourse().getCourseName(),//get the course name
                        Collectors.mapping(e -> e.getStudent().getName(), Collectors.toList())
                ));

        return grouped.entrySet().stream()//stream every course group into a entry set(key and value pair)
                .map(e -> { //
                    int courseStudentCount = e.getValue().size();//calculate the course size(student)
                    double percentage = 0;// declare
                    if (totalStudents > 0) {//if have student
                        percentage = (courseStudentCount * 100.0) / totalStudents;//calculate the student in percentage
                    }
                    return new CourseStudentGroupDTO(e.getKey(), e.getValue(), percentage, courseStudentCount); //return to new DTO class
                })
                .toList();//collect all object as list and return to DTO
    }

}
