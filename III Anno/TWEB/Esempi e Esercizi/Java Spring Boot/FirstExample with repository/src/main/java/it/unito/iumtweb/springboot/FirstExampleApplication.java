package it.unito.iumtweb.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "it.unito.iumtweb.springboot.character")

public class FirstExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstExampleApplication.class, args);
    }

}
