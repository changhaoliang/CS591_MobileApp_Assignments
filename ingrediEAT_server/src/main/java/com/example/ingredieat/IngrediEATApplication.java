package com.example.ingredieat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class IngrediEATApplication {

    public static void main(String[] args) {
        SpringApplication.run(IngrediEATApplication.class, args);
    }

}
