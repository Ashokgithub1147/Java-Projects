package com.exam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExamportalserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamportalserverApplication.class, args);
	}
	@Bean
    Logger logger() {
        return LoggerFactory.getLogger("com.exam.MyLogger");
    }
}
