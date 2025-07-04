package com.example.demo.enroll;

import java.time.LocalDateTime;

public record EnrollmentReportDTO (
	String studentName,
	String courseName,
	LocalDateTime enrollDateTime
){}
