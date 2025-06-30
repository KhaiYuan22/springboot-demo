package com.example.demo.enroll;

import java.time.LocalDateTime;

import com.example.demo.course.Course;
import com.example.demo.student.Student;

import jakarta.persistence.*;

@Entity
@Table(
		  uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_id"})
		)
public class Enrollment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "student_id")
	private Student student;
	
	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;
	
	private LocalDateTime enrollDateTime;
	
	public Enrollment() {
		super();
	}

	public Enrollment(Student student, Course course, LocalDateTime enrollDateTime) {
		super();
		this.student = student;
		this.course = course;
		this.enrollDateTime = enrollDateTime;
	}

	public Enrollment(Long id, Student student, Course course, LocalDateTime enrollDateTime) {
		super();
		this.id = id;
		this.student = student;
		this.course = course;
		this.enrollDateTime = enrollDateTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public LocalDateTime getEnrollDateTime() {
		return enrollDateTime;
	}

	public void setEnrollDateTime(LocalDateTime enrollDateTime) {
		this.enrollDateTime = enrollDateTime;
	}

	@Override
	public String toString() {
		return "Enrollment [id=" + id + ", student=" + student + ", course=" + course + ", enrollDateTime="
				+ enrollDateTime + "]";
	}

	
	
}
