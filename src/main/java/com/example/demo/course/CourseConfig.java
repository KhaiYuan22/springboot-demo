package com.example.demo.course;



import java.util.List;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;

import com.example.demo.student.StudentRepository;

@Configuration
public class CourseConfig {

	@Bean
	CommandLineRunner commandLineRunner(CourseRepository repository) {
		return args -> {
			if (repository.count() == 0) {
			Course rit = new Course("Internet of Things", "Study internet and IoT", 20);
			Course rsd = new Course("Software Development", "Develop a software", 20);
			
			repository.saveAll(
					List.of(rit,rsd)
					);
			}
			};
	}
	
}
