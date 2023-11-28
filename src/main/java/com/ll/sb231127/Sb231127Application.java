package com.ll.sb231127;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Sb231127Application {

    public static void main(String[] args) {
        SpringApplication.run(Sb231127Application.class, args);
    }

}
