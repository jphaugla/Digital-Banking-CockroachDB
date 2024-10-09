package com.jphaugla;

import io.hypersistence.utils.spring.repository.BaseJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;


@EnableKafka
@Configuration
@EnableJpaRepositories(
        value = "com.jphaugla",
        repositoryBaseClass = BaseJpaRepositoryImpl.class
)
@SpringBootApplication


public class DemoApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
