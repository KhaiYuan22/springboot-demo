package com.example.demo.course;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
	    name = "course",
	    uniqueConstraints = @UniqueConstraint(columnNames = "course_name")
	)
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long courseId;
	@Column(unique = true)
	private String courseName;
	private String description;
	private Integer creditHours;
	
	public Course() {
		super();
	}

	public Course(String courseName, String description, Integer creditHours) {
		super();
		this.courseName = courseName;
		this.description = description;
		this.creditHours = creditHours;
	}

	public Course(long courseId, String courseName, String description, Integer creditHours) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.description = description;
		this.creditHours = creditHours;
	}

	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCreditHours() {
		return creditHours;
	}

	public void setCreditHours(int creditHours) {
		this.creditHours = creditHours;
	}

	@Override
	public String toString() {
		return "Course [courseId=" + courseId + ", courseName=" + courseName + ", description=" + description
				+ ", creditHours=" + creditHours + "]";
	}

	
}
