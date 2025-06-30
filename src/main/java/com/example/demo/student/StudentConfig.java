package com.example.demo.student;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfig {

	@Bean
	CommandLineRunner commandLineRuner(StudentRepository repository) {
		return args -> {
			if (repository.count() == 0) {
			Student stud1 = new Student("stud1", "s1@gmail.com", LocalDate.of(2002, 10, 10));
			Student stud2 = new Student("stud2", "s2@gmail.com", LocalDate.of(2012, 12, 28));
			
			repository.saveAll(
					List.of(stud1,stud2)
					);
			}
		};
	}
}
