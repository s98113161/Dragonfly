package com.umedia.Dragonfly.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

//@Controller
@EnableAutoConfiguration
@SpringBootApplication
public class StartApplication {
	@Bean
    public Java8TimeDialect java8TimeDialect() {
        return new Java8TimeDialect();
    }
    public static void main(String[] args) throws Exception {
        SpringApplication.run(StartApplication.class, args);
    }
}