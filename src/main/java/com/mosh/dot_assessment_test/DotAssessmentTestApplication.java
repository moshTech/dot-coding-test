package com.mosh.dot_assessment_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
@EnableCaching
@EnableScheduling
public class DotAssessmentTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(DotAssessmentTestApplication.class, args);
	}

}
