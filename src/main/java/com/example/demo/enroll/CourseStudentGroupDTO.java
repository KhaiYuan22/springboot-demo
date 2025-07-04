package com.example.demo.enroll;

import java.util.List;

public record CourseStudentGroupDTO (
	String courseName,
	List <String> studentName,
		
	double percentage,
	long totalStudents
){}
